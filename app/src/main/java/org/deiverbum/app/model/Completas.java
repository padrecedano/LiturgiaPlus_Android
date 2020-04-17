package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;
import android.util.SparseIntArray;

import org.deiverbum.app.utils.Utils;

import java.util.List;
import java.util.Random;

import static org.deiverbum.app.utils.Utils.LS2;

public class Completas {

    private RitosIniciales ritosIniciales;
    private Himno himno;
    private Salmodia salmodia;
    private LecturaBreve lecturaBreve;
    private NuncDimitis nuncDimitis;
    private Conclusion conclusion;
    List<CompletasHimno> completasHimno;
    private List<CompletasDia> completasDias;
    private List<Responsorio> responsorio;

    public List<Responsorio> getResponsorio() {
        return responsorio;
    }

    public Completas() {
    }

    public RitosIniciales getRitosIniciales() {
        return ritosIniciales;
    }

    public void setRitosIniciales(RitosIniciales ritosIniciales) {
        this.ritosIniciales = ritosIniciales;
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

    public LecturaBreve getLecturaBreve() {
        return lecturaBreve;
    }

    public void setLecturaBreve(LecturaBreve lecturaBreve) {
        this.lecturaBreve = lecturaBreve;
    }

    public NuncDimitis getNuncDimitis() {
        return nuncDimitis;
    }

    public void setNuncDimitis(NuncDimitis nuncDimitis) {
        this.nuncDimitis = nuncDimitis;
    }

    public void setResponsorio(List<Responsorio> responsorio) {
        this.responsorio = responsorio;
    }


    public Conclusion getConclusion() {
        return conclusion;
    }

    public void setConclusion(Conclusion conclusion) {
        this.conclusion = conclusion;
    }

    public String getOracion(int weekDay) {
        return completasDias.get(weekDay).getOracion();
    }

    public List<CompletasHimno> getCompletasHimno() {
        return this.completasHimno;
    }

    public void setCompletasHimno(List<CompletasHimno> completasHimno) {
        this.completasHimno = completasHimno;
    }

    public Himno getCompletasHimno(int timeID, int weekDay) {
        SparseIntArray mMap = new SparseIntArray();

        switch (timeID) {
            case 1:
            case 2:
                mMap.put(0, 0);
                mMap.put(1, 1);
                mMap.put(2, 0);
                mMap.put(3, 1);
                mMap.put(4, 0);
                mMap.put(5, 1);
                mMap.put(6, 0);
                mMap.put(7, 0);
                break;

            case 3:
            case 4:
            case 5:
                mMap.put(0, 2);
                mMap.put(1, 3);
                mMap.put(2, 2);
                mMap.put(3, 3);
                mMap.put(4, 2);
                mMap.put(5, 3);
                mMap.put(6, 2);
                mMap.put(7, 2);
                break;

            case 6:
                mMap.put(0, 4);
                mMap.put(1, 5);
                mMap.put(2, 4);
                mMap.put(3, 5);
                mMap.put(4, 4);
                mMap.put(5, 5);
                mMap.put(6, 4);
                mMap.put(7, 4);
                break;

            default:
                mMap.put(0, 0);
                mMap.put(1, 6);
                mMap.put(2, 0);
                mMap.put(3, 6);
                mMap.put(4, 0);
                mMap.put(5, 6);
                mMap.put(6, 0);
                mMap.put(7, 0);
                break;
        }
        int i = mMap.get(weekDay);
        return completasHimno.get(i).getHimno();
    }

    public void setCompletasHimnosss(List<CompletasHimno> completasHimno) {
        this.completasHimno = completasHimno;
    }


    public List<CompletasDia> getCompletasDias() {
        return this.completasDias;
    }

    public void setCompletasDias(List<CompletasDia> completasDias) {
        this.completasDias = completasDias;
    }


    public SpannableStringBuilder getHimnoSpan(int idTiempo, int dayWeek) {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        SparseIntArray mMap = new SparseIntArray();

        switch (idTiempo) {
            case 1:
            case 2:
                mMap.put(0, 0);
                mMap.put(1, 1);
                mMap.put(2, 0);
                mMap.put(3, 1);
                mMap.put(4, 0);
                mMap.put(5, 1);
                mMap.put(6, 0);
                mMap.put(7, 0);
                break;

            case 3:
            case 4:
            case 5:
                mMap.put(0, 2);
                mMap.put(1, 3);
                mMap.put(2, 2);
                mMap.put(3, 3);
                mMap.put(4, 2);
                mMap.put(5, 3);
                mMap.put(6, 2);
                mMap.put(7, 2);
                break;

            case 6:
                mMap.put(0, 4);
                mMap.put(1, 5);
                mMap.put(2, 4);
                mMap.put(3, 5);
                mMap.put(4, 4);
                mMap.put(5, 5);
                mMap.put(6, 4);
                mMap.put(7, 4);
                break;

            default:
                mMap.put(0, 0);
                mMap.put(1, 6);
                mMap.put(2, 0);
                mMap.put(3, 6);
                mMap.put(4, 0);
                mMap.put(5, 6);
                mMap.put(6, 0);
                mMap.put(7, 0);
                break;
        }
        int i = mMap.get(dayWeek);
        Himno mHimno = completasHimno.get(i).getHimno();
        ssb.append(mHimno.getHeader());
        ssb.append(LS2);
        ssb.append(mHimno.getTextoSpan());
        return ssb;
    }

    public SpannableStringBuilder getSalmodiaSpan(int timeID, int weekDay) {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        ssb.append(LS2);
        Salmodia mSalmodia = completasDias.get(weekDay).getSalmodia();
        ssb.append(mSalmodia.getHeader());
        ssb.append(LS2);
        ssb.append(mSalmodia.getSalmoCompleto());
        return ssb;
    }

    /**
     * Devuelve Lectura Breve y Responsorio formateados para vista
     * Para el responsorio, determina el que corresponda, según sea o no
     * tiempo de Pascua (timeID=6)
     *
     * @param timeID  ID del tiempo litúrgico
     * @param weekDay número de día de la semana
     * @return una cadena formateada con Lectura y Responsorio
     */
    public SpannableStringBuilder getLecturaSpan(int timeID, int weekDay) {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        int mIndex = (timeID == 6) ? 1 : 0;
        Responsorio mResponsorio = responsorio.get(mIndex);
        CompletasLectura mLectura = completasDias.get(weekDay).getCompletasLectura();
        ssb.append(mLectura.getHeader());
        ssb.append(LS2);
        ssb.append(mLectura.getTexto());
        ssb.append(LS2);
        ssb.append(Utils.formatTitle("RESPONSORIO BREVE"));
        ssb.append(LS2);
        ssb.append(mResponsorio.getResponsorioSpan());
        return ssb;
    }

    /**
     * Devuelve la Lectura Breve y el Responsorio para el módulo de voz
     * Para el responsorio, determina el que corresponda, según sea o no
     * tiempo de Pascua (timeID=6)
     *
     * @param timeID  ID del tiempo litúrgico
     * @param weekDay número de la semana
     * @return una cadena completa y formateada con la lectura y el responsorio
     */
    public SpannableStringBuilder getLecturaForRead(int timeID, int weekDay) {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        int mIndex = (timeID == 6) ? 1 : 0;
        Responsorio mResponsorio = responsorio.get(mIndex);
        CompletasLectura mLectura = completasDias.get(weekDay).getCompletasLectura();
        ssb.append("Lectura breve.");
        ssb.append(mLectura.getTexto());
        ssb.append("Responsorio breve.");
        ssb.append(mResponsorio.getResponsorioForRead());
        return ssb;
    }

    public String getAntifonaVirgen(int timeID) {
        int mIndex = 4;
        if (timeID != 6) {
            int[] intArray = {0, 1, 2};
            mIndex = new Random().nextInt(intArray.length);
        }
        return conclusion.getAntVirgen().get(mIndex);

    }


}
