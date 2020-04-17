package org.deiverbum.app.model;

import android.text.Spanned;

import org.deiverbum.app.utils.Utils;

public class Responsorio {
    String texto;
    String forma;

    public String getTexto() {
        return this.texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getForma() {
        return this.forma;
    }

    public void setForma(String forma) {
        this.forma = forma;
    }

    /**
     * Obtener responsorio de Lectura Breve para vista
     *
     * @return Texto del responsorio debidamente formateado
     */

    public Spanned getResponsorioSpan() {
        String r = "Revisar responsorio";
        if (!forma.isEmpty()) {
            int nForma = Integer.parseInt(forma);
            if (texto != null && !texto.isEmpty()) {
                if (texto.contains("|")) {
                    String[] arrPartes = texto.split("\\|");
                    r = Utils.getResponsorio(arrPartes, nForma);
                } else {
                    r = texto;
                }
            }
        }
        return Utils.fromHtml(r);
    }

    /**
     * Obtener responsorio de Lectura Breve para voz
     *
     * @return Texto del responsorio debidamente formateado
     */

    public Spanned getResponsorioForRead() {
        String r;//= "Revisar responsorio";
        if (!forma.isEmpty()) {
            int nForma = Integer.parseInt(forma);
            if (texto != null && !texto.isEmpty()) {
                if (texto.contains("|")) {
                    String[] arrPartes = texto.split("\\|");
                    r = Utils.getResponsorioForReader(arrPartes, nForma);
                } else {
                    r = texto;
                }
            } else {
                r = "Revisar texto responsorio";
            }
        } else {

            r = "Error en forma responsorio";
        }

        return Utils.fromHtml(r);
    }

}
