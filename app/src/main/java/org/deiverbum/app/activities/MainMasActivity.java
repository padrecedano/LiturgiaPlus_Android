package org.deiverbum.app.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Spanned;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.deiverbum.app.R;
import org.deiverbum.app.utils.TTS;
import org.deiverbum.app.utils.Utils;
import org.deiverbum.app.utils.VolleyErrorHelper;
import org.deiverbum.app.utils.ZoomTextView;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static org.deiverbum.app.utils.Constants.MY_DEFAULT_TIMEOUT;
import static org.deiverbum.app.utils.Constants.SEPARADOR;

public class MainMasActivity extends AppCompatActivity {
    private static final String TAG = "MasMainActivity";
    Spanned strContenido;
    ZoomTextView mTextView;
    private String finalText;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sexta);
        progressBar = findViewById(R.id.progressBar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ProgressBar progressBar = findViewById(R.id.progressBar);
        mTextView = findViewById(R.id.tv_Zoomable);

        launchVolley();
        showData();

    }

    private void launchVolley() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://";
        JsonObjectRequest mRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            finalText = response.toString();
                            //showData();
                            if (response.has("error")) {
                                finalText = response.getString("error");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            finalText = e.getMessage();
                            //showData();
                        }
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String sError = VolleyErrorHelper.getMessage(error, getApplicationContext());
                progressBar.setVisibility(View.INVISIBLE);
                mTextView.setText(Utils.fromHtml(sError));
                strContenido = Utils.fromHtml("Error");
            }
        }) {
            @Override
            public Map getHeaders() {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "text/plain; charset=utf-8");
                headers.put("Nombre", "Pedro");
                headers.put("Apellido", "García");
                return headers;
            }
        };

        DefaultRetryPolicy mRetryPolicy = new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        mRequest.setRetryPolicy(mRetryPolicy);
        requestQueue.add(mRequest);
        progressBar.setVisibility(View.VISIBLE);
    }


    private void showData() {
        progressBar.setVisibility(View.INVISIBLE);
        mTextView.setText(finalText + "Más cosas...");
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
