package org.deiverbum.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.deiverbum.app.R;
import org.deiverbum.app.utils.Utils;
import org.deiverbum.app.utils.UtilsOld;

import static org.deiverbum.app.utils.Constants.BRS;

public class SantosActivity extends AppCompatActivity {
    private static final String TAG = "SantosActivity";
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    String strFechaHoy;
    String santoMMDD;

    String textoVida;
    private UtilsOld utilClass;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_santos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Log.i(TAG, "ok");


        utilClass = new UtilsOld();
        final TextView mTextView = findViewById(R.id.tv_Zoomable);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

        //       DatabaseReference ref = database.getReference("oficio_1");

        Intent i = getIntent();
        santoMMDD = (i.getExtras() != null) ? getIntent().getStringExtra("FECHA") : utilClass.getHoy();

//        santoMMDD = santoMMDD.substring(santoMMDD.length() - 4);
        String santoDD = santoMMDD.substring(santoMMDD.length() - 2);
        String santoMM = santoMMDD.substring(4, 6);
        Log.i(TAG, "fecha" + strFechaHoy + " mm: " + santoMM + " dd: " + santoDD);
        strFechaHoy = utilClass.getFecha();
// Access a Cloud Firestore instance from your Activity

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference messageRef = db
                .collection("liturgia").document("santos")
                .collection(santoMM).document(santoDD);


        messageRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                SpannableStringBuilder sb = new SpannableStringBuilder();

                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //Log.d(TAG, "DocumentSnapshot data: " + document.getMetadata());
                        if (document.contains("nombre")) {
                            String nombre = document.getString("nombre");
                            //sb.append(_H2RED);
                            sb.append(Utils.toH2Red(nombre));
                            sb.append(Utils.LS2);

                            //sb.append(H2RED_);
                        }
                        if (document.contains("martirologio")) {
                            String martirologio = document.getString("martirologio");
                            sb.append(Utils.toSmallSize(martirologio));
                            sb.append(Utils.LS);
                            sb.append(Utils.toSmallSize("(Martirologio Romano)"));
                        }
                        if (document.contains("vida")) {
                            String vida = document.getString("vida");
                            sb.append(Utils.LS2);
                            sb.append(Utils.fromHtml("<hr>"));

                            sb.append(Utils.toH3Red("Vida"));
                            sb.append(Utils.LS2);

                            sb.append(Utils.fromHtml(vida));

                        }
                        if (sb.toString().equals("")) {
                            textoVida = "No se ha introducido la  vida de este santo. ¿Quieres cooperar?";
                            sb.append("No se ha introducido la  vida de este santo. ¿Quieres cooperar?");
                        } else {
                            textoVida = strFechaHoy + BRS + sb.toString();
                        }

                        mTextView.setText(sb);

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });



    }

}
