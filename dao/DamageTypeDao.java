package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.DamageType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ilya on 21.12.2015.
 */
public class DamageTypeDao {
    private final Connection connection;

    public DamageTypeDao(Connection connection) {
        this.connection = connection;
    }

    public DamageType get(int id) throws SQLException {
        String sql = "SELECT * FROM damage_type WHERE damage_type = ?;";
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setInt(1, id);
        ResultSet rs = stm.executeQuery();
        rs.next();

        DamageType a = new DamageType();
        a.setId(rs.getInt("damage_type"));
        a.setName(rs.getString("name"));
        return a;
    }

    public List<DamageType> getAll() throws SQLException{
        List<DamageType> list = new ArrayList<>();

        String sql = "SELECT * FROM damage_type;";
        PreparedStatement stm = connection.prepareStatement(sql);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            DamageType a = new DamageType();
            a.setId(rs.getInt("damage_type"));
            a.setName(rs.getString("name"));
            list.add(a);
        }
        return list;
    }

    public ObservableList<DamageType> getByPerson(int personId) throws  SQLException{
        ObservableList<DamageType> list = FXCollections.observableArrayList();

        String sql = "SELECT * FROM damage_type INNER JOIN damage_person_mapping as dpm on dpm.damage_type_id=damage_type.damage_type where dpm.person_id=?;";
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setInt(1, personId);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            DamageType a = new DamageType();
            a.setId(rs.getInt("damage_type"));
            a.setName(rs.getString("name"));
            list.add(a);
        }
        return list;
    }

    public void add(DamageType obj) throws SQLException{
        String sql = "INSERT INTO damage_type (name) VALUES (?);";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, obj.getName());
            int count = stm.executeUpdate();
            if (count != 1)
                throw new SQLException(count + " records were modified instead of 1!");

        }

        sql = "SELECT MAX(damage_type) FROM damage_type;";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            ResultSet rs = stm.executeQuery();
            rs.next();
            obj.setId(rs.getInt(1));

        }
    }

    public void update(DamageType obj) throws SQLException {
        String sql = "UPDATE damage_type SET name=? WHERE damage_type=?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, obj.getName());
            stm.setInt(2, obj.getId());
            int count = stm.executeUpdate();
            if (count != 1) {
                throw new SQLException(count + " records were modified instead of 1!");
            }
        }
    }

    public void delete(DamageType obj) throws SQLException {
        String sql = "DELETE FROM damage_type WHERE damage_type = ?;";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, obj.getId());
            int count = stm.executeUpdate();
            if (count != 1) {
                throw new SQLException(count + " records were modified instead of 1!");
            }
        }
    }

}
