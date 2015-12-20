package dao;

import model.AreaType;
import model.Emergency;
import model.Organisation;
import model.Region;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Ilya on 20.12.2015.
 */

public class DaoFactory {
    private static String user = "root";
    private static String password = "";
    private static String url = "jdbc:mysql://localhost:3306/emergencies";
    private static String driver = "com.mysql.jdbc.Driver";

    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.out.println("No JDBC driver!");
            e.printStackTrace();
        }
    }

    public DaoFactory() {

    }

    public static void main(String[] args) throws SQLException{
        System.out.println("DAO main");
        List<Emergency> list;

        try (Connection con = DaoFactory.getConnection()) {
            OrganisationDao dao = DaoFactory.getOrganisationDao(con);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public static EmergencyDao getEmergencyDao(Connection connection) {
        return new EmergencyDao(connection);
    }

    public static OrganisationDao getOrganisationDao (Connection connection) {
        return new OrganisationDao(connection);
    }

    public static AreaTypeDao getAreaTypeDao (Connection connection) {
        return new AreaTypeDao(connection);
    }

    public static SeverityTypeDao getSeverityTypeDao (Connection connection) {
        return new SeverityTypeDao(connection);
    }

    public static RegionDao getRegionDao (Connection connection) {
        return new RegionDao(connection);
    }

    public static TimeTypeDao getTimeTypeDao (Connection connection) {return  new TimeTypeDao(connection); }
}
