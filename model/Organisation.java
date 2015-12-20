package model;

/**
 * Created by Oleksandr on 28.11.2015.
 */
public class Organisation {
    private String name, address;
    private int id, districtId;

    public Organisation(int id, String name, String address, int districtId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.districtId = districtId;
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

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }
}
