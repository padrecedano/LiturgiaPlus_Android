package org.deiverbum.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
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
import org.deiverbum.app.model.Breviario;
import org.deiverbum.app.model.Himno;
import org.deiverbum.app.model.Intermedia;
import org.deiverbum.app.model.LecturaBreve;
import org.deiverbum.app.model.MetaLiturgia;
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
import static org.deiverbum.app.utils.Constants.URL_TERCIA;
import static org.deiverbum.app.utils.Utils.LS2;

public class TerciaActivity extends AppCompatActivity {
    private static final String TAG = "TerciaActivity";
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
        setContentView(R.layout.activity_tercia);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTextView =  findViewById(R.id.tv_Zoomable);

        utilClass = new UtilsOld();
        strFechaHoy = (getIntent().getExtras() != null) ? getIntent().getStringExtra("FECHA") : utilClass.getHoy();
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        requestQueue = Volley.newRequestQueue(this);
        final ProgressBar progressBar = findViewById(R.id.progressBar);

        mTextView.setText(UtilsOld.fromHtml(PACIENCIA));
        jsonObjectRequest = new JsonObjectRequest(

                Request.Method.GET, URL_TERCIA + strFechaHoy,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        SpannableStringBuilder resp = getResponseData(response);
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
        sbReader = new StringBuilder();
        Gson gson = new Gson();
        SpannableStringBuilder sb = new SpannableStringBuilder();

        try {
            JSONObject jsonBreviario = jsonDatos.getJSONObject("breviario");
            Breviario breviario = gson.fromJson(String.valueOf(jsonBreviario), Breviario.class);
            MetaLiturgia meta = breviario.getMetaLiturgia();
            Intermedia hi = breviario.getIntermedia();
            Himno himno = hi.getHimno();
            Salmodia salmodia = hi.getSalmodia();
            LecturaBreve lecturaBreve = hi.getLecturaBreve();
            String hora = "HORA INTERMEDIA: TERCIA";

            sb.append(meta.getFecha());
            sb.append(Utils.LS2);

            sb.append(Utils.toH2(meta.getTiempo()));
            sb.append(Utils.LS);
            sb.append(Utils.toH3(meta.getSemana()));
            sb.append(Utils.LS2);

            sb.append(Utils.toH3Red(hora));
            sb.append(Utils.LS);
            sb.append(Utils.LS2);

            sb.append(Utils.fromHtmlToSmallRed(meta.getSalterio()));
            sb.append(Utils.LS2);


            sb.append(Utils.getSaludoDiosMio());
            sb.append(Utils.LS2);

            sb.append(himno.getHeader());
            sb.append(Utils.LS2);
            sb.append(himno.getTexto());
            sb.append(Utils.LS2);

            sb.append(salmodia.getHeader());
            sb.append(Utils.LS2);
            sb.append(salmodia.getSalmoCompleto(0));

            sb.append(Utils.LS);
            sb.append(lecturaBreve.getHeaderLectura());
            sb.append(Utils.LS2);
            sb.append(lecturaBreve.getTexto());
            sb.append(Utils.LS2);
            sb.append(lecturaBreve.getHeaderResponsorio());
            sb.append(Utils.LS2);
            sb.append(lecturaBreve.getResponsorio());
            sb.append(Utils.formatTitle("ORACIÓN"));
            sb.append(LS2);
            sb.append(Utils.fromHtml(hi.getOracion()));
            sb.append(LS2);
            sb.append(Utils.getConclusionIntermedia());
            /*For TTS*/
            sbReader.append(Utils.fromHtml("<p>" + meta.getFecha() + ".</p>"));
            sbReader.append(SEPARADOR);
            sbReader.append(Utils.fromHtml("<p>" + hora + "</p>"));
            sbReader.append(SEPARADOR);

            sbReader.append(Utils.getSaludoDiosMioForReader());
            sbReader.append(SEPARADOR);

            sbReader.append("Himno");
            sbReader.append(SEPARADOR);
            sbReader.append(himno.getTexto());
            sbReader.append(SEPARADOR);

            sbReader.append("Salmodia");
            sbReader.append(SEPARADOR);
            sbReader.append(salmodia.getSalmosForRead());
            sbReader.append(SEPARADOR);

            sbReader.append("Lectura breve");
            sbReader.append(SEPARADOR);
            sbReader.append(lecturaBreve.getTexto());
            sbReader.append(SEPARADOR);
            sbReader.append("Responsorio breve");
            sbReader.append(SEPARADOR);
            sbReader.append(lecturaBreve.getResponsorioForRead());

            sbReader.append(Utils.formatTitle("ORACIÓN"));
            sbReader.append(SEPARADOR);
            sbReader.append(Utils.fromHtml(hi.getOracion()));
            sbReader.append(SEPARADOR);

            sbReader.append(Utils.getConclusionIntermediaForRead());
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
