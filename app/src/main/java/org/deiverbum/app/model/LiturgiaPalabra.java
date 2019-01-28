package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

import java.util.List;

import static org.deiverbum.app.utils.Constants.SEPARADOR;

public class LiturgiaPalabra {

    private int tipo;
    private List<Lectura> lecturas;

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getTipos() {
        return "tipo";
    }

    public List<Lectura> getLecturas() {
        return lecturas;
    }

    public void setLecturas(List<Lectura> lecturas) {
        this.lecturas = lecturas;
    }

    public SpannableStringBuilder getEvangelio() {
        int tipo = this.tipo;
        String txtLectura = "";
        //SpannableStringBuilder entireRef="";
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        for (Lectura l : lecturas) {
            if (l.getOrden() == 40) {
                //entireRef=Utils.toH4Red(l.getLibro()+NBSP_4+l.getRef());
                sb.append(Utils.toH4Red(l.getLibro() + "       " + l.getRef()));
                sb.append(Utils.LS2);
                sb.append(SEPARADOR);

                //sb.append(Utils.toRed(l.getRef()));
                //sb.append(Utils.LS2);
                sb.append(Utils.toRed(l.getTema()));
                sb.append(Utils.LS2);
                sb.append(SEPARADOR);
                txtLectura = Utils.getFormato(l.getTexto());
                sb.append(Utils.fromHtml(txtLectura));
                sb.append(Utils.LS2);
                sb.append(SEPARADOR);
            }
        }

        return sb;
    }


    public SpannableStringBuilder getLiturgiaPalabra() {
        int tipo = this.tipo;
        String txtLectura = "";
        //SpannableStringBuilder entireRef="";
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        for (Lectura l : lecturas) {
            //entireRef=Utils.toH4Red(l.getLibro()+NBSP_4+l.getRef());
            sb.append(Utils.toH4Red(l.getLibro() + "       " + l.getRef()));
            sb.append(Utils.LS2);
            sb.append(SEPARADOR);

            //sb.append(Utils.toRed(l.getRef()));
            //sb.append(Utils.LS2);
            sb.append(Utils.toRed(l.getTema()));
            sb.append(Utils.LS2);
            sb.append(SEPARADOR);
            txtLectura = Utils.getFormato(l.getTexto());
            sb.append(Utils.fromHtml(txtLectura));
            sb.append(Utils.LS2);
            sb.append(SEPARADOR);
        }

        return sb;
    }


}
