package org.deiverbum.app.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableStringBuilder;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import org.deiverbum.app.R;
import org.deiverbum.app.model.Breviario;
import org.deiverbum.app.model.CanticoEvangelico;
import org.deiverbum.app.model.Himno;
import org.deiverbum.app.model.LecturaBreve;
import org.deiverbum.app.model.Liturgia;
import org.deiverbum.app.model.MetaLiturgia;
import org.deiverbum.app.model.Preces;
import org.deiverbum.app.model.Salmodia;
import org.deiverbum.app.model.Santo;
import org.deiverbum.app.model.Visperas;
import org.deiverbum.app.utils.TTS;
import org.deiverbum.app.utils.Utils;
import org.deiverbum.app.utils.VolleyErrorHelper;
import org.deiverbum.app.utils.ZoomTextView;
import org.json.JSONException;
import org.json.JSONObject;

import static org.deiverbum.app.utils.Constants.BR;
import static org.deiverbum.app.utils.Constants.CALENDAR_PATH;
import static org.deiverbum.app.utils.Constants.MY_DEFAULT_TIMEOUT;
import static org.deiverbum.app.utils.Constants.PACIENCIA;
import static org.deiverbum.app.utils.Constants.SEPARADOR;
import static org.deiverbum.app.utils.Constants.URL_VISPERAS;
import static org.deiverbum.app.utils.Utils.LS2;

public class VisperasActivity extends AppCompatActivity {
    private static final String TAG = "VisperasActivity";
    JsonObjectRequest jsonObjectRequest;
    ZoomTextView mTextView;
    private RequestQueue requestQueue;
    private String strFechaHoy;
    private TTS tts;
    private StringBuilder sbReader;
    private Liturgia mLiturgia;
    private ProgressBar progressBar;
    private Menu menu;
    private boolean isVoiceOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visperas);
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
        //launchFirestore();
    }

    public void launchFirestore() {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        String fechaDD = strFechaHoy.substring(6, 8);
        String fechaMM = strFechaHoy.substring(4, 6);
        String fechaYY = strFechaHoy.substring(0, 4);
        DocumentReference calRef = db.collection(CALENDAR_PATH).document(fechaYY).collection(fechaMM).document(fechaDD);
        calRef.addSnapshotListener((calSnapshot, e) -> {
            if ((calSnapshot != null) && calSnapshot.exists()) {
                mLiturgia = calSnapshot.toObject(Liturgia.class);
                DocumentReference dataRef = calSnapshot.getDocumentReference("lh.6");
                if (e != null || dataRef == null) {
                    launchVolley();
                    return;
                }
                dataRef.get().addOnSuccessListener((DocumentSnapshot dataSnapshot) -> {
                    mLiturgia.setBreviario(dataSnapshot.toObject(Breviario.class));
                    showData();
                });
            } else {
                launchVolley();
            }
        });
    }

    public void launchVolley() {
        //strFechaHoy="20200104/?";
        requestQueue = Volley.newRequestQueue(this);
        jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, URL_VISPERAS + strFechaHoy, null,
                response -> {
                    try {
                        Gson gson = new Gson();
                        JSONObject mJson = response.getJSONObject("liturgia");
                        mLiturgia = gson.fromJson(mJson.toString(), Liturgia.class);
                        showData();
                    } catch (JSONException e) {
                        mTextView.setText(e.getMessage());
                    }
                },
                error -> {
                    String sError = VolleyErrorHelper.getMessage(error, getApplicationContext());
                    mTextView.setText(Utils.fromHtml(sError));
                    progressBar.setVisibility(View.INVISIBLE);
                }
        );

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
        progressBar.setVisibility(View.VISIBLE);
    }


    private void showData() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        MetaLiturgia mMeta = mLiturgia.getMetaLiturgia();
        Breviario mBreviario = mLiturgia.getBreviario();
        Santo santo = mBreviario.getSanto();
        Visperas visperas = mBreviario.getVisperas();
        Himno himno = visperas.getHimno();
        Salmodia salmodia = visperas.getSalmodia();
        LecturaBreve lecturaBreve = visperas.getLecturaBreve();
        CanticoEvangelico ce = visperas.getCanticoEvangelico();
        Preces preces = visperas.getPreces();
        //CharSequence santoNombre = (santo.getNombre().equals("")) ? "" : Utils.toH3(santo.getNombre() + LS2);
        String hora = (mMeta.getIVisperasTime() == 0) ? "VÍSPERAS" : "I VÍSPERAS";

        sb.append(mMeta.getFecha());
        sb.append(Utils.LS2);
        sb.append(Utils.toH2(mMeta.getTiempoNombre()));
        sb.append(Utils.LS2);
        sb.append(Utils.toH3(mLiturgia.getTitulo()));
        sb.append(Utils.toH3Red(hora));
        sb.append(Utils.fromHtmlToSmallRed(mBreviario.getMetaInfo()));

        sb.append(Utils.LS2);
        sb.append(Utils.getSaludoDiosMio());
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
        sb.append(ce.getHeader());
        sb.append(Utils.LS2);
        sb.append(ce.getAntifona());
        sb.append(Utils.LS2);
        sb.append(ce.getMagnificat());
        sb.append(Utils.LS2);
        sb.append(Utils.getFinSalmo());
        sb.append(Utils.LS2);
        sb.append(ce.getAntifona());
        sb.append(Utils.LS2);
        sb.append(Utils.LS);
        sb.append(preces.getHeader());
        sb.append(LS2);
        sb.append(preces.getPreces());
        sb.append(LS2);
        sb.append(Utils.LS);
        sb.append(Utils.formatTitle("PADRE NUESTRO"));
        sb.append(LS2);
        sb.append(Utils.getPadreNuestro());
        sb.append(LS2);
        sb.append(Utils.LS);
        sb.append(Utils.formatTitle("ORACIÓN"));
        sb.append(LS2);
        sb.append(Utils.fromHtml(visperas.getOracion()));
        sb.append(LS2);
        sb.append(Utils.getConclusionHorasMayores());
        if (isVoiceOn) {
            sbReader = new StringBuilder();
            sbReader.append(Utils.fromHtml("<p>" + mMeta.getFecha() + ".</p>"));
            sbReader.append(mLiturgia.getTitulo() + "." + BR);
            sbReader.append(Utils.fromHtml("<p>" + hora + ".</p>"));
            sbReader.append(Utils.getSaludoDiosMioForReader());
            sbReader.append(himno.getHeaderForRead());
            sbReader.append(himno.getTexto());
            sbReader.append(salmodia.getHeaderForRead());
            sbReader.append(salmodia.getSalmoCompletoForRead());
            sbReader.append(lecturaBreve.getHeaderForRead());
            sbReader.append(lecturaBreve.getTexto());
            sbReader.append(lecturaBreve.getHeaderResponsorioForRead());
            sbReader.append(lecturaBreve.getResponsorioForRead());
            sbReader.append(Utils.fromHtml("<p>Cántico evangélico.</p><br />"));
            sbReader.append(ce.getAntifonaForRead());
            sbReader.append(ce.getMagnificat());
            sbReader.append(Utils.getFinSalmoForRead());
            sbReader.append(ce.getAntifonaForRead());
            sbReader.append(Utils.fromHtml("<p>Preces.</p><br />"));
            sbReader.append(preces.getPreces());
            sbReader.append(Utils.getPadreNuestro());
            sbReader.append(Utils.fromHtml("<p>Oración.</p><br />"));
            sbReader.append(visperas.getOracion());
            sbReader.append(Utils.getConclusionHorasMayoresForRead());

            if (sbReader.length() > 0) {
                menu.findItem(R.id.item_voz).setVisible(true);
            }
        }
        mTextView.setText(sb, TextView.BufferType.SPANNABLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        launchFirestore();
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
