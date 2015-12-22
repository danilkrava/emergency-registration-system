package dao;

import model.*;

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
    private static String url = "jdbc:mysql://localhost:3306/emergencies2";
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

    public static void setLoginInfo(String user0, String password0) {
        user = user0;
        password = password0;
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

    public static PersonDao getPersonDao (Connection connection) {return  new PersonDao(connection); }

    public static DamageTypeDao getDamageTypeDao (Connection connection) {return  new DamageTypeDao(connection); }

    public static MeasureDao getMeasureDao(Connection connection) {return new MeasureDao(connection);}

    public static AppliedMeasureDao getAppliedMeasureDao(Connection connection) {return new AppliedMeasureDao(connection);}

    public static ReportDao getReportDao(Connection connection) { return new ReportDao(connection);}

}
