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
import com.google.gson.JsonElement;

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
import org.deiverbum.app.model.Santo;
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
        strFechaHoy = (getIntent().getExtras() != null) ? getIntent().getStringExtra("FECHA") : Utils.getHoy();
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        progressBar = findViewById(R.id.progressBar);
        mTextView.setText(Utils.fromHtml(PACIENCIA));
        progressBar.setVisibility(View.VISIBLE);
        sbReader = new StringBuilder();
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
                Gson gson = new Gson();
                JsonElement jsonElement = gson.toJsonTree(calSnapshot.get("meta"));
                mLiturgia = new Liturgia();
                mLiturgia.setMetaLiturgia(gson.fromJson(jsonElement, MetaLiturgia.class));
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
        Santo santo = mBreviario.getSanto();
        Oficio oficio = mBreviario.getOficio();
        Invitatorio invitatorio = oficio.getInvitatorio();
        idInvitatorio = (isInvitatorio) ? invitatorio.getId() : 1;
        invitatorio.setId(idInvitatorio);
        Himno himno = oficio.getHimno();
        Salmodia salmodia = oficio.getSalmodia();
        OficioLecturas lecturasOficio = oficio.getOficioLecturas();
        Patristica patristica = lecturasOficio.getPatristica();
        Biblica biblica = lecturasOficio.getBiblica();
        TeDeum teDeum = oficio.getTeDeum();

        CharSequence santoNombre = (santo.getNombre().equals("")) ? "" : Utils.toH3(santo.getNombre() + LS2);
        SpannableStringBuilder titleInvitatorio = Utils.formatSubTitle("invitatorio");
        CharSequence santoVida = (santo.getVida().equals("")) ? "" : Utils.toSmallSize(santo.getVida() + Utils.LS2);
        String ant = getString(R.string.ant);
        String hora = "OFICIO DE LECTURA";

        sb.append(mMeta.getFecha());
        sb.append(Utils.LS2);
        sb.append(Utils.toH2(mMeta.getTiempoNombre()));
        sb.append(Utils.LS);
        sb.append(Utils.toH3(mMeta.getSemana()));
        sb.append(Utils.LS);

        sb.append(Utils.toH3Red(hora));
        sb.append(Utils.LS);

        sb.append(santoNombre);
        sb.append(santoVida);
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

        /*Texto para TTS*/

        sbReader.append(Utils.fromHtml("<p>" + mMeta.getFecha() + "</p>"));

        sbReader.append("OFICIO DE LECTURA." + BR);
        sbReader.append(SEPARADOR);

        sbReader.append(santo.getNombre() + "." + BR);
        sbReader.append(santo.getVida() + BR);
        sbReader.append(SEPARADOR);
        sbReader.append(SEPARADOR);
        sbReader.append(Utils.getSaludoOficioForReader());
        sbReader.append(SEPARADOR);

        sbReader.append(Utils.fromHtml("<p>Invitatorio.</p>"));
        sbReader.append(SEPARADOR);
        sbReader.append(invitatorio.getAntifonaForRead());
        sbReader.append(invitatorio.getTextoSpan());
        sbReader.append(Utils.getFinSalmoForRead());
        sbReader.append(invitatorio.getAntifonaForRead());
        sbReader.append(SEPARADOR);

        sbReader.append("HIMNO.");
        sbReader.append(SEPARADOR);
        sbReader.append(himno.getTexto());
        sbReader.append(SEPARADOR);

        sbReader.append(salmodia.getHeaderForRead());
        sbReader.append(salmodia.getSalmoCompletoForRead());

        sbReader.append("<p>Lecturas del oficio</p>");
        sbReader.append(SEPARADOR);
        sbReader.append(lecturasOficio.getResponsorioForRead());
        sbReader.append("PRIMERA LECTURA.");
        sbReader.append(SEPARADOR);
        sbReader.append(biblica.getLibro());
        sbReader.append(SEPARADOR);
        sbReader.append(biblica.getTema());

        sbReader.append(SEPARADOR);
        sbReader.append(biblica.getTexto());
        sbReader.append(SEPARADOR);
        sbReader.append("Palabra de Dios.");
        sbReader.append(SEPARADOR);

        sbReader.append("Responsorio ");
        sbReader.append(SEPARADOR);
        sbReader.append(biblica.getResponsorioForReader());
        sbReader.append(SEPARADOR);

        sbReader.append("SEGUNDA LECTURA.");
        sbReader.append(SEPARADOR);
        sbReader.append(patristica.padre);
        sbReader.append(", ");
        sbReader.append(patristica.obra);
        sbReader.append(SEPARADOR);
        sbReader.append(patristica.tema);
        sbReader.append(SEPARADOR);
        sbReader.append(patristica.getTexto());
        sbReader.append(SEPARADOR);
        sbReader.append(patristica.padre);
        sbReader.append(SEPARADOR);

        sbReader.append("Responsorio ");
        sbReader.append(SEPARADOR);

        sbReader.append(patristica.getResponsorioForReader());
        sbReader.append(SEPARADOR);
        sbReader.append(teDeum.getTextoSpan());
        sbReader.append(Utils.fromHtml("<p>Oración.</p><br />"));
        sbReader.append(SEPARADOR);
        sbReader.append(oficio.getOracion());
        sbReader.append(SEPARADOR);
        sbReader.append(Utils.getConclusionHorasMayoresForRead());
        progressBar.setVisibility(View.INVISIBLE);
        mTextView.setText(sb, TextView.BufferType.SPANNABLE);
        if (sbReader.length() > 0) {
            menu.findItem(R.id.item_voz).setVisible(true);
        }
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


