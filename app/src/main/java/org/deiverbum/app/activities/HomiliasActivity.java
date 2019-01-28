package org.deiverbum.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
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
import com.google.gson.Gson;

import org.deiverbum.app.R;
import org.deiverbum.app.model.HomiliaCompleta;
import org.deiverbum.app.model.Liturgia;
import org.deiverbum.app.utils.TTS;
import org.deiverbum.app.utils.Utils;
import org.deiverbum.app.utils.UtilsOld;
import org.deiverbum.app.utils.VolleyErrorHelper;
import org.deiverbum.app.utils.ZoomTextView;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static org.deiverbum.app.utils.Constants.MY_DEFAULT_TIMEOUT;
import static org.deiverbum.app.utils.Constants.PACIENCIA;
import static org.deiverbum.app.utils.Constants.SEPARADOR;
import static org.deiverbum.app.utils.Constants.URL_HOMILIAS;

public class HomiliasActivity extends AppCompatActivity {
    private static final String TAG = "HomiliasActivity";
    Spanned strContenido;
    String str;
    ZoomTextView mTextView;
    private UtilsOld utilClass;
    private RequestQueue requestQueue;
    private String strFechaHoy;
    private TTS tts;
    private StringBuilder sbReader;
    private SpannableStringBuilder ssb;
    private String sFormateado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homilias);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        /*Variables*/
        utilClass = new UtilsOld();
        requestQueue = Volley.newRequestQueue(this);
        strFechaHoy = (this.getIntent().getExtras() != null) ? getIntent().getStringExtra("FECHA") : utilClass.getHoy();
        final ProgressBar progressBar = findViewById(R.id.progressBar);

        mTextView = findViewById(R.id.tv_Zoomable);
        mTextView.setText(UtilsOld.fromHtml(PACIENCIA));


        JsonObjectRequest sRequest = new JsonObjectRequest(Request.Method.GET, URL_HOMILIAS + strFechaHoy, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject sResponse) {
                        progressBar.setVisibility(View.INVISIBLE);
                        //String sFormateado=getResponseData(sResponse);

                        strContenido = getResponseData(sResponse);
                        //strContenido=Utils.doFormat(strContenido);
                        mTextView.setText(strContenido);

                        //strContenido=Utils.fromHtml(sbReader.toString());//
                        //str=strContenido.toString().replaceAll("<p>", "|<p>");

                        //Log.d(TAG,URL_HOMILIAS+strFechaHoy);
                        //strContenido = UtilsOld.fromHtml(sResponse);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String sError = VolleyErrorHelper.getMessage(error, getApplicationContext());
                progressBar.setVisibility(View.INVISIBLE);
                mTextView.setText(UtilsOld.fromHtml(sError));
                strContenido = UtilsOld.fromHtml("Error");

            }
        });


        sRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(sRequest);
        progressBar.setVisibility(View.VISIBLE);
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
                String[] strPrimera = html.split(SEPARADOR);
                tts = new TTS(getApplicationContext(), strPrimera);
                return true;

            case R.id.item_calendario:
                Intent i = new Intent(this, CalendarioActivity.class);
                startActivity(i);
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


    protected SpannableStringBuilder getResponseData(JSONObject jsonDatos) {
        Gson gson = new Gson();
        String ant = getString(R.string.ant);

        JSONObject jsonObject = null;
        try {
            //jsonObject = new JSONObject(jsonDatos);
            ssb = new SpannableStringBuilder();
            sbReader = new StringBuilder();
            Liturgia liturgia = gson.fromJson(String.valueOf(jsonDatos.getJSONObject("liturgia")), Liturgia.class);

            //Liturgia liturgia = gson.fromJson(String.valueOf(jsonObject.getJSONObject("liturgia")), Liturgia.class);
            List<HomiliaCompleta> a = liturgia.getHomiliaCompleta();//.fromJson(jsonObject.getJSONObject("homiliaCompleta").toString(), HomiliaCompleta.class);

            for (HomiliaCompleta s : a) {
                Log.d(TAG, s.getPadre() + "++++");
                ssb.append(Utils.toH3Red(s.padre));
                ssb.append(Utils.LS2);
                ssb.append(s.getTexto());
                ssb.append(Utils.LS2);
                sbReader.append(s.padre);
                sbReader.append(SEPARADOR);
                sbReader.append(s.getTexto());
                sbReader.append(SEPARADOR);

            }

            //sbReader = new StringBuilder();
            //JSONObject jsonBreviario = jsonDatos.getJSONObject("breviario");
            //Liturgia liturgia = gson.fromJson(String.valueOf(jsonDatos), Liturgia.class);
            //HomiliaCompleta hom = liturgia.homiliaCompleta;//.fromJson(jsonDatos, Homilia.class);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ssb;
    }


}
