package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

public class Breviario {


    private MetaLiturgia metaLiturgia;
    private Santo santo;
    private Oficio oficio;
    private Laudes laudes;
    private Intermedia intermedia;
    private Visperas visperas;
    private Completas completas;
    private Mixto mixto;
    private Misa misa;
    private String metaInfo;


    public Breviario() {
    }
    public MetaLiturgia getMetaLiturgia() {
        return metaLiturgia;
    }

    public String getMetaInfo() {

        if (!metaInfo.equals("")) {
            return "<br><br>" + metaInfo;
        } else {
            return "";
        }
    }

    public SpannableStringBuilder getInvocacion() {

        return Utils.getSaludoOficio();
    }

    public void setMeta(String metaInfo) {
        this.metaInfo = metaInfo;
    }

    public void setVisperas(Visperas visperas) {
        this.visperas = visperas;
    }

    public Santo getSanto() {
        return santo;
    }

    public void setSanto(Santo santo) {
        this.santo = santo;
    }

    public Oficio getOficio() {
        return oficio;
    }

    public SpannableStringBuilder getInvocacionForRead() {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        ssb.append(Utils.getSaludoOficioForReader());
        return ssb;
    }

    public void setOficio(Oficio oficio) {
        this.oficio = oficio;
    }

    public Laudes getLaudes() {
        return laudes;
    }

    public void setLaudes(Laudes laudes) {
        this.laudes = laudes;
    }

    public Intermedia getIntermedia() {
        return intermedia;
    }

    public Visperas getVisperas() {
        return visperas;
    }

    public Completas getCompletas() {
        return completas;
    }

    public void setCompletas(Completas completas) {
        this.completas = completas;
    }

    public void setMetaLiturgia(MetaLiturgia metaLiturgia) {
        this.metaLiturgia = metaLiturgia;
    }

    public void setIntermedia(Intermedia intermedia) {
        this.intermedia = intermedia;
    }

    public Mixto getMixto() {
        return mixto;
    }

    public void setMixto(Mixto mixto) {
        this.mixto = mixto;

    }

    public Misa getMisa() {
        return misa;
    }


    public void setMisa(Misa misa) {
        this.misa = misa;

    }

}

