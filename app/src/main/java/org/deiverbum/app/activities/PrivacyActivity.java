package org.deiverbum.app.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.deiverbum.app.R;
import org.deiverbum.app.utils.Utils;

import java.io.InputStream;

public class PrivacyActivity extends AppCompatActivity {
    private static final String TAG = "PrivacyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "padre.cedano@gmail.com", null));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Sobre la Política de Privacidad de la App Liturgia+");
                intent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(intent, "Completar acción mediante:"));
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final TextView mTextView = findViewById(R.id.tv_Clickable);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        float fontSize = Float.parseFloat(prefs.getString("font_size", "18"));
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        mTextView.setMovementMethod(LinkMovementMethod.getInstance());
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        int rawId = R.raw.privacy_201902;

        try {
            InputStream in_s = getResources().openRawResource(rawId);
            byte[] b = new byte[in_s.available()];
            in_s.read(b);
            String t = new String(b);

            Spanned text = Utils.fromHtml(t);
            ssb.append(text);
            mTextView.setText(ssb, TextView.BufferType.SPANNABLE);
            in_s.close();
        } catch (Exception e) {
            mTextView.setText("Error: " + e.getMessage());
        }

    }

}
