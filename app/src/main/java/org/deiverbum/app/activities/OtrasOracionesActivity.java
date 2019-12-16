package org.deiverbum.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
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

public class OtrasOracionesActivity extends AppCompatActivity {
    private static final String TAG = "OtrasOracionesActivity";
    ZoomTextView mTextView;
    private StringBuilder sbReader;
    private SpannableStringBuilder ssb;
    private TTS tts;
    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otras_oraciones);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mTextView = findViewById(R.id.tv_Zoomable);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String mStatus = "";

        int dayCode = 0;
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            dayCode = extras.getInt("EXTRA_PAGE");
        }
        ssb = new SpannableStringBuilder();
        sbReader = new StringBuilder();
        int rawId = 0;
        Spanned titulo = null;

        if (dayCode < 5) {
            Log.d(TAG, "alf" + dayCode);
//            mTextView.setText(dayCode);
            ssb = new SpannableStringBuilder();
            sbReader = new StringBuilder();

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
            showData();

        } else {
            switch (dayCode) {

                case 5:
                    rawId = R.raw.letanias;
                    titulo = Utils.toH3Red("LETANÍAS DE NUESTRA SEÑORA");
                    ssb.append(titulo);
                    ssb.append("<br><br>");
                    sbReader.append(titulo);
                    sbReader.append(SEPARADOR);
                    break;
                case 6:
                    titulo = Utils.toH3Red("ÁNGELUS");
                    ssb.append(titulo);
                    ssb.append("<br><br>");
                    sbReader.append(titulo);
                    sbReader.append(SEPARADOR);
                    rawId = R.raw.angelus;
                    break;

                case 7:
                    titulo = Utils.toH3Red("REGINA COELI");
                    ssb.append(titulo);
                    ssb.append("<br><br>");
                    sbReader.append(titulo);
                    sbReader.append(SEPARADOR);
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
                sbReader.append(readString);
                mTextView.setText(Utils.fromHtml(ssb.toString()), TextView.BufferType.SPANNABLE);
                in_s.close();
                if (sbReader.length() > 0) {
                    //mMenu.findItem(R.id.item_voz).setVisible(true);
                }
            } catch (Exception e) {
                mTextView.setText("Error: " + e.getMessage());
            }
        }
    }

    protected void showData() {
        mTextView.setText(ssb, TextView.BufferType.SPANNABLE);
        if (mMenu != null) {

            mMenu.findItem(R.id.item_voz).setVisible(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.mMenu = menu;
        Log.d(TAG, "*menu_*" + menu.toString());
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

}
