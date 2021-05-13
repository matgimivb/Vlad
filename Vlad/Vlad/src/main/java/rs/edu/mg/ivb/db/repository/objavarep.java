/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.edu.mg.ivb.db.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.LinkedList;
import java.util.List;
import javafx.scene.image.ImageView;
import rs.edu.mg.ivb.Objava;
import rs.edu.mg.ivb.db.DBConnection;

/**
 *
 * @author lukam
 */
public class objavarep {

    public static List<Objava> getObjava() throws SQLException {
        List<Objava> result = new LinkedList<>();

        try ( Connection conn = DBConnection.getConnection();  Statement statement = conn.createStatement();  ResultSet rs = statement.executeQuery("SELECT * FROM objava ");) {
            while (rs.next()) {
                Objava o = new Objava();
                o.Idobj = rs.getInt("Idobj");
                o.username = rs.getString("Username");
                o.status = rs.getString("status");
                o.datum = rs.getDate("datum");
                o.vreme = rs.getTime("vreme");

                result.add(o);
            }
        }

        return result;
    }

    public void deleteobjava() throws SQLException {
        try ( Connection conn = DBConnection.getConnection();  Statement statement = conn.createStatement();  ResultSet rs = statement.executeQuery("SELECT * FROM objava");) {
            while (rs.next()) {
                Objava o = new Objava();
                o.Idobj = rs.getInt("Idobj");
                o.username = rs.getString("Username");
                o.status = rs.getString("status");
                o.datum = rs.getDate("datum");
                o.vreme = rs.getTime("vreme");

            }
        }

    }

}
