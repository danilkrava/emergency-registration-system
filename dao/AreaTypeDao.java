package dao;

import model.AreaType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ilya on 20.12.2015.
 */
public class AreaTypeDao {
    private final Connection connection;

    public AreaTypeDao(Connection connection) {
        this.connection = connection;
    }

    public AreaType get(int id) throws SQLException{
        String sql = "SELECT * FROM area_type WHERE area_type_id = ?;";
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setInt(1, id);
        ResultSet rs = stm.executeQuery();
        rs.next();

        AreaType a = new AreaType();
        a.setId(rs.getInt("area_type_id"));
        a.setName(rs.getString("name"));
        a.setArea(rs.getDouble("area"));
        return a;
    }

    public List<AreaType> getAll() throws SQLException{
        List<AreaType> list = new ArrayList<>();

        String sql = "SELECT * FROM organisation;";
        PreparedStatement stm = connection.prepareStatement(sql);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            AreaType a = new AreaType();
            a.setId(rs.getInt("area_type_id"));
            a.setName(rs.getString("name"));
            a.setArea(rs.getDouble("area"));
            list.add(a);
        }
        return list;
    }

    public void add(AreaType obj) throws SQLException{
        String sql = "INSERT INTO area_type (name, area) VALUES (?,?);";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, obj.getName());
            stm.setDouble(2, obj.getArea());
            int count = stm.executeUpdate();
            if (count != 1)
                throw new SQLException(count + " records were modified instead of 1!");

        }

        sql = "SELECT MAX(area_type_id) FROM area_type;";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            ResultSet rs = stm.executeQuery();
            rs.next();
            obj.setId(rs.getInt(1));

        }
    }

    public void update(AreaType obj) throws SQLException {
        String sql = "UPDATE area_type SET name=? WHERE area_type_id=?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, obj.getName());
            stm.setInt(2, obj.getId());
            int count = stm.executeUpdate();
            if (count != 1) {
                throw new SQLException(count + " records were modified instead of 1!");
            }
        }
    }

    public void delete(AreaType obj) throws SQLException {
        String sql = "DELETE FROM area_type WHERE area_type_id = ?;";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, obj.getId());
            int count = stm.executeUpdate();
            if (count != 1) {
                throw new SQLException(count + " records were modified instead of 1!");
            }
        }
    }
}
