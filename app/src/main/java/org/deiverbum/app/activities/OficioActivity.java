package org.deiverbum.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.util.TypedValue;
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
import org.deiverbum.app.model.Biblica;
import org.deiverbum.app.model.Breviario;
import org.deiverbum.app.model.Himno;
import org.deiverbum.app.model.Invitatorio;
import org.deiverbum.app.model.MetaLiturgia;
import org.deiverbum.app.model.Oficio;
import org.deiverbum.app.model.OficioLecturas;
import org.deiverbum.app.model.Patristica;
import org.deiverbum.app.model.Salmodia;
import org.deiverbum.app.model.Santo;
import org.deiverbum.app.model.TeDeum;
import org.deiverbum.app.utils.TTS;
import org.deiverbum.app.utils.Utils;
import org.deiverbum.app.utils.UtilsOld;
import org.deiverbum.app.utils.VolleyErrorHelper;
import org.deiverbum.app.utils.ZoomTextView;
import org.json.JSONException;
import org.json.JSONObject;

import static org.deiverbum.app.utils.Constants.BR;
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
    private UtilsOld utilClass;
    private RequestQueue requestQueue;
    private String strFechaHoy;
    private TTS tts;
    private int progressStatus = 0;
    private StringBuilder sbReader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oficio);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTextView = findViewById(R.id.tv_Zoomable);

        utilClass = new UtilsOld();
        strFechaHoy = (getIntent().getExtras() != null) ? getIntent().getStringExtra("FECHA") : utilClass.getHoy();
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        requestQueue = Volley.newRequestQueue(this);
        final ProgressBar progressBar = findViewById(R.id.progressBar);

        mTextView.setText(UtilsOld.fromHtml(PACIENCIA));
        jsonObjectRequest = new JsonObjectRequest(

                Request.Method.GET, OL_URL + strFechaHoy, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        SpannableStringBuilder resp = getResponseData(response);
                        //strContenido = Utils.fromHtml(resp.toString());
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
        Gson gson = new Gson();


        try {

            JSONObject jsonBreviario = jsonDatos.getJSONObject("breviario");
            Breviario breviario = gson.fromJson(String.valueOf(jsonBreviario), Breviario.class);
            MetaLiturgia meta = breviario.getMetaLiturgia();
            Santo santo = breviario.getSanto();

            Oficio oficio = breviario.getOficio();
            Invitatorio invitatorio = oficio.getInvitatorio();
            Himno himno = oficio.getHimno();
            Salmodia salmodia = oficio.getSalmodia();

            OficioLecturas lecturasOficio = oficio.getOficioLecturas();
            Patristica patristica = lecturasOficio.getPatristica();
            Biblica biblica = lecturasOficio.getBiblica();
            TeDeum teDeum = lecturasOficio.getTeDeum();
            //Oracion oracion=oficio.getOracion();


            CharSequence santoNombre = (santo.getNombre().equals("")) ? "" : Utils.toH3(santo.getNombre() + LS2);

            SpannableStringBuilder titleInvitatorio = Utils.formatSubTitle("invitatorio");

            CharSequence santoVida = (santo.getVida().equals("")) ? "" : Utils.toSmallSize(santo.getVida() + Utils.LS);
            CharSequence metaSalterio = (meta.getSalterio().equals("")) ? "" : Utils.toSmallSizeRed(Utils.fromHtml(meta.getSalterio()) + Utils.LS);
            String ant = getString(R.string.ant);


            sb.append(Utils.toH2(meta.getTiempo()));
            sb.append(Utils.LS);
            sb.append(Utils.toH3(meta.getSemana()));
            sb.append(Utils.LS2);

            //sb.append(Utils.toH3Red(hora));
            sb.append(Utils.LS);

            sb.append(santo.getNombre());
            sb.append(santo.getVida());
            sb.append(metaSalterio);

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

            sb.append(Utils.formatSubTitle("lecturas del oficio"));
            sb.append(Utils.LS2);
            //sb.append(oficio.getResponsorio());

            sb.append(lecturasOficio.getResponsorio());
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
            sb.append(teDeum.getTexto());
            sb.append(Utils.formatTitle("ORACIÓN"));
            sb.append(LS2);

            sb.append(oficio.getOracion());

            /*Texto para TTS*/

            sbReader.append("OFICIO DE LECTURA." + BR);
            sbReader.append(SEPARADOR);

            sbReader.append(santo.getNombre() + "." + BR);
            sbReader.append(santo.getVida() + BR);
            sbReader.append(meta.getSalterio() + BR);
            sbReader.append(SEPARADOR);
            sbReader.append(SEPARADOR);
            sbReader.append(Utils.getSaludoOficio());
            sbReader.append(SEPARADOR);

            sbReader.append("Invitatorio.");
            sbReader.append(SEPARADOR);
            sbReader.append(invitatorio.getAntifona());
            sbReader.append(invitatorio.getTexto());
            sbReader.append(Utils.getFinSalmo());
            sbReader.append(invitatorio.getAntifona());
            sbReader.append(SEPARADOR);

            sbReader.append("HIMNO.");
            sbReader.append(SEPARADOR);
            sbReader.append(himno.getTexto());
            sbReader.append(SEPARADOR);

            sbReader.append("SALMODIA.");
            sbReader.append(salmodia.getSalmosForRead());

            sbReader.append("<p>Lecturas del oficio</p>");
            sbReader.append(SEPARADOR);

            sbReader.append("PRIMERA LECTURA.");
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

            sbReader.append("Responsorio ");
            sbReader.append(SEPARADOR);

            sbReader.append(patristica.getResponsorioForReader());
            sbReader.append(SEPARADOR);
            sbReader.append(teDeum.getTexto());
            sbReader.append(oficio.getOracion());


        } catch (JSONException e) {
            e.printStackTrace();
        }


//            Oficio oficio = breviario.oficio;
//        sb.append(oficio.toString());
/*
        } catch (JSONException e) {

            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
*/
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
//                String[] strPrimera = strContenido.toString().split(SEPARADOR);
//                tts = new TTS(getApplicationContext(), strPrimera);
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


