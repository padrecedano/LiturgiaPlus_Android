package org.deiverbum.app.activities;

import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;

import org.deiverbum.app.R;
import org.deiverbum.app.data.MainDataModel;
import org.deiverbum.app.data.MainItemsAdapter;
import org.deiverbum.app.model.MainItem;
import org.deiverbum.app.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainItemsAdapter.ItemListener {
    RecyclerView recyclerView;
    ArrayList<MainDataModel> arrayList;
    String version;
    String strFechaHoy;
    private static final String TAG = "MainActivity";
    private static final int UPDATE_REQUEST_CODE = 20190200;
    static boolean calledAlready = false;
    private MainItemsAdapter adapter;
    private List<MainItem> mainList;
    private SharedPreferences prefs = null;
    private AppUpdateManager appUpdateManager;
    private InstallStateUpdatedListener installStateUpdatedListener = installState -> {
        // Show module progress, log state, or install the update.
        if (installState.installStatus() == InstallStatus.DOWNLOADED) {
            // After the update is downloaded, show a notification
            // and request user confirmation to restart the app.
            popupAlerter();
        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        strFechaHoy = Utils.getFecha();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setSubtitle(strFechaHoy);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        recyclerView = findViewById(R.id.recyclerView);
        mainList = new ArrayList<>();
        adapter = new MainItemsAdapter(this, mainList, this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new MainActivity.GridSpacingItemDecoration(3, dpToPx(0), true));
        recyclerView.setAdapter(adapter);
        prepareItems();
        checkAppUpdate();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent i;
        switch (item.getItemId()) {
            case R.id.nav_help:
                i = new Intent(getApplicationContext(), HelpActivity.class);
                startActivity(i);
                break;
            case R.id.nav_settings:
                i = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(i);
                break;
            case R.id.nav_about:
                i = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(i);
                break;
            case R.id.nav_author:
                i = new Intent(getApplicationContext(), AuthorActivity.class);
                startActivity(i);
                break;

            case R.id.nav_new:
                i = new Intent(getApplicationContext(), WhatIsNewActivity.class);
                startActivity(i);
                break;

            case R.id.nav_thanks:
                i = new Intent(getApplicationContext(), ThanksActivity.class);
                startActivity(i);
                break;

            case R.id.nav_privacy:
                i = new Intent(getApplicationContext(), PrivacyActivity.class);
                startActivity(i);
                break;

            case R.id.nav_terms:
                i = new Intent(getApplicationContext(), TermsActivity.class);
                startActivity(i);
                break;

            default:

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(MainItem item) {
        Intent i;

        switch (item.getItemId()) {

            case 1:
                i = new Intent(this, BreviarioActivity.class);
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

            case 99:
                i = new Intent(this, MainMasActivity.class);
                startActivity(i);
                break;

            case 9:
            case 10:
            case 11:
            case 12:
                Snackbar.make(findViewById(android.R.id.content), "Esta opción estará disponible en próximas versiones de la Liturgia+, DM", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            default:
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    private void prepareItems() {
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
                R.drawable.ic_biblia,
                R.drawable.ic_patristica,
                R.drawable.ic_sacramentos

        };

        MainItem a = new MainItem("Breviario", 1, covers[0], colorBreviario);
        mainList.add(a);
        a = new MainItem("Misa", 2, covers[1], colorMisa);
        mainList.add(a);
        a = new MainItem("Homilías", 3, covers[2], colorHomilias);
        mainList.add(a);

        a = new MainItem("Santo de hoy", 4, covers[3], colorSantos);
        mainList.add(a);
        a = new MainItem("Lecturas de hoy", 5, covers[4], colorLecturas);
        mainList.add(a);
        a = new MainItem("Comentarios Evangelio", 6, covers[5], colorEvangelio);
        mainList.add(a);

        a = new MainItem("Calendario", 7, covers[6], colorCalendario);
        mainList.add(a);
        a = new MainItem("Oraciones", 8, covers[7], colorOraciones);
        mainList.add(a);
        a = new MainItem("Más...", 9, covers[8], colorMas);
        mainList.add(a);

        a = new MainItem("Biblia", 10, covers[9], colorBiblia);
        mainList.add(a);
        a = new MainItem("Patrística", 11, covers[10], colorPadres);
        mainList.add(a);
        a = new MainItem("Sacramentos", 12, covers[11], colorSacramentos);
        mainList.add(a);


        adapter.notifyDataSetChanged();
    }

    private void checkAppUpdate() {
        // Creates instance of the manager.
        appUpdateManager = AppUpdateManagerFactory.create(this);
        appUpdateManager.registerListener(installStateUpdatedListener);
        // Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        // Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    // For a flexible update, use AppUpdateType.FLEXIBLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                // Request the update.
                try {
                    appUpdateManager.startUpdateFlowForResult(
                            // Pass the intent that is returned by 'getAppUpdateInfo()'.
                            appUpdateInfo,
                            // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                            AppUpdateType.FLEXIBLE,
                            // The current activity making the update request.
                            this,
                            // Include a request code to later monitor this update request.
                            UPDATE_REQUEST_CODE);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        unregisterListener();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkNewAppVersionState();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPDATE_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                Log.d(TAG, "Update flow failed! Result code: " + resultCode);
                // If the update is cancelled or fails,
                // you can request to start the update again.
            }
        }
    }

    /*
      You should execute this check at all app entry points.
     */
    private void checkNewAppVersionState() {
        appUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
                    @Override
                    public void onSuccess(AppUpdateInfo appUpdateInfo) {
                        //FLEXIBLE:
                        // If the update is downloaded but not installed,
                        // notify the user to complete the update.
                        if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                            popupAlerter();
                            Log.d(TAG, "checkNewAppVersionState(): resuming flexible update. Code: " + appUpdateInfo.updateAvailability());
                        }
                    }
                });
    }

    private void popupAlerter() {
        Snackbar snackbar =
                Snackbar.make(
                        findViewById(android.R.id.content),
                        "Nueva versión descargada, por favor reinicia",
                        Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("REINICIAR", view -> appUpdateManager.completeUpdate());
        snackbar.setActionTextColor(
                getResources().getColor(R.color.colorAccent));
        snackbar.show();

    }

    private void unregisterListener() {
        if (appUpdateManager != null && installStateUpdatedListener != null)
            appUpdateManager.unregisterListener(installStateUpdatedListener);
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
