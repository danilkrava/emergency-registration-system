package model;

import java.sql.Date;

/**
 * Created by Oleksandr on 28.11.2015.
 */
public class AppliedMeasure {
    private Date date;
    private double money;
    private int id;
    private Measure measure;
    private String info;

    public AppliedMeasure() {
    }

    public AppliedMeasure(Date date, double money, int id, Measure measure, String info) {
        this.date = date;
        this.money = money;
        this.id = id;
        this.measure = measure;
        this.info = info;
    }

    @Override
    public String toString() {
        return "AppliedMeasure{" +
                "date=" + date +
                ", money=" + money +
                ", id=" + id +
                ", measure=" + measure +
                ", info='" + info + '\'' +
                '}';
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Measure getMeasure() {
        return measure;
    }

    public void setMeasure(Measure measure) {
        this.measure = measure;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
