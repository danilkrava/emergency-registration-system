package dao;

import model.Emergency;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        OrganisationDao orgDao = DaoFactory.getOrganisationDao(connection);
        AreaTypeDao areaTypeDao = DaoFactory.getAreaTypeDao(connection);
        SeverityTypeDao severityTypeDao = DaoFactory.getSeverityTypeDao(connection);

        String sql = "SELECT * FROM emergency WHERE emergency_id = ?;";
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setInt(1, id);
        ResultSet rs = stm.executeQuery();
        rs.next();

        Emergency e = new Emergency();
        e.setId(rs.getInt("emergency_id"));
        e.setDate(rs.getDate("date"));
        e.setAreaType(areaTypeDao.get(rs.getInt("area_type_id")));
        e.setSeverityType(severityTypeDao.get(rs.getInt("severity_type_id")));
        e.setOrganisation(orgDao.get(rs.getInt("organisation_id")));

        return e;
    }

    public List<Emergency> getAll() throws SQLException{
        List<Emergency> list = new ArrayList<>();
        OrganisationDao orgDao = DaoFactory.getOrganisationDao(connection);
        AreaTypeDao areaTypeDao = DaoFactory.getAreaTypeDao(connection);
        SeverityTypeDao severityTypeDao = DaoFactory.getSeverityTypeDao(connection);

        String sql = "SELECT * FROM emergency;";
        PreparedStatement stm = connection.prepareStatement(sql);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            Emergency e = new Emergency();
            e.setId(rs.getInt("emergency_id"));
            e.setDate(rs.getDate("date"));
            e.setAreaType(areaTypeDao.get(rs.getInt("area_type_id")));
            e.setSeverityType(severityTypeDao.get(rs.getInt("severity_type_id")));
            e.setOrganisation(orgDao.get(rs.getInt("organisation_id")));
            list.add(e);
        }

        return list;
    }

    public void add(Emergency em) throws PersistException{
        String sql = "INSERT INTO emergency (date, organisation_id, area_type_id, severity_type_id) VALUES (?,?,?,?);";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setDate(1, em.getDate());
            stm.setInt(2, em.getOrganisation().getId());
            stm.setInt(3, em.getAreaType().getId());
            stm.setInt(4, em.getSeverityType().getId());
            int count = stm.executeUpdate();
            if (count != 1)
                throw new PersistException(count + " records were modified instead of 1!");
            //em.setId();
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    public void update(Emergency obj) throws PersistException {
        String sql = "UPDATE emergency SET date=?, organisation_id=?, area_type_id=?, severity_type_id=? WHERE emergency_id=?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setDate(1, obj.getDate());
            stm.setInt(2, obj.getOrganisation().getId());
            stm.setInt(3, obj.getAreaType().getId());
            stm.setInt(4, obj.getSeverityType().getId());
            stm.setInt(5, obj.getId());
            int count = stm.executeUpdate();
            if (count != 1) {
                throw new PersistException(count + " records were modified instead of 1!");
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    public void delete(Emergency obj) throws PersistException {
        String sql = "DELETE FROM emergency WHERE emergency_id = ?;";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, obj.getId());
            int count = stm.executeUpdate();
            if (count != 1) {
                throw new PersistException(count + " records were modified instead of 1!");
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }
}
