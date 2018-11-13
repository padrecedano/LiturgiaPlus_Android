package org.deiverbum.app.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.deiverbum.app.R;
import org.deiverbum.app.model.Benedictus;
import org.deiverbum.app.model.Biblica;
import org.deiverbum.app.model.Evangelio;
import org.deiverbum.app.model.Himno;
import org.deiverbum.app.model.Invitatorio;
import org.deiverbum.app.model.Laudes;
import org.deiverbum.app.model.LecturaBreve;
import org.deiverbum.app.model.MetaLiturgia;
import org.deiverbum.app.model.Misa;
import org.deiverbum.app.model.MisaLecturas;
import org.deiverbum.app.model.Mixto;
import org.deiverbum.app.model.Oficio;
import org.deiverbum.app.model.OficioLecturas;
import org.deiverbum.app.model.Patristica;
import org.deiverbum.app.model.Preces;
import org.deiverbum.app.model.Salmodia;
import org.deiverbum.app.model.Santo;
import org.deiverbum.app.utils.CustomTypefaceSpan;
import org.deiverbum.app.utils.TTS;
import org.deiverbum.app.utils.Utils;
import org.deiverbum.app.utils.UtilsOld;
import org.deiverbum.app.utils.VolleyErrorHelper;
import org.deiverbum.app.utils.ZoomTextView;
import org.json.JSONException;
import org.json.JSONObject;

import static org.deiverbum.app.utils.Constants.BR;
import static org.deiverbum.app.utils.Constants.MY_DEFAULT_TIMEOUT;
import static org.deiverbum.app.utils.Constants.PACIENCIA;
import static org.deiverbum.app.utils.Constants.SEPARADOR;
import static org.deiverbum.app.utils.Constants.URL_MIXTO;
import static org.deiverbum.app.utils.Utils.LS2;

public class MixtoActivity extends AppCompatActivity {
    private static final String TAG = "MixtoActivity";
    Spanned strContenido;
    JsonObjectRequest jsonObjectRequest;
    ZoomTextView mTextView;
    private UtilsOld utilClass;
    private RequestQueue requestQueue;
    private String strFechaHoy;
    private TTS tts;
    private Typeface typeface;
    private StringBuilder sbReader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tercia);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTextView = findViewById(R.id.tv_Zoomable);
        //Typeface typeface = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            typeface = getResources().getFont(R.font.roboto_black);
            mTextView.setTypeface(typeface);

        } else {

            typeface = ResourcesCompat.getFont(this, R.font.roboto_black);

        }
        //mTextView.setTypeface(typeface);

        utilClass = new UtilsOld();
        strFechaHoy = (getIntent().getExtras() != null) ? getIntent().getStringExtra("FECHA") : utilClass.getHoy();
        //mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        requestQueue = Volley.newRequestQueue(this);
        final ProgressBar progressBar = findViewById(R.id.progressBar);

        mTextView.setText(UtilsOld.fromHtml(PACIENCIA));
        jsonObjectRequest = new JsonObjectRequest(

                Request.Method.GET, URL_MIXTO + strFechaHoy, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        SpannableStringBuilder resp = getResponseData(response);
                        //strContenido = Utils.fromHtml(resp.toString());
                        //mTextView.setText(UtilsOld.fromHtml(resp.replaceAll(SEPARADOR, "")));
                        //int colorBlue = getResources().getColor(R.color.titleLiturgy);
                        //String text = getResources().getString(R.string.himno);
                        //TextAppearanceSpan styleGreen = new TextAppearanceSpan(getBaseContext(), R.style.CodeFont_Big);
                        //SpannableString green = new SpannableString(getResources().getString(R.string.himno));
                        //green.setSpan(styleGreen, 0, green.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                        //SpannableString spannable = new SpannableString(text);
                        // here we set the color
                        //spannable.setSpan(new ForegroundColorSpan(colorBlue), 0, text.length(), 0);
                        //spanstr.setSpan(new StyleSpan(Color.RED), 0, spanstr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        //spanstr.append("\n");
                        //spanstr.append("The first line is bold. This one isn't.");
                        //spanstr.append(spannable);
                        //spanstr.append(green);

                        //Spannable wordtoSpan = new SpannableString("I know just how to whisper, And I know just how to cry,I know just where to find the answers");

                        //wordtoSpan.setSpan(new ForegroundColorSpan(Color.BLUE), 10, 30, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                        SpannableStringBuilder spanstr = new SpannableStringBuilder("");
                        CharSequence cs = "invitatorio";
                        Spannable spannable = new SpannableString(cs);
                        spannable.setSpan(typeface, 0, spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                        //SpannableString spannableString = new SpannableString("*Hello World!");
                        //ForegroundColorSpan foregroundSpan = new ForegroundColorSpan(Color.RED);
                        //spannableString.setSpan(foregroundSpan, 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        spanstr.append(spannable);

                        //mTextView.setText(spanstr);

//Log.d(TAG,resp);
                        //Log.d(TAG,spanstr.toString());
                        Typeface myTypeface = Typeface.create(ResourcesCompat.getFont(getApplicationContext(), R.font.roboto_black),
                                Typeface.BOLD);
                        SpannableString string = new SpannableString("Text with typeface span.llllllllllllllllllllllllllllll");
                        // string.setSpan(new TypefaceSpan("font/roboto_black"), 0, string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        // string.setSpan(new TypefaceSpan("monospace"), 19, 22, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                        //spannable.setSpan( new CustomTypefaceSpan("roboto",myTypeface), 0, string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        SpannableStringBuilder SS = new SpannableStringBuilder("invitatorio");
                        SS.setSpan(new CustomTypefaceSpan("", myTypeface), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

                        //mTextView.setText(SS, TextView.BufferType.SPANNABLE);

                        mTextView.setText(resp, TextView.BufferType.SPANNABLE);

                        progressBar.setVisibility(View.INVISIBLE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyErrorHelper errorVolley = new VolleyErrorHelper();
                        String sError = VolleyErrorHelper.getMessage(error, getApplicationContext());
                        Log.d(TAG, "Error: " + sError);
                        mTextView.setText(UtilsOld.fromHtml(sError));
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }
        );

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
        progressBar.setVisibility(View.VISIBLE);

    }

    protected SpannableStringBuilder getResponseData(JSONObject jsonDatos) {


        //Gson gson = new Gson();


        SpannableStringBuilder sb = new SpannableStringBuilder();
        sbReader = new StringBuilder();
        try {
            Gson gson = new Gson();
            String ant = getString(R.string.ant);

            //Log.d(TAG,jsonDatos.toString());

            //Invitatorio restaurantObject = gson.fromJson(jsonDatos.invitario, Invitatorio.class);

//sb.append("ok");

            /*Obtenemos o declaramos variables*/
            JSONObject jsonBreviario = jsonDatos.getJSONObject("breviario");
            Mixto mixto = gson.fromJson(String.valueOf(jsonBreviario), Mixto.class);
            MetaLiturgia meta = mixto.getMetaLiturgia();
            Santo santo = mixto.getSanto();

            Oficio oficio = mixto.getOficio();
            Invitatorio invitatorio = oficio.getInvitatorio();
            OficioLecturas lecturasOficio = oficio.getOficioLecturas();
            Patristica patristica = lecturasOficio.getPatristica();
            Biblica biblica = lecturasOficio.getBiblica();

            Laudes laudes = mixto.getLaudes();
            Himno himno = laudes.getHimno();
            Salmodia salmodia = laudes.getSalmodia();
            LecturaBreve lecturaBreve = laudes.getLecturaBreve();
            Benedictus benedictus = laudes.getBenedictus();
            Preces preces = laudes.getPreces();
            Misa misa = mixto.getMisa();
            MisaLecturas misaLecturas = misa.getMisaLecturas();
            Evangelio misaEvangelio = misaLecturas.getEvangelio();

            String hora = "LAUDES y OFICIO";

            CharSequence santoNombre = (santo.getNombre().equals("")) ? "" : Utils.toH3(santo.getNombre() + LS2);

            SpannableStringBuilder titleInvitatorio = Utils.formatSubTitle("invitatorio");

            CharSequence santoVida = (santo.getVida().equals("")) ? "" : Utils.toSmallSize(santo.getVida() + Utils.LS);
            CharSequence metaSalterio = (meta.getSalterio().equals("")) ? "" : Utils.toSmallSizeRed(Utils.fromHtml(meta.getSalterio()) + Utils.LS);


            sb.append(meta.getFecha());
            sb.append(Utils.LS2);


            sb.append(Utils.toH2(meta.getTiempo()));
            sb.append(Utils.LS);
            sb.append(Utils.toH3(meta.getSemana()));
            sb.append(Utils.LS2);

            sb.append(Utils.toH3Red(hora));
            sb.append(Utils.LS);

            sb.append(santoNombre);
            sb.append(santoVida);
            sb.append(metaSalterio);

            sb.append(Utils.LS2);
            sb.append(Utils.getSaludoOficio());
            sb.append(Utils.LS2);
            sb.append(titleInvitatorio);
            sb.append(Utils.LS2);
            sb.append(Utils.fromHtml(ant));
            sb.append(invitatorio.getAntifona());
            sb.append(Utils.LS2);
            sb.append(Utils.fromHtml(invitatorio.getTexto()));
            sb.append(Utils.LS);
            sb.append(Utils.getFinSalmo());
            sb.append(Utils.LS2);
            sb.append(Utils.fromHtml(ant));
            sb.append(invitatorio.getAntifona());
            sb.append(Utils.LS2);

            sb.append(himno.getHeader());
            sb.append(Utils.LS2);
            sb.append(himno.getTexto());
            sb.append(Utils.LS2);

            sb.append(salmodia.getHeader());
            sb.append(Utils.LS2);
            sb.append(salmodia.getSalmoCompleto());
            sb.append(Utils.LS);
            sb.append(lecturaBreve.getHeaderLectura());
            sb.append(Utils.LS2);
            sb.append(lecturaBreve.getTexto());
            sb.append(Utils.LS2);
            sb.append(lecturaBreve.getHeaderResponsorio());
            sb.append(Utils.LS2);
            sb.append(lecturaBreve.getResponsorio());
            sb.append(Utils.LS);

            sb.append(Utils.formatSubTitle("lecturas del oficio"));
            sb.append(Utils.LS2);
            sb.append(biblica.getHeader());
            sb.append(Utils.LS2);
            sb.append(biblica.getLibro());
            sb.append("    ");

            sb.append(Utils.toRed(biblica.getCapitulo()));
            sb.append(", ");
            sb.append(Utils.toRed(biblica.getInicial()));
            sb.append(Utils.toRed(biblica.getFinal()));
            sb.append(Utils.LS2);

            sb.append(Utils.toRed(biblica.getTema()));

            sb.append(Utils.LS2);
            sb.append(biblica.getTexto());
            sb.append(Utils.LS);
            sb.append(Utils.toRed("Responsorio    "));
            sb.append(Utils.toRed(biblica.getRef()));
            sb.append(Utils.LS2);
            sb.append(biblica.getResponsorio());
            sb.append(Utils.LS2);
            sb.append(Utils.LS);

            sb.append(patristica.getHeader());
            sb.append(Utils.LS2);
            sb.append(patristica.padre);
            sb.append(", ");
            sb.append(patristica.obra);
            sb.append(Utils.LS);
            sb.append(Utils.toSmallSizeRed(patristica.fuente));
            sb.append(Utils.LS2);
            sb.append(Utils.toRed(patristica.tema));
            sb.append(Utils.LS2);

            sb.append(patristica.getTexto());

            sb.append(Utils.LS);
            sb.append(Utils.toRed("Responsorio    "));
            sb.append(Utils.toRed(patristica.ref));

            sb.append(Utils.LS2);

            sb.append(patristica.getResponsorio());
            sb.append(Utils.LS2);
            sb.append(Utils.LS);

            sb.append(Utils.formatSubTitle("evangelio del día"));
            sb.append(Utils.LS2);

            sb.append(misaEvangelio.libro);
            sb.append("    ");

            sb.append(Utils.toRed(misaEvangelio.ref));

            sb.append(Utils.LS2);
            sb.append(Utils.fromHtml(Utils.getFormato(misaEvangelio.texto)));

            sb.append(Utils.LS2);
            sb.append(Utils.LS);

            sb.append(benedictus.getHeader());
            sb.append(Utils.LS2);
            sb.append(benedictus.getAntifona());
            sb.append(Utils.LS2);
            sb.append(benedictus.getTexto());
            sb.append(Utils.LS2);
            sb.append(Utils.getFinSalmo());
            sb.append(Utils.LS2);
            sb.append(benedictus.getAntifona());
            sb.append(Utils.LS2);
            sb.append(Utils.LS);

            sb.append(preces.getHeader());
            sb.append(LS2);
            sb.append(preces.getPreces());
            sb.append(LS2);
            sb.append(Utils.LS);

            sb.append(Utils.formatTitle("PADRE NUESTRO"));
            sb.append(LS2);

            sb.append(Utils.getPadreNuestro());
            sb.append(LS2);
            sb.append(Utils.LS);

            sb.append(Utils.formatTitle("ORACIÓN"));
            sb.append(LS2);

            sb.append(Utils.fromHtml(laudes.getOracion()));

            /*Texto para el TTS*/

/*
            sbReader.append(meta.getFecha()+BR);
            sbReader.append(meta.getTiempo()+BR);
            sbReader.append(meta.getSemana()+BR);
*/
            sbReader.append(hora + BR);
            sbReader.append(SEPARADOR);

            sbReader.append(santoNombre + BR);
            sbReader.append(santoVida + BR);
            sbReader.append(metaSalterio + BR);
            sbReader.append(SEPARADOR);

            sbReader.append(Utils.getSaludoOficio());
            sbReader.append(SEPARADOR);

            sbReader.append(titleInvitatorio);
            sbReader.append(SEPARADOR);
            sbReader.append(invitatorio.getAntifona());
            sbReader.append(invitatorio.getTexto());

            sbReader.append(Utils.getFinSalmo());
            sbReader.append(invitatorio.getAntifona());
            sbReader.append(SEPARADOR);

            sbReader.append(himno.getHeader());
            sbReader.append(SEPARADOR);
            sbReader.append(himno.getTexto());
            sbReader.append(SEPARADOR);

            sbReader.append(salmodia.getHeader());
            sbReader.append(salmodia.getSalmosForRead());

            sbReader.append(lecturaBreve.getHeaderLectura());
            sbReader.append(SEPARADOR);
            sbReader.append(lecturaBreve.getTexto());
            sbReader.append(SEPARADOR);
            sbReader.append(lecturaBreve.getHeaderResponsorio());
            sbReader.append(SEPARADOR);
            sbReader.append(lecturaBreve.getResponsorio());
            sbReader.append(SEPARADOR);
            sbReader.append("<p>Lecturas del oficio</p>");
            sbReader.append(SEPARADOR);

            sbReader.append(biblica.getHeader());
            sbReader.append(SEPARADOR);
            sbReader.append(biblica.getLibro());
            sbReader.append(SEPARADOR);
            sbReader.append(biblica.getTema());
            sbReader.append(SEPARADOR);
            sbReader.append(biblica.getTexto());
            sbReader.append(SEPARADOR);
            sbReader.append("Responsorio ");
            sbReader.append(SEPARADOR);
            sbReader.append(biblica.getResponsorio());
            sbReader.append(SEPARADOR);

            sbReader.append(patristica.getHeader());
            sbReader.append(SEPARADOR);
            sbReader.append(patristica.padre);
            sbReader.append(", ");
            sbReader.append(patristica.obra);
            sbReader.append(SEPARADOR);
            sbReader.append(patristica.tema);
            sbReader.append(SEPARADOR);
            sbReader.append(patristica.getTexto());
            sbReader.append(SEPARADOR);

            sbReader.append("Responsorio ");
            sbReader.append(SEPARADOR);

            sbReader.append(patristica.getResponsorio());
            sbReader.append(SEPARADOR);

            sbReader.append("Evangelio del día");
            sbReader.append(SEPARADOR);
            sbReader.append(misaEvangelio.libro);
            sbReader.append(SEPARADOR);
            sbReader.append(misaEvangelio.texto);
            sbReader.append(SEPARADOR);

            sbReader.append(benedictus.getHeader());
            sbReader.append(SEPARADOR);
            sbReader.append(benedictus.getAntifona());
            sbReader.append(SEPARADOR);
            sbReader.append(benedictus.getTexto());
            sbReader.append(SEPARADOR);
            sbReader.append(Utils.getFinSalmo());
            sbReader.append(SEPARADOR);
            sbReader.append(benedictus.getAntifona());
            sbReader.append(SEPARADOR);

            sbReader.append(preces.getHeader());
            sbReader.append(SEPARADOR);
            sbReader.append(preces.getPreces());
            sbReader.append(SEPARADOR);


            sbReader.append(Utils.getPadreNuestro());
            sbReader.append(SEPARADOR);

            sbReader.append("ORACIÓN");
            sbReader.append(SEPARADOR);

            sbReader.append(laudes.getOracion());


            //Log.d(TAG,inv.getAntifona());
            /*
            JSONObject jsonInfo = jsonBreviario.getJSONObject("info");
            JSONObject jsonContenido = jsonBreviario.getJSONObject("contenido");
            JSONObject jsonSalmodia = jsonContenido.getJSONObject("salmodia");
            JSONObject lecturasOficio = jsonContenido.getJSONObject("lecturasOficio");
            JSONObject biblica = lecturasOficio.getJSONObject("biblica");
            JSONObject patristica = lecturasOficio.getJSONObject("patristica");
            JSONObject lecturaBreve = jsonContenido.getJSONObject("lecturaBreve");
            //JSONObject antBenedictus = jsonContenido.getJSONObject("antBenedictus");
            JSONObject lecturaMisa = jsonContenido.getJSONObject("lecturaMisa");
            JSONObject preces = jsonContenido.getJSONObject("preces");
/*
            //int tipoAntifonas=jsonSalmodia.getInt("tipo");
            String strFecha = jsonInfo.getString("fecha") + BRS;
            String strTiempo = "<h1>" + jsonInfo.getString("tiempo") + "</h1>";
            String strSemana = "<h4>" + jsonInfo.getString("semana") + "</h4>";
            String strSalterio = CSS_RED_A+jsonInfo.getString("salterio") + CSS_RED_Z+BRS;
            String sMensaje = jsonInfo.getString("mensaje");

            String sHimno = HIMNO + utilClass.getFormato(jsonContenido.getString("himno")) + BRS;
            //JSONArray arrSalmos=jsonSalmodia.getJSONArray("salmos");
            int tipoAntifonas=jsonSalmodia.getInt("tipo");
            JSONArray itemsSalmos=jsonSalmodia.getJSONArray("items");

            String txtResponsorio = jsonContenido.getString("responsorio");
            if (!utilClass.isNull(txtResponsorio)) {
                String[] arrResponsorio = txtResponsorio.split("\\|");
                txtResponsorio = RESP_V + arrResponsorio[0] + BR + RESP_R + arrResponsorio[1] + BRS;
            }

            //Bíblica
            String txtBiblicaFuente = PRIMERA_LECTURA + biblica.getString("libro") +
                    CSS_RED_A + NBSP_4 +
                    biblica.getString("capitulo") + ", " + biblica.getString("v_inicial") + biblica.getString("v_final")
                    + CSS_RED_Z + BRS;
            String txtBiblicaTema = CSS_RED_A + biblica.getString("tema") + CSS_RED_Z;
            String txtBiblicaTexto = biblica.getString("texto");
            String txtBiblicaRef = CSS_RED_A + RESP_LOWER + NBSP_2 + biblica.getString("ref") + CSS_RED_Z + BRS;
            String txtBiblicaResponsorio = biblica.getString("responsorio");

            //Hay que construir el responsorio. Los responsorios son recibidos en forma de matriz y en base a un código son desplegados
            String txtBiblicaResponsorioFinal = "";
            if (txtBiblicaResponsorio != null && !txtBiblicaResponsorio.isEmpty() && !txtBiblicaResponsorio.equals("null")) {

                String[] arrPartes = txtBiblicaResponsorio.split("\\|");
                txtBiblicaResponsorioFinal = utilClass.getResponsorio(arrPartes, 1);
            }

            //Patrística
            String txtPadres;

            String txtPadresObra = patristica.getString("padre") + ", " +
                    patristica.getString("obra");
            String txtPadresFuente = BR + CSS_RED_A + CSS_SM_A + "(" + patristica.getString("fuente") + ")" + CSS_SM_Z +
                    BRS + patristica.getString("tema") + CSS_RED_Z;

            String txtPadresTexto = patristica.getString("texto");
            String txtPadresRef = CSS_RED_A + RESP_LOWER + " " + patristica.getString("ref") + CSS_RED_Z + BRS;
            String txtPadresResponsorio = patristica.getString("responsorio");
            String txtPadresRespFinal = "";
            if (txtPadresResponsorio != null && !txtPadresResponsorio.isEmpty() && !txtPadresResponsorio.equals("null")) {

                String[] arrParts = txtPadresResponsorio.split("\\|");
                txtPadresRespFinal = utilClass.getResponsorio(arrParts, 1);
            }





            String sBiblicaResp = lecturaBreve.getString("responsorio");
            String sResponsorio = "";
            if (sBiblicaResp != null && !sBiblicaResp.isEmpty() && !sBiblicaResp.equals("null")) {

                String[] respArray = sBiblicaResp.split("\\|");
                sResponsorio = utilClass.getResponsorio(respArray, 31);
            }


            String sBiblica = CSS_RED_A + LECTURA_BREVE + NBSP_4
                    + lecturaBreve.getString("ref") + CSS_RED_Z + BRS + lecturaBreve.getString("texto") + BRS;



            String sAntifonaCE = jsonContenido.getString("antBenedictus");
            sAntifonaCE = BRS + PRE_ANT + sAntifonaCE + BRS;
            String sPrecesIntro = preces.getString("intro");
            String sPreces = "";
            if (!utilClass.isNull(sPrecesIntro)) {
                String[] introArray = sPrecesIntro.split("\\|");
                String sPrecesCuerpo = utilClass.getFormato(preces.getString("texto"));
                sPreces = PRECES + utilClass.getPreces(introArray, sPrecesCuerpo);
            }
String evangelioMisa="<h2>evangelio</h2>"+utilClass.getFormato(lecturaMisa.getString("texto"))+BRS;


            String sOracion = ORACION + utilClass.getFormato(jsonContenido.getString("oracion"));

            */
            /*LLenamos el sb en el orden*/
/*
            sb.append(strFecha);
            sb.append(strTiempo);
            sb.append(strSemana);
            sb.append(HI_TITULO);
            sb.append(strSalterio);
            sb.append(sMensaje);
            sb.append(SEPARADOR);
            sb.append(INVOCACION_INICIAL);
            sb.append(SALUDO_DIOSMIO);
            sb.append(sHimno);
            sb.append(SEPARADOR);
            sb.append(utilClass.getSalmos(itemsSalmos,tipoAntifonas));

 */
 /*
            sb.append(sBiblica);
            sb.append(SEPARADOR);
            sb.append(sResponsorio);
            sb.append(SEPARADOR);


            sb.append("<h3>lecturas</h3>");
            sb.append(txtResponsorio);
            sb.append(SEPARADOR);
            sb.append(txtBiblicaFuente);
            sb.append(SEPARADOR);
            sb.append(txtBiblicaTema);
            sb.append(SEPARADOR);
            sb.append(txtBiblicaTexto);
            sb.append(SEPARADOR);
            sb.append(txtBiblicaRef);
            sb.append(SEPARADOR);
            sb.append(txtBiblicaResponsorioFinal);
            sb.append(SEPARADOR);

            sb.append(SEGUNDA_LECTURA);
            sb.append(txtPadresObra);
            sb.append(SEPARADOR);
            sb.append(txtPadresFuente);
            sb.append(SEPARADOR);
            sb.append(txtPadresTexto);
            sb.append(SEPARADOR);
            sb.append(txtPadresRef);
            sb.append(SEPARADOR);
            sb.append(txtPadresRespFinal);
            sb.append(SEPARADOR);

            sb.append(evangelioMisa);
            sb.append(SEPARADOR);

            sb.append(CE);
            sb.append(sAntifonaCE);
            sb.append(utilClass.getFormato(BENEDICTUS));
            sb.append(FIN_SALMO);
            sb.append(sAntifonaCE);
            sb.append(SEPARADOR);

            sb.append(sPreces);
            sb.append(SEPARADOR);
            sb.append(PADRENUESTRO_TITULO);
            sb.append(utilClass.getFormato(PADRENUESTRO));






            sb.append(sOracion);
            */

        } catch (JSONException e) {
            e.printStackTrace();

        }

        return sb;
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
            String html = String.valueOf(Utils.fromHtml(sbReader.toString()));
            String[] strPrimera = html.split(SEPARADOR);
            tts = new TTS(getApplicationContext(), strPrimera);
        }

        if (id == R.id.item_calendario) {
            Intent i = new Intent(this, CalendarioActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (tts != null) {
            tts.cerrar();
        }
    }



}
