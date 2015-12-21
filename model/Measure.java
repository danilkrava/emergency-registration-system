package model;

/**
 * Created by Oleksandr on 28.11.2015.
 */
public class Measure {
    private int id;
    private TimeType timeType;
    private SeverityType severity;
    private AreaType areaType;
    private String info;

    public Measure() {
    }

    public Measure(TimeType timeType, SeverityType severity, AreaType areaType, String info) {
        this.timeType = timeType;
        this.severity = severity;
        this.areaType = areaType;
        this.info = info;
    }

    @Override
    public String toString() {
        return info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TimeType getTimeType() {
        return timeType;
    }

    public void setTimeType(TimeType timeType) {
        this.timeType = timeType;
    }

    public SeverityType getSeverity() {
        return severity;
    }

    public void setSeverity(SeverityType severity) {
        this.severity = severity;
    }

    public AreaType getAreaType() {
        return areaType;
    }

    public void setAreaType(AreaType areaType) {
        this.areaType = areaType;
    }
}
