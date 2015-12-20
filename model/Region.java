package model;

/**
 * Created by Oleksandr on 20.12.2015.
 */
public class Region {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Region() {

    }

    public Region(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
