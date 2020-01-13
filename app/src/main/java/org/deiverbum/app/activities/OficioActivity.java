package org.deiverbum.app.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
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
import org.deiverbum.app.model.Biblica;
import org.deiverbum.app.model.Breviario;
import org.deiverbum.app.model.Himno;
import org.deiverbum.app.model.Invitatorio;
import org.deiverbum.app.model.Liturgia;
import org.deiverbum.app.model.MetaLiturgia;
import org.deiverbum.app.model.Oficio;
import org.deiverbum.app.model.OficioLecturas;
import org.deiverbum.app.model.Patristica;
import org.deiverbum.app.model.Salmodia;
import org.deiverbum.app.model.TeDeum;
import org.deiverbum.app.utils.TTS;
import org.deiverbum.app.utils.Utils;
import org.deiverbum.app.utils.VolleyErrorHelper;
import org.deiverbum.app.utils.ZoomTextView;
import org.json.JSONException;
import org.json.JSONObject;

import static org.deiverbum.app.utils.Constants.BR;
import static org.deiverbum.app.utils.Constants.CALENDAR_PATH;
import static org.deiverbum.app.utils.Constants.MY_DEFAULT_TIMEOUT;
import static org.deiverbum.app.utils.Constants.OL_URL;
import static org.deiverbum.app.utils.Constants.PACIENCIA;
import static org.deiverbum.app.utils.Constants.SEPARADOR;
import static org.deiverbum.app.utils.Utils.LS2;

public class OficioActivity extends AppCompatActivity {
    private static final String TAG = "OficioActivity";
    Spanned strContenido;
    JsonObjectRequest jsonObjectRequest;
    ZoomTextView mTextView;
    private RequestQueue requestQueue;
    private String strFechaHoy;
    private TTS tts;
    private int idInvitatorio = 1;
    private boolean isInvitatorio;
    private StringBuilder sbReader;
    private ProgressBar progressBar;
    private Liturgia mLiturgia;
    private Menu menu;
    private boolean isVoiceOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oficio);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTextView = findViewById(R.id.tv_Zoomable);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        float fontSize = Float.parseFloat(prefs.getString("font_size", "18"));
        isInvitatorio = prefs.getBoolean("invitatorio", false);
        isVoiceOn = prefs.getBoolean("voice", true);
        strFechaHoy = (getIntent().getExtras() != null) ? getIntent().getStringExtra("FECHA") : Utils.getHoy();
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        progressBar = findViewById(R.id.progressBar);
        mTextView.setText(Utils.fromHtml(PACIENCIA));
        progressBar.setVisibility(View.VISIBLE);
        launchFirestore();
        //launchVolley();

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
                DocumentReference dataRef = calSnapshot.getDocumentReference("lh.1");
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
                Request.Method.GET, OL_URL + strFechaHoy, null,
                response -> {
                    try {
                        Gson gson = new Gson();
                        String jsonBreviario = String.valueOf(new JSONObject(String.valueOf(response.getJSONObject("liturgia"))));
                        mLiturgia = gson.fromJson(jsonBreviario, Liturgia.class);
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

    protected void showData() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        MetaLiturgia mMeta = mLiturgia.getMetaLiturgia();
        Breviario mBreviario = mLiturgia.getBreviario();
        Oficio oficio = mBreviario.getOficio();
        Invitatorio invitatorio = oficio.getInvitatorio();
        idInvitatorio = (isInvitatorio) ? invitatorio.getId() : 1;
        invitatorio.setId(idInvitatorio);
        SpannableStringBuilder titleInvitatorio = Utils.formatSubTitle("invitatorio");
        String ant = getString(R.string.ant);

        Himno himno = oficio.getHimno();
        Salmodia salmodia = oficio.getSalmodia();
        OficioLecturas lecturasOficio = oficio.getOficioLecturas();
        Patristica patristica = lecturasOficio.getPatristica();
        Biblica biblica = lecturasOficio.getBiblica();
        TeDeum teDeum = oficio.getTeDeum();

        sb.append(mMeta.getFecha());
        sb.append(Utils.LS2);
        sb.append(Utils.toH2(mMeta.getTiempoNombre()));
        sb.append(Utils.LS2);
        sb.append(Utils.toH3(mLiturgia.getTitulo()));
        sb.append(mLiturgia.getVida());
        sb.append(Utils.toH3Red(oficio.getTituloHora()));
        sb.append(Utils.fromHtmlToSmallRed(mBreviario.getMetaInfo()));

        sb.append(Utils.LS2);
        sb.append(Utils.getSaludoOficio());
        sb.append(Utils.LS2);
        sb.append(titleInvitatorio);
        sb.append(Utils.LS2);
        sb.append(Utils.fromHtml(ant));
        sb.append(invitatorio.getAntifona());
        sb.append(Utils.LS2);
        sb.append(invitatorio.getTextoSpan());
        sb.append(Utils.LS2);
        sb.append(Utils.getFinSalmo());
        sb.append(Utils.LS2);
        sb.append(Utils.fromHtml(ant));
        sb.append(invitatorio.getAntifona());
        sb.append(Utils.LS2);

        sb.append(himno.getHeader());
        sb.append(Utils.LS2);
        sb.append(himno.getTextoSpan());
        sb.append(Utils.LS2);

        sb.append(salmodia.getHeader());
        sb.append(Utils.LS2);
        sb.append(salmodia.getSalmoCompleto());
        sb.append(Utils.LS);
        sb.append(Utils.formatSubTitle("lecturas del oficio"));
        sb.append(Utils.LS2);
        sb.append(lecturasOficio.getResponsorioSpan());
        sb.append(Utils.LS2);
        sb.append(biblica.getHeader());
        sb.append(Utils.LS2);
        sb.append(biblica.getLibro());
        sb.append("    ");

        sb.append(Utils.toRed(biblica.getCapitulo()));
        sb.append(", ");
        sb.append(Utils.toRed(biblica.getVersoInicial()));
        sb.append(Utils.toRed(biblica.getVersoFinal()));
        sb.append(Utils.LS2);
        sb.append(Utils.toRed(biblica.getTema()));
        sb.append(Utils.LS2);
        sb.append(biblica.getTextoSpan());
        sb.append(Utils.LS);
        sb.append(Utils.toRed("Responsorio    "));
        sb.append(Utils.toRed(biblica.getRef()));
        sb.append(Utils.LS2);
        sb.append(biblica.getResponsorio());
        sb.append(Utils.LS2);
        sb.append(Utils.LS);

        sb.append(patristica.getHeader());
        sb.append(Utils.LS2);
        sb.append(patristica.padre);
        sb.append(", ");
        sb.append(patristica.obra);
        sb.append(Utils.LS);
        sb.append(Utils.toSmallSizeRed(patristica.fuente));
        sb.append(Utils.LS2);
        sb.append(Utils.toRed(patristica.tema));
        sb.append(Utils.LS2);
        sb.append(patristica.getTextoSpan());
        sb.append(Utils.LS);
        sb.append(Utils.toRed("Responsorio    "));
        sb.append(Utils.toRed(patristica.ref));
        sb.append(Utils.LS2);
        sb.append(patristica.getResponsorioSpan());
        sb.append(Utils.LS2);

        sb.append(teDeum.getTextoSpan());
        sb.append(Utils.formatTitle("ORACIÓN"));
        sb.append(LS2);

        sb.append(oficio.getOracionSpan());
        sb.append(LS2);
        sb.append(Utils.getConclusionHorasMayores());

        if (isVoiceOn) {
            sbReader = new StringBuilder();
            sbReader.append(Utils.fromHtml("<p>" + mMeta.getFecha() + ".</p>"));
            sbReader.append(mLiturgia.getTitulo() + "." + BR);
            sbReader.append(mLiturgia.getVida() + BR);
            sbReader.append(Utils.fromHtml("<p>" + oficio.getTituloHora() + ".</p>"));
            sbReader.append(Utils.getSaludoOficioForReader());
            sbReader.append(Utils.fromHtml("<p>Invitatorio.</p>"));
            sbReader.append(invitatorio.getAntifonaForRead());
            sbReader.append(invitatorio.getTextoSpan());
            sbReader.append(Utils.getFinSalmoForRead());
            sbReader.append(invitatorio.getAntifonaForRead());
            sbReader.append(himno.getHeaderForRead());
            sbReader.append(himno.getTexto());
            sbReader.append(salmodia.getHeaderForRead());
            sbReader.append(salmodia.getSalmoCompletoForRead());
            sbReader.append("<p>Lecturas del oficio.</p>");
            sbReader.append(lecturasOficio.getResponsorioForRead());
            sbReader.append("PRIMERA LECTURA.");
            sbReader.append(biblica.getLibro() + ".");
            sbReader.append(biblica.getTema() + ".");
            sbReader.append(biblica.getTexto());
            sbReader.append(Utils.fromHtml("<p>Palabra de Dios.</p>"));
            sbReader.append(Utils.fromHtml("<p>Responsorio.</p>"));
            sbReader.append(biblica.getResponsorioForReader());
            sbReader.append("SEGUNDA LECTURA.");
            sbReader.append(patristica.padre + ".");
            sbReader.append(", ");
            sbReader.append(patristica.obra + ".");
            sbReader.append(patristica.tema + ".");
            sbReader.append(patristica.getTexto());
            sbReader.append(patristica.padre + ".");
            sbReader.append(Utils.fromHtml("<p>Responsorio.</p>"));
            sbReader.append(patristica.getResponsorioForReader());
            sbReader.append(teDeum.getTextoSpan());
            sbReader.append(Utils.fromHtml("<p>Oración.</p><br />"));
            sbReader.append(oficio.getOracion());
            sbReader.append(Utils.getConclusionHorasMayoresForRead());

            if (sbReader.length() > 0) {
                menu.findItem(R.id.item_voz).setVisible(true);
            }
        }
        progressBar.setVisibility(View.INVISIBLE);
        mTextView.setText(sb, TextView.BufferType.SPANNABLE);
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
                String[] strPrimera = html.split(SEPARADOR);
                tts = new TTS(getApplicationContext(), strPrimera);
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


