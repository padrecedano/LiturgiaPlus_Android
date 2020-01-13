package org.deiverbum.app.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableStringBuilder;
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
import org.deiverbum.app.model.HomiliaCompleta;
import org.deiverbum.app.model.Liturgia;
import org.deiverbum.app.model.MetaLiturgia;
import org.deiverbum.app.utils.TTS;
import org.deiverbum.app.utils.Utils;
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
    ZoomTextView mTextView;
    JsonObjectRequest jsonObjectRequest;
    private RequestQueue requestQueue;
    private String strFechaHoy;
    private TTS tts;
    private StringBuilder sbReader;
    private SpannableStringBuilder ssb;
    private Liturgia mLiturgia;
    private ProgressBar progressBar;
    private Menu menu;
    private boolean isVoiceOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homilias);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*Variables*/
        requestQueue = Volley.newRequestQueue(this);
        strFechaHoy = (this.getIntent().getExtras() != null) ? getIntent().getStringExtra("FECHA") : Utils.getHoy();
        progressBar = findViewById(R.id.progressBar);

        mTextView = findViewById(R.id.tv_Zoomable);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        float fontSize = Float.parseFloat(prefs.getString("font_size", "18"));
        isVoiceOn = prefs.getBoolean("voice", true);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        mTextView.setText(Utils.fromHtml(PACIENCIA));
        launchVolley();
    }

    public void launchVolley() {
        requestQueue = Volley.newRequestQueue(this);
        jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, URL_HOMILIAS + strFechaHoy, null,
                response -> {
                    try {
                        Gson gson = new Gson();
                        JSONObject mJson = response.getJSONObject("liturgia");
                        mLiturgia = gson.fromJson(mJson.toString(), Liturgia.class);
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

            case R.id.item_settings:
                i = new Intent(this, SettingsActivity.class);
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

    private void showData() {
        ssb = new SpannableStringBuilder();
        List<HomiliaCompleta> a = mLiturgia.getHomiliaCompleta();
        MetaLiturgia meta = mLiturgia.getMetaLiturgia();


        ssb.append(meta.getFecha());
        ssb.append(Utils.LS2);
        ssb.append(Utils.toH2(meta.getTiempoNombre()));
        ssb.append(Utils.LS);
        ssb.append(Utils.toH3(meta.getSemana()));
        ssb.append(Utils.LS2);
        ssb.append(Utils.toH3Red("HOMILÍAS"));
        ssb.append(Utils.LS2);
        if (isVoiceOn) {
            sbReader = new StringBuilder();
            sbReader.append(Utils.fromHtml("<p>" + meta.getFecha() + ".</p>"));
            sbReader.append(SEPARADOR);
            sbReader.append(Utils.fromHtml("<p>HOMILÍAS</p>"));
            sbReader.append(SEPARADOR);
        }
        for (HomiliaCompleta s : a) {
            ssb.append(Utils.toH3Red(s.padre));
            ssb.append(Utils.LS2);
            ssb.append(s.getObraSpan());
            ssb.append(Utils.LS);
            ssb.append(s.getTemaSpan());
            ssb.append(s.getFechaSpan());
            ssb.append(Utils.LS2);

            ssb.append(s.getTextoLimpio());
            ssb.append(Utils.LS2);
            if (isVoiceOn) {

                sbReader.append(s.padre);
                sbReader.append(SEPARADOR);
                sbReader.append(s.getTexto());
                sbReader.append(SEPARADOR);
            }
        }

        if (isVoiceOn) {

            if (sbReader.length() > 0) {
                menu.findItem(R.id.item_voz).setVisible(true);
            }
        }
        progressBar.setVisibility(View.INVISIBLE);
        mTextView.setText(ssb, TextView.BufferType.SPANNABLE);
    }
}
