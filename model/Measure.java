package model;

/**
 * Created by Oleksandr on 28.11.2015.
 */
public class Measure {
    private int id;
    private TimeType timeType;
    private SeverityType severity;
    private AreaType areaType;

    public Measure() {
    }

    public Measure(int id, TimeType timeType, SeverityType severity, AreaType areaType) {
        this.id = id;
        this.timeType = timeType;
        this.severity = severity;
        this.areaType = areaType;
    }

    @Override
    public String toString() {
        return "Measure{" +
                "id=" + id +
                ", timeType=" + timeType +
                ", severity=" + severity +
                ", areaType=" + areaType +
                '}';
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
