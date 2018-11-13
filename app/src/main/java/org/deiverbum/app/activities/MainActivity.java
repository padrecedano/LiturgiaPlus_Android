package org.deiverbum.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import org.deiverbum.app.R;
import org.deiverbum.app.data.MainDataModel;
import org.deiverbum.app.data.MainRecyclerAdapter;
import org.deiverbum.app.gui.AutoFitGridLayoutManager;
import org.deiverbum.app.utils.UtilsOld;

import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainRecyclerAdapter.ItemListener {

    RecyclerView recyclerView;
    ArrayList<MainDataModel> arrayList;
    String version;
    String strFechaHoy;
    private UtilsOld utilClass;
    private static final String TAG = "MainActivity";

/*
    private FirebaseAnalytics mFirebaseAnalytics;
*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Fabric.with(this, new Crashlytics());

        final Fabric fabric = new Fabric.Builder(this)
                .kits(new Crashlytics())
                .debuggable(true)
                .build();
        Fabric.with(fabric);
/*
        DatabaseReference santosRef = FirebaseDatabase.getInstance().getReference("santos");
        santosRef.keepSynced(true);
*/
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference santosRef = db
                .collection("santos");
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);


        utilClass = new UtilsOld();
        version = utilClass.getAppInfo();//System.getProperty("line.separator")+"Liturgia+ v. "+ BuildConfig.VERSION_NAME;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //recyclerView = findViewById(R.id.recyclerView);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        recyclerView = findViewById(R.id.recyclerView);
        arrayList = new ArrayList<>();
        int colorBreviario = getResources().getColor(R.color.colorBreviario);
        int colorMisa = getResources().getColor(R.color.colorMisa);
        int colorHomilias = getResources().getColor(R.color.colorHomilias);

        int colorLecturas = getResources().getColor(R.color.colorMain_lecturas_img);
        int colorEvangelio = getResources().getColor(R.color.colorMain_evangelio_img);
        int colorSantos = getResources().getColor(R.color.colorSantos);

        int colorCalendario = getResources().getColor(R.color.colorCalendario);
        int colorOraciones = getResources().getColor(R.color.colorOraciones);
        int colorMas = getResources().getColor(R.color.colorMain_img_mas);

        arrayList.add(new MainDataModel("Breviario", R.drawable.ic_breviario, colorBreviario));
        arrayList.add(new MainDataModel("Misa", R.drawable.ic_misa, colorMisa));
        arrayList.add(new MainDataModel("Homilías", R.drawable.ic_homilias, colorHomilias));

        arrayList.add(new MainDataModel("Santo de hoy", R.drawable.ic_santos, colorSantos));
        arrayList.add(new MainDataModel("Lecturas de hoy", R.drawable.ic_lecturas, colorLecturas));
        arrayList.add(new MainDataModel("Comentarios Evangelio", R.drawable.ic_comentarios, colorEvangelio));

        arrayList.add(new MainDataModel("Calendario", R.drawable.ic_calendario, colorCalendario));
        arrayList.add(new MainDataModel("Oraciones", R.drawable.ic_oraciones, colorOraciones));
        arrayList.add(new MainDataModel("Más...", R.drawable.ic_mas, colorMas));

        MainRecyclerAdapter adapter = new MainRecyclerAdapter(this, arrayList, this);
        recyclerView.setAdapter(adapter);


        /*
         AutoFitGridLayoutManager that auto fits the cells by the column width defined.
         */

        AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(this, 200);
        recyclerView.setLayoutManager(layoutManager);


        /*
         Simple GridLayoutManager that spans two columns
         */

/*
        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
*/

        strFechaHoy = utilClass.getFecha();
        final TextView mTextView = findViewById(R.id.txt_main);
        mTextView.setText(strFechaHoy + version);







    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent i = new Intent(this, MenuActivity.class);
        //i.putExtra("FECHA", id);
        startActivity(i);
        return true;
    }
*/
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Log.d(TAG, String.valueOf(item.getTitle()));
        Intent i = new Intent(this, SettingsActivity.class);
        i.putExtra("OPTION", item.getTitle());
        startActivity(i);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(MainDataModel item) {

        Intent i;
//        utilClass.setFabric(item.text, TAG, strFechaHoy);
//        Log.i(TAG, item.toString());
        utilClass.setFabric(item.text, TAG);
        Log.i(TAG, item.toString());

        switch (item.text) {

            case "Breviario":
                i = new Intent(getApplicationContext(), BreviarioActivity.class);
                startActivity(i);
                break;

            case "Misa":
                i = new Intent(MainActivity.this, MisaActivity.class);
                startActivity(i);
                break;

            case "Homilías":
                i = new Intent(MainActivity.this, HomiliasActivity.class);
                startActivity(i);
                break;


            case "Santo de hoy":
                i = new Intent(MainActivity.this, SantosActivity.class);
                startActivity(i);
                break;

            case "Lecturas de hoy":
                i = new Intent(MainActivity.this, LecturasActivity.class);
                startActivity(i);
                break;

            case "Comentarios Evangelio":
                i = new Intent(MainActivity.this, ComentariosActivity.class);
                startActivity(i);
                break;

            case "Calendario":
                i = new Intent(MainActivity.this, CalendarioActivity.class);
                startActivity(i);
                break;

            case "Oraciones":
                i = new Intent(MainActivity.this, OracionesActivity.class);
                startActivity(i);
                break;

            case "Más...":

                i = new Intent(MainActivity.this, MainMasActivity.class);
                startActivity(i);
                /*
                Snackbar.make(getWindow().getDecorView(), "Opción disponible en una futura actualización", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                        */
                break;

            default:


        }
    }






}
