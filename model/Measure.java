package model;

/**
 * Created by Oleksandr on 28.11.2015.
 */
public class Measure {
    private int id, timeTypeId, severityTypeId, areaTypeId;

    public Measure(int id, int timeTypeId, int severityTypeId, int areaTypeId) {
        this.id = id;
        this.timeTypeId = timeTypeId;
        this.severityTypeId = severityTypeId;
        this.areaTypeId = areaTypeId;
    }

    public Measure() {
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Measure{" +
                "id=" + id +
                ", timeTypeId=" + timeTypeId +
                ", severityTypeId=" + severityTypeId +
                ", areaTypeId=" + areaTypeId +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTimeTypeId() {
        return timeTypeId;
    }

    public void setTimeTypeId(int timeTypeId) {
        this.timeTypeId = timeTypeId;
    }

    public int getSeverityTypeId() {
        return severityTypeId;
    }

    public void setSeverityTypeId(int severityTypeId) {
        this.severityTypeId = severityTypeId;
    }

    public int getAreaTypeId() {
        return areaTypeId;
    }

    public void setAreaTypeId(int areaTypeId) {
        this.areaTypeId = areaTypeId;
    }
}
