/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.edu.mg.ivb;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import rs.edu.mg.ivb.db.DBConnection;
import rs.edu.mg.ivb.db.dao.User;

/**
 *
 * @author matej
 */
public class followed {
    
    static User us = App.u;
    
    @FXML
    private AnchorPane apane;

    @FXML
    private JFXTextField tag;

    @FXML
    private Pane pane;

    @FXML
    void initialize() throws SQLException {
        {
            try ( Connection conn = DBConnection.getConnection();  Statement statement = conn.createStatement();) {
                PreparedStatement ps1 = conn.prepareStatement("SELECT Sadrzaj FROM hashfollow WHERE Username=?");
                ps1.setString(1, us.Username);
                ResultSet rs1 = ps1.executeQuery();
                int i = 0;
                pane.getChildren().clear();

                while (rs1.next()) {
                    String str1 = rs1.getString("Sadrzaj");
                    
                    apane.setMinHeight(290 + 100 * i);
                    apane.setMaxHeight(290 + 100 * i);
                    pane.setMinHeight(130 + 100 * i);
                    pane.setMaxHeight(130 + 100 * i);

                    JFXButton delete = new JFXButton("unfollow");
                    delete.setLayoutX(580);
                    delete.setLayoutY(30 + 100 * i);
                    delete.setPrefWidth(200);
                    delete.setPrefHeight(50);
                    delete.setStyle("-fx-font-size: 24px; -fx-font-weight:Britanic Bold; -fx-background-color: lavender; -fx-text-fill: #8c3add");

                    Label L = new Label();
                    L.setText("#"+str1);
                    L.setStyle("-fx-font-size:  18px;-fx-font-weight:Britanic Bold; -fx-background-color: wheat; -fx-text-fill: darkorange; -fx-font-weight: bold");
                    L.setLayoutX(230);
                    L.setLayoutY(30 + 100 * i);
                    L.setPrefWidth(320);
                    L.setPrefHeight(50);
                    pane.getChildren().addAll(delete, L);


                    delete.setOnMousePressed((javafx.scene.input.MouseEvent me) -> {

                        try ( Connection conn1 = DBConnection.getConnection();) {
                            PreparedStatement ps4 = conn1.prepareStatement("DELETE FROM hashfollow WHERE Sadrzaj=?");
                            ps4.setString(1, str1);
                            ps4.execute();

                        } catch (SQLException ex) {
                            Logger.getLogger(accept.class.getName()).log(Level.SEVERE, null, ex);
                        }
                       pane.getChildren().removeAll(delete, L);

                    }
                    );
                    i++;

                }
            }
        }
    }


    @FXML
    void refresh(ActionEvent event) throws SQLException {
        {

            initialize();
        }
    }

    @FXML
    void followtag(ActionEvent event) throws SQLException {
        String tagic = tag.getText();
        try ( Connection conn = DBConnection.getConnection();  Statement statement = conn.createStatement();) {
            PreparedStatement ps1 = conn.prepareStatement("SELECT * From hashfollow WHERE Username=? AND Sadrzaj=?");
            ps1.setString(1, us.Username);
            ps1.setString(2, tagic);
            ResultSet rs1 = ps1.executeQuery();


            if (rs1.next()) {
                Alert.AlertType type = Alert.AlertType.INFORMATION;
                Alert alert = new Alert(type, "");
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.getDialogPane().setContentText("You have already followed this hashtag");
                alert.getDialogPane().setHeaderText("5 na projekat");
                alert.showAndWait();
            } else {
                
                    PreparedStatement ps4 = conn.prepareStatement("Insert Into hashfollow (Username,Sadrzaj) VALUES (?,?)");
                    ps4.setString(1, us.Username);
                    ps4.setString(2, tagic);
                    ps4.executeUpdate();

                    Alert.AlertType type = Alert.AlertType.INFORMATION;
                    Alert alert = new Alert(type, "");
                    alert.initModality(Modality.APPLICATION_MODAL);
                    alert.getDialogPane().setContentText("You have successfully followed #"+tagic);
                    alert.getDialogPane().setHeaderText("5 na projekat");
                    alert.showAndWait();

            }
        initialize();}

    }
}
