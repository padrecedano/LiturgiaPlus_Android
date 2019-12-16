package org.deiverbum.app.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.deiverbum.app.R;
import org.deiverbum.app.utils.TTS;
import org.deiverbum.app.utils.Utils;

import java.util.HashMap;

import static org.deiverbum.app.utils.Constants.BRS;
import static org.deiverbum.app.utils.Constants.SEPARADOR;

public class SantosActivity extends AppCompatActivity {
    private static final String TAG = "SantosActivity";
    String strFechaHoy;
    String santoMMDD;
    private TTS tts;
    private StringBuilder sbReader;
    private ProgressBar progressBar;
    private String santoMM;
    String textoVida;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_santos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final TextView mTextView = findViewById(R.id.tv_Zoomable);
        progressBar = findViewById(R.id.progressBar);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        float fontSize = Float.parseFloat(prefs.getString("font_size", "18"));
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        Intent i = getIntent();
        santoMMDD = (i.getExtras() != null) ? getIntent().getStringExtra("FECHA") : Utils.getHoy();

        final String santoDD = santoMMDD.substring(santoMMDD.length() - 2);
        santoMM = santoMMDD.substring(4, 6);
        strFechaHoy = Utils.getFecha();
        sbReader = new StringBuilder();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference messageRef = db
                .collection("liturgia").document("santos").collection(santoMM).document(santoDD);

        messageRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                SpannableStringBuilder sb = new SpannableStringBuilder();
                Spanned err = Utils.fromHtml("No se ha introducido la  vida de este santo. <br><br>¿Quieres cooperar enviando breves biografías de santos?<br><br>Mándame un correo a la dirección: <b>padre.cedano@gmail.com</b>");

                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        HashMap<String, String> monthNames = new HashMap<>();
                        monthNames.put("01", "Enero");
                        monthNames.put("02", "Febrero");
                        monthNames.put("03", "Marzo");
                        monthNames.put("04", "Abril");
                        monthNames.put("05", "Mayo");
                        monthNames.put("06", "Junio");
                        monthNames.put("07", "Julio");
                        monthNames.put("08", "Agosto");
                        monthNames.put("09", "Septiembre");
                        monthNames.put("10", "Octubre");
                        monthNames.put("11", "Noviembre");
                        monthNames.put("12", "Diciembre");
                        String nombreMes = santoDD + " de " + monthNames.get(santoMM);

                        if (document.contains("nombre")) {
                            String nombre = document.getString("nombre");
                            sb.append(Utils.toH3Red(nombreMes));
                            sb.append(Utils.LS);
                            sb.append(Utils.toH2Red(nombre));
                            sb.append(Utils.LS2);
                            sbReader.append(nombre);
                            sbReader.append(SEPARADOR);

                        }
                        if (document.contains("martirologio")) {
                            String martirologio = document.getString("martirologio");
                            sb.append(Utils.toSmallSize(martirologio));
                            sb.append(Utils.LS);
                            sb.append(Utils.toSmallSize("(Martirologio Romano)"));
                            sbReader.append(martirologio);
                            sbReader.append("\n");

                            sbReader.append("Del martirologio romano.");
                        }
                        if (document.contains("vida")) {
                            String vida = document.getString("vida");
                            sb.append(Utils.LS2);
                            sb.append(Utils.fromHtml("<hr>"));
                            sb.append(Utils.toH3Red("Vida"));
                            sb.append(Utils.LS2);
                            sbReader.append(SEPARADOR);
                            sb.append(Utils.fromHtml(vida.replaceAll(SEPARADOR, "")));
                            sbReader.append(vida);

                        }
                        if (sb.toString().equals("")) {
                            sb.append(err);
                            sbReader.append("No se encontró la vida del santo de hoy");
                        } else {
                            textoVida = strFechaHoy + BRS + sb.toString();
                        }
                        //mTextView.setText(textoVida);
                    } else {
                        sb.append(err);

                    }
                } else {
                    sb.append(err);

                }
                mTextView.setText(sb);
                progressBar.setVisibility(View.INVISIBLE);
                if (sbReader.length() > 0) {
                    menu.findItem(R.id.item_voz).setVisible(true);
                }

            }
        });

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
                Log.d(TAG, html);
                String[] textParts = html.split(SEPARADOR);
                tts = new TTS(getApplicationContext(), textParts);
                return true;

            case R.id.item_calendario:
                Intent i = new Intent(this, CalendarioActivity.class);
                startActivity(i);
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
