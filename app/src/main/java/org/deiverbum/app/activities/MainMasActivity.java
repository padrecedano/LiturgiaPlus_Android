package org.deiverbum.app.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spanned;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.RequestQueue;

import org.deiverbum.app.BuildConfig;
import org.deiverbum.app.R;
import org.deiverbum.app.data.AlbumsAdapter;
import org.deiverbum.app.model.Album;
import org.deiverbum.app.utils.TTS;
import org.deiverbum.app.utils.UtilsOld;
import org.deiverbum.app.utils.ZoomTextView;

import java.util.ArrayList;
import java.util.List;

import static org.deiverbum.app.utils.Constants.SEPARADOR;

public class MainMasActivity extends AppCompatActivity implements AlbumsAdapter.ItemListener {
    private static final String TAG = "MasMainActivity";
    private static String mAPIKey = BuildConfig.mAPIKEY;
    Spanned strContenido;
    String mFirebaseAnalytics;
    ZoomTextView mTextView;
    private UtilsOld utilClass;
    private RequestQueue requestQueue;
    private String strFechaHoy;
    private RecyclerView recyclerView;
    private AlbumsAdapter adapter;
    private List<Album> albumList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_mas);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initCollapsingToolbar();

        recyclerView = findViewById(R.id.recycler_view);

        albumList = new ArrayList<>();
        adapter = new AlbumsAdapter(this, albumList, this);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, dpToPx(-1), true));
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareAlbums();

/*
        setContentView(R.layout.activity_oraciones);
        mFirebaseAnalytics = "a";
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        utilClass = new UtilsOld();
        requestQueue = Volley.newRequestQueue(this);
        strFechaHoy = (this.getIntent().getExtras() != null) ? getIntent().getStringExtra("FECHA") : utilClass.getHoy();
        final ProgressBar progressBar = findViewById(R.id.progressBar);

        mTextView = findViewById(R.id.tv_Zoomable);
        mTextView.setText(UtilsOld.fromHtml(PACIENCIA));

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, URL_ORACIONES + strFechaHoy, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        progressBar.setVisibility(View.INVISIBLE);
                        mTextView.setText(UtilsOld.fromHtml(response.toString()));

                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        String sError = VolleyErrorHelper.getMessage(error, getApplicationContext());
                        progressBar.setVisibility(View.INVISIBLE);
                        mTextView.setText(UtilsOld.fromHtml(sError));


                    }
                }) {
     /*
            @Override
            public Map getHeaders() {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json");
                headers.put("apiKey", mAPIKey);
                headers.put("user", "appLiturgiaPlus");
                headers.put("pwd", "myPass");
                headers.put("endpoint", "oraciones");
                headers.put("fecha", strFechaHoy);

                return headers;
            }
        };

*/
/*
        StringRequest sRequest = new StringRequest(Request.Method.GET, URL_ORACIONES + strFechaHoy,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String sResponse) {
                        progressBar.setVisibility(View.INVISIBLE);
                        mTextView.setText(UtilsOld.fromHtml(sResponse));
                        strContenido = UtilsOld.fromHtml(sResponse);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String sError = VolleyErrorHelper.getMessage(error, getApplicationContext());
                progressBar.setVisibility(View.INVISIBLE);
                mTextView.setText(UtilsOld.fromHtml(sError));
                strContenido = UtilsOld.fromHtml("Error");


            }


        });


        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjReq);
        progressBar.setVisibility(View.VISIBLE);

*/
    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
/*
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }
*/

    /**
     * Adding few albums for testing
     */
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

        a = new Album("Más...", 9, covers[8], colorMas);
        albumList.add(a);

        a = new Album("Greatest Hits", 10, covers[9], colorBreviario);
        albumList.add(a);

        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.item_voz) {
            if (!strContenido.equals("Error")) {

                String[] strPrimera = strContenido.toString().split(SEPARADOR);
                new TTS(getApplicationContext(), strPrimera);
            }
        }

        if (id == R.id.item_calendario) {
            Intent i = new Intent(this, CalendarioActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(Album item) {
        Log.d(TAG, String.valueOf(item.getItemId()));

        Intent i;
        //utilClass.setFabric(item.getName(), TAG);
        Log.i(TAG, item.toString());

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
                /*
                Snackbar.make(getWindow().getDecorView(), "Opción disponible en una futura actualización", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                        */
                break;

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
