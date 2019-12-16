package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

import java.util.HashMap;
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
            sb.append(Utils.LS2);
            sb.append(Utils.toH3Red(findOrden(l.getOrden())));
            sb.append(Utils.LS2);

            sb.append(Utils.toH4Red(l.getLibro() + "       " + l.getRef()));
            sb.append(Utils.LS2);

            //sb.append(Utils.toRed(l.getRef()));
            //sb.append(Utils.LS2);
            sb.append(Utils.toRed(l.getTema()));
            sb.append(Utils.LS2);
            txtLectura = Utils.getFormato(l.getTexto());
            sb.append(Utils.fromHtml(txtLectura));
            sb.append(Utils.LS2);
        }

        return sb;
    }

    public SpannableStringBuilder getLiturgiaPalabraforRead() {
        int tipo = this.tipo;
        String txtLectura = "";
        //SpannableStringBuilder entireRef="";
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        for (Lectura l : lecturas) {
            //entireRef=Utils.toH4Red(l.getLibro()+NBSP_4+l.getRef());
            sb.append(findOrden(l.getOrden()));
            sb.append(Utils.LS2);
            sb.append(SEPARADOR);
            sb.append(l.getLibro());
            sb.append(Utils.LS2);
            sb.append(SEPARADOR);

            sb.append(Utils.LS2);
            sb.append(l.getTema());
            sb.append(Utils.LS2);
            sb.append(SEPARADOR);
            txtLectura = l.getTexto();
            sb.append(Utils.fromHtml(txtLectura));
            sb.append(Utils.LS2);
        }

        return sb;
    }

    public String findOrden(int orden) {

        HashMap<Integer, String> orderMap = new HashMap<Integer, String>();
        orderMap.put(1, "Primera Lectura");
        orderMap.put(10, "Primera Lectura");
        orderMap.put(2, "Salmo Responsorial");
        orderMap.put(20, "Salmo Responsorial");
        orderMap.put(3, "Segunda Lectura");
        orderMap.put(30, "Segunda Lectura");
        orderMap.put(4, "Evangelio");
        orderMap.put(40, "Evangelio");

        String orderText = orderMap.get(orden);

        return orderText;


    }

}
