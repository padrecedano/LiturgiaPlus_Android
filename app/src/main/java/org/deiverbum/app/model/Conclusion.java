package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.deiverbum.app.utils.Utils;

public class Conclusion {

    @SerializedName("bendicion")
    @Expose
    private String bendicion;
    @SerializedName("antVirgen")
    @Expose
    private String antVirgen;

    public SpannableStringBuilder getBendicion() {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        ssb.append(Utils.toRed("V. "));
        ssb.append("El Señor todopoderoso nos conceda una noche tranquila y una santa muerte.");
        ssb.append(Utils.LS2);
        ssb.append(Utils.toRed("R. "));
        ssb.append("Amén.");
        return ssb;
    }

    public String getBendicionForRead() {
        return "El Señor todopoderoso nos conceda una noche tranquila y una santa muerte. Amén.";
    }

    public void setBendicion(String bendicion) {
        this.bendicion = bendicion;
    }

    public Spanned getAntVirgen() {
        return Utils.fromHtml(antVirgen);
    }

    public void setAntVirgen(String antVirgen) {
        this.antVirgen = antVirgen;
    }

}

