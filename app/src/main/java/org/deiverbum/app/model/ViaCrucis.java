package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

import java.util.List;

import static org.deiverbum.app.utils.Utils.LS;
import static org.deiverbum.app.utils.Utils.LS2;

public class ViaCrucis {

    private String titulo;
    private String fecha;
    private String autor;

    private IntroViaCrucis introViaCrucis;
    private String adoramus;
    private List<String> respuestas;
    private List<Estacion> estaciones;
    private String oracion;


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public IntroViaCrucis getIntroViaCrucis() {
        return this.introViaCrucis;
    }

    public void setIntro(IntroViaCrucis introViaCrucis) {
        this.introViaCrucis = introViaCrucis;
    }

    public SpannableStringBuilder getAdoramus() {
        String[] textParts = adoramus.split("\\|");
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        ssb.append(Utils.toRed("V/. "));
        ssb.append(textParts[0]);
        ssb.append(LS2);
        ssb.append(Utils.toRed("R/. "));
        ssb.append(textParts[1]);
        return ssb;//this.adoramus;
    }

    public void setAdoramus(String adoramus) {
        this.adoramus = adoramus;
    }

    public List<String> getRespuestas() {
        return this.respuestas;
    }

    public void setRespuestas(List<String> respuestas) {
        this.respuestas = respuestas;
    }

    public SpannableStringBuilder getAllEstaciones(boolean isForRead) {
        SpannableStringBuilder ssb = new SpannableStringBuilder("");

        if (isForRead == false) {
            for (Estacion e : estaciones) {
                ssb.append(LS2);
                ssb.append(Utils.toH3Red(e.getTitulo()));
                ssb.append(LS2);
                ssb.append(Utils.toH3(e.getSubtitulo()));
                ssb.append(LS2);
                ssb.append(getAdoramus());
                ssb.append(LS2);
                ssb.append(e.getTextoBiblicoSpan());
                ssb.append(LS2);
                ssb.append(e.getMeditacionSpan(respuestas));
                ssb.append(LS);
                ssb.append(Utils.getPadreNuestro());
                ssb.append(LS2);
                ssb.append(Utils.toH4Red("Estrofa del Stabat Mater"));
                ssb.append(LS2);
                ssb.append(Utils.fromHtml(Utils.getFormato(e.getCanto())));
                ssb.append(LS2);
            }
        } else {
            String[] textParts = adoramus.split("\\|");
            String txtAdoramus = textParts[0] + "." + textParts[1] + ".";

            for (Estacion e : estaciones) {
                ssb.append(LS2);
                ssb.append(e.getTitulo());
                ssb.append(LS2);
                ssb.append(e.getSubtitulo());
                ssb.append(LS2);

                ssb.append(txtAdoramus);
                ssb.append(LS2);
                ssb.append(e.getTextoBiblicoSpan());
                ssb.append(LS2);
                ssb.append(e.getMeditacionSpan(respuestas));
                ssb.append(LS);
                ssb.append(Utils.getPadreNuestro());
                ssb.append(LS2);
                ssb.append("Estrofa del Stabat Mater.");
                ssb.append(LS2);
                ssb.append(Utils.fromHtml(Utils.getFormato(e.getCanto())));
                ssb.append(LS2);
            }


        }

        return ssb;
    }


    public List<Estacion> getEstaciones() {
        return this.estaciones;
    }

    public void setEstaciones(List<Estacion> estaciones) {
        this.estaciones = estaciones;
    }

    public String getOracion() {
        return this.oracion;
    }

    public void setOracion(String oracion) {
        this.oracion = oracion;
    }

}
