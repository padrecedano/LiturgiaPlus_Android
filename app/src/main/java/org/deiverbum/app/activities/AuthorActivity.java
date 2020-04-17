package org.deiverbum.app.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.deiverbum.app.R;
import org.deiverbum.app.utils.Utils;

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

        String mText = "Soy Alfertson Cedano Guerrero, presbítero nacido en la República Dominicana, e incardinado en la Arquidiócesis de Santo Domingo. " +

                "Soy el desarrollador de la aplicación Liturgia+, lanzada en 2018 con la intención de poner en tus manos una herramienta moderna " +
                "que pueda serte de ayuda en la vida espiritual en estos tiempos de movilidad.<br><br>" +
                "Liturgia+ nació en el año 2018 y es ofrecida de forma gratuita. Detrás de ella hay muchos años de recopilación, " +
                "organización, corrección de errores ortográficos, etc, del contenido, que no es sólo el Breviario o la Misa. " +
                "El +  significa mucho más contenido recogido durante años (homilías, comentarios de los padres de la Iglesia, oraciones...) " +
                "y significa también más gente que ha colaborado conmigo en módulos concretos o revisando el contenido.<br><br> " +
                "<i>La paciencia todo lo alcanza</i>. Sigo trabajando en esta aplicación en la medida en que el tiempo me lo permite, " +
                "para irla mejorando cada día. Si tienes alguna sugerencia o duda sobre cualquier aspecto de Liturgia+ " +
                "puedes ponerte en contacto conmigo por correo electrónico pulsando el botón que aparece en la parte inferior derecha de esta pantalla " +
                "o a través de mi cuenta de <a href=\"https://twitter.com/padrecedano/\">Twitter</a>.<br><br>" +
                "Gracias por usar esta aplicación, esperando que te sea de ayuda. Eso sí, puedes pagarme el esfuerzo con una oración.... Eso se nota, cuando alguien ora por ti. Gracias y que Dios te bendiga a ti y a los tuyos.<br><br>" +
                "- <a href=\"https://www.liturgiaplus.app/\">Página web de Liturgia+</a><br><br>" +
                "- <a href=\"https://www.deiverbum.org/\">Mi primer amor online (desde 2013)</a><br><br>" +
                "- <a href=\"https://twitter.com/padrecedano/\">Twitter (@padrecedano)</a><br>";


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        float fontSize = Float.parseFloat(prefs.getString("font_size", "18"));

        final TextView mTextView = findViewById(R.id.tv_Clickable);
        mTextView.setMovementMethod(LinkMovementMethod.getInstance());

        mTextView.setTextSize(fontSize);

        mTextView.setText(Utils.fromHtml(mText));
        mTextView.setClickable(true);

    }

}
