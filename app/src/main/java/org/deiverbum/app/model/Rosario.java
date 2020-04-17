package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import org.deiverbum.app.utils.Utils;

import java.util.List;

public class Rosario {
    public String saludo;
    public String padrenuestro;
    public String avemaria;
    public String gloria;
    public Misterios misterioss;
    public String letanias;
    public String oracion;
    public String salve;
    private List<Misterio> misterios;

    public SpannableStringBuilder getMisterios(int dayCode) {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        Misterio m = misterios.get(dayCode - 1);
        for (String s : m.getContenido()) {
            ssb.append(Utils.fromHtml(getPadrenuestro()));
            ssb.append(Utils.LS2);
            ssb.append(Utils.toH2Red(s));
            ssb.append(Utils.LS2);
            for (int i = 0; i < 10; i++) {
                ssb.append(Utils.fromHtml(getAvemaria()));
                ssb.append(Utils.LS2);
            }
            ssb.append(Utils.fromHtml(gloria));
            ssb.append(Utils.LS2);
        }
        return ssb;
    }

    /*
      @TODO
      - Arreglar esto de otro modo
     */
    public String getByDay(int dayCode) {
        switch (dayCode) {
            case 1:
                return "Gloriosos";
            case 2:
                return "Gosozos";
            case 3:
                return "Dolorosos";
            case 4:
                return "Luminosos";
            default:
                return "*";
        }
    }


    public void setMisterios(List<Misterio> misterios) {
        this.misterios = misterios;
    }

    public Spanned getSaludo() {
        return Utils.fromHtml(saludo);
    }

    public void setSaludo(String saludo) {
        this.saludo = saludo;
    }

    public String getPadrenuestro() {
        return padrenuestro;
    }

    public void setPadrenuestro(String padrenuestro) {
        this.padrenuestro = padrenuestro;
    }

    public SpannableStringBuilder misterioCompleto() {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        ssb.append(Utils.fromHtml(getPadrenuestro()));
        ssb.append(Utils.LS2);
        for (int i = 0; i < 10; i++) {
            ssb.append(Utils.fromHtml(getAvemaria()));
            ssb.append(Utils.LS2);

        }
        ssb.append(Utils.fromHtml(gloria));
        ssb.append(Utils.LS2);
        return ssb;
    }

    public String getAvemaria() {
        return avemaria;
    }

    public void setAvemaria(String avemaria) {
        this.avemaria = avemaria;
    }

    public String getGloria() {
        return gloria;
    }

    public void setGloria(String gloria) {
        this.gloria = gloria;
    }

    public Spanned getLetanias() {
        return Utils.fromHtml(letanias);
    }

    public void setLetanias(String letanias) {
        this.letanias = letanias;
    }

    public Spanned getOracion() {
        return Utils.fromHtml(oracion);
    }

    public void setOracion(String oracion) {
        this.oracion = oracion;
    }

    public Spanned getSalve() {
        return Utils.fromHtml(salve);
    }

    public void setSalve(String salve) {
        this.salve = salve;
    }

}