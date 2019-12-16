package org.deiverbum.app.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.deiverbum.app.R;

public class AuthorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "padre.cedano@gmail.com", null));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Sobre la App Liturgia+");
                intent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(intent, "Completar acción mediante:"));
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String mText = "Soy Alfertson Cedano Guerrero, presbítero nacido en la República Dominicana, e incardinado en la Arquidiócesis de Santo Domingo\n\n" +
                "Soy el desarrollador de la aplicación Liturgia+, la cual quiere ser una herramienta moderna que nos acompañe en nuestro itinerario espiritual.\n\n" +
                "Esta aplicación vio la luz en el año 2018. Todavía sigo trabajando en ella, mejorándola y organizando el contenido que ésta ofrece.\n\n" +
                "Liturgia+  es una aplicación totalmente gratuita y creada sin fines de lucro.\n\n" +
                "Puedes ponerte en contacto conmigo por correo electrónico si fuera necesario pulsando el botón que aparece en la parte inferior derecha de esta pantalla.\n\n" +
                "Gracias por usar esta aplicación, esperando que te sea de ayuda. Eso sí, puedes pagarme el esfuerzo con una oración.... Eso se nota, cuando alguien ora por ti. Gracias y que Dios te bendiga a ti y a los tuyos.\n\n";

        final TextView mTextView = findViewById(R.id.tv_Clickable);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        float fontSize = Float.parseFloat(prefs.getString("font_size", "18"));
        mTextView.setTextSize(fontSize);

        mTextView.setText(mText);

    }

}
