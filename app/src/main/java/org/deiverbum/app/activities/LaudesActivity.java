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
import org.deiverbum.app.model.Benedictus;
import org.deiverbum.app.model.Breviario;
import org.deiverbum.app.model.Himno;
import org.deiverbum.app.model.Invitatorio;
import org.deiverbum.app.model.Laudes;
import org.deiverbum.app.model.LecturaBreve;
import org.deiverbum.app.model.MetaLiturgia;
import org.deiverbum.app.model.Oficio;
import org.deiverbum.app.model.Preces;
import org.deiverbum.app.model.Salmodia;
import org.deiverbum.app.model.Santo;
import org.deiverbum.app.utils.TTS;
import org.deiverbum.app.utils.Utils;
import org.deiverbum.app.utils.UtilsOld;
import org.deiverbum.app.utils.VolleyErrorHelper;
import org.deiverbum.app.utils.ZoomTextView;
import org.json.JSONException;
import org.json.JSONObject;

import static org.deiverbum.app.utils.Constants.BR;
import static org.deiverbum.app.utils.Constants.LA_URL;
import static org.deiverbum.app.utils.Constants.MY_DEFAULT_TIMEOUT;
import static org.deiverbum.app.utils.Constants.PACIENCIA;
import static org.deiverbum.app.utils.Constants.SEPARADOR;
import static org.deiverbum.app.utils.Utils.LS2;

public class LaudesActivity extends AppCompatActivity {
    private static final String TAG = "LaudesActivity";
    Spanned strContenido;
    JsonObjectRequest jsonObjectRequest;
    ZoomTextView mTextView;
    private UtilsOld utilClass;
    private RequestQueue requestQueue;
    private String strFechaHoy;
    private TTS tts;
    private StringBuilder sbReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laudes);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTextView =  findViewById(R.id.tv_Zoomable);

        utilClass = new UtilsOld();
        strFechaHoy = (getIntent().getExtras() != null) ? getIntent().getStringExtra("FECHA") : utilClass.getHoy();
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        requestQueue = Volley.newRequestQueue(this);
        final ProgressBar progressBar = findViewById(R.id.progressBar);
//strFechaHoy="20180826";
        mTextView.setText(UtilsOld.fromHtml(PACIENCIA));
        jsonObjectRequest = new JsonObjectRequest(

                Request.Method.GET, LA_URL + strFechaHoy,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        SpannableStringBuilder resp = getResponseData(response);
                        //strContenido = UtilsOld.fromHtml(resp);
                        //strContenido = Utils.fromHtml(resp.toString());

                        SpannableStringBuilder tv = getResponseData(response);
                        mTextView.setText(resp, TextView.BufferType.SPANNABLE);

                        //mTextView.setText(UtilsOld.fromHtml(resp.replaceAll(SEPARADOR, "")));
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


        try {
            sbReader = new StringBuilder();
            Gson gson = new Gson();
            JSONObject jsonBreviario = jsonDatos.getJSONObject("breviario");
            //Log.d(TAG,String.valueOf(jsonBreviario));

            Breviario breviario = gson.fromJson(String.valueOf(jsonBreviario), Breviario.class);
            MetaLiturgia meta = breviario.getMetaLiturgia();
            Santo santo = breviario.getSanto();

            Oficio oficio = breviario.getOficio();
            Invitatorio invitatorio = oficio.getInvitatorio();
            Laudes laudes = breviario.getLaudes();
            Himno himno = laudes.getHimno();
            Salmodia salmodia = laudes.getSalmodia();
            LecturaBreve lecturaBreve = laudes.getLecturaBreve();
            Benedictus benedictus = laudes.getBenedictus();
            Preces preces = laudes.getPreces();
            //Oracion oracion=oficio.getOracion();

            CharSequence santoNombre = (santo.getNombre().equals("")) ? "" : Utils.toH3(santo.getNombre() + LS2);

            SpannableStringBuilder titleInvitatorio = Utils.formatSubTitle("invitatorio");

            CharSequence santoVida = (santo.getVida().equals("")) ? "" : Utils.toSmallSize(santo.getVida() + Utils.LS);
            CharSequence metaSalterio = (meta.getSalterio().equals("")) ? "" : Utils.toSmallSizeRed(Utils.fromHtml(meta.getSalterio()) + Utils.LS);
            String ant = getString(R.string.ant);

            String hora = "LAUDES";


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
            sb.append(lecturaBreve.getHeaderLectura());
            sb.append(Utils.LS2);
            sb.append(lecturaBreve.getTexto());
            sb.append(Utils.LS2);
            sb.append(lecturaBreve.getHeaderResponsorio());
            sb.append(Utils.LS2);
            sb.append(lecturaBreve.getResponsorio());

            sb.append(Utils.LS2);
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


            /*Texto para TTS*/

            sbReader.append("LAUDES." + BR);
            sbReader.append(SEPARADOR);

            sbReader.append(santo.getNombre() + "." + BR);
            sbReader.append(santo.getVida() + BR);
            sbReader.append(meta.getSalterio() + BR);
            sbReader.append(SEPARADOR);
            sbReader.append(Utils.getSaludoOficioForReader());
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
            sbReader.append(lecturaBreve.getHeaderLectura());
            sbReader.append(SEPARADOR);
            sbReader.append(lecturaBreve.getTexto());
            sbReader.append(SEPARADOR);
            sbReader.append(lecturaBreve.getHeaderResponsorio());
            sbReader.append(SEPARADOR);
            sbReader.append(lecturaBreve.getResponsorio());
            sbReader.append(SEPARADOR);

            sbReader.append(benedictus.getHeader());
            sbReader.append(SEPARADOR);
            sbReader.append(benedictus.getAntifona());
            sbReader.append(SEPARADOR);
            sbReader.append(benedictus.getTexto());
            sbReader.append(SEPARADOR);
            sbReader.append(Utils.getFinSalmo());
            sbReader.append(SEPARADOR);
            sbReader.append(benedictus.getAntifona());
            sbReader.append(SEPARADOR);

            sbReader.append(preces.getHeader());
            sbReader.append(SEPARADOR);
            sbReader.append(preces.getPreces());
            sbReader.append(SEPARADOR);


            sbReader.append(Utils.getPadreNuestro());
            sbReader.append(SEPARADOR);

            sbReader.append("ORACIÓN");
            sbReader.append(SEPARADOR);

            sbReader.append(laudes.getOracion());



/*
        try {
            JSONObject jsonBreviario = jsonDatos.getJSONObject("breviario");
            JSONObject jsonContenido = jsonBreviario.getJSONObject("contenido");
            JSONObject oInfo = jsonBreviario.getJSONObject("info");
            JSONObject s1 = jsonContenido.getJSONObject("salmos").getJSONObject("s1");
            JSONObject s2 = jsonContenido.getJSONObject("salmos").getJSONObject("s2");
            JSONObject s3 = jsonContenido.getJSONObject("salmos").getJSONObject("s3");
            JSONObject biblica = jsonContenido.getJSONObject("biblica");
            JSONObject ce = jsonContenido.getJSONObject("ce");

            JSONObject preces = jsonContenido.getJSONObject("preces");

            String sVida = "";
            String infoFecha = oInfo.getString("fecha") + BRS;
            String infoTiempo = "<h1>" + oInfo.getString("tiempo") + "</h1>";
            String infoSemana = "<h3>" + oInfo.getString("semana") + "</h3>";
            String infoSalterio = CSS_RED_A +oInfo.getString("salterio") + CSS_RED_Z +BRS;
            String infoMensaje = oInfo.getString("mensaje");

            String txtVida = "";
            if (!jsonContenido.getString("vida").equals("")) {
                txtVida = CSS_SM_A + jsonContenido.getString("vida") + CSS_SM_Z + BRS;
            }

            String txtSanto = "";
            if (!jsonContenido.getString("santo").equals("")) {
                txtSanto = "<h3>" + jsonContenido.getString("santo") + "</h3>";
            }

            String sAntifonaInv = PRE_ANT + jsonContenido.getString("antifonai") + BRS;
            String sHimno = "";
            String jsonHimno=jsonContenido.getString("himno");
            sHimno=( jsonHimno!= null || !jsonHimno.isEmpty() || !jsonHimno.equals("null")) ? HIMNO + utilClass.getFormato(jsonHimno)+BRS:"";

            String sOrden1 = s1.getString("orden");
            String sAntifona1 = utilClass.getFormato(s1.getString("antifona"));
            String sRef1 = s1.getString("txt_ref");
            String sTema1 = s1.getString("tema");
            String sIntro1 = s1.getString("txt_intro");
            String sParte1 = s1.getString("parte");
            String sSalmo1 = s1.getString("txt_salmo");
            String sSalmoCompleto1 = utilClass.getSalmoCompleto(sOrden1, sAntifona1, sRef1, sTema1, sIntro1, sParte1, sSalmo1);

            String sOrden2 = s2.getString("orden");
            String sAntifona2 = utilClass.getFormato(s2.getString("antifona"));
            String sRef2 = s2.getString("txt_ref");
            String sTema2 = s2.getString("tema");
            String sIntro2 = s2.getString("txt_intro");
            String sParte2 = s2.getString("parte");
            String sSalmo2 = s2.getString("txt_salmo");
            String sSalmoCompleto2 = utilClass.getSalmoCompleto(sOrden2, sAntifona2, sRef2, sTema2, sIntro2, sParte2, sSalmo2);

            String sOrden3 = s3.getString("orden");
            String sAntifona3 = utilClass.getFormato(s3.getString("antifona"));
            String sRef3 = s3.getString("txt_ref");
            String sTema3 = s3.getString("tema");
            String sIntro3 = s3.getString("txt_intro");
            String sParte3 = s3.getString("parte");
            String sSalmo3 = s3.getString("txt_salmo");
            String sSalmoCompleto3 = utilClass.getSalmoCompleto(sOrden3, sAntifona3, sRef3, sTema3, sIntro3, sParte3, sSalmo3);

            String sLecturaBreve = "";//ERR_RESPONSORIO;
            String sRespLBreve = "";

            sLecturaBreve = CSS_RED_A + LECTURA_BREVE + NBSP_4 + biblica.getString("lbreve_ref") + CSS_RED_Z + BRS +
                    biblica.getString("txt_lbreve") + BRS;

            int nForma = biblica.getInt("id_forma");
            if (nForma!=0) {
                String[] respArray = biblica.getString("txt_responsorio").split("\\|");
                sRespLBreve = utilClass.getResponsorio(respArray, nForma);
                sRespLBreve = RESP_BREVE + BRS + sRespLBreve;
            }else{
                sRespLBreve = CSS_RED_A+"En lugar del responsorio breve se dice la siguiente antífona: " + CSS_RED_Z+BRS + biblica.getString("txt_responsorio")+BRS;
            }
            String sAntifonaCE = ce.getString("txt_antifonace");
            sAntifonaCE = BRS + PRE_ANT + sAntifonaCE + BRS;
            String sPrecesIntro = preces.getString("txt_preces_intro");
            String sPreces = "";
            if (!utilClass.isNull(sPrecesIntro)) {
                String[] introArray = preces.getString("txt_preces_intro").split("\\|");
                String sPrecesCuerpo = utilClass.getFormato(preces.getString("txt_preces"));
                sPreces = PRECES + utilClass.getPreces(introArray, sPrecesCuerpo);
            }

            String sOracion = ORACION + utilClass.getFormato(jsonContenido.getString("oracion"));

            sb.append(infoFecha);
            sb.append(infoTiempo);
            sb.append(LA_TITULO);
            sb.append(infoMensaje);
            sb.append(infoSemana);
            if (!txtSanto.equals("")) {
                sb.append(txtSanto);
            }

            if (!txtVida.equals("")) {
                sb.append(txtVida);
                sb.append(SEPARADOR);
            }

            sb.append(infoSalterio);
            sb.append(INVITATORIO);

            sb.append(SEPARADOR);
            sb.append("<p>" + SALUDO_OFICIO + "</p>");

            sb.append(sAntifonaInv);
            sb.append(SEPARADOR);
            sb.append(sHimno);
            sb.append(SEPARADOR);
            sb.append(SALMODIA);
            sb.append(sSalmoCompleto1);
            sb.append(SEPARADOR);
            sb.append(sSalmoCompleto2);
            sb.append(SEPARADOR);
            sb.append(sSalmoCompleto3);
            sb.append(SEPARADOR);

            sb.append(sLecturaBreve);
            sb.append(SEPARADOR);
            sb.append(sRespLBreve);
            sb.append(SEPARADOR);
            sb.append(CE);
            sb.append(sAntifonaCE);
            sb.append(utilClass.getFormato(BENEDICTUS));
            sb.append(FIN_SALMO);
            sb.append(sAntifonaCE);
            sb.append(SEPARADOR);

            sb.append(sPreces);
            sb.append(SEPARADOR);
            sb.append(PADRENUESTRO_TITULO);
            sb.append(utilClass.getFormato(PADRENUESTRO));
            sb.append(sOracion);
*/
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
                //String[] strPrimera = strContenido.toString().split(SEPARADOR);
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
