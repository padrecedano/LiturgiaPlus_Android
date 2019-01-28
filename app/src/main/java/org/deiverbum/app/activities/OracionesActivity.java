package org.deiverbum.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.RequestQueue;

import org.deiverbum.app.BuildConfig;
import org.deiverbum.app.R;
import org.deiverbum.app.data.OracionesAdapter;
import org.deiverbum.app.model.Oraciones;
import org.deiverbum.app.utils.TTS;
import org.deiverbum.app.utils.UtilsOld;
import org.deiverbum.app.utils.ZoomTextView;

import java.util.ArrayList;
import java.util.List;

import static org.deiverbum.app.utils.Constants.SEPARADOR;

public class OracionesActivity extends AppCompatActivity {
    private static final String TAG = "OracionesActivity";
    Spanned strContenido;
    ZoomTextView mTextView;
    private static String mAPIKey = BuildConfig.mAPIKEY;
    private RequestQueue requestQueue;
    private String strFechaHoy;
    List<Oraciones> oracionesList;
    RecyclerView oracionesRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter oracionesAdapter;
    private UtilsOld utilClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oraciones_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        oracionesRecyclerView = findViewById(R.id.rv_menu);
        createMovieList();
        layoutManager = new LinearLayoutManager(this);
        oracionesAdapter = new OracionesAdapter(oracionesList);
        oracionesRecyclerView.setLayoutManager(layoutManager);
        oracionesRecyclerView.setAdapter(oracionesAdapter);





        /*Variables*/
/*

        mTextView = findViewById(R.id.tv_Zoomable);
        final ProgressBar progressBar = findViewById(R.id.progressBar);

//        mTextView.setText(UtilsOld.fromHtml(PACIENCIA));
/*
        utilClass = new UtilsOld();
        requestQueue = Volley.newRequestQueue(this);
        strFechaHoy = (this.getIntent().getExtras() != null) ? getIntent().getStringExtra("FECHA") : utilClass.getHoy();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, URL_ORACIONES + strFechaHoy,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        progressBar.setVisibility(View.INVISIBLE);
                        mTextView.setText(UtilsOld.fromHtml(response.toString()));
                        //strContenido = UtilsOld.fromHtml(response);

                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        String sError = VolleyErrorHelper.getMessage(error, getApplicationContext());
                        progressBar.setVisibility(View.INVISIBLE);
                        mTextView.setText(UtilsOld.fromHtml(sError));


                        //Failure Callback
                    }
                })
        {
            @Override
            public Map getHeaders() throws AuthFailureError {
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



        StringRequest sRequest = new StringRequest(Request.Method.GET, URL_ORACIONES + strFechaHoy,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String sResponse) {
                        progressBar.setVisibility(View.INVISIBLE);
                        mTextView.setText(UtilsOld.fromHtml(sResponse));
                        strContenido = UtilsOld.fromHtml(sResponse);
                    }
                }, new Response.ErrorListener() {
                */
/*
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
/*
        InputStream inputStream = getResources().openRawResource(R.raw.rosario);

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));





        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int ctr;
        try {
            ctr = inputStream.read();
            while (ctr != -1) {
                byteArrayOutputStream.write(ctr);
                ctr = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder();

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(byteArrayOutputStream.toString());
            JSONObject jsonRosario = jsonObject.getJSONObject("rosario");
            progressBar.setVisibility(View.INVISIBLE);
            sb.append(jsonRosario.getString("saludo"));
            sb.append(jsonRosario.getString("padrenuestro"));
            Log.d("Text Data", jsonRosario.getString("saludo"));
            mTextView.setText(UtilsOld.fromHtml(sb.toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

*/





    }


    private void createMovieList() {
        oracionesList = new ArrayList<Oraciones>();
        Oraciones oracionesListItem1 = new Oraciones();
        oracionesListItem1.setName("Misterios Gloriosos");
        oracionesListItem1.setDescription("Domingos y Miércoles");
        oracionesListItem1.setImageId(R.drawable.ic_letter_d);
        oracionesListItem1.setCaseID(1);
        oracionesList.add(oracionesListItem1);

        Oraciones oracionesListItem2 = new Oraciones();
        oracionesListItem2.setName("Misterios Gozosos");
        oracionesListItem2.setDescription("Lunes y Sábados");
        oracionesListItem2.setImageId(R.drawable.ic_letter_l);
        oracionesList.add(oracionesListItem2);

        Oraciones oracionesListItem3 = new Oraciones();
        oracionesListItem3.setName("Misterios Dolorosos");
        oracionesListItem3.setDescription("Martes y Viernes");
        oracionesListItem3.setImageId(R.drawable.ic_letter_m);
        oracionesList.add(oracionesListItem3);

        Oraciones oracionesListItem4 = new Oraciones();
        oracionesListItem4.setName("Misterios Luminosos");
        oracionesListItem4.setDescription("Jueves");
        oracionesListItem4.setImageId(R.drawable.ic_letter_j);
        oracionesList.add(oracionesListItem4);

        Oraciones oracionesListItem5 = new Oraciones();
        oracionesListItem5.setName("Letanías");
        oracionesListItem5.setDescription("Solamente las Letanías");
        oracionesListItem5.setImageId(R.drawable.ic_letter_t);
        oracionesList.add(oracionesListItem5);

        Oraciones oracionesListItem6 = new Oraciones();
        oracionesListItem6.setName("Ángelus");
        oracionesListItem6.setDescription("Recuerda la Encarnación de Cristo");
        oracionesListItem6.setImageId(R.drawable.ic_letter_a);
        oracionesList.add(oracionesListItem6);

        Oraciones oracionesListItem7 = new Oraciones();
        oracionesListItem7.setName("Regina Coeli");
        oracionesListItem7.setDescription("En el Tiempo de Pascua");
        oracionesListItem7.setImageId(R.drawable.ic_letter_r);
        oracionesList.add(oracionesListItem7);
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


}
