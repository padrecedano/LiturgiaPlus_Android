package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import org.deiverbum.app.utils.Utils;

public class Oficio {
    private Invitatorio invitatorio;
    private Himno himno;
    private Salmodia salmodia;
    private String oracion;
    private String responsorio;
    private OficioLecturas oficioLecturas;
    private TeDeum teDeum;

    public Oficio() {
    }
    //public Oficio (Himno himno) {}

    public Himno getHimno() {
        return himno;
    }

    public void setHimno(Himno himno) {
        this.himno = himno;
    }

    public String getResponsorio() {
        return responsorio;
    }

    public Invitatorio getInvitatorio() {
        return invitatorio;
    }

    public void setInvitatorio(Invitatorio invitatorio) {
        this.invitatorio = invitatorio;
    }

    public SpannableStringBuilder getResponsorioSpan() {
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

    public Spanned getOracionSpan() {
        return Utils.fromHtml(oracion);
    }

    public void setResponsorio(String responsorio) {
        this.responsorio = responsorio;
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

    public String getOracion() {
        return oracion;
    }

    public TeDeum getTeDeum() {
        return teDeum;
    }

    public void setOracion(String oracion) {
        this.oracion = oracion;
    }

    public void setTeDeum(TeDeum teDeum) {
        this.teDeum = teDeum;
    }
}