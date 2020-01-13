package org.deiverbum.app.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.TypedValue;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.deiverbum.app.R;

import java.io.IOException;
import java.io.InputStream;

public class WhatIsNewActivity extends AppCompatActivity {
    private static final String TAG = "WINActivity";
    private String txtOutput;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_what_is_new);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTextView = findViewById(R.id.tv_Clickable);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        float fontSize = Float.parseFloat(prefs.getString("font_size", "18"));
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        mTextView.setMovementMethod(LinkMovementMethod.getInstance());
        //final ProgressBar progressBar = findViewById(R.id.progressBar);

        String mText = "";
        try {

            int rawId = R.raw.whatisnew;
            InputStream in = getResources().openRawResource(rawId);
            if (in != null) {
                byte[] b = new byte[in.available()];
                in.read(b);
                mText = new String(b);
            } else {
                mText = "***";
            }
        } catch (IOException e) {

        }
        mTextView.setText(Html.fromHtml(mText));
        //progressBar.setVisibility(View.INVISIBLE);

    }

}
