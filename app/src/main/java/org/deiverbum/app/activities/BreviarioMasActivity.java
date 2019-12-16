package org.deiverbum.app.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.deiverbum.app.R;
import org.deiverbum.app.utils.ZoomTextView;

public class BreviarioMasActivity extends AppCompatActivity {
    ZoomTextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breviario_mas);

        //setContentView(R.layout.activity_breviario_mas);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTextView = findViewById(R.id.tv_Zoomable);
        mTextView.setText("La idea de este módulo es dar la posibilidad de elegir una hora del breviario basándonos en el día, por ejemplo: «Laudes del Martes II del Tiempo Ordinario», para los laudes de ese día, independientemente de la memoria o fiesta que se celebre.\n\nEstará disponible en próximas versiones de la App, DM");
        /*
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
