package dao;

import model.Emergency;
import model.Organisation;
import model.Report;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ilya on 21.12.2015.
 */
public class ReportDao {
    private final Connection connection;

    public ReportDao(Connection connection) {
        this.connection = connection;
    }

    public Report get(int id) throws SQLException {
        EmergencyDao emergencyDao = DaoFactory.getEmergencyDao(connection);

        String sql = "SELECT * FROM report WHERE report_id = ?;";
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setInt(1, id);
        ResultSet rs = stm.executeQuery();
        rs.next();

        Report r = new Report();
        r.setId(rs.getInt("report_id"));
        r.setEmergency(emergencyDao.get(rs.getInt("emergency_id")));
        r.setDate(rs.getDate("date"));
        r.setRadiation(rs.getDouble("radiation"));
        r.setInfo(rs.getString("info"));
        return r;
    }

    public List<Report> getByEmergency(Emergency emergency) throws SQLException{
        List<Report> list = new ArrayList<>();

        String sql = "SELECT * FROM report WHERE emergency_id=?;";
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setInt(1, emergency.getId());
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            Report a = new Report();
            a.setId(rs.getInt("report_id"));
            a.setInfo(rs.getString("info"));
            a.setRadiation(rs.getFloat("radiation"));
            a.setEmergency(emergency);
            a.setDate(rs.getDate("date"));
            list.add(a);
        }
        return list;
    }

    public List<Report> getAll() throws SQLException{
        List<Report> list = new ArrayList<>();
        EmergencyDao emergencyDao = DaoFactory.getEmergencyDao(connection);

        String sql = "SELECT * FROM report;";
        PreparedStatement stm = connection.prepareStatement(sql);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            Report r = new Report();
            r.setId(rs.getInt("report_id"));
            r.setEmergency(emergencyDao.get(rs.getInt("emergency_id")));
            r.setDate(rs.getDate("date"));
            r.setRadiation(rs.getDouble("radiation"));
            r.setInfo(rs.getString("info"));
            list.add(r);
        }

        return list;
    }

    public void add(Report obj) throws SQLException{
        String sql = "INSERT INTO report (emergency_id, date, radiation, info) VALUES (?,?,?,?);";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, obj.getEmergency().getId());
            stm.setDate(2, obj.getDate());
            stm.setFloat(3, (float) obj.getRadiation());
            stm.setString(4, obj.getInfo());
            int count = stm.executeUpdate();
            if (count != 1)
                throw new SQLException(count + " records were modified instead of 1!");

        }

        sql = "SELECT MAX(report_id) FROM report;";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            ResultSet rs = stm.executeQuery();
            rs.next();
            obj.setId(rs.getInt(1));
        }
    }

   public void update(Report obj) throws SQLException {
        String sql = "UPDATE report SET emergency_id=?, date=?, radiation=?, info=? WHERE report_id=?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, obj.getEmergency().getId());
            stm.setDate(2, obj.getDate());
            stm.setFloat(3, (float) obj.getRadiation());
            stm.setString(4, obj.getInfo());
            stm.setInt(5, obj.getId());
            int count = stm.executeUpdate();
            if (count != 1) {
                throw new SQLException(count + " records were modified instead of 1!");
            }
        }
    }

    public void delete(Report obj) throws SQLException {
        String sql = "DELETE FROM report WHERE report_id = ?;";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, obj.getId());
            int count = stm.executeUpdate();
            if (count != 1) {
                throw new SQLException(count + " records were modified instead of 1!");
            }
        }
    }
}

