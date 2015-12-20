package model;

/**
 * Created by Oleksandr on 28.11.2015.
 */
public class Measure {
    private int timeTypeId, severityTypeId, areaTypeId;

    public Measure(int timeTypeId, int severityTypeId, int areaTypeId) {
        this.timeTypeId = timeTypeId;
        this.severityTypeId = severityTypeId;
        this.areaTypeId = areaTypeId;
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
