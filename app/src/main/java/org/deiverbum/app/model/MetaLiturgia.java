package org.deiverbum.app.model;

import org.deiverbum.app.utils.Utils;

import java.util.HashMap;

public class MetaLiturgia {
    private String fecha;
    private int tiempo;
    private String semana;
    private String mensaje;
    private String salterio;
    private int color;
    private String meta;
    private String titulo;
    private int weekDay;

    public MetaLiturgia() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFecha() {
        return Utils.getLongDate(fecha);//.getfecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public String getTiempoNombre() {
        if (tiempo < 15) {
            HashMap<Integer, String> mapTiempos = new HashMap<>();
            mapTiempos.put(0, "***");
            mapTiempos.put(1, "Tiempo de Adviento");
            mapTiempos.put(2, "Tiempo de Navidad");
            mapTiempos.put(3, "Tiempo de Cuaresma");
            mapTiempos.put(4, "Semana Santa");
            mapTiempos.put(5, "Santo Triduo Pascual");
            mapTiempos.put(6, "Tiempo de Pascua");
            mapTiempos.put(7, "Tiempo Ordinario");
            mapTiempos.put(8, "***");
            mapTiempos.put(9, "Propio de los Santos");
            mapTiempos.put(10, "Oficios Comunes");
            mapTiempos.put(11, "Misas Rituales");
            mapTiempos.put(12, "Diversas Necesidades");
            mapTiempos.put(13, "Misas Votivas");
            mapTiempos.put(14, "Oficio de Difuntos");
            mapTiempos.put(15, "***");
            mapTiempos.put(16, "***");
            mapTiempos.put(17, "***");
            mapTiempos.put(18, "***");

            return mapTiempos.get(tiempo);
        } else {
            return "***";
        }

    }

    public void setweekDaye(int weekDay) {
        this.weekDay = weekDay;
    }

    public int getweekDay() {
        return this.weekDay;
    }
    public String getSemana() {
        return semana;
    }

    public void setSemana(String semana) {
        this.semana = semana;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Deprecated
    public String getSalterio() {
        return salterio;
    }


    public void setSalterio(String salterio) {
        this.salterio = salterio;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }
}

