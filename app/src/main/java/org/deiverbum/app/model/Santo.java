package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;

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

    public SpannableStringBuilder getVidaSpan() {
        return Utils.fromHtmlSmall(vida);
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

