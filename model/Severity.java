package model;

/**
 * Created by Oleksandr on 20.12.2015.
 */
public class Severity {
    private int id;
    private String info;

    public Severity(int id, String info) {
        this.id = id;
        this.info = info;
    }

    public Severity() {
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
}
