package org.deiverbum.app.model;

public class BibliaLibros {
    private int id;
    private String name;
    private String description;

    public BibliaLibros() {
    }

    public BibliaLibros(int id, String name, String description) {
        this.name = name;
        this.id = id;
        this.description = description;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}