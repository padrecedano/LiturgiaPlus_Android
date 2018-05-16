package org.deiverbum.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.deiverbum.app.R;
import org.deiverbum.app.utils.Utils;

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
    private Utils utilClass;
    private RequestQueue requestQueue;
    private String strFecha;
    private MaterialCalendarView mCalendarView;
    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance(DateFormat.MEDIUM);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
  //      @BindView(R.id.calendarView)
  //      MaterialCalendarView widget;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mCalendarView = (MaterialCalendarView) findViewById(R.id.calendarView);
        mCalendarView.setSelectionMode(SELECTION_MODE_SINGLE);
        //mCalendarView.setCurrentDate(Calendar.getInstance());
        mCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .commit();

        mCalendarView.setOnDateChangedListener(this);

        this.registerForContextMenu(mCalendarView);

/*        final CalendarView simpleCalendarView = findViewById(R.id.calendarView);

        simpleCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String sb = String.format("%04d", year) +
                        String.format("%02d", month + 1) +
                        String.format("%02d", dayOfMonth);
                strFecha = sb;
                Log.d(TAG, "Día: " + dayOfMonth + " Mes: " + month + " Año: " + year);
                simpleCalendarView.performLongClick();
            }
        });

        this.registerForContextMenu(simpleCalendarView);
*/

        utilClass = new Utils();
//        final TextView mTextView = (TextView) findViewById(R.id.txt_santo);
//        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18)

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
        //Log.i(TAG,getSelectedDatesString()+"........++++++");
        mCalendarView.performLongClick();
        strFecha=getSelectedDatesString();
    }


}
