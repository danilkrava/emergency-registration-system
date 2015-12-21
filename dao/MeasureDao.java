package dao;

import model.Emergency;
import model.Measure;
import model.TimeType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ilya on 21.12.2015.
 */
public class MeasureDao {
    private final Connection connection;

    public MeasureDao(Connection connection) {
        this.connection = connection;
    }

    public Measure get(int id) throws SQLException {
        Measure e = new Measure();
        TimeTypeDao timeTypeDao = DaoFactory.getTimeTypeDao(connection);
        AreaTypeDao areaTypeDao = DaoFactory.getAreaTypeDao(connection);
        SeverityTypeDao severityTypeDao = DaoFactory.getSeverityTypeDao(connection);

        String sql = "SELECT * FROM measure WHERE measure_id = ?;";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            rs.next();
            e.setId(rs.getInt("measure_id"));
            e.setTimeType(timeTypeDao.get(rs.getInt("time_type_id")));
            e.setAreaType(areaTypeDao.get(rs.getInt("area_type_id")));
            e.setSeverityType(severityTypeDao.get(rs.getInt("severity_type_id")));
            e.setInfo(rs.getString("info"));
        }
        return e;
    }

    public List<Measure> getAll() throws SQLException{
        List<Measure> list = new ArrayList<>();
        TimeTypeDao timeTypeDao = DaoFactory.getTimeTypeDao(connection);
        AreaTypeDao areaTypeDao = DaoFactory.getAreaTypeDao(connection);
        SeverityTypeDao severityTypeDao = DaoFactory.getSeverityTypeDao(connection);

        String sql = "SELECT * FROM measure;";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Measure e = new Measure();
                e.setId(rs.getInt("measure_id"));
                e.setTimeType(timeTypeDao.get(rs.getInt("time_type_id")));
                e.setAreaType(areaTypeDao.get(rs.getInt("area_type_id")));
                e.setSeverityType(severityTypeDao.get(rs.getInt("severity_type_id")));
                e.setInfo(rs.getString("info"));
                list.add(e);
            }
        }
        return list;
    }

    public List<Measure> getByEmergency(Emergency emergency) throws SQLException{
        List<Measure> list = new ArrayList<>();
        TimeTypeDao timeTypeDao = DaoFactory.getTimeTypeDao(connection);
        AreaTypeDao areaTypeDao = DaoFactory.getAreaTypeDao(connection);
        SeverityTypeDao severityTypeDao = DaoFactory.getSeverityTypeDao(connection);
        long yearCount = Math.round((new java.util.Date().getTime() - emergency.getDate().getTime())/365);
        String sql = "SELECT * FROM time_type WHERE years_elapsed = (SELECT MAX(years_elapsed) FROM time_type WHERE years_elapsed <= " + yearCount + ");";
        TimeType type = new TimeType();
        try (PreparedStatement stm = connection.prepareStatement(sql)){
            ResultSet rs = stm.executeQuery();
                rs.next();
                type.setId(rs.getInt("time_type_id"));
                type.setTimeElapsed(rs.getInt("years_elapsed"));
                type.setName(rs.getString("name"));
        }

        sql = "SELECT * FROM measure WHERE (((measure.area_type_id = " + emergency.getAreaType().getId()+") OR " +
                "ISNULL(measure.area_type_id)) " +
                "AND ((measure.severity_type_id = " + emergency.getSeverityType().getId() + ") OR" +
                "ISNULL(measure.severity_type_id))" +
                "AND ((measure.time_type_id = " + type.getId() + ") OR" +
                "ISNULL(measure.time_type_id)))";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Measure e = new Measure();
                e.setId(rs.getInt("measure_id"));
                e.setTimeType(timeTypeDao.get(rs.getInt("time_type_id")));
                e.setAreaType(areaTypeDao.get(rs.getInt("area_type_id")));
                e.setSeverityType(severityTypeDao.get(rs.getInt("severity_type_id")));
                e.setInfo(rs.getString("info"));
                list.add(e);
            }
        }
        return list;
    }

    public void add(Measure obj) throws SQLException{
        String sql = "INSERT INTO measure (time_type_id, area_type_id, severity_type_id, info) VALUES (?,?,?,?);";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, obj.getTimeType().getId());
            stm.setInt(2, obj.getAreaType().getId());
            stm.setInt(3, obj.getSeverityType().getId());
            stm.setString(4, obj.getInfo());
            int count = stm.executeUpdate();
            if (count != 1)
                throw new SQLException(count + " records were modified instead of 1!");
        }

        sql = "SELECT MAX(measure_id) FROM measure;";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            ResultSet rs = stm.executeQuery();
            rs.next();
            obj.setId(rs.getInt(1));
        }
    }

    public void update(Measure obj) throws SQLException {
        String sql = "UPDATE measure SET time_type_id=?, area_type_id=?, severity_type_id=?, info=? WHERE measure_id=?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, obj.getTimeType().getId());
            stm.setInt(2, obj.getAreaType().getId());
            stm.setInt(3, obj.getSeverityType().getId());
            stm.setString(4, obj.getInfo());
            stm.setInt(5, obj.getId());
            int count = stm.executeUpdate();
            if (count != 1) {
                throw new SQLException(count + " records were modified instead of 1!");
            }
        }
    }

    public void delete(Measure obj) throws SQLException {
        String sql = "DELETE FROM measure WHERE measure_id = ?;";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, obj.getId());
            int count = stm.executeUpdate();
            if (count != 1) {
                throw new SQLException(count + " records were modified instead of 1!");
            }
        }
    }
}
