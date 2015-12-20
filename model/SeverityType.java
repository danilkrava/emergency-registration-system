package model;

/**
 * Created by Oleksandr on 20.12.2015.
 */
public class SeverityType {
    private int id;
    private String info;

    public SeverityType(int id, String info) {
        this.id = id;
        this.info = info;
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
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "Severity{" +
                "id=" + id +
                ", info='" + info + '\'' +
                '}';
    }
}
