package model;

/**
 * Created by Oleksandr on 28.11.2015.
 */
public class AppliedMeasure {
    private Date date;
    private float money;
    private int measureId;
    private String info;

    public AppliedMeasure(Date date, float money, int measureId, String info) {
        this.date = date;
        this.money = money;
        this.measureId = measureId;
        this.info = info;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
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
