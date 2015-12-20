package dao;

import model.Region;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ilya on 20.12.2015.
 */
public class RegionDao {
    private final Connection connection;

    public RegionDao(Connection connection) {
        this.connection = connection;
    }

    public Region get(int id) throws SQLException {
        String sql = "SELECT * FROM region WHERE region_id = ?;";
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setInt(1, id);
        ResultSet rs = stm.executeQuery();
        rs.next();

        Region r = new Region();
        r.setId(rs.getInt("region_id"));
        r.setName(rs.getString("name"));
        return r;
    }

    public List<Region> getAll() throws SQLException{
        List<Region> list = new ArrayList<>();

        String sql = "SELECT * FROM emergency;";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Region r = new Region();
                r.setId(rs.getInt("region_id"));
                r.setName(rs.getString("name"));
                list.add(r);
            }
        }
        return list;
    }
}
