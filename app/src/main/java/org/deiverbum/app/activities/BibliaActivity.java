package org.deiverbum.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.deiverbum.app.R;
import org.deiverbum.app.data.BibliaAdapter;
import org.deiverbum.app.data.BibliaLibrosItemsAdapter;
import org.deiverbum.app.model.BibliaLibros;
import org.deiverbum.app.utils.ZoomTextView;

import java.util.ArrayList;
import java.util.List;

public class BibliaActivity extends AppCompatActivity implements BibliaLibrosItemsAdapter.ItemListener {
    private static final String TAG = "BibliaActivity";
    ZoomTextView mTextView;
    List<BibliaLibros> booksList;
    RecyclerView booksRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter oracionesAdapter;
    private BibliaLibrosItemsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oraciones_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        booksRecyclerView = findViewById(R.id.rv_menu);
        //ViewCompat.setNestedScrollingEnabled(oracionesRecyclerView,true);
        createBooksList();
        layoutManager = new LinearLayoutManager(this);
        oracionesAdapter = new BibliaAdapter(booksList);
        booksRecyclerView.setLayoutManager(layoutManager);
        booksRecyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //this.menu = menu;
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }


    private void createBooksList() {
        booksList = new ArrayList<>();
        booksList.add(new BibliaLibros(1, "Génesis", "En el principio creó Dios el cielo y la tierra"));
        booksList.add(new BibliaLibros(2, "Éxodo", "...los israelitas que fueron a Egipto con Jacob"));
        booksList.add(new BibliaLibros(3, "Levítico", "Yahvé llamó a Moisés y le habló así..."));
        booksList.add(new BibliaLibros(4, "Números", "Yahvé habló a Moisés en el desierto..."));
        booksList.add(new BibliaLibros(5, "Deuteronomio", "..."));
        booksList.add(new BibliaLibros(6, "Josué", "..."));
        booksList.add(new BibliaLibros(7, "Jueces", "..."));
        booksList.add(new BibliaLibros(8, "Rut", "..."));
        booksList.add(new BibliaLibros(9, "1 Samuel", "..."));
        booksList.add(new BibliaLibros(10, "2 Samuel", "..."));
        booksList.add(new BibliaLibros(11, "1 Reyes", "..."));
        booksList.add(new BibliaLibros(12, "2 Reyes", "..."));
        booksList.add(new BibliaLibros(13, "1 Crónicas", "..."));
        booksList.add(new BibliaLibros(14, "2 Crónicas", "..."));
        booksList.add(new BibliaLibros(15, "Esdras", "..."));
        booksList.add(new BibliaLibros(16, "Nehemías", "..."));
        booksList.add(new BibliaLibros(17, "Tobías", "..."));
        booksList.add(new BibliaLibros(18, "Judit", "..."));
        booksList.add(new BibliaLibros(19, "Ester", "..."));
        booksList.add(new BibliaLibros(20, "1 Macabeos", "..."));
        booksList.add(new BibliaLibros(21, "2 Macabeos", "..."));
        booksList.add(new BibliaLibros(22, "Salmos", "..."));
        booksList.add(new BibliaLibros(23, "Cantar de los Cantares", "..."));
        booksList.add(new BibliaLibros(24, "Lamentaciones", "..."));
        booksList.add(new BibliaLibros(25, "Job", "..."));
        booksList.add(new BibliaLibros(26, "Proverbios", "..."));
        booksList.add(new BibliaLibros(27, "Eclesiastés", "..."));
        booksList.add(new BibliaLibros(28, "Sabiduría", "..."));
        booksList.add(new BibliaLibros(29, "Eclesiástico", "..."));
        booksList.add(new BibliaLibros(30, "Isaías", "..."));
        booksList.add(new BibliaLibros(31, "Jeremías", "..."));
        booksList.add(new BibliaLibros(32, "Baruc", "..."));
        booksList.add(new BibliaLibros(33, "Ezequiel", "..."));
        booksList.add(new BibliaLibros(34, "Daniel", "..."));
        booksList.add(new BibliaLibros(35, "Oseas", "..."));
        booksList.add(new BibliaLibros(36, "Joel", "..."));
        booksList.add(new BibliaLibros(37, "Amós", "..."));
        booksList.add(new BibliaLibros(38, "Abdías", "..."));
        booksList.add(new BibliaLibros(39, "Jonás", "..."));
        booksList.add(new BibliaLibros(40, "Miqueas", "..."));
        booksList.add(new BibliaLibros(41, "Nahún", "..."));
        booksList.add(new BibliaLibros(42, "Habacuc", "..."));
        booksList.add(new BibliaLibros(43, "Sofonías", "..."));
        booksList.add(new BibliaLibros(44, "Ageo", "..."));
        booksList.add(new BibliaLibros(45, "Zacarías", "..."));
        booksList.add(new BibliaLibros(46, "Malaquías", "..."));
        booksList.add(new BibliaLibros(47, "Mateo", "..."));
        booksList.add(new BibliaLibros(48, "Marcos", "..."));
        booksList.add(new BibliaLibros(49, "Lucas", "..."));
        booksList.add(new BibliaLibros(50, "Juan", "..."));
        booksList.add(new BibliaLibros(51, "Hechos de los Apóstoles", "..."));
        booksList.add(new BibliaLibros(52, "Romanos", "..."));
        booksList.add(new BibliaLibros(53, "1 Corintios", "..."));
        booksList.add(new BibliaLibros(54, "2 Corintios", "..."));
        booksList.add(new BibliaLibros(55, "Gálatas", "..."));
        booksList.add(new BibliaLibros(56, "Efesios", "..."));
        booksList.add(new BibliaLibros(57, "Filipenses", "..."));
        booksList.add(new BibliaLibros(58, "Colosenses", "..."));
        booksList.add(new BibliaLibros(59, "1 Tesalonicenses", "..."));
        booksList.add(new BibliaLibros(60, "2 Tesalonicenses", "..."));
        booksList.add(new BibliaLibros(61, "1 Timoteo", "..."));
        booksList.add(new BibliaLibros(62, "2 Timoteo", "..."));
        booksList.add(new BibliaLibros(63, "Tito", "..."));
        booksList.add(new BibliaLibros(64, "Filemón", "..."));
        booksList.add(new BibliaLibros(65, "Hebreos", "..."));
        booksList.add(new BibliaLibros(66, "Santiago", "..."));
        booksList.add(new BibliaLibros(67, "1 Pedro", "..."));
        booksList.add(new BibliaLibros(68, "2 Pedro", "..."));
        booksList.add(new BibliaLibros(69, "1 Juan", "..."));
        booksList.add(new BibliaLibros(70, "2 Juan", "..."));
        booksList.add(new BibliaLibros(71, "3 Juan", "..."));
        booksList.add(new BibliaLibros(72, "Judas", "..."));
        booksList.add(new BibliaLibros(73, "Apocalipsis", "..."));
        adapter = new BibliaLibrosItemsAdapter(booksList, this);

    }


    @Override
    public void onItemClick(BibliaLibros item) {
        Intent i = new Intent(this, BibliaLibrosActivity.class);
        i.putExtra("BOOK_ID", item.getId());
        i.putExtra("BOOK_NAME", item.getName());
        startActivity(i);
    }


}
