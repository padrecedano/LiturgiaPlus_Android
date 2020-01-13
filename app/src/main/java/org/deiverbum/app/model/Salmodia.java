package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import org.deiverbum.app.utils.Utils;

import java.util.List;

public class Salmodia {
    private int tipo;
    private List<SalmoCompleto> salmoCompleto;

    public Salmodia() {
    }

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
        for (SalmoCompleto s : salmoCompleto) {
            String ant = "<p>" + s.getAntifona() + ".</p>";
            ssb.append(ant);
            ssb.append(Utils.LS2);
            ssb.append(s.getSalmo());
            ssb.append(Utils.LS2);
            if (s.getSalmo().endsWith("∸")) {
            } else {
                ssb.append(Utils.getFinSalmo());
            }
            ssb.append(ant);
            //ssb.append(SEPARADOR);
        }
        return ssb;
    }

    public SpannableStringBuilder getSalmoCompletoForRead() {
        return getSalmoCompletoForRead(-1);
    }

    public SpannableStringBuilder getSalmoCompleto() {
        return getSalmoCompleto(-1);
    }

    public void setSalmoCompleto(List<SalmoCompleto> salmoCompleto) {
        this.salmoCompleto = salmoCompleto;
    }

    public SpannableStringBuilder getSalmoCompleto(int hourIndex) {
        //String tipo = this.tipo;
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        String salmo = "";
        String preAntifona = "Ant. ";
        String antUnica = "";

        if (tipo == 1) {
            sb.append(Utils.toRed(preAntifona));
            antUnica = salmoCompleto.get(hourIndex).getAntifona();
            sb.append(antUnica);
        }
        if (tipo == 2) {
            sb.append(Utils.toRed(preAntifona));
            antUnica = salmoCompleto.get(0).getAntifona();
            sb.append(antUnica);
        }
        for (SalmoCompleto s : salmoCompleto) {
            SpannableStringBuilder tema = new SpannableStringBuilder("");
            SpannableStringBuilder parte = new SpannableStringBuilder("");
            SpannableStringBuilder intro = new SpannableStringBuilder("");
            SpannableStringBuilder ref = new SpannableStringBuilder("");
            String preRef = String.valueOf(s.getRef());

            if (tipo == 0) {
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
            if (preRef != null && !preRef.isEmpty()) {
                ref.append(Utils.LS);
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

            if (s.getSalmo().endsWith("∸")) {
                sb.append(Utils.LS);
                sb.append(Utils.getNoGloria());
            } else {
                sb.append(Utils.LS2);
                sb.append(Utils.getFinSalmo());
            }

            if (tipo == 0) {
                sb.append(Utils.LS2);
                sb.append(Utils.toRed(preAntifona));
                sb.append(Utils.getAntifonaLimpia(s.getAntifona()));
                sb.append(Utils.LS2);
            }
        }
        if (tipo == 1) {
            sb.append(Utils.LS2);

            sb.append(Utils.toRed(preAntifona));
            antUnica = Utils.getAntifonaLimpia(salmoCompleto.get(hourIndex).getAntifona());
            sb.append(antUnica);
            sb.append(Utils.LS2);
        }
        if (tipo == 2) {
            sb.append(Utils.LS2);
            sb.append(Utils.toRed(preAntifona));
            antUnica = Utils.getAntifonaLimpia(salmoCompleto.get(0).getAntifona());
            sb.append(antUnica);
            sb.append(Utils.LS2);
        }
        return sb;
    }

    public SpannableStringBuilder getSalmoCompletoForRead(int hourIndex) {
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        String salmo = "";
        String antUnica = "";

        if (tipo == 1) {
            antUnica = Utils.getAntifonaLimpia(salmoCompleto.get(hourIndex).getAntifona());
            sb.append(antUnica);
        }
        if (tipo == 2) {
            antUnica = Utils.getAntifonaLimpia(salmoCompleto.get(0).getAntifona());
            //antUnica = salmoCompleto.get(0).getAntifona();
            sb.append(antUnica);
        }
        for (SalmoCompleto s : salmoCompleto) {
            if (tipo == 0) {
                sb.append(Utils.fromHtml(s.getAntifona()));
            }

            sb.append(Utils.LS2);
            salmo = Utils.getFormato(s.getSalmo());
            sb.append(Utils.fromHtml(salmo));
            //sb.append(SEPARADOR);

            if (!(s.getSalmo().endsWith("∸"))) {
                sb.append(Utils.getFinSalmo());
            }
            sb.append(Utils.LS2);

            if (tipo == 0) {
                sb.append(Utils.getAntifonaLimpia(s.getAntifona()));
                sb.append(Utils.LS2);
            }
        }
        if (tipo == 1) {
            sb.append(Utils.getAntifonaLimpia(antUnica));
            sb.append(Utils.LS2);
        }
        if (tipo == 2) {
            sb.append(Utils.getAntifonaLimpia(antUnica));
            sb.append(Utils.LS2);
        }

        return sb;
    }

    public SpannableStringBuilder getHeader() {

        return Utils.formatTitle("SALMODIA");
    }

    public Spanned getHeaderForRead() {
        return Utils.fromHtml("<p>SALMODIA.</p>");
    }
}