package model;

/**
 * Created by Oleksandr on 28.11.2015.
 */
public class AreaType {
    private String name;
    private float area;

    public AreaType(String name, float area) {
        this.name = name;
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }
}
