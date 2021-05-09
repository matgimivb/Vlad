/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.edu.mg.ivb;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import rs.edu.mg.ivb.db.DBConnection;
import rs.edu.mg.ivb.db.dao.User;

/**
 *
 * @author lukam
 */
public class accept {
    

    static User us= App.u;
 
    
    
    
    @FXML
    private JFXTextField usernamestring;

    @FXML
    private JFXButton sendbuttnon;
    
    @FXML
    private JFXButton opa;
    
    @FXML
    private AnchorPane itisinajjaci;
    
 
    
    @FXML
    void getstring(ActionEvent event) {

    }
   /*static Pane rofl(String username)
    {Pane s=new Pane();
    s.prefWidth(820);
    s.prefHeight(130);
    Label l=l1; l.setLayoutX(200);l.setLayoutY(45); l.prefWidth(120); l.prefHeight(45);
    Label m=l2; l.setLayoutX(340);l.setLayoutY(45); l.prefWidth(212); l.prefHeight(45);
   l.setText(username);
   JFXButton b2=no;b2.setLayoutX(200);b2.setLayoutY(45);
   JFXButton b1=yes;b1.setLayoutX(200);b1.setLayoutY(45); 
  s.getChildren().addAll(l,m,b2,b1);
   
    return s;
    }*/

    @FXML
     void  lol( ActionEvent event) throws SQLException
    {
        
        List<String> odkoga=new LinkedList<>();
         List<String> zakoga=new LinkedList<>();
          List<String> status=new LinkedList<>();
        try ( Connection conn = DBConnection.getConnection();  Statement statement = conn.createStatement();  ) 
        {PreparedStatement ps1=conn.prepareStatement("SELECT zakoga,odkoga,tre FROM prijateljstvo WHERE zakoga=? AND tre='M'");
        ps1.setString(1, us.Username);
        ResultSet rs1=ps1.executeQuery();
        int i=0;
        

        while(rs1.next())
        { 
            String str1=rs1.getString("odkoga");
            String str2=rs1.getString("zakoga");
            itisinajjaci.prefHeight(290+100*i);
            
           
            
                    
             
            /*odkoga.add(rs1.getString("odkoga"));
            zakoga.add(rs1.getString("zakoga"));
            status.add(rs1.getString("tre"));*/
      
            opa.setOpacity(0);
            JFXButton yes = new JFXButton("Yes");
            yes.setLayoutX(580);
            yes.setLayoutY(190+100*i);
            yes.setPrefWidth(80);
            yes.setPrefHeight(50);
            yes.setStyle("-fx-font-size: 24px; -fx-font-weight:Britanic Bold; -fx-background-color: black; -fx-text-fill: #b52020");
            yes.setOnMouseEntered( (javafx.scene.input.MouseEvent me) -> {
                    yes.setStyle("-fx-font-size:  24px; -fx-font-weight:Britanic Bold; -fx-font-weight:  black;  -fx-text-fill: #b52020");

            });
            yes.setOnMouseExited( (javafx.scene.input.MouseEvent me) -> {
                    yes.setStyle("-fx-font-size:  24px; -fx-font-weight:Britanic Bold; -fx-background-color: black; -fx-text-fill: #b52020");

            });

       
            JFXButton no = new JFXButton("No");
            no.setLayoutX(690);
            no.setLayoutY(190+100*i);
            no.setPrefWidth(80);
            no.setPrefHeight(50);
            no.setStyle("-fx-font-size:  24px;-fx-font-weight:Britanic Bold; -fx-background-color: black; -fx-text-fill: #b52020");
            no.setOnMouseEntered( (javafx.scene.input.MouseEvent me) -> {
                    no.setStyle("-fx-font-size:  24px;-fx-font-weight:Britanic Bold; -fx-font-weight:  black;  -fx-text-fill: #b52020");

            });
            no.setOnMouseExited( (javafx.scene.input.MouseEvent me) -> {
                    no.setStyle("-fx-font-size:  24px;-fx-font-weight:Britanic Bold; -fx-background-color: black; -fx-text-fill: #b52020");

            });
            
         
        
         
            Label L=new Label();
            L.setText(rs1.getString("odkoga")+" wants to become friends.");
            L.setStyle("-fx-font-size:  18px;-fx-font-weight:Britanic Bold; -fx-background-color: white; -fx-text-fill: #b52020");
            L.setLayoutX(230);
            L.setLayoutY(190+100*i);
            L.setPrefWidth(320);
            L.setPrefHeight(50);
            itisinajjaci.getChildren().addAll(yes,no,L);
               no.setOnMousePressed((javafx.scene.input.MouseEvent me) ->{
                try {
                    PreparedStatement ps5=conn.prepareStatement("DELETE FROM prijateljstvo WHERE odkoga=? AND zakoga=?");
                    ps5.setString(1,str1);
                    ps5.setString(2,str2);
                    ps5.execute();
                    
                } catch (SQLException ex) {
                    Logger.getLogger(accept.class.getName()).log(Level.SEVERE, null, ex);
                }
           itisinajjaci.getChildren().removeAll(yes,no,L);
            
            }
            );
                             yes.setOnMousePressed((javafx.scene.input.MouseEvent me) ->{
                  
                  try ( Connection conn1= DBConnection.getConnection();    ) 
                   
                  {   PreparedStatement ps4=conn1.prepareStatement("UPDATE prijateljstvo SET  tre='Y' WHERE odkoga=? AND zakoga=?");
                    ps4.setString(1,str1);
                    ps4.setString(2,str2);
                    ps4.execute();
                    
                   
                    
   
                  
           
            
                  } catch (SQLException ex) {
                    Logger.getLogger(accept.class.getName()).log(Level.SEVERE, null, ex);
                }
           itisinajjaci.getChildren().removeAll(yes,no,L);
            
            }
            );
            i++;
            
            
            }}}
        

        
    
    
    
 
        
           

        
    


    @FXML
    void sendreq(ActionEvent event) throws SQLException {
      String usernam=usernamestring.getText();
       try ( Connection conn = DBConnection.getConnection();  Statement statement = conn.createStatement();  ) {
        PreparedStatement ps1=conn.prepareStatement("SELECT odkoga,zakoga From Prijateljstvo P WHERE odkoga=? AND zakoga=?");
        ps1.setString(1, usernam);
        ps1.setString(2, us.Username);
        PreparedStatement ps2=conn.prepareStatement("SELECT odkoga,zakoga From Prijateljstvo P WHERE odkoga=? AND zakoga=?");
        ps2.setString(1, us.Username);
        ps2.setString(2, usernam);
           ResultSet rs1 = ps1.executeQuery(); 
           ResultSet rs2 = ps2.executeQuery();
           
       if(rs1.next() || rs2.next()) 
       {AlertType type=AlertType.INFORMATION;
       Alert alert=new Alert(type,"");
       alert.initModality(Modality.APPLICATION_MODAL);
       alert.getDialogPane().setContentText("You have already sent him a requst or you are already frinds");
       alert.getDialogPane().setHeaderText("Unable to make request");
       alert.showAndWait();
       }
        else 
       {
           PreparedStatement ps3=conn.prepareStatement("SELECT Username From User WHERE Username=?");
           ps3.setString(1,usernam);
           ResultSet rs3 = ps3.executeQuery(); 
          if(rs3.next()) 
           {
         PreparedStatement ps4=conn.prepareStatement("Insert Into Prijateljstvo (odkoga,zakoga,tre)VALUES (?,?,?)");
         ps4.setString(1, us.Username);
         ps4.setString(2, usernam);
         ps4.setString(3, "M");
         ps4.executeUpdate();
         
               AlertType type=AlertType.INFORMATION;
       Alert alert=new Alert(type,"");
       alert.initModality(Modality.APPLICATION_MODAL);
       alert.getDialogPane().setContentText("WOW");
       alert.getDialogPane().setHeaderText("Request is sent");
       alert.showAndWait();
           }
           else {AlertType type=AlertType.INFORMATION;
       Alert alert=new Alert(type,"");
       alert.initModality(Modality.APPLICATION_MODAL);
       alert.getDialogPane().setContentText("You are legit retarded");
       alert.getDialogPane().setHeaderText("Username does not exsist");
       alert.showAndWait();
                      }}
           }



    }
    

}