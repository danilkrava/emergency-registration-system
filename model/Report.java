package model;

public class Report {
    private int emergencyId;
    private Date date;
    private float radiation;
    private String info;

    public Report(int emergencyId, Date date, float radiation, String info) {
        this.emergencyId = emergencyId;
        this.date = date;
        this.radiation = radiation;
        this.info = info;
        //lsdlmkmkmk
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

    public float getRadiation() {
        return radiation;
    }

    public void setRadiation(float radiation) {
        this.radiation = radiation;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
