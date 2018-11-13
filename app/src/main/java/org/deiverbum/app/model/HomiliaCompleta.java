package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import org.deiverbum.app.utils.Utils;

public class HomiliaCompleta {
    public String padre;
    public int id_homilia;
    public String texto;

    public String getPadre() {
        return padre;
    }

    public void setPadre(String padre) {
        this.padre = padre;
    }

    public int getId_homilia() {
        return id_homilia;
    }

    public void setId_homilia(int id_homilia) {
        this.id_homilia = id_homilia;
    }

    public Spanned getTexto() {
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        Spanned str = Utils.fromHtml(texto);
        //ssb.append(str);
        //str=Utils.toSmallSize(str);
        return str;
        //return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}