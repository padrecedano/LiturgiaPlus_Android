package org.deiverbum.app.model;

import android.text.Spanned;

import org.deiverbum.app.utils.Utils;

public class Invitatorio {
    public String antifona;
    public String texto;

    public String getAntifona() {
        return antifona;
    }

    public void setAntifona(String antifona) {
        this.antifona = antifona;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Spanned getAntifonaForRead() {
        return Utils.fromHtml("<p>" + antifona + ".</p>");
    }
}
