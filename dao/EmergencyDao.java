package dao;

import model.Emergency;
import model.Measure;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ilya on 20.12.2015.
 */
public class EmergencyDao {
    private final Connection connection;

    public EmergencyDao(Connection connection) {
        this.connection = connection;
    }

    public Emergency get(int id) throws SQLException {
        Emergency e = new Emergency();
        OrganisationDao orgDao = DaoFactory.getOrganisationDao(connection);
        AreaTypeDao areaTypeDao = DaoFactory.getAreaTypeDao(connection);
        SeverityTypeDao severityTypeDao = DaoFactory.getSeverityTypeDao(connection);

        String sql = "SELECT * FROM emergency WHERE emergency_id = ?;";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            rs.next();

            e.setId(rs.getInt("emergency_id"));
            e.setDate(rs.getDate("date"));
            e.setAreaType(areaTypeDao.get(rs.getInt("area_type_id")));
            e.setSeverityType(severityTypeDao.get(rs.getInt("severity_type_id")));
            e.setOrganisation(orgDao.get(rs.getInt("organisation_id")));
            e.setInfo(rs.getString("info"));
        }
        return e;
    }

    public List<Emergency> getAll() throws SQLException {
        List<Emergency> list = new ArrayList<>();
        OrganisationDao orgDao = DaoFactory.getOrganisationDao(connection);
        AreaTypeDao areaTypeDao = DaoFactory.getAreaTypeDao(connection);
        SeverityTypeDao severityTypeDao = DaoFactory.getSeverityTypeDao(connection);

        String sql = "SELECT * FROM emergency;";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Emergency e = new Emergency();
                e.setId(rs.getInt("emergency_id"));
                e.setDate(rs.getDate("date"));
                e.setAreaType(areaTypeDao.get(rs.getInt("area_type_id")));
                e.setSeverityType(severityTypeDao.get(rs.getInt("severity_type_id")));
                e.setOrganisation(orgDao.get(rs.getInt("organisation_id")));
                e.setInfo(rs.getString("info"));
                list.add(e);
            }
        }
        return list;
    }

    public List<Emergency> filter(Date from, Date to, int organisationId, int areaTypeId, int severityTypeId) throws SQLException {
        List<Emergency> list = new ArrayList<>();
        OrganisationDao orgDao = DaoFactory.getOrganisationDao(connection);
        AreaTypeDao areaTypeDao = DaoFactory.getAreaTypeDao(connection);
        SeverityTypeDao severityTypeDao = DaoFactory.getSeverityTypeDao(connection);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM emergency");
        if (from != null || to != null || organisationId != -1 || areaTypeId != -1 || severityTypeId != -1) {
            sb.append(" where");
            boolean isFirst = true;
            if (from != null && to != null) {
                sb.append("(date BETWEEN '" + from + "' AND '" + to + "')");
                isFirst = false;
            } else {
                if (from != null) {
                    sb.append("(date >= '" + from + "')");
                    isFirst = false;
                }
                if (to != null) {
                    sb.append("(date <= '" + to + "')");
                    isFirst = false;
                }
            }
            if (organisationId != -1) {
                if (!isFirst)
                    sb.append(" AND");
                else
                    isFirst = false;
                sb.append("(organisation_id = " + organisationId + ")");
            }
            if (areaTypeId != -1) {
                if (!isFirst)
                    sb.append(" AND");
                else
                    isFirst = false;
                sb.append("(area_type_id = " + areaTypeId + ")");
            }
            if (severityTypeId != -1) {
                if (!isFirst)
                    sb.append(" AND");
                else
                    isFirst = false;
                sb.append("(severity_type_id = " + severityTypeId + ")");
            }
            sb.append(";");
        }
        String sql = sb.toString();

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Emergency e = new Emergency();
                e.setId(rs.getInt("emergency_id"));
                e.setDate(rs.getDate("date"));
                e.setAreaType(areaTypeDao.get(rs.getInt("area_type_id")));
                e.setSeverityType(severityTypeDao.get(rs.getInt("severity_type_id")));
                e.setOrganisation(orgDao.get(rs.getInt("organisation_id")));
                e.setInfo(rs.getString("info"));
                list.add(e);
            }
        }
        return list;
    }

    public void add(Emergency obj) throws SQLException {
        String sql = "INSERT INTO emergency (date, organisation_id, area_type_id, severity_type_id, info) VALUES (?,?,?,?,?);";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setDate(1, obj.getDate());
            stm.setInt(2, obj.getOrganisation().getId());
            stm.setInt(3, obj.getAreaType().getId());
            stm.setInt(4, obj.getSeverityType().getId());
            stm.setString(5, obj.getInfo());
            int count = stm.executeUpdate();
            if (count != 1)
                throw new SQLException(count + " records were modified instead of 1!");
        }

        sql = "SELECT MAX(emergency_id) FROM emergency;";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            ResultSet rs = stm.executeQuery();
            rs.next();
            obj.setId(rs.getInt(1));
        }
    }

    public void update(Emergency obj) throws SQLException {
        String sql = "UPDATE emergency SET date=?, organisation_id=?, area_type_id=?, severity_type_id=?, info=? WHERE emergency_id=?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setDate(1, obj.getDate());
            stm.setInt(2, obj.getOrganisation().getId());
            stm.setInt(3, obj.getAreaType().getId());
            stm.setInt(4, obj.getSeverityType().getId());
            stm.setString(5, obj.getInfo());
            stm.setInt(6, obj.getId());

            int count = stm.executeUpdate();
            if (count != 1) {
                throw new SQLException(count + " records were modified instead of 1!");
            }
        }
    }

    public void delete(Emergency obj) throws SQLException {
        String sql = "DELETE FROM emergency WHERE emergency_id = ?;";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, obj.getId());
            int count = stm.executeUpdate();
            if (count != 1) {
                throw new SQLException(count + " records were modified instead of 1!");
            }
        }
    }
}
