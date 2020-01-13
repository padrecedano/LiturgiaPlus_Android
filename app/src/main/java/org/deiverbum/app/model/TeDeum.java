package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import org.deiverbum.app.utils.Utils;

public class TeDeum {
    public boolean status;
    public String texto;

    public TeDeum() {
    }
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTexto() {
        return texto;
    }

    public Spanned getTextoSpan() {
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        if (status) {
            ssb.append(getHeader());
            ssb.append(Utils.LS2);

            ssb.append(Utils.getTeDeum());

            //String teDeum = !isStatus() ? Utils.getTeDeum() : "";
        }
        return ssb;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String ifApplicable() {
        return "";
    }

    public SpannableStringBuilder getHeader() {

        return Utils.formatTitle("TE DEUM");
    }
}

