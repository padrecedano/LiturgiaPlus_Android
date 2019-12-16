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
import com.google.gson.JsonElement;

import org.deiverbum.app.R;
import org.deiverbum.app.model.Benedictus;
import org.deiverbum.app.model.Biblica;
import org.deiverbum.app.model.Breviario;
import org.deiverbum.app.model.Evangelio;
import org.deiverbum.app.model.Himno;
import org.deiverbum.app.model.Invitatorio;
import org.deiverbum.app.model.Laudes;
import org.deiverbum.app.model.LecturaBreve;
import org.deiverbum.app.model.Liturgia;
import org.deiverbum.app.model.MetaLiturgia;
import org.deiverbum.app.model.Misa;
import org.deiverbum.app.model.MisaLecturas;
import org.deiverbum.app.model.Mixto;
import org.deiverbum.app.model.Oficio;
import org.deiverbum.app.model.OficioLecturas;
import org.deiverbum.app.model.Patristica;
import org.deiverbum.app.model.Preces;
import org.deiverbum.app.model.Salmodia;
import org.deiverbum.app.model.Santo;
import org.deiverbum.app.utils.TTS;
import org.deiverbum.app.utils.Utils;
import org.deiverbum.app.utils.VolleyErrorHelper;
import org.deiverbum.app.utils.ZoomTextView;
import org.json.JSONException;
import org.json.JSONObject;

import static org.deiverbum.app.utils.Constants.BR;
import static org.deiverbum.app.utils.Constants.CALENDAR_PATH;
import static org.deiverbum.app.utils.Constants.MIXTO_URL;
import static org.deiverbum.app.utils.Constants.MY_DEFAULT_TIMEOUT;
import static org.deiverbum.app.utils.Constants.PACIENCIA;
import static org.deiverbum.app.utils.Constants.SEPARADOR;
import static org.deiverbum.app.utils.Utils.LS2;

public class MixtoActivity extends AppCompatActivity {
    private static final String TAG = "MixtoActivity";
    JsonObjectRequest jsonObjectRequest;
    ZoomTextView mTextView;
    private RequestQueue requestQueue;
    private String strFechaHoy;
    private TTS tts;
    private StringBuilder sbReader;
    private ProgressBar progressBar;
    private Menu menu;
    private int idInvitatorio = 1;
    private boolean isInvitatorio;
    private Liturgia mLiturgia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tercia);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTextView = findViewById(R.id.tv_Zoomable);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        float fontSize = Float.parseFloat(prefs.getString("font_size", "18"));
        isInvitatorio = prefs.getBoolean("invitatorio", false);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        mTextView.setText(Utils.fromHtml(PACIENCIA));
        progressBar = findViewById(R.id.progressBar);

        strFechaHoy = (getIntent().getExtras() != null) ? getIntent().getStringExtra("FECHA") : Utils.getHoy();
        //launchVolley();
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
                Gson gson = new Gson();
                JsonElement jsonElement = gson.toJsonTree(calSnapshot.get("meta"));
                mLiturgia = new Liturgia();
                mLiturgia.setMetaLiturgia(gson.fromJson(jsonElement, MetaLiturgia.class));
                DocumentReference dataRef = calSnapshot.getDocumentReference("lh.0");
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
                Request.Method.GET, MIXTO_URL + strFechaHoy, null,
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

    private void showData() {

        SpannableStringBuilder sb = new SpannableStringBuilder();
        sbReader = new StringBuilder();
        String ant = getString(R.string.ant);
        MetaLiturgia meta = mLiturgia.getMetaLiturgia();
        Breviario mBreviario = mLiturgia.getBreviario();
        Santo santo = mBreviario.getSanto();
        Mixto mixto = mBreviario.getMixto();

        Oficio oficio = mBreviario.getOficio();
        Invitatorio invitatorio = oficio.getInvitatorio();
        idInvitatorio = (isInvitatorio) ? invitatorio.getId() : 1;
        invitatorio.setId(idInvitatorio);
        OficioLecturas lecturasOficio = oficio.getOficioLecturas();
        Patristica patristica = lecturasOficio.getPatristica();
        Biblica biblica = lecturasOficio.getBiblica();

        Laudes laudes = mBreviario.getLaudes();
        Himno himno = laudes.getHimno();
        Salmodia salmodia = laudes.getSalmodia();
        LecturaBreve lecturaBreve = laudes.getLecturaBreve();
        Benedictus benedictus = laudes.getBenedictus();
        Preces preces = laudes.getPreces();
        Misa misa = mBreviario.getMisa();
        MisaLecturas misaLecturas = misa.getMisaLecturas();
        Evangelio misaEvangelio = misaLecturas.getEvangelio();

        String hora = "LAUDES y OFICIO";

        CharSequence santoNombre = (santo.getNombre().equals("")) ? "" : Utils.toH3(santo.getNombre() + LS2);
        SpannableStringBuilder titleInvitatorio = Utils.formatSubTitle("invitatorio");
        CharSequence santoVida = (santo.getVida().equals("")) ? "" : Utils.toSmallSize(santo.getVida() + Utils.LS);

        sb.append(meta.getFecha());
        sb.append(Utils.LS2);
        sb.append(Utils.toH2(meta.getTiempoNombre()));
        sb.append(Utils.LS);
        sb.append(Utils.toH3(meta.getSemana()));
        sb.append(Utils.LS2);

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
        sb.append(Utils.LS);
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
        sb.append(lecturaBreve.getHeaderLectura());
        sb.append(Utils.LS2);
        sb.append(lecturaBreve.getTexto());
        sb.append(Utils.LS2);
        sb.append(lecturaBreve.getHeaderResponsorio());
        sb.append(Utils.LS2);
        sb.append(lecturaBreve.getResponsorioSpan());
        sb.append(Utils.LS);
        sb.append(Utils.LS2);

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
        sb.append(Utils.LS);

        sb.append(Utils.formatSubTitle("evangelio del día"));
        sb.append(Utils.LS2);
        sb.append(misaEvangelio.libro);
        sb.append("    ");
        sb.append(Utils.toRed(misaEvangelio.ref));
        sb.append(Utils.LS2);
        sb.append(Utils.fromHtml(Utils.getFormato(misaEvangelio.texto)));
        sb.append(Utils.LS2);
        sb.append(Utils.LS);

        sb.append(benedictus.getHeader());
        sb.append(Utils.LS2);
        sb.append(benedictus.getAntifona());
        sb.append(Utils.LS2);
        sb.append(benedictus.getTexto());
        sb.append(Utils.LS2);
        sb.append(Utils.getFinSalmo());
        sb.append(Utils.LS2);
        sb.append(benedictus.getAntifona());
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

        sb.append(Utils.fromHtml(laudes.getOracion()));
        sb.append(LS2);
        sb.append(Utils.getConclusionHorasMayores());
        /*Texto para el TTS*/

        sbReader.append(Utils.fromHtml("<p>" + meta.getFecha() + ".</p>"));
        sbReader.append(hora + BR);
        sbReader.append(SEPARADOR);

        sbReader.append(santoNombre + BR);
        sbReader.append(santoVida + BR);
        sbReader.append(SEPARADOR);

        sbReader.append(Utils.getSaludoOficioForReader());
        sbReader.append(SEPARADOR);
        sbReader.append(Utils.fromHtml("<p>Invitatorio.</p>"));
        sbReader.append(SEPARADOR);
        sbReader.append(invitatorio.getAntifonaForRead());
        sbReader.append(invitatorio.getTextoSpan());

        sbReader.append(Utils.getFinSalmoForRead());
        sbReader.append(invitatorio.getAntifona());
        sbReader.append(SEPARADOR);

        sbReader.append(himno.getHeader());
        sbReader.append(SEPARADOR);
        sbReader.append(himno.getTexto());
        sbReader.append(SEPARADOR);

        sbReader.append(salmodia.getHeaderForRead());
        sbReader.append(salmodia.getSalmoCompletoForRead());

        sbReader.append(Utils.fromHtml("<p>Lectura Breve.</p><br />"));
        sbReader.append(SEPARADOR);
        sbReader.append(lecturaBreve.getTexto());
        sbReader.append(SEPARADOR);
        sbReader.append(Utils.fromHtml("<p>Responsorio.</p><br />"));
        sbReader.append(SEPARADOR);
        sbReader.append(lecturaBreve.getResponsorioForRead());
        sbReader.append(SEPARADOR);

        sbReader.append("<p>Lecturas del oficio</p>");
        sbReader.append(SEPARADOR);

        sbReader.append(biblica.getHeader());
        sbReader.append(SEPARADOR);
        sbReader.append(biblica.getLibro());
        sbReader.append(SEPARADOR);
        sbReader.append(biblica.getTema());
        sbReader.append(SEPARADOR);
        sbReader.append(biblica.getTexto());
        sbReader.append(SEPARADOR);

        sbReader.append("Responsorio ");
        sbReader.append(SEPARADOR);
        sbReader.append(biblica.getResponsorioForReader());
        sbReader.append(SEPARADOR);

        sbReader.append(patristica.getHeader());
        sbReader.append(SEPARADOR);
        sbReader.append(patristica.padre);
        sbReader.append(", ");
        sbReader.append(patristica.obra);
        sbReader.append(SEPARADOR);
        sbReader.append(patristica.tema);
        sbReader.append(SEPARADOR);
        sbReader.append(patristica.getTexto());
        sbReader.append(SEPARADOR);

        sbReader.append("Responsorio ");
        sbReader.append(SEPARADOR);

        sbReader.append(patristica.getResponsorioForReader());
        sbReader.append(SEPARADOR);

        sbReader.append("Evangelio del día");
        sbReader.append(SEPARADOR);
        sbReader.append(misaEvangelio.libro);
        sbReader.append(SEPARADOR);
        sbReader.append(misaEvangelio.getEvangelioForRead());

        sbReader.append(SEPARADOR);
        sbReader.append(Utils.fromHtml("<p>Palabra del Señor.</p><br />"));
        sbReader.append(Utils.fromHtml("<p>Gloria a ti, Señor Jesús.</p><br />"));
        sbReader.append(SEPARADOR);

        sbReader.append(benedictus.getHeader());
        sbReader.append(SEPARADOR);
        sbReader.append(benedictus.getAntifonaForRead());
        sbReader.append(SEPARADOR);
        sbReader.append(benedictus.getTexto());
        sbReader.append(SEPARADOR);
        sbReader.append(Utils.getFinSalmoForRead());
        sbReader.append(SEPARADOR);
        sbReader.append(benedictus.getAntifonaForRead());
        sbReader.append(SEPARADOR);

        sbReader.append(Utils.fromHtml("<p>Preces.</p><br />"));
        sbReader.append(SEPARADOR);
        sbReader.append(preces.getPreces());
        sbReader.append(SEPARADOR);

        sbReader.append(Utils.getPadreNuestro());
        sbReader.append(SEPARADOR);
        sbReader.append(Utils.fromHtml("<p>Oración.</p><br />"));
        sbReader.append(SEPARADOR);
        sbReader.append(laudes.getOracion());
        sbReader.append(SEPARADOR);
        sbReader.append(Utils.getConclusionHorasMayoresForRead());
        mTextView.setText(sb, TextView.BufferType.SPANNABLE);
        progressBar.setVisibility(View.INVISIBLE);
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
                String[] textParts = html.split(SEPARADOR);
                tts = new TTS(getApplicationContext(), textParts);
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
