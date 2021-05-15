package rs.edu.mg.ivb.db.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import rs.edu.mg.ivb.Audio;
import rs.edu.mg.ivb.Objava;
import rs.edu.mg.ivb.db.DBConnection;

public class audiorep {

    public static List<Audio> getAudio() throws SQLException {
        List<Audio> result = new LinkedList<>();

        try ( Connection conn = DBConnection.getConnection();  Statement statement = conn.createStatement();  ResultSet rs = statement.executeQuery("SELECT IdAudio,Username,datum,vreme FROM audio ORDER BY datum DESC,vreme DESC  ");) {
            while (rs.next()) {
                Audio o = new Audio();
                o.IdAudio = rs.getInt("IdAudio");
                o.username = rs.getString("Username");
                //       o.status = rs.getString("status");
                //o.zvuk = rs.getBlob("Zvuk");
                o.datum = rs.getDate("datum");
                o.vreme = rs.getTime("vreme");

                result.add(o);
            }
        }

        return result;
    }
    
      public static int getNum() throws SQLException {
        List<Audio> result = new LinkedList<>();

        try ( Connection conn = DBConnection.getConnection();  Statement statement = conn.createStatement();  ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM audio ORDER BY datum DESC,vreme DESC  ");) {
           rs.next();
            int i=rs.getInt(1);
                
            return i;
        }
        
    }
}
