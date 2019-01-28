package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import org.deiverbum.app.utils.Utils;

public class LecturaBreve {
    public String ref;
    public String texto;
    public String responsorio;
    public int forma;

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Spanned getResponsorio() {
        String r = "";
        if (responsorio != null && !responsorio.isEmpty() && !responsorio.equals("null")) {

            String[] arrPartes = responsorio.split("\\|");
            r = Utils.getResponsorio(arrPartes, forma);
        }
        return Utils.fromHtml(r);
    }

    public Spanned getResponsorioForRead() {
        String r = "";
        if (responsorio != null && !responsorio.isEmpty() && !responsorio.equals("null")) {

            String[] arrPartes = responsorio.split("\\|");
            r = Utils.getResponsorioForReader(arrPartes, forma);
        }
        return Utils.fromHtml(r);
    }


    public void setResponsorio(String responsorio) {
        this.responsorio = responsorio;
    }

    public int getForma() {
        return forma;
    }

    public void setForma(int forma) {
        this.forma = forma;
    }

    public SpannableStringBuilder getHeaderLectura() {

        return Utils.formatTitle("LECTURA BREVE    " + this.ref);
    }

    public SpannableStringBuilder getHeaderResponsorio() {

        return Utils.formatTitle("RESPONSORIO BREVE");
    }
}