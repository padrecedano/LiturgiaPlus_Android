package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import org.deiverbum.app.utils.Utils;

import static org.deiverbum.app.utils.Constants.SEPARADOR;

public class HomiliaCompleta {
    public String padre;
    public int id_homilia;
    public String texto;
    public String tema;
    public String obra;
    public String fecha;


    public String getPadre() {
        return padre;
    }
    public void setPadre(String padre) {
        this.padre = padre;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getObra() {
        return obra;
    }

    public void setObra(String obra) {
        this.obra = obra;
    }

    public Spanned getTemaSpan() {
        return Utils.toRed(tema);
    }

    public Spanned getObraSpan() {
        return Utils.toH4Red(obra);
    }

    public Spanned getFechaSpan() {
        if (fecha.equals("0000-00-00") || fecha.equals("")) {
            return Utils.fromHtml("");
        }
        return Utils.toH4Red("\n" + fecha + "\n");
    }



    public int getId_homilia() {
        return id_homilia;
    }

    public void setId_homilia(int id_homilia) {
        this.id_homilia = id_homilia;
    }

    public Spanned getTexto() {
        Spanned str = Utils.fromHtml(texto);
        return str;
    }

    public Spanned getTextoLimpio() {
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        Spanned str = Utils.fromHtml(texto.replaceAll(SEPARADOR, ""));
        return str;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
