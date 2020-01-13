package org.deiverbum.app.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.deiverbum.app.R;
import org.deiverbum.app.utils.Utils;

public class BibliaLibrosActivity extends AppCompatActivity {
    private static final String TAG = "BibliaLibrosActivity";
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biblia_libros);
        Toolbar toolbar = findViewById(R.id.toolbar);
        String bookName = (this.getIntent().getExtras() != null) ? getIntent().getStringExtra("BOOK_NAME") : "Biblia Libros...";
        toolbar.setTitle(bookName);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTextView = findViewById(R.id.tv_Clickable);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        float fontSize = Float.parseFloat(prefs.getString("font_size", "18"));
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        mTextView.setMovementMethod(LinkMovementMethod.getInstance());
        launchFirestore();
        ProgressBar progressBar = findViewById(R.id.progressBar);
        //progressBar.setVisibility(View.INVISIBLE);


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
}