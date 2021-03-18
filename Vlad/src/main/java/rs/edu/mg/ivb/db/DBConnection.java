package rs.edu.mg.ivb.db;

import java.sql.*;

public class DBConnection {
    // SQLite
    //public final static String DB_URL = "src/main/resources/rs/edu/mg/ivb/db/vladDB.db";
    //public final static String CONNECTION_STRING = "jdbc:sqlite:"+DB_URL;
    
    //MySQL
    public final static String DB_URL = "";
    public final static String CONNECTION_STRING = "jdbc:mysql:"+DB_URL;

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(CONNECTION_STRING);
    }
    
}
