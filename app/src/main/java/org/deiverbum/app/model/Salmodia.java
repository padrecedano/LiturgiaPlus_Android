package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

import java.util.List;

import static org.deiverbum.app.utils.Constants.SEPARADOR;

public class Salmodia {
    private int tipo;
    private List<SalmoCompleto> salmoCompleto;

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    /**
     * @deprecated Usar getSalmoCompletoForRead
     * En el caso de la hora intermedia, pasarle los parámetros
     * 0 : Tercia
     * 1 : Sexta
     * 2 : Nona
     */
    @Deprecated
    public SpannableStringBuilder getSalmosForRead() {
        SpannableStringBuilder ssb = new SpannableStringBuilder("");

        String salmo = "";
        String preant = "";

        for (SalmoCompleto s : salmoCompleto) {
            String ant = "<p>" + s.getAntifona() + ".</p>";

            ssb.append(ant);
            //ssb.append(s.getIntro());
            ssb.append(Utils.LS2);
            ssb.append(s.getSalmo());
            ssb.append(Utils.LS2);

            if (s.getSalmo().endsWith("∸")) {
                //ssb.append(Utils.getNoGloria());
            } else {
                ssb.append(Utils.getFinSalmo());
            }
            ssb.append(ant);
            ssb.append(SEPARADOR);
        }


        return ssb;
    }

    public SpannableStringBuilder getSalmoCompletoForRead() {
        return getSalmoCompletoForRead(-1);
    }

    public SpannableStringBuilder getSalmoCompleto() {
        return getSalmoCompleto(-1);
    }

    public SpannableStringBuilder getSalmoCompleto(int hourIndex) {
        int tipo = this.tipo;
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        String salmo = "";
        String preAntifona = "Ant. ";
        String antUnica = "";

        if (tipo == 1) {
            sb.append(Utils.toRed(preAntifona));
            antUnica = salmoCompleto.get(hourIndex).getAntifona();
            sb.append(antUnica);
        }
        for (SalmoCompleto s : salmoCompleto) {
            SpannableStringBuilder tema = new SpannableStringBuilder("");
            SpannableStringBuilder parte = new SpannableStringBuilder("");
            SpannableStringBuilder intro = new SpannableStringBuilder("");
            SpannableStringBuilder ref = new SpannableStringBuilder("");

            if (tipo != 1) {

                sb.append(Utils.toRed(preAntifona + s.getOrden() + ". "));
                sb.append(Utils.fromHtml(s.getAntifona()));

            }
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

            if (!s.getRef().equals("")) {
                ref.append(s.getRef());
                ref.append(Utils.LS2);
            }

            sb.append(Utils.LS2);
            sb.append(ref);
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

            if (tipo != 1) {
                sb.append(Utils.toRed(preAntifona));
                sb.append(Utils.getAntifonaLimpia(s.getAntifona()));
                sb.append(Utils.LS2);

            }
        }
        if (tipo == 1) {
            sb.append(Utils.toRed(preAntifona));
            antUnica = Utils.getAntifonaLimpia(salmoCompleto.get(hourIndex).getAntifona());
            sb.append(antUnica);
            sb.append(Utils.LS2);

        }
        return sb;
    }

    public SpannableStringBuilder getSalmoCompletoForRead(int hourIndex) {
        int tipo = this.tipo;
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        String salmo = "";
        String preAntifona = "Ant. ";
        String antUnica = "";

        if (tipo == 1) {
            sb.append(Utils.toRed(preAntifona));
            antUnica = salmoCompleto.get(hourIndex).getAntifona();
            sb.append(antUnica);
        }
        for (SalmoCompleto s : salmoCompleto) {
            SpannableStringBuilder tema = new SpannableStringBuilder("");
            SpannableStringBuilder parte = new SpannableStringBuilder("");
            SpannableStringBuilder intro = new SpannableStringBuilder("");
            SpannableStringBuilder ref = new SpannableStringBuilder("");

            if (tipo != 1) {

                sb.append(Utils.toRed(preAntifona + s.getOrden() + ". "));
                sb.append(Utils.fromHtml(s.getAntifona()));

            }
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

            if (!s.getRef().equals("")) {
                ref.append(s.getRef());
                ref.append(Utils.LS2);
            }

            sb.append(Utils.LS2);
            sb.append(ref);
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

            if (tipo != 1) {
                sb.append(Utils.toRed(preAntifona));
                sb.append(Utils.getAntifonaLimpia(s.getAntifona()));
                sb.append(Utils.LS2);

            }
        }
        if (tipo == 1) {
            sb.append(Utils.toRed(preAntifona));
            antUnica = Utils.getAntifonaLimpia(salmoCompleto.get(hourIndex).getAntifona());
            sb.append(antUnica);
            sb.append(Utils.LS2);

        }
        return sb;
    }


    public void setSalmoCompleto(List<SalmoCompleto> salmoCompleto) {
        this.salmoCompleto = salmoCompleto;
    }

    public SpannableStringBuilder getHeader() {

        return Utils.formatTitle("SALMODIA");
    }
}