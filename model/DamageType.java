package model;

/**
 * Created by Oleksandr on 21.12.2015.
 */
public class DamageType {
    private int id;
    private String name;

    public DamageType() {

    }

    public DamageType(String name) {
        this.name = name;
    }

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

    @Override
    public String toString() {
        return name;
    }
}
