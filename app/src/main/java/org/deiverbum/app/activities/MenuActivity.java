package org.deiverbum.app.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.deiverbum.app.R;
import org.deiverbum.app.utils.UtilsOld;
import org.deiverbum.app.utils.VolleyErrorHelper;
import org.deiverbum.app.utils.ZoomTextView;

import static org.deiverbum.app.utils.Constants.MY_DEFAULT_TIMEOUT;
import static org.deiverbum.app.utils.Constants.PACIENCIA;
import static org.deiverbum.app.utils.Constants.URL_MENU;

public class MenuActivity extends AppCompatActivity {
    private static final String TAG = "MenuActivity";
    Spanned strContenido;
    ZoomTextView mTextView;
    private UtilsOld utilClass;
    private RequestQueue requestQueue;
    private String strFechaHoy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String menuOption  = (getIntent().getExtras() != null) ? getIntent().getStringExtra("OPTION") : "";
Log.d(TAG, "ITem: "+menuOption);


        requestQueue = Volley.newRequestQueue(this);
        strFechaHoy = (this.getIntent().getExtras() != null) ? getIntent().getStringExtra("FECHA") : utilClass.getHoy();
        final ProgressBar progressBar = findViewById(R.id.progressBar);

        mTextView = findViewById(R.id.tv_Zoomable);
        mTextView.setText(UtilsOld.fromHtml(PACIENCIA));

        StringRequest sRequest = new StringRequest(Request.Method.GET, URL_MENU + menuOption,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String sResponse) {
                        progressBar.setVisibility(View.INVISIBLE);
                        mTextView.setText(UtilsOld.fromHtml(sResponse));
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

}
