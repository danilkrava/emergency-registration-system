package dao;
import model.SeverityType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ilya on 20.12.2015.
 */
public class SeverityTypeDao {
    private final Connection connection;

    public SeverityTypeDao(Connection connection) {
        this.connection = connection;
    }

    public SeverityType get(int id) throws SQLException {
        String sql = "SELECT * FROM severity_type WHERE severity_type_id = ?;";
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setInt(1, id);
        ResultSet rs = stm.executeQuery();
        rs.next();

        SeverityType s = new SeverityType();
        s.setId(rs.getInt("severity_type_id"));
        s.setName(rs.getString("name"));
        return s;
    }

    public List<SeverityType> getAll() throws SQLException{
        List<SeverityType> list = new ArrayList<>();

        String sql = "SELECT * FROM organisation;";
        PreparedStatement stm = connection.prepareStatement(sql);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            SeverityType s = new SeverityType();
            s.setId(rs.getInt("severity_type_id"));
            s.setName(rs.getString("name"));
            list.add(s);
        }

        return list;
    }
}
