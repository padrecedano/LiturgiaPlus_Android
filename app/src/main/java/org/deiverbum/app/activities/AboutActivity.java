package org.deiverbum.app.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.TypedValue;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.deiverbum.app.R;

import java.io.IOException;
import java.io.InputStream;

public class AboutActivity extends AppCompatActivity {
    private static final String TAG = "AboutActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_what_is_new);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final TextView mTextView = findViewById(R.id.tv_Clickable);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        float fontSize = Float.parseFloat(prefs.getString("font_size", "18"));
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);

        String mText = "";
        try {
            int rawId = R.raw.about_201902;
            InputStream in = getResources().openRawResource(rawId);
            if (in != null) {
                byte[] b = new byte[in.available()];
                in.read(b);
                mText = new String(b);
            } else {
                mText = "...";
            }
        } catch (IOException e) {

        }
        mTextView.setText(Html.fromHtml(mText));

    }

}
