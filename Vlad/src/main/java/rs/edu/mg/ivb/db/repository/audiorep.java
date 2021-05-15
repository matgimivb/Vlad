package rs.edu.mg.ivb.db.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import rs.edu.mg.ivb.App;
import rs.edu.mg.ivb.Audio;
import rs.edu.mg.ivb.Objava;
import rs.edu.mg.ivb.db.DBConnection;
import rs.edu.mg.ivb.db.dao.User;

public class audiorep {
    
       static User us = App.u;

    public static List<Audio> getAudio() throws SQLException {
        List<Audio> result = new LinkedList<>();

        try ( Connection conn = DBConnection.getConnection();  Statement statement = conn.createStatement();){
                PreparedStatement ps = conn.prepareStatement("SELECT DISTINCT A.IdAudio,A.Username,A.datum,A.vreme FROM audio A, hashtable H,prijateljstvo P,hashfollow HF WHERE A.Username=? OR (P.odkoga=A.Username AND P.zakoga=? AND P.tre=?) OR (P.zakoga=A.Username AND P.odkoga=? AND P.tre=?) OR (HF.Username=? AND HF.Sadrzaj=H.Sadrzaj AND H.IdAudio=A.IdAudio) ORDER BY datum DESC,vreme DESC "); 
                ps.setString(1,us.Username);
                ps.setString(2,us.Username);
                ps.setString(3,"Y");
                ps.setString(4,us.Username);
                ps.setString(6,us.Username);
                ps.setString(5,"Y");
               ResultSet rs=ps.executeQuery();
                
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

        try ( Connection conn = DBConnection.getConnection();  Statement statement = conn.createStatement(); ){
           PreparedStatement ps = conn.prepareStatement("SELECT DISTINCT A.IdAudio,A.Username,A.datum,A.vreme FROM audio A, hashtable H,prijateljstvo P,hashfollow HF WHERE A.Username=? OR (P.odkoga=A.Username AND P.zakoga=? AND P.tre=?) OR (P.zakoga=A.Username AND P.odkoga=? AND P.tre=?) OR (HF.Username=? AND HF.Sadrzaj=H.Sadrzaj AND H.IdAudio=A.IdAudio) ORDER BY datum DESC,vreme DESC "); 
                ps.setString(1,us.Username);
                ps.setString(2,us.Username);
                ps.setString(3,"Y");
                ps.setString(4,us.Username);
                ps.setString(6,us.Username);
                ps.setString(5,"Y");
                
               ResultSet rs=ps.executeQuery();
            int i=0;
            while(rs.next()) i++;           
                
            return i;
        }
        
    }
}
