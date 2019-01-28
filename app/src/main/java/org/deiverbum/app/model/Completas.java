package org.deiverbum.app.model;

public class Completas {

    private RitosIniciales ritosIniciales;
    private Himno himno;
    private Salmodia salmodia;
    private LecturaBreve lecturaBreve;
    private NuncDimitis nuncDimitis;
    private String oracion;
    private Conclusion conclusion;

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

    public String getOracion() {
        return oracion;
    }

    public void setOracion(String oracion) {
        this.oracion = oracion;
    }

    public Conclusion getConclusion() {
        return conclusion;
    }

    public void setConclusion(Conclusion conclusion) {
        this.conclusion = conclusion;
    }

}
