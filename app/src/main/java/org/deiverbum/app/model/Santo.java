package org.deiverbum.app.model;

import android.text.Spanned;

import org.deiverbum.app.utils.Utils;

public class Santo {
    private String nombre;
    private String vida;
    private String martirologio;

    public String getNombre() {

        return nombre;
    }

    public void setNombre(String nombre) {

        this.nombre = nombre;
    }

    public Spanned getVidaSmall() {
        if (!vida.equals("")) {
            return Utils.fromHtml("<p><small>" + vida + "</small></p>");
        } else {

            return Utils.fromHtml("");
        }
    }

    public String getVida() {
        return vida;
    }

    public void setVida(String vida) {
        this.vida = vida;
    }

    public String getMartirologio() {
        return martirologio;
    }
}

