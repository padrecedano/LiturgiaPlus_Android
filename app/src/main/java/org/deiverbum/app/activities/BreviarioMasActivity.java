package org.deiverbum.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spanned;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import org.deiverbum.app.R;
import org.deiverbum.app.data.CustomItemClickListener;
import org.deiverbum.app.data.RVAdapter;
import org.deiverbum.app.model.Person;
import org.deiverbum.app.utils.UtilsOld;
import org.deiverbum.app.utils.ZoomTextView;

import java.util.ArrayList;
import java.util.List;

public class BreviarioMasActivity extends AppCompatActivity {
    private static final String TAG = "CompletasActivity";
    Spanned strContenido;
    ZoomTextView mTextView;
    private UtilsOld utilClass;
    private RequestQueue requestQueue;
    private String strFechaHoy;
    private List<Person> persons;
    private RecyclerView rv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mixto);
        setContentView(R.layout.recyclerview_activity);

        rv = findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        initializeData();
        initializeAdapter();
    }

    private void initializeData() {
        persons = new ArrayList<>();
        persons.add(new Person("Rosario: Misterios Gloriosos", "domingos y miércoles", R.drawable.ic_about));
        persons.add(new Person("Rosario: Misterios Gozosos", "lunes y sábados", R.drawable.ic_author));
        persons.add(new Person("Rosario: Misterios Dolorosos", "martes y viernes", R.drawable.ic_about));
        persons.add(new Person("Rosario: Misterios Luminosos", "jueves", R.drawable.ic_help));
    }

    private void initializeAdapter() {
        RVAdapter adapter = new RVAdapter(persons, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(BreviarioMasActivity.this, "Clicked Item: " + position, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), RosarioActivity.class);
                i.putExtra("EXTRA_PAGE", 2);
                startActivity(i);

                //Intent i = new Intent(getApplicationContext(), RosarioActivity.class);
                //startActivity(i);
            }
        });
        rv.setAdapter(adapter);
    }


}
