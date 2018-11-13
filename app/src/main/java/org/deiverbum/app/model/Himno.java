package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.deiverbum.app.utils.Utils;


public class Himno {
    //    public String texto;
    @SerializedName("texto")
    @Expose
    private String texto;

    public Spanned getTexto() {
        Spanned str = Utils.fromHtml(Utils.getFormato(texto));
        return str;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public SpannableStringBuilder getHeader() {

        return Utils.formatTitle("HIMNO");
    }
}
