package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

import java.util.List;

public class Misterios {
    public Gloriosos gloriosos;
    public Gozosos gozosos;
    public Luminosos luminosos;
    public Dolorosos dolorosos;

    public Gloriosos getGloriosos() {
        return gloriosos;
    }

    public void setGloriosos(Gloriosos gloriosos) {
        this.gloriosos = gloriosos;
    }

    public Gozosos getGozosos() {
        return gozosos;
    }

    public void setGozosos(Gozosos gozosos) {
        this.gozosos = gozosos;
    }

    public Luminosos getLuminosos() {
        return luminosos;
    }

    public void setLuminosos(Luminosos luminosos) {
        this.luminosos = luminosos;
    }

    public Dolorosos getDolorosos() {
        return dolorosos;
    }

    public void setDolorosos(Dolorosos dolorosos) {
        this.dolorosos = dolorosos;
    }

    public SpannableStringBuilder getContenido(Rosario r, int dayCode) {
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        List<String> list = null;
        Rosario rosario = new Rosario();
        switch (dayCode) {
            case 1:
                list = gloriosos.contenido;
                break;
            case 2:
                list = gozosos.contenido;
                break;
            case 3:
                list = dolorosos.contenido;
                break;
            case 4:
                list = luminosos.contenido;
                break;
            default:
        }
        for (String s : list) {
            ssb.append(Utils.toH3Red(s));
            ssb.append(Utils.LS2);
            ssb.append(r.misterioCompleto());
            ssb.append(Utils.LS2);
            //return "a";//contenido;
        }
        return ssb;
    }

    public SpannableStringBuilder getText(List<String> list) {
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        for (String s : list) {
            ssb.append(s);
            ssb.append(Utils.LS2);
            //return "a";//contenido;
        }

        return ssb;
    }


}
