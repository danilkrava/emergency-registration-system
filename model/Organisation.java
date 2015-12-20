package model;

/**
 * Created by Oleksandr on 28.11.2015.
 */
public class Organisation {
    private String name, address;
    private int districtId;

    public Organisation(String name, String address, int districtId) {
        this.name = name;
        this.address = address;
        this.districtId = districtId;
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
