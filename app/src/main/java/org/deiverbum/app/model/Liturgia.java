package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;

import com.google.firebase.firestore.Exclude;

import java.util.List;

public class Liturgia {
    private List<HomiliaCompleta> homiliaCompleta;
    private List<ComentarioBiblico> comentarioCompleto;
    private Comentario comentario;

    private Misa misa;
    private MetaLiturgia meta;
    private Breviario lh;
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
        return lh;
    }

    public void setBreviario(Breviario lh) {
        this.lh = lh;
    }

    @Exclude
    public void getlh() {
    }

    public void setlh(Breviario lh) {
        this.lh = lh;
    }
    public String toString() {
        return "This is the data: ";
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

