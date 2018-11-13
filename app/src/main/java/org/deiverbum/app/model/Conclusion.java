package org.deiverbum.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Conclusion {

    @SerializedName("bendicion")
    @Expose
    private String bendicion;
    @SerializedName("antVirgen")
    @Expose
    private String antVirgen;

    public String getBendicion() {
        return bendicion;
    }

    public void setBendicion(String bendicion) {
        this.bendicion = bendicion;
    }

    public String getAntVirgen() {
        return antVirgen;
    }

    public void setAntVirgen(String antVirgen) {
        this.antVirgen = antVirgen;
    }

}

