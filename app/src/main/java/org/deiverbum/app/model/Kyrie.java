package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.deiverbum.app.utils.Utils;

public class Kyrie {

    @SerializedName("introduccion")
    @Expose
    private String introduccion;
    @SerializedName("texto")
    @Expose
    private String texto;
    @SerializedName("conclusion")
    @Expose
    private String conclusion;
    private int tipo;

    public SpannableStringBuilder getIntroduccion() {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        String[] introArray = introduccion.split("\\|");
        if (introArray.length == 3) {
            ssb.append(Utils.toSmallSizeRed(introArray[0]));
            ssb.append(Utils.LS2);
            ssb.append(introArray[1]);
            ssb.append(Utils.LS2);
            ssb.append(Utils.toSmallSizeRed(introArray[2]));

        } else {
            //ssb.append(String.valueOf(introArray.length));
            ssb.append(introduccion);

        }
        return ssb;
    }

    public void setIntroduccion(String introduccion) {
        this.introduccion = introduccion;
    }

    public SpannableStringBuilder getTexto() {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        this.texto = Utils.getFormato(texto);
        switch (this.tipo) {
            case 1:
                ssb.append(Utils.fromHtml(texto));
                break;
            default:
                ssb.append(texto);
                break;
        }
        return ssb;//texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public SpannableStringBuilder getConclusion() {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        //this.conclusion=Utils.getFormato(conclusion);

        String[] introArray = conclusion.split("\\|");

        if (introArray.length == 2) {
            ssb.append(Utils.toSmallSizeRed(introArray[0]));
            ssb.append(Utils.LS2);
            ssb.append(String.valueOf(introArray.length));
            ssb.append(Utils.LS2);

            String[] str = introArray[1].split("\\∬");

            if (str.length == 2) {
                ssb.append(Utils.toRed("V. "));
                ssb.append(str[0]);
                ssb.append(Utils.LS);
                ssb.append(Utils.toRed("R. "));
                ssb.append(str[1]);

            } else {
                ssb.append(introArray[1]);

            }
            ssb.append(Utils.LS2);
//            ssb.append(Utils.toSmallSizeRed(introArray[2]));

        } else {
            ssb.append(Utils.LS2 + "conc");
            ssb.append(String.valueOf(introArray.length));
            ssb.append(conclusion);

        }
        return ssb;
        //        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public int getTipo() {
        return tipo;
    }
}
