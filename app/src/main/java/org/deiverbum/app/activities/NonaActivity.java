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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static org.deiverbum.app.utils.Constants.BRS;
import static org.deiverbum.app.utils.Constants.CSS_RED_A;
import static org.deiverbum.app.utils.Constants.CSS_RED_Z;
import static org.deiverbum.app.utils.Constants.HIMNO;
import static org.deiverbum.app.utils.Constants.HI_TITULO;
import static org.deiverbum.app.utils.Constants.LECTURA_BREVE;
import static org.deiverbum.app.utils.Constants.MY_DEFAULT_TIMEOUT;
import static org.deiverbum.app.utils.Constants.NBSP_4;
import static org.deiverbum.app.utils.Constants.ORACION;
import static org.deiverbum.app.utils.Constants.PACIENCIA;
import static org.deiverbum.app.utils.Constants.SEPARADOR;
import static org.deiverbum.app.utils.Constants.URL_NONA;

public class NonaActivity extends AppCompatActivity {
    private static final String TAG = "NonaActivity";
    Spanned strContenido;
    JsonObjectRequest jsonObjectRequest;
    ZoomTextView mTextView;
    private Utils utilClass;
    private RequestQueue requestQueue;
    private String strFechaHoy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nona);
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

                Request.Method.GET, URL_NONA + strFechaHoy,null,
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

            JSONObject jsonBreviario = jsonDatos.getJSONObject("breviario");
            JSONObject jsonInfo = jsonBreviario.getJSONObject("info");
            JSONObject jsonContenido = jsonBreviario.getJSONObject("contenido");
            JSONObject jsonSalmodia = jsonContenido.getJSONObject("salmodia");
            JSONObject biblica = jsonContenido.getJSONObject("biblica");
            int tipoAntifonas=jsonSalmodia.getInt("tipo");
            String strFecha = jsonInfo.getString("fecha") + BRS;
            String strTiempo = "<h1>" + jsonInfo.getString("tiempo") + "</h1>";
            String strSemana = "<h4>" + jsonInfo.getString("semana") + "</h4>";
            String strSalterio = CSS_RED_A+jsonInfo.getString("salterio") + CSS_RED_Z+BRS;
            String sMensaje = jsonInfo.getString("mensaje");

            String sHimno = HIMNO + utilClass.getFormato(jsonContenido.getString("himno")) + BRS;
            JSONArray arrSalmos=jsonSalmodia.getJSONArray("salmos");

            StringBuilder sbSalmos=new StringBuilder();
            for (int i = 0; i < arrSalmos.length(); i++) {
                JSONObject jsonSalmo = arrSalmos.getJSONObject(i);

                sbSalmos.append(jsonSalmo.getString("orden"));
                sbSalmos.append(BRS);
                sbSalmos.append(jsonSalmo.getString("antifona"));
                sbSalmos.append(BRS);
                sbSalmos.append(jsonSalmo.getString("ref"));
                sbSalmos.append(BRS);
                sbSalmos.append(jsonSalmo.getString("tema"));
                sbSalmos.append(BRS);
                sbSalmos.append(jsonSalmo.getString("intro"));
                sbSalmos.append(BRS);
                sbSalmos.append(jsonSalmo.getString("parte"));
                sbSalmos.append(BRS);
                sbSalmos.append(jsonSalmo.getString("salmo"));
                sbSalmos.append(BRS);
            }

            String sBiblicaResp = biblica.getString("responsorio");
            String sResponsorio = "";
            if (sBiblicaResp != null && !sBiblicaResp.isEmpty() && !sBiblicaResp.equals("null")) {

                String[] respArray = sBiblicaResp.split("\\|");
                sResponsorio = utilClass.getResponsorio(respArray, 31);
            }

            String sBiblica = CSS_RED_A + LECTURA_BREVE + NBSP_4
                    + biblica.getString("ref") + CSS_RED_Z + BRS + biblica.getString("texto") + BRS;

            String sOracion = ORACION + utilClass.getFormato(jsonContenido.getString("oracion"));
            /*LLenamos el sb en el orden*/
            sb.append(strFecha);
            sb.append(strTiempo);
            sb.append(strSemana);
            sb.append(HI_TITULO);
            sb.append(strSalterio);
            sb.append(sMensaje);
            sb.append(SEPARADOR);

            sb.append(sHimno);
            sb.append(SEPARADOR);
            //           sb.append(SALMODIA);sb.append(salmos.toString());
 /*
            sb.append(salmoUnoCompleto);
            sb.append(SEPARADOR);
            sb.append(salmoDosCompleto);
            sb.append(SEPARADOR);
            sb.append(salmoTresCompleto);
            sb.append(SEPARADOR);
*/
            sb.append(utilClass.getSalmos(arrSalmos,tipoAntifonas));
            sb.append(sBiblica);
            sb.append(SEPARADOR);
            sb.append(sResponsorio);
            sb.append(SEPARADOR);
            sb.append(sOracion);
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
