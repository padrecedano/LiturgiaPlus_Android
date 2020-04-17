package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

public class CompletasLectura {
    private String forma;
    private String ref;
    private String texto;
    //private List<Responsorio> responsorio;

    public String getRef() {
        return this.ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getTexto() {
        return this.texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    /*
        public List<Responsorio> getResponsorio() {
            return this.responsorio;
        }
        public void setResponsorio(List<Responsorio> responsorio) {
            this.responsorio = responsorio;
        }
    */
    public SpannableStringBuilder getHeader() {

        return Utils.formatTitle("LECTURA BREVE    " + this.ref);
    }
/*
    public Spanned getResponsorioSpan(int timeID) {
        int mIndex=(timeID==6) ? 1: 0;
        Responsorio mResponsorio=responsorio.get(mIndex);
        String mForma=mResponsorio.getForma();

        String r = "Revisar responsorio";
        if (!mForma.isEmpty()) {
            int nForma = Integer.parseInt(mForma);
            String s=mResponsorio.getTexto();
            if (s != null && !s.isEmpty()) {
                if (s.contains("|")) {
                    String[] arrPartes = s.split("\\|");
                    r = Utils.getResponsorio(arrPartes, nForma);
                } else {
                    r = s;
                }
            }
        }

        return Utils.fromHtml(r);
    }

    public String getForma() {
        return forma;
    }

    public void setForma(String forma) {
        this.forma = forma;
    }
*/

}
