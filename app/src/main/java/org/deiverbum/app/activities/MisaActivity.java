package org.deiverbum.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.deiverbum.app.R;
import org.deiverbum.app.utils.TTS;
import org.deiverbum.app.utils.Utils;
import org.deiverbum.app.utils.VolleyErrorHelper;
import org.deiverbum.app.utils.ZoomTextView;

import static org.deiverbum.app.utils.Constants.MY_DEFAULT_TIMEOUT;
import static org.deiverbum.app.utils.Constants.PACIENCIA;
import static org.deiverbum.app.utils.Constants.SEPARADOR;
import static org.deiverbum.app.utils.Constants.URL_MISA;

public class MisaActivity extends AppCompatActivity {
    private static final String TAG = "MisaActivity";
    Spanned strContenido;
    ZoomTextView mTextView;
    private RequestQueue requestQueue;
    private String strFechaHoy;
    private TTS tts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_misa);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*Variables*/
        requestQueue = Volley.newRequestQueue(this);
        strFechaHoy = (this.getIntent().getExtras() != null) ? getIntent().getStringExtra("FECHA") : Utils.getHoy();
        final ProgressBar progressBar = findViewById(R.id.progressBar);

        mTextView = findViewById(R.id.tv_Zoomable);
        mTextView.setText(Utils.fromHtml(PACIENCIA));

        StringRequest sRequest = new StringRequest(Request.Method.GET, URL_MISA + strFechaHoy,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String sResponse) {
                        progressBar.setVisibility(View.INVISIBLE);
                        mTextView.setText(Utils.fromHtml(sResponse));
                        strContenido = Utils.fromHtml(sResponse);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String sError = VolleyErrorHelper.getMessage(error, getApplicationContext());
                progressBar.setVisibility(View.INVISIBLE);
                mTextView.setText(Utils.fromHtml(sError));
                strContenido = Utils.fromHtml("Error");

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
