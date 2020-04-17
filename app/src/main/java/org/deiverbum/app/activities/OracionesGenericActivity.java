package org.deiverbum.app.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.deiverbum.app.R;
import org.deiverbum.app.model.IntroViaCrucis;
import org.deiverbum.app.model.Rosario;
import org.deiverbum.app.model.ViaCrucis;
import org.deiverbum.app.utils.TTS;
import org.deiverbum.app.utils.Utils;
import org.deiverbum.app.utils.ZoomTextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import static org.deiverbum.app.utils.Constants.SCREEN_TIME_OFF;
import static org.deiverbum.app.utils.Constants.SEPARADOR;
import static org.deiverbum.app.utils.Utils.LS;
import static org.deiverbum.app.utils.Utils.LS2;

public class OracionesGenericActivity extends AppCompatActivity {
    ZoomTextView mTextView;
    private StringBuilder sbReader;
    private SpannableStringBuilder ssb;
    private TTS tts;
    private static final String TAG = "OracionesGenericActivity";
    private Menu mMenu;
    private boolean isVoiceOn;
    private int dayCode = 0;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_santos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mTextView = findViewById(R.id.tv_Zoomable);
        progressBar = findViewById(R.id.progressBar);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        float fontSize = Float.parseFloat(prefs.getString("font_size", "18"));
        isVoiceOn = prefs.getBoolean("voice", true);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sbReader = new StringBuilder();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        };
        handler.postDelayed(r, SCREEN_TIME_OFF);

        String mTitle = "";
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            dayCode = extras.getInt("EXTRA_PAGE");
            mTitle = extras.getString("TITLE");
        }
        ssb = new SpannableStringBuilder();


        if (dayCode < 5) {
            getSupportActionBar().setTitle("Rosario: " + mTitle);
            doRosary();
        } else if (dayCode < 8) {
            doVarious();

        } else {
            doViaCrucis();
        }
    }

    protected void showData() {
        progressBar.setVisibility(View.INVISIBLE);
        mTextView.setText(ssb, TextView.BufferType.SPANNABLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        menu.findItem(R.id.item_voz).setVisible(true);
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

    private void doRosary() {
        ssb = new SpannableStringBuilder();
        InputStream raw = getResources().openRawResource(R.raw.rosario);
        Reader rd = new BufferedReader(new InputStreamReader(raw));
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(rd, JsonObject.class);
        JsonObject jsonRosario = jsonObject.getAsJsonObject("rosario");
        Rosario mRosario = gson.fromJson(jsonRosario, Rosario.class);
        getSupportActionBar().setTitle("Rosario - Misterios " + mRosario.getByDay(dayCode));
        SpannableStringBuilder misterios = mRosario.getMisterios(dayCode);
        ssb.append(Utils.toH2Red("INVOCACIÓN INICIAL"));
        ssb.append(LS2);
        ssb.append(mRosario.getSaludo());
        ssb.append(LS2);

        ssb.append(misterios);
        ssb.append(LS);
        ssb.append(Utils.toH2Red("LETANÍAS DE NUESTRA SEÑORA"));
        ssb.append(LS2);

        ssb.append(mRosario.getLetanias());

        ssb.append(LS2);
        ssb.append(Utils.toH2Red("SALVE"));
        ssb.append(LS2);

        ssb.append(mRosario.getSalve());

        ssb.append(LS2);
        ssb.append(Utils.toH2Red("ORACIÓN"));
        ssb.append(LS2);

        ssb.append(mRosario.getOracion());

        if (isVoiceOn) {
            sbReader.append(mRosario.getSaludo());
            sbReader.append(misterios);
            sbReader.append("LETANÍAS DE NUESTRA SEÑORA");
            sbReader.append(mRosario.getLetanias());
            sbReader.append("SALVE.");
            sbReader.append(mRosario.getSalve());
            sbReader.append("ORACIÓN.");
            sbReader.append(mRosario.getOracion());
        }
        showData();

    }

    private void doVarious() {
        String mTitle = "";
        int rawId = 0;
        switch (dayCode) {

            case 5:
                rawId = R.raw.letanias;
                mTitle = "Letanías de Nuestra Señora";
                ssb.append(Utils.toH2Red(mTitle));
                ssb.append("<br><br>");
                if (isVoiceOn) {
                    sbReader.append(mTitle);
                }
                break;
            case 6:
                mTitle = "ÁNGELUS";
                ssb.append(mTitle);
                ssb.append("<br><br>");
                if (isVoiceOn) {
                    sbReader.append(mTitle);
                }
                rawId = R.raw.angelus;

                break;

            case 7:
                mTitle = "REGINA COELI";
                ssb.append(mTitle);
                ssb.append("<br><br>");
                if (isVoiceOn) {
                    sbReader.append(mTitle);
                }
                rawId = R.raw.regina;
                break;

        }

        try {
            getSupportActionBar().setTitle(mTitle);

            InputStream in_s = getResources().openRawResource(rawId);
            byte[] b = new byte[in_s.available()];
            in_s.read(b);
            String t = new String(b);

            String redString = t.replace("V.", "<font color='red'>V.</font>")
                    .replace("R.", "<font color='red'>R.</font>");
            String readString = t.replace("V. ", "")
                    .replace("R. ", "")
                    .replace("Durante el tiempo pascual, en lugar del Ángelus, se dice el Regina coeli:", "");

            Spanned text = Utils.fromHtml(new String(b));
            ssb.append(redString);
            if (isVoiceOn) {
                sbReader.append(readString);
            }
            progressBar.setVisibility(View.INVISIBLE);

            mTextView.setText(Utils.fromHtml(ssb.toString()), TextView.BufferType.SPANNABLE);
            in_s.close();

        } catch (Exception e) {
            progressBar.setVisibility(View.INVISIBLE);
            mTextView.setText("Error: " + e.getMessage());
        }

    }

    private void doViaCrucis() {
        String mTitle = "Via Crucis";
        getSupportActionBar().setTitle(mTitle);
        InputStream raw = (dayCode == 8) ? getResources().openRawResource(R.raw.viacrucis2003) : getResources().openRawResource(R.raw.viacrucis2005);
        Reader rd = new BufferedReader(new InputStreamReader(raw));
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(rd, JsonObject.class);
        JsonObject jsonData = jsonObject.getAsJsonObject("viaCrucis");
        ViaCrucis mViaCrucis = gson.fromJson(jsonData, ViaCrucis.class);
        IntroViaCrucis mIntro = mViaCrucis.getIntroViaCrucis();
        ssb.append(Utils.toH2Red(mTitle));
        ssb.append(LS2);
        ssb.append(Utils.toH3(mViaCrucis.getTitulo()));
        ssb.append(LS);
        ssb.append(Utils.toH4(mViaCrucis.getFecha()));
        ssb.append(LS);
        ssb.append(Utils.toH4Red(mViaCrucis.getAutor()));
        ssb.append(LS2);
        ssb.append(Utils.getSaludoEnElNombre());
        ssb.append(LS2);
        ssb.append(Utils.toH3Red("Preámbulo"));
        ssb.append(LS2);
        ssb.append(Utils.fromHtml(mIntro.getIntro()));
        ssb.append(LS2);
        ssb.append(Utils.toH3Red("Oración inicial"));
        ssb.append(LS2);
        ssb.append(Utils.fromHtml(mIntro.getOracion()));
        ssb.append(LS2);
        ssb.append(mViaCrucis.getAllEstaciones(false));
        ssb.append(Utils.toH3Red("Oración final"));
        ssb.append(LS2);
        ssb.append(Utils.fromHtml(Utils.getFormato(mViaCrucis.getOracion())));
        ssb.append(LS2);
        ssb.append(Utils.getConclusionHorasMayores());
        ssb.append(LS2);
        ssb.append(Utils.toRed("Si la celebración la preside un ministro ordenado se concluye con la bendición, como habitualmente."));

        if (isVoiceOn) {
            sbReader.append(mTitle);
            sbReader.append(LS2);
            sbReader.append(mViaCrucis.getTitulo());
            sbReader.append(LS);
            sbReader.append(mViaCrucis.getFecha());
            sbReader.append(LS);
            sbReader.append(mViaCrucis.getAutor());
            sbReader.append(LS2);
            sbReader.append(Utils.getSaludoEnElNombre());
            sbReader.append(LS2);
            sbReader.append(Utils.toH3Red("Preámbulo."));
            sbReader.append(LS2);
            sbReader.append(Utils.fromHtml(mIntro.getIntro()));
            sbReader.append(LS2);
            sbReader.append(Utils.toH3Red("Oración inicial."));
            sbReader.append(LS2);
            sbReader.append(Utils.fromHtml(mIntro.getOracion()));
            sbReader.append(LS2);
            sbReader.append(mViaCrucis.getAllEstaciones(true));
            sbReader.append("Oración final.");
            sbReader.append(LS2);
            sbReader.append(Utils.fromHtml(Utils.getFormato(mViaCrucis.getOracion())));
            sbReader.append(LS2);
            sbReader.append(Utils.getConclusionHorasMayores());
            sbReader.append(LS2);
        }
        showData();


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (tts != null) {
            tts.cerrar();
        }
    }

}
