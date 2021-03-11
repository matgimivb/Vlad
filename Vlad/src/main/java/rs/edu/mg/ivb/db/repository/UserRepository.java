package rs.edu.mg.ivb.db.repository;

import java.sql.*;
import java.util.*;
import rs.edu.mg.ivb.db.DBConnection;
import rs.edu.mg.ivb.db.dao.*;


public class UserRepository {
    public List<User> getUsers() throws SQLException {
        List<User> result = new LinkedList<>();

        try (Connection conn = DBConnection.getConnection();
                Statement statement = conn.createStatement();
                ResultSet rs = statement.executeQuery("SELECT * FROM User");) {
            while (rs.next()) {
                User u = new User();
                u.ID = rs.getInt("ID");
                u.Username = rs.getString("Username");
                u.FirstName = rs.getString("FirstName");
                u.LastName = rs.getString("LastName");
                u.Email = rs.getString("Email");

                result.add(u);
            }
        }

        return result;
    }

    public User saveUser(User u) throws SQLException {

        User result = u;
        if (u.ID == null) {
            // insert user
            try (Connection conn = DBConnection.getConnection();
                    PreparedStatement statement = conn.prepareStatement("INSERT INTO User VALUES (NULL, ?, ?, ?, ?, ?, NULL)");) {
                statement.setString(1, u.Username);
                statement.setString(2, u.Password);
                statement.setString(3, u.FirstName);
                statement.setString(4, u.LastName);
                statement.setString(5, u.Email);

                statement.execute();
                ResultSet rs = statement.getGeneratedKeys();
                rs.next();
                u.ID = rs.getInt(1);
            }

        } else {
            // update user

            try (Connection conn = DBConnection.getConnection();
                    PreparedStatement statement = conn.prepareStatement("UPDATE User SET Username=?, Password=?, FirstName=?, LastName=?, Email=? WHERE Id=?");) {
                statement.setString(1, u.Username);
                statement.setString(2, u.Password);
                statement.setString(3, u.FirstName);
                statement.setString(4, u.LastName);
                statement.setString(5, u.Email);

                statement.setInt(6, u.ID);

                statement.execute();
            }

        }
        return result;
    }

    public List<User> getUsersByFirstName(String FirstName) throws SQLException {

        List<User> result = new LinkedList<>();

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement statement = conn.prepareStatement("SELECT * FROM User WHERE FirstName=?");) {
            statement.setString(1, FirstName);

            try (ResultSet rs = statement.executeQuery()) {

                while (rs.next()) {
                    User u = new User();
                    u.ID = rs.getInt("ID");
                    u.Username = rs.getString("Username");
                    u.FirstName = rs.getString("FirstName");
                    u.LastName = rs.getString("LastName");
                    u.Email = rs.getString("Email");

                    result.add(u);
                }
            }
        }

        return result;

    }

    public List<User> filter(User user) throws SQLException {

        List<User> result = new LinkedList<>();

        String sql = "SELECT * FROM User WHERE 1=1 ";

        LinkedList list = new LinkedList<>();

        if (user.ID != null) {
            sql += " AND ID=? ";
            list.add(user.ID);
        }

        if (user.FirstName != null) {
            sql += " AND FirstName=? ";
            list.add(user.FirstName);
        }

        if (user.LastName != null) {
            sql += " AND LastName=? ";
            list.add(user.LastName);
        }

        if (user.Username != null) {
            sql += " AND Username=? ";
            list.add(user.Username);
        }

        if (user.Email != null) {
            sql += " AND Email=? ";
            list.add(user.Email);
        }

   

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql);) {

            System.out.println(sql);
            
            int parametarIndex = 1;
            for(Object o:list){
                statement.setObject(parametarIndex++, o);
            }
            
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                User u = new User();
                u.ID = rs.getInt("ID");
                u.Username = rs.getString("Username");
                u.FirstName = rs.getString("FirstName");
                u.LastName = rs.getString("LastName");
                u.Email = rs.getString("Email");

                result.add(u);
            }

        }

        return result;
    }
}
