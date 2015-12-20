package model;

import java.sql.Date;

public class Report {
    private int id;
    private Emergency emergency;
    private Date date;
    private double radiation;
    private String info;

    public Report(int id, Emergency emergency, Date date, double radiation, String info) {
        this.id = id;
        this.emergency = emergency;
        this.date = date;
        this.radiation = radiation;
        this.info = info;
    }

    public Report() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Emergency getEmergency() {
        return emergency;
    }

    public void setEmergency(Emergency emergency) {
        this.emergency = emergency;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getRadiation() {
        return radiation;
    }

    public void setRadiation(double radiation) {
        this.radiation = radiation;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", emergency=" + emergency +
                ", date=" + date +
                ", radiation=" + radiation +
                ", info='" + info + '\'' +
                '}';
    }
}
