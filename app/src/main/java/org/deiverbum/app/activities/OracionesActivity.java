package org.deiverbum.app.activities;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.deiverbum.app.R;
import org.deiverbum.app.data.OracionesAdapter;
import org.deiverbum.app.model.Oraciones;
import org.deiverbum.app.utils.ZoomTextView;

import java.util.ArrayList;
import java.util.List;

public class OracionesActivity extends AppCompatActivity {
    private static final String TAG = "OracionesActivity";
    ZoomTextView mTextView;
    List<Oraciones> oracionesList;
    RecyclerView oracionesRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter oracionesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oraciones_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        oracionesRecyclerView = findViewById(R.id.rv_menu);
        //ViewCompat.setNestedScrollingEnabled(oracionesRecyclerView,true);
        createPrayersList();
        layoutManager = new LinearLayoutManager(this);
        oracionesAdapter = new OracionesAdapter(oracionesList);
        oracionesRecyclerView.setLayoutManager(layoutManager);
        oracionesRecyclerView.setAdapter(oracionesAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //this.menu = menu;
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }


    private void createPrayersList() {
        oracionesList = new ArrayList<>();
        Oraciones oracionesListItem1 = new Oraciones();
        oracionesListItem1.setName("Misterios Gloriosos");
        oracionesListItem1.setDescription("Domingos y Miércoles");
        //oracionesListItem1.setImageId(R.drawable.ic_letter_d);
        oracionesListItem1.setCaseID(1);
        oracionesList.add(oracionesListItem1);

        Oraciones oracionesListItem2 = new Oraciones();
        oracionesListItem2.setName("Misterios Gozosos");
        oracionesListItem2.setDescription("Lunes y Sábados");
        //oracionesListItem2.setImageId(R.drawable.ic_letter_l);
        oracionesList.add(oracionesListItem2);

        Oraciones oracionesListItem3 = new Oraciones();
        oracionesListItem3.setName("Misterios Dolorosos");
        oracionesListItem3.setDescription("Martes y Viernes");
        //oracionesListItem3.setImageId(R.drawable.ic_letter_m);
        oracionesList.add(oracionesListItem3);

        Oraciones oracionesListItem4 = new Oraciones();
        oracionesListItem4.setName("Misterios Luminosos");
        oracionesListItem4.setDescription("Jueves");
        //oracionesListItem4.setImageId(R.drawable.ic_letter_j);
        oracionesList.add(oracionesListItem4);

        Oraciones oracionesListItem5 = new Oraciones();
        oracionesListItem5.setName("Letanías");
        oracionesListItem5.setDescription("Solamente las Letanías");
        //oracionesListItem5.setImageId(R.drawable.ic_letter_t);
        oracionesList.add(oracionesListItem5);

        Oraciones oracionesListItem6 = new Oraciones();
        oracionesListItem6.setName("Ángelus");
        oracionesListItem6.setDescription("Recuerda la Encarnación de Cristo");
        //oracionesListItem6.setImageId(R.drawable.ic_letter_a);
        oracionesList.add(oracionesListItem6);

        Oraciones oracionesListItem7 = new Oraciones();
        oracionesListItem7.setName("Regina Coeli");
        oracionesListItem7.setDescription("En el Tiempo de Pascua");
        //oracionesListItem7.setImageId(R.drawable.ic_letter_r);
        oracionesList.add(oracionesListItem7);
    }


}
