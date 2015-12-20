package model;

/**
 * Created by Oleksandr on 28.11.2015.
 */
public class District {
    private String name, region;

    public District(String name, String region) {
        this.name = name;
        this.region = region;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
