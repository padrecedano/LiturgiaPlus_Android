package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import org.deiverbum.app.utils.Utils;

public class SalmoCompleto {
    private String orden;
    private String antifona;
    private String ref;
    private String tema;
    private String intro;
    private String parte;
    private String salmo;

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public String getAntifona() {
        return antifona;
    }

    public String getAntifonaNew(int tipo) {
        String ant = "a";
        if (tipo == 1) {
            ant = "";
        }
        return ant;
    }

    public void setAntifona(String antifona) {
        this.antifona = antifona;
    }


    public SpannableStringBuilder getRef() {
        SpannableStringBuilder ssb = new SpannableStringBuilder(Utils.fromHtml(ref));
        return Utils.ssbRed(ssb);
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getTema() {
        return tema;

    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getParte() {
        return parte;

    }

    public void setParte(String parte) {
        this.parte = parte;
    }

    public String getSalmo() {
        return salmo;
    }

    public void setSalmo(String salmo) {
        this.salmo = salmo;
    }

    public Spanned getTextos() {
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        Spanned str = Utils.fromHtml(Utils.getFormato(intro));
        ssb.append(str);
        return Utils.ssbSmallSize(ssb);
    }


}