package org.deiverbum.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.deiverbum.app.R;
import org.deiverbum.app.model.Breviario;
import org.deiverbum.app.model.Completas;
import org.deiverbum.app.model.Kyrie;
import org.deiverbum.app.model.LecturaBreve;
import org.deiverbum.app.model.NuncDimitis;
import org.deiverbum.app.model.RitosIniciales;
import org.deiverbum.app.model.Salmodia;
import org.deiverbum.app.utils.TTS;
import org.deiverbum.app.utils.Utils;
import org.deiverbum.app.utils.UtilsOld;
import org.deiverbum.app.utils.VolleyErrorHelper;
import org.deiverbum.app.utils.ZoomTextView;
import org.json.JSONException;
import org.json.JSONObject;

import static org.deiverbum.app.utils.Constants.MY_DEFAULT_TIMEOUT;
import static org.deiverbum.app.utils.Constants.PACIENCIA;
import static org.deiverbum.app.utils.Constants.SEPARADOR;
import static org.deiverbum.app.utils.Constants.URL_COMPLETAS;

public class CompletasActivity extends AppCompatActivity {
    private static final String TAG = "CompletasActivity";
    Spanned strContenido;
    ZoomTextView mTextView;
    private UtilsOld utilClass;
    private RequestQueue requestQueue;
    private String strFechaHoy;
    private TTS tts;
    private StringBuilder sbReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mixto);
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

        StringRequest sRequest = new StringRequest(Request.Method.GET, URL_COMPLETAS + strFechaHoy,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String sResponse) {
                        SpannableStringBuilder str = getResponseData(sResponse);
                        progressBar.setVisibility(View.INVISIBLE);
                        //mTextView.setText(UtilsOld.fromHtml(sResponse));
                        mTextView.setText(str, TextView.BufferType.SPANNABLE);

                        strContenido = UtilsOld.fromHtml(sResponse);
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

    protected SpannableStringBuilder getResponseData(String jsonDatos) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        try {
            JSONObject jo = new JSONObject(jsonDatos);
            sbReader = new StringBuilder();
            Gson gson = new Gson();
            JSONObject jsonBreviario = jo.getJSONObject("breviario");

            //Breviario breviario = gson.fromJson(String.valueOf(jsonDatos), Breviario.class);
            Breviario breviario = gson.fromJson(String.valueOf(jsonBreviario), Breviario.class);
            Completas completas = breviario.getCompletas();
            RitosIniciales ri = completas.getRitosIniciales();
            Kyrie kyrie = ri.getKyrie();

            Salmodia salmodia = completas.getSalmodia();
            LecturaBreve lecturaBreve = completas.getLecturaBreve();


            NuncDimitis nunc = completas.getNuncDimitis();
            //Log.d(TAG,himno.getTexto().toString());
            sb.append(kyrie.getIntroduccion());
            sb.append(Utils.LS2);
            sb.append(kyrie.getTexto());
            sb.append(Utils.LS2);
            sb.append(kyrie.getConclusion());
            sb.append(Utils.LS2);

            sb.append(String.valueOf(kyrie.getTipo()));

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


            sbReader.append(kyrie.getIntroduccion());
            sbReader.append(Utils.LS2);
            sbReader.append(kyrie.getTexto());
            sbReader.append(Utils.LS2);
            sbReader.append(kyrie.getConclusion());
            sbReader.append(Utils.LS2);

            sbReader.append(String.valueOf(kyrie.getTipo()));


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
        int id = item.getItemId();

        if (id == R.id.item_voz) {
            if (!strContenido.equals("Error")) {

                String[] strPrimera = strContenido.toString().split(SEPARADOR);
                tts = new TTS(getApplicationContext(), strPrimera);
            }
        }

        if (id == R.id.item_calendario) {
            Intent i = new Intent(this, CalendarioActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (tts != null) {
            tts.cerrar();
        }

    }




}
