package model;

import javax.print.attribute.standard.Severity;
import java.sql.Date;

/**
 * Created by Oleksandr on 28.11.2015.
 */
public class Emergency {
    private Date date;
    private int id;
    private AreaType areaType;
    private SeverityType severityType;
    private Organisation organisation;

    public Emergency() {
    }

    public Emergency(Date date, AreaType areaType, SeverityType severityType, Organisation organisation) {
        this.date = date;
        this.areaType = areaType;
        this.severityType = severityType;
        this.organisation = organisation;
    }

    @Override
    public String toString() {
        return organisation + " " + date;
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

    public SeverityType getSeverityType() {
        return severityType;
    }

    public void setSeverityType(SeverityType severityType) {
        this.severityType = severityType;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }
}
