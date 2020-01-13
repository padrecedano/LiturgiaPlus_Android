package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import org.deiverbum.app.utils.Utils;


public class Himno {
    private String texto;

    public Himno() {
    }

    public Spanned getTextoSpan() {
        Spanned str = Utils.fromHtml(Utils.getFormato(texto));
        return str;
    }

    //@PropertyName("himno.texto")
    public String getTexto() {

        //return this.texto;
        //Spanned str = Utils.fromHtml(Utils.getFormato(texto));
        return texto;
    }


    public void setTexto(String texto) {
        this.texto = texto;
    }

    public SpannableStringBuilder getHeader() {

        return Utils.formatTitle("HIMNO");
    }

    public Spanned getHeaderForRead() {
        return Utils.fromHtml("<p>Himno.</p>");
    }

}
