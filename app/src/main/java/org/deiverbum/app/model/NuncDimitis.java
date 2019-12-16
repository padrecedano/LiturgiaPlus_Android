package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

public class NuncDimitis {

    //@SerializedName("antifona")
    //@Expose
    private String antifona;
    //@SerializedName("texto")
    //@Expose
    private String texto;

    public NuncDimitis() {
    }

    public String getAntifona() {
        return antifona;

    }

    public SpannableStringBuilder getAntifonaSpan() {
        SpannableStringBuilder ssb = new SpannableStringBuilder("");

        String preAnt = "Ant. ";
        ssb.append(Utils.toRed(preAnt));
        ssb.append(antifona);
        return ssb;
    }
    public void setAntifona(String antifona) {
        this.antifona = antifona;
    }

    public String getAntifonaForRead() {
        return antifona;


    }

    public String getTexto() {

        return Utils.getFormato(texto);
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public SpannableStringBuilder getHeader() {

        return Utils.formatTitle("CÁNTICO EVANGÉLICO");
    }
}

