package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import org.deiverbum.app.utils.Utils;

public class Oficio {
    public Invitatorio invitatorio;
    public Himno himno;
    public Salmodia salmodia;
    public String oracion;

    public String responsorio;
    public OficioLecturas oficioLecturas;

    public Invitatorio getInvitatorio() {
        return invitatorio;
    }

    public void setInvitatorio(Invitatorio invitatorio) {
        this.invitatorio = invitatorio;
    }

    public SpannableStringBuilder getResponsorio() {
        String[] textParts = responsorio.split("\\|");
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        if (textParts.length == 2) {
            ssb.append("V. ");
            ssb.append(textParts[0]);
            ssb.append(Utils.LS);
            ssb.append("R. ");
            ssb.append(textParts[1]);
        } else {
            ssb.append(responsorio);

        }


        return ssb;//responsorio;
    }

    public void setResponsorio(String responsorio) {
        this.responsorio = responsorio;
    }

    public Himno getHimno() {
        return himno;
    }

    public void setHimno(Himno himno) {
        this.himno = himno;
    }

    public Salmodia getSalmodia() {
        return salmodia;
    }

    public void setSalmodia(Salmodia salmodia) {
        this.salmodia = salmodia;
    }

    public OficioLecturas getOficioLecturas() {
        return oficioLecturas;
    }

    public void setOficioLecturas(OficioLecturas oficioLecturas) {
        this.oficioLecturas = oficioLecturas;
    }

    public Spanned getOracion() {
        return Utils.fromHtml(oracion);
    }

    public void setOracion(String oracion) {
        this.oracion = oracion;
    }

}