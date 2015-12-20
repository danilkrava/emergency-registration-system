package dao;

import model.Organisation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ilya on 20.12.2015.
 */
public class OrganisationDao {
    private final Connection connection;

    public OrganisationDao(Connection connection) {
        this.connection = connection;
    }

    public Organisation get(int id) throws SQLException{
        RegionDao regionDao = DaoFactory.getRegionDao(connection);

        String sql = "SELECT * FROM organisation WHERE orgranisation_id = ?;";
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setInt(1, id);
        ResultSet rs = stm.executeQuery();
        rs.next();

        Organisation o = new Organisation();
        o.setId(rs.getInt("orgranisation_id"));
        o.setName(rs.getString("name"));
        o.setAddress(rs.getString("address"));
        o.setRegion(regionDao.get(rs.getInt("region_id")));
        return o;
    }

    public List<Organisation> getAll() throws SQLException{
        List<Organisation> list = new ArrayList<>();
        RegionDao regionDao = DaoFactory.getRegionDao(connection);

        String sql = "SELECT * FROM organisation;";
        PreparedStatement stm = connection.prepareStatement(sql);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            Organisation o = new Organisation();
            o.setId(rs.getInt("orgranisation_id"));
            o.setName(rs.getString("name"));
            o.setAddress(rs.getString("address"));
            o.setRegion(regionDao.get(rs.getInt("region_id")));
            list.add(o);
        }

        return list;
    }
}
