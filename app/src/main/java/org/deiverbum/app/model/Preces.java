package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import org.deiverbum.app.utils.Utils;

public class Preces {
    public String intro;
    public String texto;

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Spanned getPreces() {
        //SpannableStringBuilder ssb=new SpannableStringBuilder(Utils.getPreces(intro,texto));
        return Utils.fromHtml(Utils.getPreces(intro, texto));
    }

    public String getTexto() {
        return texto;
    }


    public void setTexto(String texto) {
        this.texto = texto;
    }

    public SpannableStringBuilder getHeader() {

        return Utils.formatTitle("PRECES");
    }
}
