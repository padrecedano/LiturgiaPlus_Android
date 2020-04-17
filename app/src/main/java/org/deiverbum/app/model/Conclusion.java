package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.deiverbum.app.utils.Utils;

import java.util.List;
import java.util.Random;

public class Conclusion {

    @SerializedName("bendicion")
    @Expose
    private String bendicion;

    List<String> antVirgen;
    private String antVirgenOld;

    public List<String> getAntVirgen() {
        return this.antVirgen;
    }

    public void setAntVirgen(List<String> antVirgen) {
        this.antVirgen = antVirgen;
    }

    public Conclusion() {
    }

    public String getBendicion() {
        return bendicion;
    }

    public SpannableStringBuilder getBendicionSpan() {
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

    public Spanned getAntVirgenSpan() {
        return Utils.fromHtml("");
    }

    public String getAntifonaVirgen(int timeID) {
        int mIndex = 4;
        if (timeID != 6) {
            int[] intArray = {0, 1, 2};
            mIndex = new Random().nextInt(intArray.length);
        }
        return antVirgen.get(mIndex);

    }

    public void setAntVirgenOld(String antVirgen) {
        this.antVirgenOld = "";
    }

}

