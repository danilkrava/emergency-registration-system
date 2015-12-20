package model;

public class Report {
    private int id, emergencyId;
    private Date date;
    private double radiation;
    private String info;

    public Report(int id, int emergencyId, Date date, double radiation, String info) {
        this.id = id;
        this.emergencyId = emergencyId;
        this.date = date;
        this.radiation = radiation;
        this.info = info;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmergencyId() {
        return emergencyId;
    }

    public void setEmergencyId(int emergencyId) {
        this.emergencyId = emergencyId;
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

    public Report() {
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
}
