package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

import java.util.List;

import static org.deiverbum.app.utils.Constants.SEPARADOR;

public class Salmodia {
    public int tipo;
    public List<SalmoCompleto> salmoCompleto;

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public SpannableStringBuilder getSalmosForRead() {
        SpannableStringBuilder ssb = new SpannableStringBuilder("");

        String salmo = "";
        String preant = "";
        String ant = "";

        for (SalmoCompleto s : salmoCompleto) {
            preant = "Ant. " + s.getOrden() + ". ";
            ant = " " + s.getAntifona();
            //ssb.append(Utils.toRed(preant));
            ssb.append("<p>" + s.getAntifona() + ".</p>");
            //ssb.append(Utils.LS2);
            //ssb.append(s.getRef());
            //ssb.append(Utils.LS2);
            //ssb.append(Utils.toRed(s.getTema()));
            //ssb.append(Utils.LS2);
            ssb.append(s.getIntro());
            ssb.append(Utils.LS2);
            //ssb.append(s.getParte());
            //salmo=Utils.getFormato(s.getSalmo());
            ssb.append(s.getSalmo());
            ssb.append(Utils.LS2);

            if (s.getSalmo().endsWith("∸")) {
                //ssb.append(Utils.getNoGloria());
            } else {
                ssb.append(Utils.getFinSalmo());
            }
            ssb.append(Utils.LS2);
            //ssb.append(Utils.toRed("Ant. "));
            ssb.append(ant);
            ssb.append(SEPARADOR);
        }


        return ssb;
    }

    public SpannableStringBuilder getSalmoCompleto() {
        SpannableStringBuilder sb = new SpannableStringBuilder("");

        String salmo = "";
        String preant = "";
        String ant = "";

        for (SalmoCompleto s : salmoCompleto) {
            SpannableStringBuilder tema = new SpannableStringBuilder("");
            SpannableStringBuilder parte = new SpannableStringBuilder("");
            SpannableStringBuilder intro = new SpannableStringBuilder("");

            preant = "Ant. " + s.getOrden() + ". ";
            ant = " " + Utils.getAntifonaLimpia(s.getAntifona());
            if (!s.getTema().equals("")) {
                tema.append(Utils.toRed(s.getTema()));
                tema.append(Utils.LS2);
            }

            if (!s.getIntro().equals("")) {
                intro.append(Utils.fromHtmlSmall(s.getIntro()));
                intro.append(Utils.LS2);
            }
            if (!s.getParte().equals("")) {
                parte.append(Utils.toRed(s.getParte()));
                parte.append(Utils.LS2);
            }

            sb.append(Utils.toRed(preant));
            sb.append(ant);
            sb.append(Utils.LS2);
            sb.append(s.getRef());
            sb.append(Utils.LS2);
            sb.append(tema);
            sb.append(intro);
            sb.append(parte);
            salmo = Utils.getFormato(s.getSalmo());
            sb.append(Utils.fromHtml(salmo));
            sb.append(Utils.LS2);

            if (s.getSalmo().endsWith("∸")) {
                sb.append(Utils.getNoGloria());
            } else {
                sb.append(Utils.getFinSalmo());
            }
            sb.append(Utils.LS2);
            sb.append(Utils.toRed("Ant. "));
            sb.append(ant);
            sb.append(Utils.LS2);
        }

        return sb;


        //return "salmoCompleto";


    }

    public void setSalmoCompleto(List<SalmoCompleto> salmoCompleto) {
        this.salmoCompleto = salmoCompleto;
    }

    public SpannableStringBuilder getHeader() {

        return Utils.formatTitle("SALMODIA");
    }
}