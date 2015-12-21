package dao;

import model.AppliedMeasure;
import model.Measure;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ilya on 21.12.2015.
 */
public class AppliedMeasureDao {
    private final Connection connection;

    public AppliedMeasureDao(Connection connection) {
        this.connection = connection;
    }

    public AppliedMeasure get(int id) throws SQLException {
        AppliedMeasure e = new AppliedMeasure();
        MeasureDao measureDao = DaoFactory.getMeasureDao(connection);

        String sql = "SELECT * FROM applied_measure WHERE applied_measure_id = ?;";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            rs.next();
            e.setId(rs.getInt("applied_measure_id"));
            e.setDate(rs.getDate("date"));
            e.setMoney(rs.getFloat("money"));
            e.setMeasure(measureDao.get(rs.getInt("measure_id")));
            e.setInfo(rs.getString("measure_info"));
        }
        return e;
    }

    public List<AppliedMeasure> getAll() throws SQLException{
        List<AppliedMeasure> list = new ArrayList<>();
        MeasureDao measureDao = DaoFactory.getMeasureDao(connection);

        String sql = "SELECT * FROM applied_measure;";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                AppliedMeasure e = new AppliedMeasure();
                e.setId(rs.getInt("applied_measure_id"));
                e.setDate(rs.getDate("date"));
                e.setMoney(rs.getFloat("money"));
                e.setMeasure(measureDao.get(rs.getInt("measure_id")));
                e.setInfo(rs.getString("measure_info"));
                list.add(e);
            }
        }
        return list;
    }


    public void add(AppliedMeasure obj) throws SQLException{
        String sql = "INSERT INTO applied_measure (date, money, measure_id, measure_info) VALUES (?,?,?,?);";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setDate(1, obj.getDate());
            stm.setFloat(2, (float) obj.getMoney());
            stm.setInt(3, obj.getMeasure().getId());
            stm.setString(4, obj.getInfo());
            int count = stm.executeUpdate();
            if (count != 1)
                throw new SQLException(count + " records were modified instead of 1!");
        }

        sql = "SELECT MAX(applied_measure_id) FROM applied_measure;";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            ResultSet rs = stm.executeQuery();
            rs.next();
            obj.setId(rs.getInt(1));
        }
    }

    public void update(AppliedMeasure obj) throws SQLException {
        String sql = "UPDATE applied_measure SET date=?, money=?, measure_id=?, measure_info=? WHERE measure_id=?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setDate(1, obj.getDate());
            stm.setFloat(2, (float) obj.getMoney());
            stm.setInt(3, obj.getMeasure().getId());
            stm.setString(4, obj.getInfo());
            stm.setInt(5, obj.getId());
            int count = stm.executeUpdate();
            if (count != 1) {
                throw new SQLException(count + " records were modified instead of 1!");
            }
        }
    }

    public void delete(AppliedMeasure obj) throws SQLException {
        String sql = "DELETE FROM applied_measure WHERE applied_measure_id = ?;";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, obj.getId());
            int count = stm.executeUpdate();
            if (count != 1) {
                throw new SQLException(count + " records were modified instead of 1!");
            }
        }
    }
}
