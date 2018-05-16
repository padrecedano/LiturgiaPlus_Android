package org.deiverbum.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import org.deiverbum.app.R;
import org.deiverbum.app.data.BreviarioDataModel;
import org.deiverbum.app.data.BreviarioRecyclerAdapter;
import org.deiverbum.app.gui.AutoFitGridLayoutManager;
import org.deiverbum.app.utils.Utils;

import java.util.ArrayList;

public class BreviarioActivity extends AppCompatActivity
        implements BreviarioRecyclerAdapter.ItemListener {

    private static final String TAG = "BreviarioActivity";
    //   private String sHoy = getFecha();
    private Utils utilClass;
    private String strFechaHoy;
    RecyclerView recyclerView;
    ArrayList<BreviarioDataModel> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        utilClass = new Utils();
        setContentView(R.layout.activity_breviario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        recyclerView = findViewById(R.id.rv_Breviario);

        arrayList = new ArrayList<BreviarioDataModel>();
        int colorGrupo1 = getResources().getColor(R.color.color_fondo_grupo1);
        int colorGrupo2 = getResources().getColor(R.color.color_fondo_grupo2);
        int colorGrupo3 = getResources().getColor(R.color.color_fondo_grupo3);
        int colorOficio = getResources().getColor(R.color.color_breviario_oficio_bg);
        int colorLaudes = getResources().getColor(R.color.color_breviario_laudes_bg);

        int colorLecturas = getResources().getColor(R.color.colorMain_lecturas_img);
        int colorEvangelio = getResources().getColor(R.color.colorMain_evangelio_img);
        int colorSantos = getResources().getColor(R.color.colorSantos);

        int colorCalendario = getResources().getColor(R.color.colorCalendario);
        int colorOraciones = getResources().getColor(R.color.colorOraciones);
        int colorMas = getResources().getColor(R.color.colorMain_img_mas);
        int colorTransparente = getResources().getColor(R.color.transparent);
        //Drawable bg          = findViewById(R.id.bg);


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


        AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(this, 200);
        recyclerView.setLayoutManager(layoutManager);
        final TextView mTextview = findViewById(R.id.txt_container);
        mTextview.setText(utilClass.getFecha()+utilClass.getAppInfo());


    }


    @Override
    public void onItemClick(BreviarioDataModel item) {
        Intent i;
        //utilClass.setFabric(item.text, TAG, strFechaHoy);

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
                i = new Intent(BreviarioActivity.this, BreviarioMasActivity.class);
                startActivity(i);
                break;


            default:
        }
    }


}
