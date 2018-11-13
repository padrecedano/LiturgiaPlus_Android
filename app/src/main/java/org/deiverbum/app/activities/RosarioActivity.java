package org.deiverbum.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private TTS tts;

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
            //int position= Integer.parseInt(value );
            mViewPager.setCurrentItem(dayCode);
        }
/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
    }

    private String textContent(int dayID) {
        InputStream raw = getResources().openRawResource(R.raw.rosario);
        Reader rd = new BufferedReader(new InputStreamReader(raw));

        //   BufferedReader bufferedReader = new BufferedReader(new FileReader(path));

        Gson gson = new Gson();
        Object json = gson.fromJson(rd, Object.class);

        System.out.println(json.getClass());
        System.out.println(json.toString());

        String textContent = "ok";

        return textContent;


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_rosario, menu);
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
                //String[] strPrimera = strContenido.toString().split(SEPARADOR);
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
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            InputStream raw = getResources().openRawResource(R.raw.rosario);
            Reader rd = new BufferedReader(new InputStreamReader(raw));

            //   BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            SpannableStringBuilder ssb = new SpannableStringBuilder();
            Gson gson = new Gson();
            sbReader = new StringBuilder();


            JsonObject jsonObject = gson.fromJson(rd, JsonObject.class);
            JsonObject jsonRosario = jsonObject.getAsJsonObject("rosario");


            Rosario mRosario = gson.fromJson(jsonRosario, Rosario.class);
            SpannableStringBuilder misterios = mRosario.getMisterios(dayCode);
            //String m=mRosario.getMisterios(dayCode);
            //ssb.append(misterios.getLuminosos().getTexto());
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


            /*
            ssb.append(Utils.fromHtml(mRosario.getSaludo()));
            ssb.append(Utils.LS);
            ssb.append(Utils.fromHtml(mRosario.getAvemaria()));
            ssb.append(Utils.LS);
            ssb.append(Utils.fromHtml(mRosario.getPadrenuestro()));
*/
            //JsonObject address = json.g.getAsJsonObject("address");

            //System.out.println(json.getClass());
            //System.out.println(json.getLetanias());
/*
            SpannableString redString = new SpannableString("rojo \n");
            SpannableString blueString = new SpannableString("azul");
            SpannableStringBuilder ssb=new SpannableStringBuilder("");
            redString.setSpan(new ForegroundColorSpan(Color.RED), 0, blueString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            blueString.setSpan(new ForegroundColorSpan(Color.BLUE), 0, blueString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ssb.append(redString);
            SpannableStringBuilder ssbs=new SpannableStringBuilder("rojo");
            ssb.append(blueString);
*/
            textView.setText(ssb, TextView.BufferType.SPANNABLE);
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
            return 4;
        }
    }


}
