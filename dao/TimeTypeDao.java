package dao;

import model.SeverityType;
import model.TimeType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ilya on 21.12.2015.
 */
public class TimeTypeDao {
    private final Connection connection;

    public TimeTypeDao(Connection connection) {
        this.connection = connection;
    }

    public TimeType get(int id) throws SQLException {
        String sql = "SELECT * FROM time_type WHERE time_type_id = ?;";
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setInt(1, id);
        ResultSet rs = stm.executeQuery();
        rs.next();

        TimeType s = new TimeType();
        s.setId(rs.getInt("time_type_id"));
        s.setName(rs.getString("name"));
        s.setTimeElapsed(rs.getInt("years_elapsed"));
        return s;
    }

    public List<TimeType> getAll() throws SQLException{
        List<TimeType> list = new ArrayList<>();

        String sql = "SELECT * FROM time_type;";
        PreparedStatement stm = connection.prepareStatement(sql);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            TimeType s = new TimeType();
            s.setId(rs.getInt("time_type_id"));
            s.setName(rs.getString("name"));
            s.setTimeElapsed(rs.getInt("years_elapsed"));
            list.add(s);
        }
        return list;
    }

    public void add(TimeType obj) throws SQLException{
        String sql = "INSERT INTO time_type (name, years_elapsed) VALUES (?,?);";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, obj.getName());
            stm.setInt(2, obj.getTimeElapsed());
            int count = stm.executeUpdate();
            if (count != 1)
                throw new SQLException(count + " records were modified instead of 1!");
        }

        sql = "SELECT MAX(time_type_id) FROM time_type;";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            ResultSet rs = stm.executeQuery();
            rs.next();
            obj.setId(rs.getInt(1));
        }
    }

    public void update(TimeType obj) throws SQLException {
        String sql = "UPDATE time_type SET name=?, years_elapsed=? WHERE severity_type_id=?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, obj.getName());
            stm.setInt(2, obj.getTimeElapsed());
            stm.setInt(3, obj.getId());
            int count = stm.executeUpdate();
            if (count != 1) {
                throw new SQLException(count + " records were modified instead of 1!");
            }
        }
    }

    public void delete(TimeType obj) throws SQLException {
        String sql = "DELETE FROM time_type WHERE time_type_id = ?;";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, obj.getId());
            int count = stm.executeUpdate();
            if (count != 1) {
                throw new SQLException(count + " records were modified instead of 1!");
            }
        }
    }
}
