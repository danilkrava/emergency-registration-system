package model;

/**
 * Created by Oleksandr on 28.11.2015.
 */
public class AreaType {
    private int id;
    private String name;
    private double area;

    public AreaType(String name, double area) {
        this.name = name;
        this.area = area;
    }

    public AreaType() {
    }

    @Override
    public String toString() {
        return name;
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
