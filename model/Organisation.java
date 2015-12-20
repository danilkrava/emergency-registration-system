package model;

/**
 * Created by Oleksandr on 28.11.2015.
 */
public class Organisation {
    private String name, address;
    private int id, regionId;

    public Organisation(int id, String name, String address, int regionId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.regionId = regionId;
    }

    public Organisation() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }
}
