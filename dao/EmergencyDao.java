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
}
