package org.deiverbum.app.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
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

import static org.deiverbum.app.utils.Constants.PACIENCIA;
import static org.deiverbum.app.utils.Constants.SCREEN_TIME_OFF;
import static org.deiverbum.app.utils.Constants.SEPARADOR;

public class BibliaLibrosActivity extends AppCompatActivity {
    private static final String TAG = "BibliaLibrosActivity";
    private TextView mTextView;
    private TTS tts;
    private ProgressBar progressBar;
    private Menu menu;
    private boolean isVoiceOn;
    private StringBuilder sbReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biblia_libros);
        Toolbar toolbar = findViewById(R.id.toolbar);
        String bookName = (this.getIntent().getExtras() != null) ? getIntent().getStringExtra("BOOK_NAME") : "Biblia Libros...";
        toolbar.setTitle(bookName);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressBar = findViewById(R.id.progressBar);
        mTextView = findViewById(R.id.tv_Zoomable);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        float fontSize = Float.parseFloat(prefs.getString("font_size", "18"));
        isVoiceOn = prefs.getBoolean("voice", true);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        mTextView.setMovementMethod(LinkMovementMethod.getInstance());
        mTextView.setText(Utils.fromHtml(PACIENCIA));

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        };
        handler.postDelayed(r, SCREEN_TIME_OFF);


        launchFirestore();
        //progressBar.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);


    }

    public void launchFirestore() {
        SpannableStringBuilder ssb = new SpannableStringBuilder();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        int bookID = (this.getIntent().getExtras() != null) ? getIntent().getIntExtra("BOOK_ID", 0) : 0;
        DocumentReference docRef = db
                .collection("es").document("biblia").collection("intro").document(String.valueOf(bookID));
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Spanned err = Utils.fromHtml("Todavía no hay introducción a este libro. <br>Proyecto abierto a la colaboración. <br><a href=\"http://bit.ly/2FInp4n\">Ver los detalles aquí</a>.");

                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        ssb.append(Utils.fromHtml(document.getString("intro")));
                        if (isVoiceOn) {
                            sbReader = new StringBuilder();
                            sbReader.append(Utils.fromHtml(document.getString("intro")));
                            if (sbReader.length() > 0) {
                                menu.findItem(R.id.item_voz).setVisible(true);
                            }
                        }
                        //progressBar.setVisibility(View.INVISIBLE);

                        mTextView.setText(ssb);
                    } else {
                        ssb.append(err);
                        Log.d(TAG, "error");
                        mTextView.setText(ssb);
                    }
                } else {
                    ssb.append(err);
                    //Log.d(TAG,"error");

                }
                //mTextView.setText(sb);
                //progressBar.setVisibility(View.INVISIBLE);


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
}