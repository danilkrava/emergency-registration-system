package model;

import java.sql.Date;

/**
 * Created by Oleksandr on 28.11.2015.
 */
public class Emergency {
    private Date date;
    private int id;
    private AreaType areaType;
    private Severity severity;
    private Organisation organisation;

    public Emergency() {
    }

    public Emergency(Date date, int id, AreaType areaType, Severity severity, Organisation organisation) {
        this.date = date;
        this.id = id;
        this.areaType = areaType;
        this.severity = severity;
        this.organisation = organisation;
    }

    @Override
    public String toString() {
        return "Emergency{" +
                "date=" + date +
                ", id=" + id +
                ", areaType=" + areaType +
                ", severity=" + severity +
                ", organisation=" + organisation +
                '}';
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AreaType getAreaType() {
        return areaType;
    }

    public void setAreaType(AreaType areaType) {
        this.areaType = areaType;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }
}
