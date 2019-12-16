package org.deiverbum.app.activities;

import android.os.Bundle;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.deiverbum.app.R;
import org.deiverbum.app.utils.Utils;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final TextView mTextView = findViewById(R.id.tv_Clickable);
        mTextView.setMovementMethod(LinkMovementMethod.getInstance());

        Spanned url = Utils.fromHtml("<p>Consulta el siguiente enlace con algunas instrucciones sobre el funcionamiento de la App:</p><a href=\"https://www.liturgiaplus.app/ayuda\">Ayuda en línea</a>");
        mTextView.setText(url);
        mTextView.setClickable(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

}
