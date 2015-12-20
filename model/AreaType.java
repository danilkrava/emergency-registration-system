package model;

/**
 * Created by Oleksandr on 28.11.2015.
 */
public class AreaType {
    private int id;
    private String name;
    private double area;

    public AreaType(int id, String name, double area) {
        this.id = id;
        this.name = name;
        this.area = area;
    }

    public AreaType() {
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

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }
}
