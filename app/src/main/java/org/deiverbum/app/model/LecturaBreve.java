package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import org.deiverbum.app.utils.Utils;

public class LecturaBreve {
    public String ref;
    public String texto;
    public String responsorio;
    public String forma;

    public LecturaBreve() {
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getResponsorio() {
        return responsorio;
    }

    public void setResponsorio(String responsorio) {
        this.responsorio = responsorio;
    }

    public Spanned getResponsorioSpan() {
        String r = "Revisar responsorio";
        if (!forma.isEmpty()) {
            int nForma = Integer.parseInt(forma);
            if (responsorio != null && !responsorio.isEmpty()) {
                if (responsorio.contains("|")) {
                    String[] arrPartes = responsorio.split("\\|");
                    r = Utils.getResponsorio(arrPartes, nForma);
                } else {
                    r = responsorio;
                }
            }
        }

        return Utils.fromHtml(r);
    }

    public Spanned getResponsorioForRead() {
        String r = "Revisar responsorio";
        if (!forma.isEmpty()) {
            int nForma = Integer.parseInt(forma);
            if (responsorio != null && !responsorio.isEmpty()) {
                if (responsorio.contains("|")) {

                    String[] arrPartes = responsorio.split("\\|");
                    r = Utils.getResponsorioForReader(arrPartes, nForma);
                } else {
                    r = responsorio;
                }
            } else {
                r = "Revisar texto responsorio";
            }
        } else {

            r = "Error en forma responsorio";
        }

        return Utils.fromHtml(r);
    }

    public String getForma() {
        return forma;
    }

    public void setForma(String forma) {
        this.forma = forma;
    }

    public SpannableStringBuilder getHeaderLectura() {

        return Utils.formatTitle("LECTURA BREVE    " + this.ref);
    }

    public SpannableStringBuilder getHeaderResponsorio() {
        return Utils.formatTitle("RESPONSORIO BREVE");
    }

    public Spanned getHeaderForRead() {
        return Utils.fromHtml("<p>Lectura Breve.</p>");
    }

    public Spanned getHeaderResponsorioForRead() {
        return Utils.fromHtml("<p>Responsorio Breve.</p>");
    }
}