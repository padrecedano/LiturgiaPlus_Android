package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import org.deiverbum.app.utils.Utils;

import static org.deiverbum.app.utils.Constants.SEPARADOR;

public class Rosario {
    public String saludo;
    public String padrenuestro;
    public String avemaria;
    public String gloria;
    public Misterios misterios;
    public String letanias;
    public String oracion;
    public String salve;

    public SpannableStringBuilder getMisterios(int dayCode) {
        //SpannableStringBuilder ssb = new SpannableStringBuilder();
        return misterios.getContenido(this, dayCode);
        /*
        switch (dayCode) {
            case 1:
                ssb = misterios.getGloriosos().getContenido();
                break;
            case 2:
                ssb = misterios.getContenido(dayCode);
                break;
            default:

        }
        return ssb;//return misterios;
        */
    }

    public void setMisterios(Misterios misterios) {
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
            ssb.append(SEPARADOR);

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