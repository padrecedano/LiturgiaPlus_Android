package org.deiverbum.app.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
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
import org.deiverbum.app.model.Comentario;
import org.deiverbum.app.model.ComentarioBiblico;
import org.deiverbum.app.model.Liturgia;
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
import static org.deiverbum.app.utils.Constants.URL_COMENTARIOS;
import static org.deiverbum.app.utils.Utils.LS2;

public class ComentariosActivity extends AppCompatActivity {
    private static final String TAG = "ComentariosActivity";
    ZoomTextView mTextView;
    JsonObjectRequest jsonObjectRequest;
    private RequestQueue requestQueue;
    private String strFechaHoy;
    private ProgressBar progressBar;
    private Comentario mComentario;
    private StringBuilder sbReader;
    private TTS tts;
    private Menu menu;
    private Liturgia mLiturgia;
    private boolean isVoiceOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                Request.Method.GET, URL_COMENTARIOS + strFechaHoy, null,
                response -> {
                    try {
                        Gson gson = new Gson();
                        JSONObject mJson = response.getJSONObject("biblia");
                        if (mJson.has("error")) {
                            Spanned errorMsg = Utils.fromHtml(mJson.getString("error"));
                            mTextView.setText(errorMsg);
                            progressBar.setVisibility(View.INVISIBLE);
                            return;
                        }
                        mComentario = gson.fromJson(mJson.toString(), Comentario.class);
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

    protected void showData() {
        List<ComentarioBiblico> a = mComentario.getComentariosBiblicos();
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        ssb.append(Utils.toH3Red("COMENTARIOS BÍBLICOS"));
        ssb.append(Utils.LS2);
        ssb.append(Utils.formatSubTitle(mComentario.getPericopa()));
        ssb.append(LS2);
        ssb.append(mComentario.getComentarioCompleto());

        if (isVoiceOn) {
            sbReader = new StringBuilder();
            sbReader.append(Utils.fromHtml("<p>COMENTARIOS AL EVANGELIO.</p>"));
            sbReader.append(SEPARADOR);
            sbReader.append(mComentario.getPericopa());
            sbReader.append(SEPARADOR);
            sbReader.append(mComentario.getComentarioCompletoForRead());

            if (sbReader.length() > 0) {
                menu.findItem(R.id.item_voz).setVisible(true);
            }
        }
        progressBar.setVisibility(View.INVISIBLE);
        mTextView.setText(ssb, TextView.BufferType.SPANNABLE);
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
                String html = String.valueOf(Utils.fromHtml(sbReader.toString()));
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
}
