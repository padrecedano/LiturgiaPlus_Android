package org.deiverbum.app.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.deiverbum.app.R;
import org.deiverbum.app.model.Liturgia;
import org.deiverbum.app.model.LiturgiaPalabra;
import org.deiverbum.app.model.MetaLiturgia;
import org.deiverbum.app.model.Misa;
import org.deiverbum.app.utils.TTS;
import org.deiverbum.app.utils.Utils;
import org.deiverbum.app.utils.VolleyErrorHelper;
import org.deiverbum.app.utils.ZoomTextView;
import org.json.JSONException;
import org.json.JSONObject;

import static org.deiverbum.app.utils.Constants.MY_DEFAULT_TIMEOUT;
import static org.deiverbum.app.utils.Constants.PACIENCIA;
import static org.deiverbum.app.utils.Constants.SEPARADOR;
import static org.deiverbum.app.utils.Constants.URL_LECTURAS;

public class LecturasActivity extends AppCompatActivity {
    private static final String TAG = "LecturasActivity";
    ZoomTextView mTextView;
    private RequestQueue requestQueue;
    private String strFechaHoy;
    JsonObjectRequest jsonObjectRequest;
    private StringBuilder sbReader;
    private Menu menu;
    private ProgressBar progressBar;
    private Liturgia mLiturgia;
    private TTS tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturas);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        requestQueue = Volley.newRequestQueue(this);
        strFechaHoy = (this.getIntent().getExtras() != null) ? getIntent().getStringExtra("FECHA") : Utils.getHoy();
        progressBar = findViewById(R.id.progressBar);
        mTextView = findViewById(R.id.tv_Zoomable);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        float fontSize = Float.parseFloat(prefs.getString("font_size", "18"));
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        mTextView.setText(Utils.fromHtml(PACIENCIA));
        mTextView.setTextIsSelectable(true);
        Log.d(TAG, "lect_" + URL_LECTURAS + strFechaHoy);
        launchVolley();
    }

    public void launchVolley() {
        requestQueue = Volley.newRequestQueue(this);
        jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, URL_LECTURAS + strFechaHoy, null,
                response -> {
                    try {
                        Gson gson = new Gson();
                        JSONObject mJson = response.getJSONObject("liturgia");
                        mLiturgia = gson.fromJson(mJson.toString(), Liturgia.class);
                        Misa m = mLiturgia.getMisa();
                        Log.d(TAG, "L__" + mJson.toString());
                        showData();
                    } catch (JSONException e) {
                        mTextView.setText(e.getMessage());
                    }
                },
                error -> {
                    String sError = VolleyErrorHelper.getMessage(error, getApplicationContext());
                    mTextView.setText(Utils.fromHtml(sError));
                    progressBar.setVisibility(View.INVISIBLE);
                }
        );

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
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
                String notQuotes = Utils.stripQuotation(sbReader.toString());
                String html = String.valueOf(Utils.fromHtml(notQuotes));
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

    protected void showData() {
        sbReader = new StringBuilder();
        SpannableStringBuilder sb = new SpannableStringBuilder();
        Misa misa = mLiturgia.getMisa();
        MetaLiturgia meta = mLiturgia.getMetaLiturgia();
        LiturgiaPalabra lp = misa.getLiturgiaPalabra();
        String hora = "LECTURAS DE LA MISA";
        sb.append(meta.getFecha());
        sb.append(Utils.LS2);
        sb.append(Utils.toH2(meta.getTiempoNombre()));
        sb.append(Utils.LS);
        sb.append(Utils.toH3(meta.getSemana()));
        sb.append(Utils.LS2);
        sb.append(Utils.toH3Red(hora));
        sb.append(Utils.LS);
        sb.append(lp.getLiturgiaPalabra());
        sb.append(Utils.LS2);
        sb.append(Utils.toSmallSize("Versión bíblica oficial \n \u00a9Conferencia Episcopal Española"));
        sbReader.append(lp.getLiturgiaPalabraforRead());
        if (sbReader.length() > 0) {
            menu.findItem(R.id.item_voz).setVisible(true);
        }
        progressBar.setVisibility(View.INVISIBLE);
        mTextView.setText(sb, TextView.BufferType.SPANNABLE);
    }


}
