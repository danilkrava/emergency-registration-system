package model;

/**
 * Created by Oleksandr on 20.12.2015.
 */
public class SeverityType {
    private int id;
    private String name;

    public SeverityType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public SeverityType() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInfo() {
        return name;
    }

    public void setInfo(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Severity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
