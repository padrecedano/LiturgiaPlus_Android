package org.deiverbum.app.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import com.android.volley.RequestQueue;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.deiverbum.app.R;
import org.deiverbum.app.model.Breviario;
import org.deiverbum.app.model.Completas;
import org.deiverbum.app.model.Conclusion;
import org.deiverbum.app.model.Himno;
import org.deiverbum.app.model.Kyrie;
import org.deiverbum.app.model.LecturaBreve;
import org.deiverbum.app.model.Liturgia;
import org.deiverbum.app.model.MetaLiturgia;
import org.deiverbum.app.model.NuncDimitis;
import org.deiverbum.app.model.RitosIniciales;
import org.deiverbum.app.model.Salmodia;
import org.deiverbum.app.utils.TTS;
import org.deiverbum.app.utils.Utils;
import org.deiverbum.app.utils.ZoomTextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.deiverbum.app.utils.Constants.CALENDAR_PATH;
import static org.deiverbum.app.utils.Constants.PACIENCIA;
import static org.deiverbum.app.utils.Constants.SEPARADOR;
import static org.deiverbum.app.utils.Utils.LS2;

public class CompletasActivity extends AppCompatActivity {
    private static final String TAG = "CompletasActivity";
    Spanned strContenido;
    ZoomTextView mTextView;
    private RequestQueue requestQueue;
    private String strFechaHoy;
    private TTS tts;
    private StringBuilder sbReader;
    private Breviario mBreviario;
    private ProgressBar progressBar;
    private Menu menu;
    private Completas mCompletas;
    private Liturgia mLiturgia;
    private int weekDay;
    private boolean isVoiceOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completas);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTextView = findViewById(R.id.tv_Zoomable);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        float fontSize = Float.parseFloat(prefs.getString("font_size", "18"));
        isVoiceOn = prefs.getBoolean("voice", true);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        progressBar = findViewById(R.id.progressBar);
        mTextView.setText(Utils.fromHtml(PACIENCIA));
        strFechaHoy = (getIntent().getExtras() != null) ? getIntent().getStringExtra("FECHA") : Utils.getHoy();
        launchFirestore();
    }


    public void launchFirestore() {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        String fechaDD = strFechaHoy.substring(6, 8);
        String fechaMM = strFechaHoy.substring(4, 6);
        String fechaYY = strFechaHoy.substring(0, 4);
        DocumentReference calRef = db.collection(CALENDAR_PATH).document(fechaYY).collection(fechaMM).document(fechaDD);
        calRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        MetaLiturgia m = document.get("meta", MetaLiturgia.class);
                        weekDay = m.getweekDay();
                    }
                }
                showData();
            }
        });
    }

    private void getDataFromJson() {
        String filePath = "res/raw/completas_" + weekDay + ".json";
        InputStream raw = getClass().getClassLoader().getResourceAsStream(filePath);
        Reader rd = new BufferedReader(new InputStreamReader(raw));
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(rd, JsonObject.class);
        JsonObject mJson = jsonObject.getAsJsonObject("breviario");
        mBreviario = gson.fromJson(mJson, Breviario.class);
        showData();
    }

    private void showData() {
        if (weekDay == 0) {
            Calendar c = Calendar.getInstance();
            DateFormat df = new SimpleDateFormat("yyyyMMdd");
            try {
                c.setTime(df.parse(strFechaHoy));
                weekDay = c.get(Calendar.DAY_OF_WEEK);
            } catch (ParseException e) {
            }
        }
        String filePath = "res/raw/completas_" + weekDay + ".json";
        Log.d(TAG, filePath);
        InputStream raw = getClass().getClassLoader().getResourceAsStream(filePath);
        Reader rd = new BufferedReader(new InputStreamReader(raw));
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(rd, JsonObject.class);
        JsonObject mJson = jsonObject.getAsJsonObject("breviario");
        mBreviario = gson.fromJson(mJson, Breviario.class);

        SpannableStringBuilder sb = new SpannableStringBuilder();
        sbReader = new StringBuilder();
        Completas mCompletas = mBreviario.getCompletas();
        RitosIniciales ri = mCompletas.getRitosIniciales();
        Kyrie kyrie = ri.getKyrie();
        Himno himno = mCompletas.getHimno();
        Salmodia salmodia = mCompletas.getSalmodia();
        LecturaBreve lecturaBreve = mCompletas.getLecturaBreve();
        NuncDimitis nunc = mCompletas.getNuncDimitis();
        Conclusion conclusion = mCompletas.getConclusion();
        sb.append(Utils.toH3Red("COMPLETAS"));
        sb.append(Utils.LS2);
        sb.append(mBreviario.getInvocacion());
        sb.append(Utils.LS2);
        sb.append(kyrie.getIntroduccion());
        sb.append(Utils.LS2);
        sb.append(kyrie.getTexto());
        sb.append(Utils.LS2);
        sb.append(kyrie.getConclusion());
        sb.append(Utils.LS2);
        sb.append(himno.getHeader());
        sb.append(Utils.LS2);
        sb.append(himno.getTextoSpan());
        sb.append(Utils.LS2);
        sb.append(salmodia.getHeader());
        sb.append(Utils.LS2);
        sb.append(salmodia.getSalmoCompleto());
        sb.append(Utils.LS);
        sb.append(lecturaBreve.getHeaderLectura());
        sb.append(Utils.LS2);
        sb.append(lecturaBreve.getTexto());
        sb.append(Utils.LS2);
        sb.append(lecturaBreve.getHeaderResponsorio());
        sb.append(Utils.LS2);
        sb.append(lecturaBreve.getResponsorioSpan());
        sb.append(Utils.LS2);
        sb.append(nunc.getHeader());
        sb.append(Utils.LS2);
        sb.append(nunc.getAntifona());
        sb.append(Utils.LS2);
        sb.append(Utils.fromHtml(nunc.getTexto()));
        sb.append(Utils.LS2);
        sb.append(Utils.getFinSalmo());
        sb.append(LS2);
        sb.append(Utils.formatTitle("ORACIÓN"));
        sb.append(LS2);
        sb.append(Utils.fromHtml(mCompletas.getOracion()));
        sb.append(LS2);
        sb.append(Utils.formatTitle("CONCLUSIÓN"));
        sb.append(LS2);
        sb.append(conclusion.getBendicion());
        sb.append(LS2);
        sb.append(Utils.formatTitle("ANTÍFONA FINAL DE LA SANTÍSIMA VIRGEN"));
        sb.append(LS2);
        sb.append(conclusion.getAntVirgenSpan());

        if (isVoiceOn) {
            sbReader = new StringBuilder();
            sbReader.append(Utils.fromHtml("<p>COMPLETAS.</p>"));
            sbReader.append(Utils.LS);
            sbReader.append(mBreviario.getInvocacionForRead());
            sbReader.append(Utils.LS);
            sbReader.append(Utils.LS);
            sbReader.append(kyrie.getIntroduccionForRead());
            sbReader.append(kyrie.getTextoForRead());
            sbReader.append(kyrie.getConclusionForRead());
            sbReader.append(SEPARADOR);
            sbReader.append(Utils.fromHtml("<p>HIMNO.</p>"));
            sbReader.append(himno.getTexto());
            sbReader.append(SEPARADOR);
            sbReader.append(salmodia.getHeaderForRead());
            sbReader.append(salmodia.getSalmoCompletoForRead());
            sbReader.append(Utils.fromHtml("<p>LECTURA BREVE.</p>"));
            sbReader.append(lecturaBreve.getTexto());
            sbReader.append(Utils.fromHtml("<p>RESPONSORIO.</p>"));
            sbReader.append(lecturaBreve.getResponsorioForRead());
            sbReader.append(SEPARADOR);
            sbReader.append(nunc.getHeader());
            sbReader.append(nunc.getAntifonaForRead());
            sbReader.append(nunc.getTexto());
            sbReader.append(Utils.getFinSalmo());
            sbReader.append(SEPARADOR);
            sbReader.append(Utils.fromHtml("<p>ORACIÓN.</p>"));
            sbReader.append(mCompletas.getOracion());
            sbReader.append(conclusion.getBendicionForRead());
            sbReader.append(Utils.fromHtml("<p>ANTÍFONA FINAL DE LA SANTÍSIMA VIRGEN.</p>"));
            sbReader.append(conclusion.getAntVirgen());
        }
        mTextView.setText(sb, TextView.BufferType.SPANNABLE);
        progressBar.setVisibility(View.INVISIBLE);
        /*
        if (sbReader.length()>0) {
            menu.findItem(R.id.item_voz).setVisible(true);
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        //getDataFromJson();
        menu.findItem(R.id.item_voz).setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                if (tts != null) {
                    tts.cerrar();
                }
                NavUtils.navigateUpFromSameTask(this);
                return true;

            case R.id.item_voz:
                String html = String.valueOf(Utils.fromHtml(sbReader.toString()));
                String[] textParts = html.split(SEPARADOR);
                tts = new TTS(getApplicationContext(), textParts);
                return true;

            case R.id.item_calendario:
                Intent i = new Intent(this, CalendarioActivity.class);
                startActivity(i);
                return true;

            case R.id.item_settings:
                i = new Intent(this, SettingsActivity.class);
                startActivity(i);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (tts != null) {
            tts.cerrar();
        }
    }
}
