package org.deiverbum.app.model;

import android.text.Spanned;

import org.deiverbum.app.utils.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class Invitatorio {
    public String antifona;
    public String texto;
    private int id;

    public Invitatorio() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAntifona() {
        return antifona;
    }

    public void setAntifona(String antifona) {
        this.antifona = antifona;
    }

    public String getTexto() {
        String t = "";
        try {

            String filePath = String.format("res/raw/invitatorio_%d.txt", 1);
            InputStream in = this.getClass().getClassLoader().getResourceAsStream(filePath);
            byte[] b = new byte[in.available()];
            in.read(b);
            t = new String(b);
        } catch (IOException e) {
            e.printStackTrace();
            t = e.getMessage();
        }


        return t;
    }

    public Spanned getTextoSpan() {
        try {
            HashMap<String, String> mMap = new HashMap();
            mMap.put("0", "res/raw/invitatorio_1.txt");
            mMap.put("1", "res/raw/invitatorio_1.txt");
            mMap.put("2", "res/raw/invitatorio_2.txt");
            mMap.put("3", "res/raw/invitatorio_3.txt");
            mMap.put("4", "res/raw/invitatorio_4.txt");

            String mId = String.valueOf(id);
            String filePath = mMap.get(mId);

            InputStream in = this.getClass().getClassLoader().getResourceAsStream(filePath);
            if (in != null) {
                byte[] b = new byte[in.available()];
                in.read(b);
                texto = new String(b);
            } else {
                texto = "No se encontró el texto del invitatorio";
            }
        } catch (IOException e) {
            e.printStackTrace();
            texto = e.getMessage();
        }

        return Utils.fromHtml(texto);
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Spanned getAntifonaForRead() {
        return Utils.fromHtml("<p>" + antifona + ".</p>");
    }
}
