package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

public class OficioLecturas {
    private String responsorio;
    private Biblica biblica;
    private Patristica patristica;
    private TeDeum teDeum;

    public SpannableStringBuilder getResponsorio() {
        String[] textParts = responsorio.split("\\|");
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        if (textParts.length == 2) {
            ssb.append(Utils.toRed("V. "));
            ssb.append(textParts[0]);
            ssb.append(Utils.LS);
            ssb.append(Utils.toRed("R. "));
            ssb.append(textParts[1]);
        } else {
            ssb.append(responsorio);

        }


        return ssb;//responsorio;
    }


    public Patristica getPatristica() {
        return patristica;
    }

    public void setPatristica(Patristica patristica) {
        this.patristica = patristica;
    }

    public Biblica getBiblica() {
        return biblica;
    }

    public void setBiblica(Biblica biblica) {
        this.biblica = biblica;
    }

    public TeDeum getTeDeum() {
        return teDeum;
    }

    public void setTeDeum(TeDeum teDeum) {
        this.teDeum = teDeum;
    }
}

