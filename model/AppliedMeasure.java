package model;

/**
 * Created by Oleksandr on 28.11.2015.
 */
public class AppliedMeasure {
    private Date date;
    private double money;
    private int id, measureId;
    private String info;

    public AppliedMeasure() {
    }

    public AppliedMeasure(int id, Date date, double money, int measureId, String info) {
        this.id = id;
        this.date = date;
        this.money = money;
        this.measureId = measureId;
        this.info = info;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getMoney() { return money; }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getMeasureId() {
        return measureId;
    }

    public void setMeasureId(int measureId) {
        this.measureId = measureId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
