package model;

/**
 * Created by Oleksandr on 28.11.2015.
 */
public class Organisation {
    private String name, address;
    private int id;
    private Region region;

    public Organisation() {
    }

    public Organisation(String name, String address, Region region) {
        this.name = name;
        this.address = address;
        this.region = region;
    }

    @Override
    public String toString() {
        return "Organisation{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", id=" + id +
                ", region=" + region +
                '}';
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }
}
