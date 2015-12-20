package model;

import java.sql.*;

/**
 * Created by Oleksandr on 28.11.2015.
 */
public class Emergency {
    private java.sql.Date date;
    private int id, areaTypeId, severityTypeId, organisationId;

    public Emergency(int id, java.sql.Date date, int areaTypeId, int severityTypeId, int organisationId) {
        this.id = id;
        this.date = date;
        this.areaTypeId = areaTypeId;
        this.severityTypeId = severityTypeId;
        this.organisationId = organisationId;
    }

    public Emergency() {
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Emergency{" +
                "date=" + date +
                ", id=" + id +
                ", areaTypeId=" + areaTypeId +
                ", severityTypeId=" + severityTypeId +
                ", organisationId=" + organisationId +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public java.sql.Date getDate() {
        return date;
    }

    public void setDate(java.sql.Date date) {
        this.date = date;
    }

    public int getAreaTypeId() {
        return areaTypeId;
    }

    public void setAreaTypeId(int areaTypeId) {
        this.areaTypeId = areaTypeId;
    }

    public int getSeverityTypeId() {
        return severityTypeId;
    }

    public void setSeverityTypeId(int severityTypeId) {
        this.severityTypeId = severityTypeId;
    }

    public int getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(int organisationId) {
        this.organisationId = organisationId;
    }
}
