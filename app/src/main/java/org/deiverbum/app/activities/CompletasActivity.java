package org.deiverbum.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
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
import org.deiverbum.app.model.Conclusion;
import org.deiverbum.app.model.Himno;
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
import static org.deiverbum.app.utils.Utils.LS2;

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

            Breviario breviario = gson.fromJson(String.valueOf(jsonBreviario), Breviario.class);
            Completas completas = breviario.getCompletas();
            RitosIniciales ri = completas.getRitosIniciales();
            Kyrie kyrie = ri.getKyrie();
            Himno himno = completas.getHimno();

            Salmodia salmodia = completas.getSalmodia();
            LecturaBreve lecturaBreve = completas.getLecturaBreve();


            NuncDimitis nunc = completas.getNuncDimitis();
            Conclusion conclusion = completas.getConclusion();


            sb.append(Utils.toH3Red("COMPLETAS"));
            sb.append(Utils.LS2);

            sb.append(breviario.getInvocacion());
            sb.append(Utils.LS2);

            sb.append(kyrie.getIntroduccion());
            sb.append(Utils.LS2);
            sb.append(kyrie.getTexto());
            sb.append(Utils.LS2);
            sb.append(kyrie.getConclusion());
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

            sb.append(nunc.getHeader());
            sb.append(Utils.LS2);

            sb.append(nunc.getAntifona());
            sb.append(Utils.LS2);
            sb.append(Utils.fromHtml(nunc.getTexto()));
            sb.append(Utils.LS2);
            sb.append(Utils.getFinSalmo());
            sb.append(LS2);

            sb.append(Utils.formatTitle("ORACIÓN"));
            sb.append(LS2);

            sb.append(Utils.fromHtml(completas.getOracion()));
            sb.append(LS2);

            sb.append(Utils.formatTitle("CONCLUSIÓN"));
            sb.append(LS2);
            sb.append(conclusion.getBendicion());
            sb.append(LS2);

            sb.append(Utils.formatTitle("ANTÍFONA FINAL DE LA SANTÍSIMA VIRGEN"));
            sb.append(LS2);

            sb.append(conclusion.getAntVirgen());
            sbReader.append("ho");
            sbReader.append(SEPARADOR);

            sbReader.append("la");

/*
            sbReader.append(Utils.fromHtml("<p>COMPLETAS</p>"));
            sbReader.append(Utils.LS);
            sbReader.append(breviario.getInvocacionForRead());
            sbReader.append(Utils.LS);
            sbReader.append(Utils.LS);
            sbReader.append(kyrie.getIntroduccionForRead());

            sbReader.append(kyrie.getTextoForRead());
            sbReader.append(kyrie.getConclusionForRead());
            sbReader.append(SEPARADOR);

            sbReader.append(Utils.fromHtml("<p>HIMNO</p>"));
            sbReader.append(himno.getTexto());
            sbReader.append(SEPARADOR);


            sbReader.append(salmodia.getHeader());
            sbReader.append(salmodia.getSalmosForRead());

            sbReader.append(Utils.fromHtml("<p>LECTURA BREVE.</p>"));
            sbReader.append(lecturaBreve.getTexto());
            sbReader.append(Utils.fromHtml("<p>RESPONSORIO.</p>"));
            sbReader.append(lecturaBreve.getResponsorioForRead());
            sbReader.append(SEPARADOR);

            sbReader.append(nunc.getHeader());
            sbReader.append(nunc.getAntifonaForRead());
            sbReader.append(nunc.getTexto());
            sbReader.append(Utils.getFinSalmo());
            sbReader.append(SEPARADOR);

            sbReader.append(Utils.fromHtml("<p>ORACIÓN.</p>"));
            sbReader.append(completas.getOracion());
            sbReader.append(conclusion.getBendicionForRead());
            sbReader.append(Utils.fromHtml("<p>ANTÍFONA FINAL DE LA SANTÍSIMA VIRGEN.</p>"));
            sbReader.append(conclusion.getAntVirgen());
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
