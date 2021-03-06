package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import org.deiverbum.app.utils.Utils;

import static org.deiverbum.app.utils.Utils.LS2;

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

    public SpannableStringBuilder getResponsorioSpan() {
        int nForma = Integer.parseInt(forma);
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        if (nForma == 0) {
            ssb.append(Utils.toRed("En lugar del responsorio breve se dice la siguiente antífona:"));
            ssb.append(LS2);
            ssb.append(responsorio);
        } else {
            String r = "Revisar responsorio";
            if (!forma.isEmpty()) {
                if (responsorio != null && !responsorio.isEmpty()) {
                    if (responsorio.contains("|")) {
                        String[] arrPartes = responsorio.split("\\|");
                        r = Utils.getResponsorio(arrPartes, nForma);
                    } else {
                        r = responsorio;
                    }
                }
            }
            ssb.append(getHeaderResponsorio());
            ssb.append(LS2);
            ssb.append(Utils.fromHtml(r));
        }
        return ssb;
    }

    /**
     * Devuelve el texto del Responsorio Breve con formato
     * y adaptación adecuada para la lectura de voz
     *
     * @return Una cadena formateada con el responsorio
     */

    public SpannableStringBuilder getResponsorioForRead() {
        int nForma = Integer.parseInt(forma);
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        if (nForma == 0) {
            ssb.append(responsorio);
        } else {
            String r = "Revisar responsorio";
            if (!forma.isEmpty()) {
                if (responsorio != null && !responsorio.isEmpty()) {
                    if (responsorio.contains("|")) {
                        String[] arrPartes = responsorio.split("\\|");
                        r = Utils.getResponsorioForReader(arrPartes, nForma);
                    } else {
                        r = responsorio;
                    }
                }
            }
            ssb.append(LS2);
            ssb.append(Utils.fromHtml(r));
        }
        return ssb;
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