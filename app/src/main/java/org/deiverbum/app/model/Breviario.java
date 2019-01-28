package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.deiverbum.app.utils.Utils;

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
    private Intermedia intermedia;
    private Visperas visperas;

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

    public SpannableStringBuilder getInvocacion() {

        return Utils.getSaludoOficio();
    }

    public SpannableStringBuilder getInvocacionForRead() {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        ssb.append(Utils.getSaludoOficioForReader());
        return ssb;
    }


    public Intermedia getIntermedia() {
        return intermedia;
    }

    public Visperas getVisperas() {
        return visperas;
    }
}

