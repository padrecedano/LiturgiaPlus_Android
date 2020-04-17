package org.deiverbum.app.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import org.deiverbum.app.R;
import org.deiverbum.app.data.BreviarioDataModel;
import org.deiverbum.app.data.BreviarioRecyclerAdapter;
import org.deiverbum.app.utils.Utils;

import java.util.ArrayList;

public class BreviarioActivity extends AppCompatActivity
        implements BreviarioRecyclerAdapter.ItemListener {

    private static final String TAG = "BreviarioActivity";
    RecyclerView recyclerView;
    ArrayList<BreviarioDataModel> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breviario);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setSubtitle(Utils.getFecha());

        recyclerView = findViewById(R.id.rv_Breviario);

        arrayList = new ArrayList<BreviarioDataModel>();
        int colorGrupo1 = getResources().getColor(R.color.color_fondo_grupo1);
        int colorGrupo2 = getResources().getColor(R.color.color_fondo_grupo2);

        arrayList.add(new BreviarioDataModel("Oficio+Laudes", colorGrupo1, "M"));
        arrayList.add(new BreviarioDataModel("Oficio", colorGrupo1, "O"));
        arrayList.add(new BreviarioDataModel("Laudes",  colorGrupo1, "L"));

        arrayList.add(new BreviarioDataModel("Tercia",  colorGrupo2, "T"));
        arrayList.add(new BreviarioDataModel("Sexta",  colorGrupo2, "S"));
        arrayList.add(new BreviarioDataModel("Nona", colorGrupo2, "N"));

        arrayList.add(new BreviarioDataModel("Vísperas",  colorGrupo1, "V"));
        arrayList.add(new BreviarioDataModel("Completas",  colorGrupo1, "C"));
        arrayList.add(new BreviarioDataModel("Más...", colorGrupo1, "+"));

        BreviarioRecyclerAdapter adapter = new BreviarioRecyclerAdapter(this, arrayList, this);
        recyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.addItemDecoration(new BreviarioActivity.GridSpacingItemDecoration(3, dpToPx(0), true));

        recyclerView.setLayoutManager(mLayoutManager);


    }


    @Override
    public void onItemClick(BreviarioDataModel item) {
        Intent i;
        switch (item.text) {

            case "Oficio+Laudes":
                i = new Intent(getApplicationContext(), MixtoActivity.class);
                startActivity(i);
                break;

            case "Oficio":
                i = new Intent(BreviarioActivity.this, OficioActivity.class);
                startActivity(i);
                break;

            case "Laudes":
                i = new Intent(BreviarioActivity.this,LaudesActivity.class);
                startActivity(i);
                break;

            case "Tercia":
                i = new Intent(BreviarioActivity.this, TerciaActivity.class);
                startActivity(i);
                break;

            case "Sexta":
                i = new Intent(BreviarioActivity.this, SextaActivity.class);
                startActivity(i);
                break;

            case "Nona":
                i = new Intent(BreviarioActivity.this, NonaActivity.class);
                startActivity(i);
                break;

            case "Vísperas":
                i = new Intent(BreviarioActivity.this, VisperasActivity.class);
                startActivity(i);
                break;

            case "Completas":
                i = new Intent(BreviarioActivity.this, CompletasActivity.class);
                startActivity(i);
                break;

            case "Más...":
                Snackbar.make(findViewById(android.R.id.content), "Esta opción estará disponible en próximas versiones de la Liturgia+, DM", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
