package org.deiverbum.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.deiverbum.app.R;
import org.deiverbum.app.model.Rosario;
import org.deiverbum.app.utils.TTS;
import org.deiverbum.app.utils.Utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import static org.deiverbum.app.utils.Constants.SEPARADOR;

public class RosarioActivity extends AppCompatActivity {

    private static StringBuilder sbReader;
    private static int dayCode;
    private static Menu menu;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private TTS tts;
    /**
     * The {@link PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rosario);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            dayCode = extras.getInt("EXTRA_PAGE");
            mViewPager.setCurrentItem(dayCode);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_rosario, menu);
        menu = menu;
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            case android.R.id.home:
                if (tts != null) {
                    tts.cerrar();
                }
                NavUtils.navigateUpFromSameTask(this);
                return true;

            case R.id.item_voz:
                String html = String.valueOf(Utils.fromHtml(sbReader.toString()));
                String[] textParts = html.split(SEPARADOR);
                tts = new TTS(getApplicationContext(), textParts);

                return true;

            case R.id.item_calendario:
                Intent i = new Intent(this, CalendarioActivity.class);
                startActivity(i);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (tts != null) {
            tts.cerrar();
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_rosario, container, false);
            TextView textView = rootView.findViewById(R.id.tv_Zoomable);
            SpannableStringBuilder ssb = new SpannableStringBuilder();
            sbReader = new StringBuilder();

            InputStream raw = getResources().openRawResource(R.raw.rosario);
            Reader rd = new BufferedReader(new InputStreamReader(raw));
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(rd, JsonObject.class);
            JsonObject jsonRosario = jsonObject.getAsJsonObject("rosario");
            Rosario mRosario = gson.fromJson(jsonRosario, Rosario.class);

            SpannableStringBuilder misterios = mRosario.getMisterios(dayCode);
            ssb.append(Utils.toH2Red("INVOCACIÓN INICIAL"));
            ssb.append(Utils.LS2);
            ssb.append(mRosario.getSaludo());
            ssb.append(Utils.LS2);
            ssb.append(misterios);
            ssb.append(Utils.LS);
            ssb.append(Utils.toH2Red("LETANÍAS DE NUESTRA SEÑORA"));
            ssb.append(Utils.LS2);
            ssb.append(mRosario.getLetanias());
            ssb.append(Utils.LS2);
            ssb.append(Utils.toH2Red("SALVE"));
            ssb.append(Utils.LS2);
            ssb.append(mRosario.getSalve());
            ssb.append(Utils.LS2);
            ssb.append(Utils.toH2Red("ORACIÓN"));
            ssb.append(Utils.LS2);
            ssb.append(mRosario.getOracion());

            sbReader.append(mRosario.getSaludo());
            sbReader.append(SEPARADOR);

            sbReader.append(misterios);
            sbReader.append(SEPARADOR);

            sbReader.append("LETANÍAS DE NUESTRA SEÑORA");
            sbReader.append(mRosario.getLetanias());

            sbReader.append(SEPARADOR);
            sbReader.append("SALVE.");
            sbReader.append(mRosario.getSalve());

            sbReader.append(SEPARADOR);
            sbReader.append("ORACIÓN.");
            sbReader.append(mRosario.getOracion());
            textView.setText(ssb, TextView.BufferType.SPANNABLE);
            if (sbReader.length() > 0) {
                //menu.findItem(R.id.item_voz).setVisible(true);
            }
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 6;
        }
    }


}
