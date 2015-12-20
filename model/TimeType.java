package model;

/**
 * Created by Oleksandr on 20.12.2015.
 */
public class TimeType {
    private int id;
    private int timeElapsed;
    private String name;

    public TimeType() {
    }

    public TimeType(String name, int timeElapsed) {
        this.name = name;
        this.timeElapsed = timeElapsed;
    }

    @Override
    public String toString() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimeElapsed() {
        return timeElapsed;
    }

    public void setTimeElapsed(int timeElapsed) {
        this.timeElapsed = timeElapsed;
    }
}
