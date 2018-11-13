package org.deiverbum.app.model;

public class Santo {
    public String nombre;
    public String vida;
    private String martirologio;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getVida() {
        //SpannableStringBuilder ssb=new SpannableStringBuilder("");
        //return ((this.vida.equals("")) ? "" : Utils.toSmallSize(vida);
        return vida;//.equals("") ? "member" : "guest";
        //return (vida.equals("")) ?  new SpannableStringBuilder("") : Utils.toSmallSize(vida);


    }

    public void setVida(String vida) {
        this.vida = vida;
    }

    public String getMartirologio() {

        return martirologio;//.equals("") ? "member" : "guest";


    }
}

