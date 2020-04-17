package org.deiverbum.app.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.SpannableStringBuilder;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
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
import org.deiverbum.app.model.Himno;
import org.deiverbum.app.model.Intermedia;
import org.deiverbum.app.model.LecturaBreve;
import org.deiverbum.app.model.Liturgia;
import org.deiverbum.app.model.MetaLiturgia;
import org.deiverbum.app.model.Salmodia;
import org.deiverbum.app.utils.TTS;
import org.deiverbum.app.utils.Utils;
import org.deiverbum.app.utils.VolleyErrorHelper;
import org.deiverbum.app.utils.ZoomTextView;

import static org.deiverbum.app.utils.Constants.CALENDAR_PATH;
import static org.deiverbum.app.utils.Constants.MY_DEFAULT_TIMEOUT;
import static org.deiverbum.app.utils.Constants.PACIENCIA;
import static org.deiverbum.app.utils.Constants.SCREEN_TIME_OFF;
import static org.deiverbum.app.utils.Constants.SEPARADOR;
import static org.deiverbum.app.utils.Constants.URL_TERCIA;
import static org.deiverbum.app.utils.Utils.LS;
import static org.deiverbum.app.utils.Utils.LS2;

public class TerciaActivity extends AppCompatActivity {
    private static final String TAG = "TerciaActivity";
    JsonObjectRequest jsonObjectRequest;
    private ZoomTextView mTextView;
    private RequestQueue requestQueue;
    private String strFechaHoy;
    private TTS tts;
    private ProgressBar progressBar;
    private Liturgia mLiturgia;
    private StringBuilder sbReader;
    private Menu menu;
    private boolean isVoiceOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tercia);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTextView = findViewById(R.id.tv_Zoomable);
        strFechaHoy = (getIntent().getExtras() != null) ? getIntent().getStringExtra("FECHA") : Utils.getHoy();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        float fontSize = Float.parseFloat(prefs.getString("font_size", "18"));
        isVoiceOn = prefs.getBoolean("voice", true);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        progressBar = findViewById(R.id.progressBar);
        mTextView.setText(Utils.fromHtml(PACIENCIA));
        progressBar.setVisibility(View.VISIBLE);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        };
        handler.postDelayed(r, SCREEN_TIME_OFF);
        launchFirestore();

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
                DocumentReference dataRef = calSnapshot.getDocumentReference("lh.3");
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
        requestQueue = Volley.newRequestQueue(this);
        jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, URL_TERCIA + strFechaHoy, null,
                response -> {
                    try {
                        Gson gson = new Gson();
                        mLiturgia = gson.fromJson(response.getJSONObject("liturgia").toString(), Liturgia.class);
                        showData();
                    } catch (Exception e) {
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

    protected void showData() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        Breviario mBreviario = mLiturgia.getBreviario();
        Intermedia hi = mBreviario.getIntermedia();
        MetaLiturgia mMeta = mLiturgia.getMetaLiturgia();
        Himno himno = hi.getHimno();
        Salmodia salmodia = hi.getSalmodia();
        LecturaBreve lecturaBreve = hi.getLecturaBreve();
        String tituloHora = "HORA INTERMEDIA: TERCIA";

        sb.append(mMeta.getFecha());
        sb.append(Utils.LS2);
        sb.append(Utils.toH2(mMeta.getTiempoNombre()));
        sb.append(Utils.LS2);
        sb.append(Utils.toH3(mLiturgia.getTitulo()));
        sb.append(Utils.toH3Red(tituloHora));
        sb.append(Utils.fromHtmlToSmallRed(mBreviario.getMetaInfo()));

        sb.append(LS2);
        sb.append(Utils.getSaludoDiosMio());
        sb.append(LS2);

        sb.append(himno.getHeader());
        sb.append(LS2);
        sb.append(himno.getTextoSpan());
        sb.append(LS2);

        sb.append(salmodia.getHeader());
        sb.append(LS2);
        sb.append(salmodia.getSalmoCompleto(0));

        sb.append(LS);
        sb.append(lecturaBreve.getHeaderLectura());
        sb.append(LS2);
        sb.append(lecturaBreve.getTexto());
        sb.append(LS2);
        sb.append(lecturaBreve.getResponsorioSpan());
        sb.append(LS);
        sb.append(Utils.formatTitle("ORACIÓN"));
        sb.append(LS2);
        sb.append(Utils.fromHtml(hi.getOracion()));
        sb.append(LS2);
        sb.append(Utils.getConclusionIntermedia());

        if (isVoiceOn) {
            sbReader = new StringBuilder();
            sbReader.append(Utils.fromHtml("<p>" + mMeta.getFecha() + ".</p>"));
            sbReader.append(Utils.fromHtml("<p>" + tituloHora + ".</p>"));
            sbReader.append(Utils.getSaludoDiosMioForReader());
            sbReader.append(himno.getHeaderForRead());
            sbReader.append(himno.getTexto());
            sbReader.append(salmodia.getHeaderForRead());
            sbReader.append(salmodia.getSalmoCompletoForRead(0));
            sbReader.append(lecturaBreve.getHeaderForRead());
            sbReader.append(lecturaBreve.getTexto());
            sbReader.append(lecturaBreve.getHeaderResponsorioForRead());
            sbReader.append(lecturaBreve.getResponsorioForRead());
            sbReader.append(Utils.fromHtml("<p>Oración.</p>"));
            sbReader.append(Utils.fromHtml(hi.getOracion()));
            sbReader.append(Utils.getConclusionIntermediaForRead());
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
