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
        createPrayersList();
        layoutManager = new LinearLayoutManager(this);
        oracionesAdapter = new OracionesAdapter(oracionesList);
        oracionesRecyclerView.setLayoutManager(layoutManager);
        oracionesRecyclerView.setAdapter(oracionesAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }


    private void createPrayersList() {
        oracionesList = new ArrayList<>();
        oracionesList.add(new Oraciones("Misterios Gloriosos", "Domingos y Miércoles"));
        oracionesList.add(new Oraciones("Misterios Gozosos", "Lunes y Sábados"));
        oracionesList.add(new Oraciones("Misterios Dolorosos", "Martes y Viernes"));
        oracionesList.add(new Oraciones("Misterios Luminosos", "Jueves"));
        oracionesList.add(new Oraciones("Letanías Lauretanas", "Solamente las Letanías"));
        oracionesList.add(new Oraciones("Ángelus", "Recuerda la Encarnación de Cristo"));
        oracionesList.add(new Oraciones("Regina Coeli", "En lugar del Àngelus, en el tiempo de Pascua"));
        oracionesList.add(new Oraciones("Via Crucis 2003", "Con meditaciones de Juan Pablo II"));
        oracionesList.add(new Oraciones("Via Crucis 2005", "Con meditaciones de Joseph Ratzinger"));
    }

}
