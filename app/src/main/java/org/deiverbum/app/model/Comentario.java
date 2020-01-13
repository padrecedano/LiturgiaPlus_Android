package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

import java.util.List;

import static org.deiverbum.app.utils.Constants.SEPARADOR;
import static org.deiverbum.app.utils.Utils.LS2;

public class Comentario {
    private String pericopa;
    private List<ComentarioBiblico> comentariosBiblicos;
    private List<ComentarioBiblico> comentarioCompleto;

    public Comentario() {
    }

    public List<ComentarioBiblico> getComentariosBiblicos() {
        return comentarioCompleto;
    }

    public void setComentariosBiblicos(List<ComentarioBiblico> comentarioCompleto) {
        this.comentarioCompleto = comentarioCompleto;
    }

    public SpannableStringBuilder getComentarioCompleto() {
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        for (ComentarioBiblico c : comentarioCompleto) {
            /*
            Obra o=c.getObra();
            Padre p=o.getPadre();
            */
            ssb.append(Utils.toH2Red(c.getPadre()));
            ssb.append(Utils.LS);
            ssb.append(Utils.toH3Red(c.getObra()));
            if (!c.getRef().equals("")) {
                ssb.append(Utils.LS);
                ssb.append(Utils.toSmallSizeRed(c.getRef()));
            } else {
                ssb.append(LS2);
            }

            if (!c.getTema().equals("")) {
                ssb.append(LS2);
                ssb.append(Utils.toH4Red(c.getTema()));
            }

            if (!c.getCita().equals("")) {
                ssb.append(LS2);
                ssb.append(Utils.toMediumSize(c.getCita()));
            } else {
                ssb.append(LS2);
            }
            ssb.append(Utils.fromHtml(c.getTexto().replaceAll(SEPARADOR, "")));
            ssb.append(LS2);
        }
        return ssb;
    }

    public StringBuilder getComentarioCompletoForRead() {
        StringBuilder sb = new StringBuilder();

        for (ComentarioBiblico c : comentarioCompleto) {
            sb.append(Utils.toH3Red(c.getPadre()));
//            ssb.append(LS);
//            ssb.append(c.getTema());
            sb.append(LS2);
            sb.append(Utils.fromHtml(c.getTexto()));
            sb.append(SEPARADOR);

//            ssb.append(LS2);
        }
        return sb;
    }

    public String getPericopa() {
        return this.pericopa;
    }

    public void setPericopa(String pericopa) {
        this.pericopa = pericopa;
    }
}