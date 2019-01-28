package org.deiverbum.app.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import org.deiverbum.app.R;
import org.deiverbum.app.data.AlbumsAdapter;
import org.deiverbum.app.data.MainDataModel;
import org.deiverbum.app.model.Album;
import org.deiverbum.app.utils.UtilsOld;

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AlbumsAdapter.ItemListener {

    RecyclerView recyclerView;
    ArrayList<MainDataModel> arrayList;
    String version;
    String strFechaHoy;
    private UtilsOld utilClass;
    private static final String TAG = "MainActivity";
    private AlbumsAdapter adapter;
    private List<Album> albumList;
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
        strFechaHoy = utilClass.getFecha();

        version = utilClass.getAppInfo();//System.getProperty("line.separator")+"Liturgia+ v. "+ BuildConfig.VERSION_NAME;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setSubtitle(strFechaHoy);

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
        //final TextView mTextView = findViewById(R.id.txt_main);
        //mTextView.setText(strFechaHoy + version);
        albumList = new ArrayList<>();
        adapter = new AlbumsAdapter(this, albumList, this);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new MainActivity.GridSpacingItemDecoration(3, dpToPx(-1), true));
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        prepareAlbums();


        /*
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

*/
        /*
         AutoFitGridLayoutManager that auto fits the cells by the column width defined.
         */
/*
        AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(this, 200);
        recyclerView.setLayoutManager(layoutManager);
*/

        /*
         Simple GridLayoutManager that spans two columns
         */

/*
        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
*/



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
    public void onItemClick(Album item) {
        Intent i;
        switch (item.getItemId()) {

            case 1:
                i = new Intent(getApplicationContext(), BreviarioActivity.class);
                startActivity(i);
                break;

            case 2:
                i = new Intent(this, MisaActivity.class);
                startActivity(i);
                break;

            case 3:
                i = new Intent(this, HomiliasActivity.class);
                startActivity(i);
                break;


            case 4:
                i = new Intent(this, SantosActivity.class);
                startActivity(i);
                break;

            case 5:
                i = new Intent(this, LecturasActivity.class);
                startActivity(i);
                break;

            case 6:
                i = new Intent(this, ComentariosActivity.class);
                startActivity(i);
                break;

            case 7:
                i = new Intent(this, CalendarioActivity.class);
                startActivity(i);
                break;

            case 8:
                i = new Intent(this, OracionesActivity.class);
                startActivity(i);
                break;

            case 9:
                i = new Intent(this, MainMasActivity.class);
                startActivity(i);
                break;

            default:
        }
    }


    private void prepareAlbums() {
        int colorBreviario = getResources().getColor(R.color.colorBreviario);
        int colorMisa = getResources().getColor(R.color.colorMisa);
        int colorHomilias = getResources().getColor(R.color.colorHomilias);

        int colorLecturas = getResources().getColor(R.color.colorMain_lecturas_img);
        int colorEvangelio = getResources().getColor(R.color.colorMain_evangelio_img);
        int colorSantos = getResources().getColor(R.color.colorSantos);

        int colorCalendario = getResources().getColor(R.color.colorCalendario);
        int colorOraciones = getResources().getColor(R.color.colorOraciones);
        int colorMas = getResources().getColor(R.color.colorMain_img_mas);
        int colorBiblia = getResources().getColor(R.color.colorBiblia);
        int colorPadres = getResources().getColor(R.color.colorPadres);
        int colorSacramentos = getResources().getColor(R.color.colorSacramentos);


        int[] covers = new int[]{
                R.drawable.ic_breviario,
                R.drawable.ic_misa,
                R.drawable.ic_homilias,
                R.drawable.ic_santos,
                R.drawable.ic_lecturas,
                R.drawable.ic_comentarios,
                R.drawable.ic_calendario,
                R.drawable.ic_oraciones,
                R.drawable.ic_mas,
                R.drawable.ic_comentarios,
                R.drawable.ic_comentarios};

        Album a = new Album("Breviario", 1, covers[0], colorBreviario);
        albumList.add(a);

        a = new Album("Misa", 2, covers[1], colorMisa);
        albumList.add(a);

        a = new Album("Homilías", 3, covers[2], colorHomilias);
        albumList.add(a);

        a = new Album("Santo de hoy", 4, covers[3], colorSantos);
        albumList.add(a);

        a = new Album("Lecturas de hoy", 5, covers[4], colorLecturas);
        albumList.add(a);

        a = new Album("Comentarios Evangelio", 6, covers[5], colorEvangelio);
        albumList.add(a);

        a = new Album("Calendario", 7, covers[6], colorCalendario);
        albumList.add(a);

        a = new Album("Oraciones", 8, covers[7], colorOraciones);
        albumList.add(a);

        a = new Album("Biblia", 9, covers[8], colorBiblia);
        albumList.add(a);

        a = new Album("Patrística", 10, covers[9], colorPadres);
        albumList.add(a);

        a = new Album("Sacramentos", 11, covers[9], colorSacramentos);
        albumList.add(a);

        a = new Album("Más...", 10, covers[9], colorMas);
        albumList.add(a);


        adapter.notifyDataSetChanged();
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }





}
