package dao;

import model.Emergency;
import model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ilya on 21.12.2015.
 */
public class PersonDao {
    private final Connection connection;

    public PersonDao(Connection connection) {
        this.connection = connection;
    }

    public Person get(int id) throws SQLException {
        String sql = "SELECT * FROM person WHERE person_id = ?;";
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setInt(1, id);
        ResultSet rs = stm.executeQuery();
        rs.next();

        Person a = new Person();
        a.setId(rs.getInt("person_id"));
        a.setName(rs.getString("first_name"));
        a.setSurname(rs.getString("last_name"));
        a.setMiddleName(rs.getString("middle_name"));
        a.setBirthDate(rs.getDate("birth_date"));
        return a;
    }

    public List<Person> getByEmergency(int emergencyId) throws SQLException{
        List<Person> list = new ArrayList<>();

        String sql = "SELECT * FROM person INNER JOIN emergency_person_mapping as epm on epm.person_id=person.person_id WHERE epm.emergency_id=?;";
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setInt(1,emergencyId);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            Person a = new Person();
            a.setId(rs.getInt("person_id"));
            a.setName(rs.getString("first_name"));
            a.setSurname(rs.getString("last_name"));
            a.setMiddleName(rs.getString("middle_name"));
            a.setBirthDate(rs.getDate("birth_date"));
            list.add(a);
        }
        return list;
    }

    public void add(Person obj, Emergency em) throws SQLException{
        add(obj);
        String sql = "INSERT INTO emergency_person_mapping (person_id, emergency_id) VALUES (?,?);";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, obj.getId());
            stm.setInt(2, em.getId());
            stm.executeQuery();
        }
    }

    private void add(Person obj) throws SQLException{
        String sql = "INSERT INTO person (first_name, last_name, middle_name, birth_date) VALUES (?,?,?,?);";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, obj.getName());
            stm.setString(2, obj.getSurname());
            stm.setString(3, obj.getMiddleName());
            stm.setDate(4, obj.getBirthDate());
            int count = stm.executeUpdate();
            if (count != 1)
                throw new SQLException(count + " records were modified instead of 1!");
        }

        sql = "SELECT MAX(person_id) FROM person;";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            ResultSet rs = stm.executeQuery();
            rs.next();
            obj.setId(rs.getInt(1));
        }
    }



    public void update(Person obj) throws SQLException {
        String sql = "UPDATE person SET first_name=?, middle_name=?, last_name=?, birth_date=? WHERE person_id=?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, obj.getName());
            stm.setString(2, obj.getMiddleName());
            stm.setString(3, obj.getSurname());
            stm.setDate(4, obj.getBirthDate());
            stm.setInt(5, obj.getId());
            int count = stm.executeUpdate();
            if (count != 1) {
                throw new SQLException(count + " records were modified instead of 1!");
            }
        }
    }

    public void delete(Person obj) throws SQLException {
        String sql = "DELETE FROM person WHERE person_id = ?;";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, obj.getId());
            int count = stm.executeUpdate();
            if (count != 1) {
                throw new SQLException(count + " records were modified instead of 1!");
            }
        }
    }
}
