package org.deiverbum.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spanned;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.deiverbum.app.R;
import org.deiverbum.app.utils.TTS;
import org.deiverbum.app.utils.Utils;
import org.deiverbum.app.utils.VolleyErrorHelper;
import org.deiverbum.app.utils.ZoomTextView;
import org.json.JSONException;
import org.json.JSONObject;

import static org.deiverbum.app.utils.Constants.BRS;
import static org.deiverbum.app.utils.Constants.CE;
import static org.deiverbum.app.utils.Constants.CSS_RED_A;
import static org.deiverbum.app.utils.Constants.CSS_RED_Z;
import static org.deiverbum.app.utils.Constants.ERR_GENERAL;
import static org.deiverbum.app.utils.Constants.ERR_RESPONSORIO;
import static org.deiverbum.app.utils.Constants.HIMNO;
import static org.deiverbum.app.utils.Constants.LECTURA_BREVE;
import static org.deiverbum.app.utils.Constants.MY_DEFAULT_TIMEOUT;
import static org.deiverbum.app.utils.Constants.NBSP_4;
import static org.deiverbum.app.utils.Constants.ORACION;
import static org.deiverbum.app.utils.Constants.PACIENCIA;
import static org.deiverbum.app.utils.Constants.PADRENUESTRO;
import static org.deiverbum.app.utils.Constants.PADRENUESTRO_TITULO;
import static org.deiverbum.app.utils.Constants.PRECES;
import static org.deiverbum.app.utils.Constants.PRE_ANT;
import static org.deiverbum.app.utils.Constants.RESP_BREVE;
import static org.deiverbum.app.utils.Constants.SALMODIA;
import static org.deiverbum.app.utils.Constants.SEPARADOR;
import static org.deiverbum.app.utils.Constants.URL_VISPERAS;
import static org.deiverbum.app.utils.Constants.VI_TITULO;

public class VisperasActivity extends AppCompatActivity {
    private static final String TAG = "VisperasActivity";
    Spanned strContenido;
    JsonObjectRequest jsonObjectRequest;
    ZoomTextView mTextView;
    private Utils utilClass;
    private RequestQueue requestQueue;
    private String strFechaHoy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visperas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTextView =  findViewById(R.id.tv_Zoomable);

        utilClass = new Utils();
        strFechaHoy = (getIntent().getExtras() != null) ? getIntent().getStringExtra("FECHA") : utilClass.getHoy();
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        requestQueue = Volley.newRequestQueue(this);
        final ProgressBar progressBar = findViewById(R.id.progressBar);

        mTextView.setText(Utils.fromHtml(PACIENCIA));
        jsonObjectRequest = new JsonObjectRequest(

                Request.Method.GET, URL_VISPERAS + strFechaHoy,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String resp = getResponseData(response);
                        strContenido = Utils.fromHtml(resp);
                        mTextView.setText(Utils.fromHtml(resp.replaceAll(SEPARADOR, "")));
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyErrorHelper errorVolley = new VolleyErrorHelper();
                        String sError = VolleyErrorHelper.getMessage(error, getApplicationContext());
                        Log.d(TAG, "Error: " + sError);
                        mTextView.setText(Utils.fromHtml(sError));
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
    protected String getResponseData(JSONObject jsonDatos) {
        StringBuilder sb = new StringBuilder();

        try {

            //JSONObject json_todo = js_arr.getJSONObject(0);
            JSONObject jsonBreviario = jsonDatos.getJSONObject("breviario");
            JSONObject oInfo = jsonBreviario.getJSONObject("info");

            int nCodigo = Integer.parseInt(oInfo.getString("codigo"));
            if (nCodigo < 1) {
                sb.append(ERR_GENERAL);

            } else {

                JSONObject jsonContenido = jsonBreviario.getJSONObject("contenido");
                //JSONObject hora = jsonContenido.getJSONObject("vi");
                JSONObject s1 = jsonContenido.getJSONObject("salmos").getJSONObject("s1");
                JSONObject s2 = jsonContenido.getJSONObject("salmos").getJSONObject("s2");
                JSONObject s3 = jsonContenido.getJSONObject("salmos").getJSONObject("s3");
                JSONObject biblica = jsonContenido.getJSONObject("biblica");
                JSONObject ce = jsonContenido.getJSONObject("ce");

                JSONObject preces = jsonContenido.getJSONObject("preces");

                String sVida = "";
                String sMensaje = oInfo.getString("mensaje");

                int id_tiempo = oInfo.getInt("id_tiempo");
/*
                switch (id_tiempo) {
                    case 9:
                        sVida = CSS_B_A + jsonContenido.getString("txt_santo") + CSS_B_Z + BRS + CSS_SM_A + hora.getString("txt_vida") + CSS_SM_Z;
                        break;
                    default:
                        break;
                }
*/
                String sOrden1 = s1.getString("orden");
                String sAntifona1 = s1.getString("antifona");
                String sRef1 = s1.getString("txt_ref");
                String sTema1 = s1.getString("tema");
                String sIntro1 = s1.getString("txt_intro");
                String sParte1 = s1.getString("parte");
                String sSalmo1 = s1.getString("txt_salmo");
                String sSalmoCompleto1 = utilClass.getSalmoCompleto(sOrden1, sAntifona1, sRef1, sTema1, sIntro1, sParte1, sSalmo1);

                String sOrden2 = s2.getString("orden");
                String sAntifona2 = s2.getString("antifona");
                String sRef2 = s2.getString("txt_ref");
                String sTema2 = s2.getString("tema");
                String sIntro2 = s2.getString("txt_intro");
                String sParte2 = s2.getString("parte");
                String sSalmo2 = s2.getString("txt_salmo");
                String sSalmoCompleto2 = utilClass.getSalmoCompleto(sOrden2, sAntifona2, sRef2, sTema2, sIntro2, sParte2, sSalmo2);

                String sOrden3 = s3.getString("orden");
                String sAntifona3 = s3.getString("antifona");
                String sRef3 = s3.getString("txt_ref");
                String sTema3 = s3.getString("tema");
                String sIntro3 = s3.getString("txt_intro");
                String sParte3 = s3.getString("parte");
                String sSalmo3 = s3.getString("txt_salmo");
                String sSalmoCompleto3 = utilClass.getSalmoCompleto(sOrden3, sAntifona3, sRef3, sTema3, sIntro3, sParte3, sSalmo3);

//            String sAntifonaInv = PRE_ANT+hora.getString("antifonai")+BR;
                String sHimno = HIMNO + utilClass.getFormato(jsonContenido.getString("himno"));
                String sLecturaBreve = ERR_RESPONSORIO;
                String sRespLBreve = "";
                String sForma = biblica.getString("id_forma");
                if (sForma != null && !sForma.isEmpty())

                {

                    int nForma = Integer.parseInt(sForma);//biblica.getInt("id_forma");

                    String[] respArray = biblica.getString("txt_responsorio").split("\\|");
                    sRespLBreve = utilClass.getResponsorio(respArray, nForma);
                    sLecturaBreve = CSS_RED_A + LECTURA_BREVE + NBSP_4 + biblica.getString("lbreve_ref") + CSS_RED_Z + BRS +
                            biblica.getString("txt_lbreve") + BRS;
                    sRespLBreve = RESP_BREVE + BRS + sRespLBreve;

                }
                String sAntifonaCE = ce.getString("txt_antifonace");

                //Otro dolorcito de cabeza a resolver, las antífonas del CE de los Domingos,
                // las cuales son trienales. Una posible solución sería incluirlas en un array


                sAntifonaCE = BRS + PRE_ANT + sAntifonaCE + BRS;
                String sPrecesIntro = preces.getString("txt_preces_intro");
                String sPreces = "";

                if (!utilClass.isNull(sPrecesIntro)) {
                    String[] introArray = preces.getString("txt_preces_intro").split("\\|");
                    String sPrecesCuerpo = utilClass.getFormato(preces.getString("txt_preces"));
                    sPreces = PRECES + utilClass.getPreces(introArray, sPrecesCuerpo);
                }

                String sOracion = ORACION + utilClass.getFormato(jsonContenido.getString("oracion"));

                sb.append(VI_TITULO);
                sb.append(sMensaje);
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
                //           sb.append(sPrecesIntro);
                sb.append(SEPARADOR);
                sb.append(sPreces);
                sb.append(SEPARADOR);
                sb.append(PADRENUESTRO_TITULO);
                sb.append(utilClass.getFormato(PADRENUESTRO));
                sb.append(sOracion);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.item_voz) {
            String[] strPrimera = strContenido.toString().split(SEPARADOR);
            new TTS(getApplicationContext(), strPrimera);
        }

        if (id == R.id.item_calendario) {
            Intent i = new Intent(this, CalendarioActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

}
