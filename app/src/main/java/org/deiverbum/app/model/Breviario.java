package org.deiverbum.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Breviario {

    @SerializedName("metaLiturgia")
    @Expose
    private MetaLiturgia metaLiturgia;
    @SerializedName("completas")
    @Expose
    private Completas completas;
    @SerializedName("santo")
    @Expose
    private Santo santo;
    private Oficio oficio;
    private Laudes laudes;

    public MetaLiturgia getMetaLiturgia() {
        return metaLiturgia;
    }

    public void setMetaLiturgia(MetaLiturgia metaLiturgia) {
        this.metaLiturgia = metaLiturgia;
    }

    public Completas getCompletas() {
        return completas;
    }

    public void setCompletas(Completas completas) {
        this.completas = completas;
    }

    public Santo getSanto() {
        return santo;
    }

    public Oficio getOficio() {
        return oficio;
    }

    public Laudes getLaudes() {
        return laudes;
    }
}

