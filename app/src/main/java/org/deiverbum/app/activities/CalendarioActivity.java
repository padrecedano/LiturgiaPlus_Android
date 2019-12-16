package org.deiverbum.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.deiverbum.app.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.prolificinteractive.materialcalendarview.MaterialCalendarView.SELECTION_MODE_SINGLE;

public class CalendarioActivity extends AppCompatActivity implements OnDateSelectedListener {
    private static final String TAG = "CalendarioActivity";
    String items;
    StringRequest sStringRequest;

    JsonArrayRequest jsArrayRequest;
    CalendarView calendar;
    private RequestQueue requestQueue;
    private String strFecha;
    private MaterialCalendarView mCalendarView;
    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance(DateFormat.MEDIUM);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mCalendarView = findViewById(R.id.calendarView);
        mCalendarView.setSelectionMode(SELECTION_MODE_SINGLE);
        mCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .commit();

        mCalendarView.setOnDateChangedListener(this);
        this.registerForContextMenu(mCalendarView);


    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.calendario_menu, menu);

        super.onCreateContextMenu(menu, v, menuInfo);

    }

    public boolean onContextItemSelected(MenuItem item) {
        Intent i = null;
        switch (item.getItemId()) {

            case R.id.nav_misa:
                i = new Intent(this, MisaActivity.class);
                i.putExtra("FECHA", strFecha);
                startActivity(i);
                return true;

            case R.id.nav_homilias:
                i = new Intent(this, HomiliasActivity.class);
                i.putExtra("FECHA", strFecha);
                startActivity(i);
                return true;

            case R.id.nav_lecturas:
                i = new Intent(this, LecturasActivity.class);
                i.putExtra("FECHA", strFecha);
                startActivity(i);
                return true;

            case R.id.nav_comentarios:
                i = new Intent(this, ComentariosActivity.class);
                i.putExtra("FECHA", strFecha);
                startActivity(i);
                return true;

            case R.id.nav_santo:
                i = new Intent(this, SantosActivity.class);
                i.putExtra("FECHA", strFecha);
                startActivity(i);
                return true;

            case R.id.nav_mixto:
                i = new Intent(this, MixtoActivity.class);
                i.putExtra("FECHA", strFecha);
                startActivity(i);
                return true;

            case R.id.nav_oficio:
                i = new Intent(this, OficioActivity.class);
                i.putExtra("FECHA", strFecha);
                startActivity(i);
                return true;

            case R.id.nav_laudes:
                i = new Intent(this, LaudesActivity.class);
                i.putExtra("FECHA", strFecha);
                startActivity(i);
                return true;

            case R.id.nav_tercia:
                i = new Intent(this, TerciaActivity.class);
                i.putExtra("FECHA", strFecha);
                startActivity(i);
                return true;

            case R.id.nav_sexta:
                i = new Intent(this, SextaActivity.class);
                i.putExtra("FECHA", strFecha);
                startActivity(i);
                return true;

            case R.id.nav_nona:
                i = new Intent(this, NonaActivity.class);
                i.putExtra("FECHA", strFecha);
                startActivity(i);
                return true;

            case R.id.nav_visperas:
                i = new Intent(this, VisperasActivity.class);
                i.putExtra("FECHA", strFecha);
                startActivity(i);
                return true;

            case R.id.nav_completas:
                i = new Intent(this, CompletasActivity.class);
                i.putExtra("FECHA", strFecha);
                startActivity(i);
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }




    private String getSelectedDatesString() {
        CalendarDay date = mCalendarView.getSelectedDate();
        if (date == null) {
            return "00000000";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(date.getDate());//FORMATTER.format(date.getDate());
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView mCalendarView, @Nullable CalendarDay date, boolean selected) {
        mCalendarView.performLongClick();
        strFecha=getSelectedDatesString();
    }

}
