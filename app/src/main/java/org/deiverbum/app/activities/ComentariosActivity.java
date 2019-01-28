package org.deiverbum.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.deiverbum.app.R;
import org.deiverbum.app.utils.TTS;
import org.deiverbum.app.utils.UtilsOld;
import org.deiverbum.app.utils.ZoomTextView;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import static org.deiverbum.app.utils.Constants.PACIENCIA;
import static org.deiverbum.app.utils.Constants.SEPARADOR;
import static org.deiverbum.app.utils.Constants.URL_COMENTARIOS;

public class ComentariosActivity extends AppCompatActivity {
    private static final String TAG = "ComentariosActivity";
    Spanned strContenido;
    ZoomTextView mTextView;
    private UtilsOld utilClass;
    private RequestQueue requestQueue;
    private String strFechaHoy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*Variables*/
        utilClass = new UtilsOld();
        requestQueue = Volley.newRequestQueue(this);
        strFechaHoy = (this.getIntent().getExtras() != null) ? getIntent().getStringExtra("FECHA") : utilClass.getHoy();
        //final ProgressBar progressBar = findViewById(R.id.progressBar);

        mTextView = findViewById(R.id.tv_Zoomable);
        mTextView.setText(UtilsOld.fromHtml(PACIENCIA));

        /*Variables*/


        mTextView = findViewById(R.id.tv_Zoomable);

//        mTextView.setText(UtilsOld.fromHtml(PACIENCIA));

        utilClass = new UtilsOld();
        requestWithSomeHttpHeaders();
        requestQueue = Volley.newRequestQueue(this);
        strFechaHoy = (this.getIntent().getExtras() != null) ? getIntent().getStringExtra("FECHA") : utilClass.getHoy();
        Log.d(TAG, URL_COMENTARIOS);
/*
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, URL_COMENTARIOS + strFechaHoy,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG,response.toString());

                        progressBar.setVisibility(View.INVISIBLE);
                        mTextView.setText(UtilsOld.fromHtml(response.toString()));
                        //strContenido = UtilsOld.fromHtml(response);

                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        String sError = VolleyErrorHelper.getMessage(error, getApplicationContext());
                        progressBar.setVisibility(View.INVISIBLE);
                        mTextView.setText(UtilsOld.fromHtml(sError));


                    }
                })
        {
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("user", "appLiturgiaPlus");
                headers.put("pwd", "myPass");
                headers.put("endpoint", "oraciones");
                headers.put("fecha", strFechaHoy);

                return headers;
            }
        };

*/



/*
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjReq);
        progressBar.setVisibility(View.VISIBLE);
*/


        StringBuilder sb = new StringBuilder();


    }

    public void requestWithSomeHttpHeaders() {
        final ProgressBar progressBar = findViewById(R.id.progressBar);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://deiverbum.org/api/v2/comentarios/20181109";
        StringRequest getRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        progressBar.setVisibility(View.INVISIBLE);
                        mTextView = findViewById(R.id.tv_Zoomable);
                        //String repuesta = new String(response, "ISO-8859-1");

                        try {
                            byte[] bytesResponse = response.getBytes(Charset.forName("ISO-8859-1"));
                            String newString = new String(bytesResponse, "ISO-8859-1");
                            mTextView.setText("--." + newString);
                            Log.d(TAG, newString);

                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("ERROR", "error => " + error.toString());
                        progressBar.setVisibility(View.INVISIBLE);

                    }
                }
        ) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                //params.put("Content-Type" ,"text/plain; charset=utf-8");
                params.put("User", "usr");
                //params.put("Accept-Language", "fr");

                return params;
            }
            /*
            @Override
            public String getBodyContentType()
            {
                return "text/plain; charset=utf-8";
            }
*/


        };
        queue.add(getRequest);

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
                new TTS(getApplicationContext(), strPrimera);
            }
        }

        if (id == R.id.item_calendario) {
            Intent i = new Intent(this, CalendarioActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

}
