package dao;

import model.Emergency;
import model.Organisation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Ilya on 20.12.2015.
 */

public class DaoFactory {
    private String user = "root";
    private String password = "";
    private String url = "jdbc:mysql://localhost:3306/emergencies";
    private String driver = "com.mysql.jdbc.Driver";

    public DaoFactory() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.out.println("No JDBC driver!");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException{
        System.out.println("DAO main");
        List<Emergency> list;
        DaoFactory daoFactory = new DaoFactory();

        try (Connection con = daoFactory.getConnection()) {
            OrganisationDao dao = DaoFactory.getOrganisationDao(con);
            System.out.println(dao.getAll().get(0));
        }
    }

    public Connection getConnection() throws SQLException {
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
}
