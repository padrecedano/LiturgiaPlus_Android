package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

public class Liturgia {
    private List<HomiliaCompleta> homiliaCompleta;
    private List<ComentarioBiblico> comentarioCompleto;
    private Comentario comentario;

    private Misa misa;
    private MetaLiturgia meta;
    @SerializedName("lh")
    @Expose
    private Breviario breviario;
    private HashMap<String, Object> lhFirebase;
    private Santo santo;
    public boolean hasSaint = false;

    public Liturgia() {
    }


    public List<HomiliaCompleta> getHomiliaCompleta() {
        return homiliaCompleta;
    }

    public List<ComentarioBiblico> getComentarioCompleta() {
        return comentarioCompleto;
    }

    public Comentario getComentario() {
        return comentario;
    }

    public void setComentario(Comentario comentario) {
        this.comentario = comentario;
    }
    public void setHomiliaCompleta(List<HomiliaCompleta> homiliaCompleta) {
        this.homiliaCompleta = homiliaCompleta;
    }

    public String getTitulo() {

        if (hasSaint) {
            return santo.getNombre() + "\n\n";
        } else {
            return meta.getTitulo() + "\n\n";
        }
    }

    public SpannableStringBuilder getVida() {
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        if (meta.getHasSaint()) {
            ssb.append(santo.getVidaSmall());
        }
        return ssb;
    }

    public Misa getMisa() {
        return misa;
    }

    public void setMisa(Misa misa) {
        this.misa = misa;
    }

    public Santo getSanto() {
        return santo;
    }

    public void setSanto(Santo santo) {
        this.santo = santo;
    }
    public Breviario getBreviario() {
        return breviario;
    }

    public void setBreviario(Breviario breviario) {
        this.breviario = breviario;
    }

    public HashMap<String, Object> getLh() {
        return lhFirebase;
    }

    /*
        @PropertyName("lh")ListList
        public void setLh(List<String> lh) {
            this.lh = lh;public void setLh(HashMap<String,Object> lh) {
        }*/
    public void setLh(HashMap<String, Object> lh) {
        this.lhFirebase = lh;
    }
    public String toString() {
        return "This is the data: " + meta.getFecha();
    }

    public MetaLiturgia getMetaLiturgia() {
        return meta;
    }

    public MetaLiturgia getMeta() {
        return meta;
    }
    public void setMetaLiturgia(MetaLiturgia meta) {
        this.meta = meta;
    }


}

