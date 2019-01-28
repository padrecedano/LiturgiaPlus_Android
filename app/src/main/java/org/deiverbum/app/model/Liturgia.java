package org.deiverbum.app.model;

import java.util.List;

public class Liturgia {
    public List<HomiliaCompleta> homiliaCompleta;
    private Misa misa;

    public List<HomiliaCompleta> getHomiliaCompleta() {
        return homiliaCompleta;
    }

    public void setHomiliaCompleta(List<HomiliaCompleta> homiliaCompleta) {
        this.homiliaCompleta = homiliaCompleta;
    }

    public Misa getMisa() {
        return misa;
    }

    public void setMisa(Misa misa) {
        this.misa = misa;
    }
}

