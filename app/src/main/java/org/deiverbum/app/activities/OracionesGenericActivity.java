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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.deiverbum.app.R;
import org.deiverbum.app.model.Rosario;
import org.deiverbum.app.utils.TTS;
import org.deiverbum.app.utils.Utils;
import org.deiverbum.app.utils.ZoomTextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import static org.deiverbum.app.utils.Constants.SEPARADOR;

public class OracionesGenericActivity extends AppCompatActivity {
    ZoomTextView mTextView;
    private StringBuilder sbReader;
    private SpannableStringBuilder ssb;
    private TTS tts;
    private static final String TAG = "OracionesGenericActivity";
    private Menu mMenu;
    private boolean isVoiceOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oraciones_generic);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mTextView = findViewById(R.id.tv_Zoomable);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        float fontSize = Float.parseFloat(prefs.getString("font_size", "18"));
        isVoiceOn = prefs.getBoolean("voice", true);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sbReader = new StringBuilder();

        int dayCode = 0;
        String mTitle = "";
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            dayCode = extras.getInt("EXTRA_PAGE");
            mTitle = extras.getString("TITLE");
        }
        ssb = new SpannableStringBuilder();
        int rawId = 0;
        Spanned titulo = null;

        if (dayCode < 5) {
            getSupportActionBar().setTitle("Rosario: " + mTitle);
            ssb = new SpannableStringBuilder();
            InputStream raw = getResources().openRawResource(R.raw.rosario);
            Reader rd = new BufferedReader(new InputStreamReader(raw));
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(rd, JsonObject.class);
            JsonObject jsonRosario = jsonObject.getAsJsonObject("rosario");
            Rosario mRosario = gson.fromJson(jsonRosario, Rosario.class);
            SpannableStringBuilder misterios = mRosario.getMisterios(dayCode);
            ssb.append(Utils.toH2Red("INVOCACIÓN INICIAL"));
            ssb.append(Utils.LS2);
            ssb.append(mRosario.getSaludo());
            ssb.append(Utils.LS2);

            ssb.append(misterios);
            ssb.append(Utils.LS);
            ssb.append(Utils.toH2Red("LETANÍAS DE NUESTRA SEÑORA"));
            ssb.append(Utils.LS2);

            ssb.append(mRosario.getLetanias());

            ssb.append(Utils.LS2);
            ssb.append(Utils.toH2Red("SALVE"));
            ssb.append(Utils.LS2);

            ssb.append(mRosario.getSalve());

            ssb.append(Utils.LS2);
            ssb.append(Utils.toH2Red("ORACIÓN"));
            ssb.append(Utils.LS2);

            ssb.append(mRosario.getOracion());

            if (isVoiceOn) {
                sbReader.append(mRosario.getSaludo());
                sbReader.append(SEPARADOR);

                sbReader.append(misterios);
                sbReader.append(SEPARADOR);

                sbReader.append("LETANÍAS DE NUESTRA SEÑORA");
                sbReader.append(mRosario.getLetanias());

                sbReader.append(SEPARADOR);
                sbReader.append("SALVE.");
                sbReader.append(mRosario.getSalve());

                sbReader.append(SEPARADOR);
                sbReader.append("ORACIÓN.");
                sbReader.append(mRosario.getOracion());
            }
            showData();

        } else {
            getSupportActionBar().setTitle(mTitle);

            switch (dayCode) {

                case 5:
                    rawId = R.raw.letanias;
                    titulo = Utils.toH3Red("LETANÍAS DE NUESTRA SEÑORA");
                    ssb.append(titulo);
                    ssb.append("<br><br>");
                    if (isVoiceOn) {

                        sbReader.append(titulo);
                        sbReader.append(SEPARADOR);
                    }
                    break;
                case 6:
                    titulo = Utils.toH3Red("ÁNGELUS");
                    ssb.append(titulo);
                    ssb.append("<br><br>");
                    if (isVoiceOn) {
                        sbReader.append(titulo);
                        sbReader.append(SEPARADOR);
                    }
                    rawId = R.raw.angelus;

                    break;

                case 7:
                    titulo = Utils.toH3Red("REGINA COELI");
                    ssb.append(titulo);
                    ssb.append("<br><br>");
                    if (isVoiceOn) {
                        sbReader.append(titulo);
                        sbReader.append(SEPARADOR);
                    }
                    rawId = R.raw.regina;
                    break;
            }

            try {
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
                mTextView.setText(Utils.fromHtml(ssb.toString()), TextView.BufferType.SPANNABLE);
                in_s.close();

            } catch (Exception e) {
                mTextView.setText("Error: " + e.getMessage());
            }
        }
    }

    protected void showData() {
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (tts != null) {
            tts.cerrar();
        }
    }

}
