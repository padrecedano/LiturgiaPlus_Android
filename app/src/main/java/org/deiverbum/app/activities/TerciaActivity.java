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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.deiverbum.app.R;
import org.deiverbum.app.model.Breviario;
import org.deiverbum.app.model.Himno;
import org.deiverbum.app.model.Intermedia;
import org.deiverbum.app.model.LecturaBreve;
import org.deiverbum.app.model.Liturgia;
import org.deiverbum.app.model.MetaLiturgia;
import org.deiverbum.app.model.Salmodia;
import org.deiverbum.app.utils.TTS;
import org.deiverbum.app.utils.Utils;
import org.deiverbum.app.utils.VolleyErrorHelper;
import org.deiverbum.app.utils.ZoomTextView;
import org.json.JSONException;
import org.json.JSONObject;

import static org.deiverbum.app.utils.Constants.CALENDAR_PATH;
import static org.deiverbum.app.utils.Constants.MY_DEFAULT_TIMEOUT;
import static org.deiverbum.app.utils.Constants.PACIENCIA;
import static org.deiverbum.app.utils.Constants.SEPARADOR;
import static org.deiverbum.app.utils.Constants.URL_TERCIA;
import static org.deiverbum.app.utils.Utils.LS;
import static org.deiverbum.app.utils.Utils.LS2;

public class TerciaActivity extends AppCompatActivity {
    private static final String TAG = "TerciaActivity";
    JsonObjectRequest jsonObjectRequest;
    private ZoomTextView mTextView;
    private RequestQueue requestQueue;
    private String strFechaHoy;
    private TTS tts;
    private ProgressBar progressBar;
    private Liturgia mLiturgia;
    private StringBuilder sbReader;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tercia);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTextView = findViewById(R.id.tv_Zoomable);
        strFechaHoy = (getIntent().getExtras() != null) ? getIntent().getStringExtra("FECHA") : Utils.getHoy();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        float fontSize = Float.parseFloat(prefs.getString("font_size", "18"));
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        progressBar = findViewById(R.id.progressBar);
        mTextView.setText(Utils.fromHtml(PACIENCIA));
        progressBar.setVisibility(View.VISIBLE);
        launchFirestore();

    }

    public void launchFirestore() {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        String fechaDD = strFechaHoy.substring(6, 8);
        String fechaMM = strFechaHoy.substring(4, 6);
        String fechaYY = strFechaHoy.substring(0, 4);
        DocumentReference calRef = db.collection(CALENDAR_PATH).document(fechaYY).collection(fechaMM).document(fechaDD);
        calRef.addSnapshotListener((calSnapshot, e) -> {
            if ((calSnapshot != null) && calSnapshot.exists()) {
                Gson gson = new Gson();
                JsonElement jsonElement = gson.toJsonTree(calSnapshot.get("meta"));
                Log.d(TAG, calSnapshot.toString());

                mLiturgia = new Liturgia();
                mLiturgia.setMetaLiturgia(gson.fromJson(jsonElement, MetaLiturgia.class));
                DocumentReference dataRef = calSnapshot.getDocumentReference("lh.3");
                if (e != null || dataRef == null) {
                    launchVolley();
                    return;
                }
                dataRef.get().addOnSuccessListener((DocumentSnapshot dataSnapshot) -> {
                    mLiturgia.setBreviario(dataSnapshot.toObject(Breviario.class));
                    //Log.d(TAG,dataSnapshot.toString());
                    showData();
                });
            } else {
                launchVolley();
            }
        });
    }

    public void launchVolley() {
        requestQueue = Volley.newRequestQueue(this);
        jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, URL_TERCIA + strFechaHoy, null,
                response -> {
                    try {
                        Gson gson = new Gson();
                        String jsonBreviario = String.valueOf(new JSONObject(String.valueOf(response.getJSONObject("liturgia"))));
                        mLiturgia = gson.fromJson(jsonBreviario, Liturgia.class);
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
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sbReader = new StringBuilder();
        Breviario mBreviario = mLiturgia.getBreviario();
        Intermedia hi = mBreviario.getIntermedia();
        MetaLiturgia meta = mLiturgia.getMetaLiturgia();
        Himno himno = hi.getHimno();
        Salmodia salmodia = hi.getSalmodia();
        LecturaBreve lecturaBreve = hi.getLecturaBreve();
        String hora = "HORA INTERMEDIA: TERCIA";

        sb.append(meta.getFecha());
        sb.append(Utils.LS2);
        sb.append(Utils.toH2(meta.getTiempoNombre()));
        sb.append(LS);
        sb.append(Utils.toH3(meta.getSemana()));
        sb.append(Utils.LS2);

        sb.append(Utils.toH3Red(hora));
        sb.append(LS);
        sb.append(LS2);

        sb.append(Utils.fromHtmlToSmallRed(mBreviario.getMetaInfo()));
        sb.append(LS2);
        sb.append(Utils.getSaludoDiosMio());
        sb.append(LS2);

        sb.append(himno.getHeader());
        sb.append(LS2);
        sb.append(himno.getTextoSpan());
        sb.append(LS2);

        sb.append(salmodia.getHeader());
        sb.append(LS2);
        sb.append(salmodia.getSalmoCompleto(0));

        sb.append(LS);
        sb.append(lecturaBreve.getHeaderLectura());
        sb.append(LS2);
        sb.append(lecturaBreve.getTexto());
        sb.append(LS2);
        sb.append(lecturaBreve.getHeaderResponsorio());
        sb.append(LS2);
        sb.append(lecturaBreve.getResponsorioSpan());
        sb.append(LS);
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
        sbReader.append(salmodia.getSalmoCompletoForRead(0));
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

        mTextView.setText(sb, TextView.BufferType.SPANNABLE);
        progressBar.setVisibility(View.INVISIBLE);
        if (sbReader.length() > 0) {
            menu.findItem(R.id.item_voz).setVisible(true);
        }
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
