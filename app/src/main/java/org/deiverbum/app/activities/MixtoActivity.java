package org.deiverbum.app.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.deiverbum.app.R;
import org.deiverbum.app.model.Benedictus;
import org.deiverbum.app.model.Biblica;
import org.deiverbum.app.model.Himno;
import org.deiverbum.app.model.Invitatorio;
import org.deiverbum.app.model.Laudes;
import org.deiverbum.app.model.LecturaBreve;
import org.deiverbum.app.model.LiturgiaPalabra;
import org.deiverbum.app.model.MetaLiturgia;
import org.deiverbum.app.model.Misa;
import org.deiverbum.app.model.Mixto;
import org.deiverbum.app.model.Oficio;
import org.deiverbum.app.model.OficioLecturas;
import org.deiverbum.app.model.Patristica;
import org.deiverbum.app.model.Preces;
import org.deiverbum.app.model.Salmodia;
import org.deiverbum.app.model.Santo;
import org.deiverbum.app.utils.CustomTypefaceSpan;
import org.deiverbum.app.utils.TTS;
import org.deiverbum.app.utils.Utils;
import org.deiverbum.app.utils.UtilsOld;
import org.deiverbum.app.utils.VolleyErrorHelper;
import org.deiverbum.app.utils.ZoomTextView;
import org.json.JSONException;
import org.json.JSONObject;

import static org.deiverbum.app.utils.Constants.BR;
import static org.deiverbum.app.utils.Constants.MY_DEFAULT_TIMEOUT;
import static org.deiverbum.app.utils.Constants.PACIENCIA;
import static org.deiverbum.app.utils.Constants.SEPARADOR;
import static org.deiverbum.app.utils.Constants.URL_MIXTO;
import static org.deiverbum.app.utils.Utils.LS2;

public class MixtoActivity extends AppCompatActivity {
    private static final String TAG = "MixtoActivity";
    Spanned strContenido;
    JsonObjectRequest jsonObjectRequest;
    ZoomTextView mTextView;
    private UtilsOld utilClass;
    private RequestQueue requestQueue;
    private String strFechaHoy;
    private TTS tts;
    private Typeface typeface;
    private StringBuilder sbReader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tercia);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTextView = findViewById(R.id.tv_Zoomable);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            typeface = getResources().getFont(R.font.roboto_black);
            mTextView.setTypeface(typeface);

        } else {

            typeface = ResourcesCompat.getFont(this, R.font.roboto_black);

        }

        utilClass = new UtilsOld();
        strFechaHoy = (getIntent().getExtras() != null) ? getIntent().getStringExtra("FECHA") : utilClass.getHoy();
        requestQueue = Volley.newRequestQueue(this);
        final ProgressBar progressBar = findViewById(R.id.progressBar);

        mTextView.setText(UtilsOld.fromHtml(PACIENCIA));
        jsonObjectRequest = new JsonObjectRequest(

                Request.Method.GET, URL_MIXTO + strFechaHoy, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        SpannableStringBuilder resp = getResponseData(response);

                        SpannableStringBuilder spanstr = new SpannableStringBuilder("");
                        CharSequence cs = "invitatorio";
                        Spannable spannable = new SpannableString(cs);
                        spannable.setSpan(typeface, 0, spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


                        spanstr.append(spannable);

                        Typeface myTypeface = Typeface.create(ResourcesCompat.getFont(getApplicationContext(), R.font.roboto_black),
                                Typeface.BOLD);
                        SpannableStringBuilder SS = new SpannableStringBuilder("invitatorio");
                        SS.setSpan(new CustomTypefaceSpan("", myTypeface), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                        mTextView.setText(resp, TextView.BufferType.SPANNABLE);

                        progressBar.setVisibility(View.INVISIBLE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyErrorHelper errorVolley = new VolleyErrorHelper();
                        String sError = VolleyErrorHelper.getMessage(error, getApplicationContext());
                        Log.d(TAG, "Error: " + sError);
                        mTextView.setText(UtilsOld.fromHtml(sError));
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }
        );

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
        progressBar.setVisibility(View.VISIBLE);

    }

    protected SpannableStringBuilder getResponseData(JSONObject jsonDatos) {




        SpannableStringBuilder sb = new SpannableStringBuilder();
        sbReader = new StringBuilder();
        try {
            Gson gson = new Gson();
            String ant = getString(R.string.ant);

            /*Obtenemos o declaramos variables*/
            JSONObject jsonBreviario = jsonDatos.getJSONObject("breviario");
            Mixto mixto = gson.fromJson(String.valueOf(jsonBreviario), Mixto.class);
            MetaLiturgia meta = mixto.getMetaLiturgia();
            Santo santo = mixto.getSanto();

            Oficio oficio = mixto.getOficio();
            Invitatorio invitatorio = oficio.getInvitatorio();
            OficioLecturas lecturasOficio = oficio.getOficioLecturas();
            Patristica patristica = lecturasOficio.getPatristica();
            Biblica biblica = lecturasOficio.getBiblica();

            Laudes laudes = mixto.getLaudes();
            Himno himno = laudes.getHimno();
            Salmodia salmodia = laudes.getSalmodia();
            LecturaBreve lecturaBreve = laudes.getLecturaBreve();
            Benedictus benedictus = laudes.getBenedictus();
            Preces preces = laudes.getPreces();
            Misa misa = mixto.getMisa();
            //MisaLecturas misaLecturas = misa.getMisaLecturas();
            //Evangelio misaEvangelio = misaLecturas.getEvangelio();
            //Misa misa = mixto.getMisa();
            LiturgiaPalabra lp = misa.getLiturgiaPalabra();

            String hora = "LAUDES y OFICIO";

            CharSequence santoNombre = (santo.getNombre().equals("")) ? "" : Utils.toH3(santo.getNombre() + LS2);

            SpannableStringBuilder titleInvitatorio = Utils.formatSubTitle("invitatorio");

            CharSequence santoVida = (santo.getVida().equals("")) ? "" : Utils.toSmallSize(santo.getVida() + Utils.LS);


            sb.append(meta.getFecha());
            sb.append(Utils.LS2);


            sb.append(Utils.toH2(meta.getTiempo()));
            sb.append(Utils.LS);
            sb.append(Utils.toH3(meta.getSemana()));
            sb.append(Utils.LS2);

            sb.append(Utils.toH3Red(hora));
            sb.append(Utils.LS);

            sb.append(santoNombre);
            sb.append(santoVida);
            sb.append(Utils.fromHtmlToSmallRed(meta.getSalterio()));

            sb.append(Utils.LS2);
            sb.append(Utils.getSaludoOficio());
            sb.append(Utils.LS2);
            sb.append(titleInvitatorio);
            sb.append(Utils.LS2);
            sb.append(Utils.fromHtml(ant));
            sb.append(invitatorio.getAntifona());
            sb.append(Utils.LS2);
            sb.append(Utils.fromHtml(invitatorio.getTexto()));
            sb.append(Utils.LS);
            sb.append(Utils.getFinSalmo());
            sb.append(Utils.LS2);
            sb.append(Utils.fromHtml(ant));
            sb.append(invitatorio.getAntifona());
            sb.append(Utils.LS2);

            sb.append(himno.getHeader());
            sb.append(Utils.LS2);
            sb.append(himno.getTexto());
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
            sb.append(lecturaBreve.getResponsorio());
            sb.append(Utils.LS);

            sb.append(Utils.formatSubTitle("lecturas del oficio"));
            sb.append(Utils.LS2);
            sb.append(biblica.getHeader());
            sb.append(Utils.LS2);
            sb.append(biblica.getLibro());
            sb.append("    ");

            sb.append(Utils.toRed(biblica.getCapitulo()));
            sb.append(", ");
            sb.append(Utils.toRed(biblica.getInicial()));
            sb.append(Utils.toRed(biblica.getFinal()));
            sb.append(Utils.LS2);

            sb.append(Utils.toRed(biblica.getTema()));

            sb.append(Utils.LS2);
            sb.append(biblica.getTexto());
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

            sb.append(patristica.getTexto());

            sb.append(Utils.LS);
            sb.append(Utils.toRed("Responsorio    "));
            sb.append(Utils.toRed(patristica.ref));

            sb.append(Utils.LS2);

            sb.append(patristica.getResponsorio());
            sb.append(Utils.LS2);
            sb.append(Utils.LS);

            sb.append(Utils.formatSubTitle("evangelio del día"));
            sb.append(Utils.LS2);
//            sb.append(lp.getEvangelio());

/*
            sb.append(misaEvangelio.libro);
            sb.append("    ");

            sb.append(Utils.toRed(misaEvangelio.ref));

            sb.append(Utils.LS2);
            sb.append(Utils.fromHtml(Utils.getFormato(misaEvangelio.texto)));
*/
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

            sbReader.append(meta.getFecha());
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
            sbReader.append(invitatorio.getTexto());

            sbReader.append(Utils.getFinSalmoForRead());
            sbReader.append(invitatorio.getAntifona());
            sbReader.append(SEPARADOR);

            sbReader.append(himno.getHeader());
            sbReader.append(SEPARADOR);
            sbReader.append(himno.getTexto());
            sbReader.append(SEPARADOR);

            sbReader.append(Utils.fromHtml("<p>Salmodia.</p><br />"));
            sbReader.append(salmodia.getSalmosForRead());

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
/*
            sbReader.append("Evangelio del día");
            sbReader.append(SEPARADOR);
            sbReader.append(misaEvangelio.libro);
            sbReader.append(SEPARADOR);
            sbReader.append(misaEvangelio.getEvangelioForRead());
*/
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



        } catch (JSONException e) {
            e.printStackTrace();

        }

        return sb;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
