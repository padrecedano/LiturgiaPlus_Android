package org.deiverbum.app.model;

public class Mixto {
    public MetaLiturgia metaLiturgia;
    public Santo santo;
    public Laudes laudes;
    public Oficio oficio;

    public Himno himno;
    public Misa misa;

    public Laudes getLaudes() {
        return laudes;
    }

    public void setLaudes(Laudes laudes) {
        this.laudes = laudes;
    }

    public Oficio getOficio() {
        return oficio;
    }

    public void setOficio(Oficio oficio) {
        this.oficio = oficio;
    }

    public MetaLiturgia getMetaLiturgia() {
        return metaLiturgia;
    }

    public void setMetaLiturgia(MetaLiturgia metaLiturgia) {
        this.metaLiturgia = metaLiturgia;
    }

    public Himno getHimno() {
        return himno;
    }

    public void setHimno(Himno himno) {
        this.himno = himno;
    }

    public Santo getSanto() {
        return santo;
    }

    public void setSanto(Santo santo) {
        this.santo = santo;
    }

    public Misa getMisa() {
        return misa;
    }

    public void setMisa(Misa misa) {
        this.misa = misa;
    }

    public String getTituloHora() {
        return "LAUDES y OFICIO";
    }

}
