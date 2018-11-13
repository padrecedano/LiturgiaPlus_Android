package org.deiverbum.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NuncDimitis {

    @SerializedName("antifona")
    @Expose
    private String antifona;
    @SerializedName("texto")
    @Expose
    private String texto;

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

}

