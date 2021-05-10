/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.edu.mg.ivb;

import com.jfoenix.controls.JFXButton;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import rs.edu.mg.ivb.db.DBConnection;
import rs.edu.mg.ivb.db.dao.User;
import rs.edu.mg.ivb.db.repository.objavarep;

/**
 *
 * @author lukam
 */


public class SecondaryController {

    @FXML
    private TextArea text;

    @FXML
    private JFXButton postBtn;

    @FXML
    private ScrollPane scroll;

    @FXML
    private AnchorPane randompane;

    @FXML
    private JFXButton sendbtn;

    @FXML
    public static JFXButton story;
     
    static User us= App.u;
     
     
     

    @FXML
    public void initialize() throws SQLException
    {{
        int i=0;
        Connection conn1= DBConnection.getConnection();  Statement statement = conn1.createStatement();
        List <Objava> obj;
      
        obj = objavarep.getObjava();
       // randompane.setPrefHeight(100);
       for(i=0;i<obj.size();i++)
       {
           SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis()-60*60*24*1000);
            Date d1=obj.get(i).datum;
            if(date.compareTo(d1)>0) 
            {PreparedStatement ps=conn1.prepareStatement("DELETE FROM objava WHERE Idobj=?");
            ps.setInt(1, obj.get(i).Idobj);
            ps.execute();}
            else if(date.compareTo(d1)==0)
            {SimpleDateFormat formatter1= new SimpleDateFormat("HH:mm:ss");
            Date date1 = new Date(System.currentTimeMillis());
            Time d11=obj.get(i).vreme;
            if(d11.compareTo(date1)<0)  
          {PreparedStatement ps=conn1.prepareStatement("DELETE objava WHERE Idobj=?");
            ps.setInt(1, obj.get(i).Idobj);}
            }} 
      // randompane.setMinWidth(1000);
       //randompane.setMaxWidth(1000);
       for(i=0;i<obj.size();i++)
        {
            Circle C=new Circle (40);
            C.setFill(Color.BLACK);
            C.setLayoutX(50+100*(obj.size()-i-1));
            C.setLayoutY(50.0);
        
           
           
          //  randompane.prefWidth(100*(i+1));
            randompane.setMinWidth(100*(i+1));
            randompane.setMaxWidth(100*(i+1));
            Label L=new Label();
            L.setText(obj.get(i).username);
            L.setStyle("-fx-font-size:  18px;-fx-font-weight:Britanic Bold; -fx-background-color: black; -fx-text-fill: #b52020");
            L.setLayoutX(11+100*(obj.size()-i-1));
            L.setLayoutY(100);
            L.setPrefWidth(80);
            L.setPrefHeight(30);
            L.setTextAlignment(TextAlignment.CENTER);
            Label M=new Label();
            M.setText(obj.get(i).Idobj+"");
            M.setOpacity(0);
             randompane.getChildren().addAll(C,L,M);
             C.setOnMousePressed((javafx.scene.input.MouseEvent me) ->{
                  try { 
                    //  System.out.println(L.getText());
                 FXMLLoader FxmlLoader = new FXMLLoader(SecondaryController.this.getClass().getResource("story.fxml"));
                    Parent root1;
                                       App.k=M.getText();
               
                    root1 = (Parent) FxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root1));
                    stage.show();


                  }
                    catch (IOException ex) {
                    Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
        }});
                     }}}
    @FXML
    void liststory(ActionEvent event) throws SQLException {
        int i=0;
        Connection conn1= DBConnection.getConnection();  Statement statement = conn1.createStatement();
        List <Objava> obj;
      
        obj = objavarep.getObjava();
       // randompane.setPrefHeight(100);
       randompane.getChildren().clear();
       for(i=0;i<obj.size();i++)
       {
           SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis()-60*60*24*1000);
            Date d1=obj.get(i).datum;
            if(date.compareTo(d1)>0) 
            {PreparedStatement ps=conn1.prepareStatement("DELETE FROM objava WHERE Idobj=?");
            ps.setInt(1, obj.get(i).Idobj);
            ps.execute();}
            else if(date.compareTo(d1)==0)
            {SimpleDateFormat formatter1= new SimpleDateFormat("HH:mm:ss");
            Date date1 = new Date(System.currentTimeMillis());
            Time d11=obj.get(i).vreme;
            if(d11.compareTo(date1)<0)  
          {PreparedStatement ps=conn1.prepareStatement("DELETE objava WHERE Idobj=?");
            ps.setInt(1, obj.get(i).Idobj);}
            }} 
       randompane.getChildren().clear();

      for(i=0;i<obj.size();i++)
        {
            Circle C=new Circle (40);
            C.setFill(Color.BLACK);
            C.setLayoutX(50+100*(obj.size()-i-1));
            C.setLayoutY(50.0);
        
           
           
          //  randompane.prefWidth(100*(i+1));
            randompane.setMinWidth(100*(i+1));
            randompane.setMaxWidth(100*(i+1));
            Label L=new Label();
            L.setText(obj.get(i).username);
            L.setStyle("-fx-font-size:  18px;-fx-font-weight:Britanic Bold; -fx-background-color: black; -fx-text-fill: #b52020");
            L.setLayoutX(11+100*(obj.size()-i-1));
            L.setLayoutY(100);
            L.setPrefWidth(80);
            L.setPrefHeight(30);
            L.setTextAlignment(TextAlignment.CENTER);
            Label M=new Label();
            M.setText(obj.get(i).Idobj+"");
            M.setOpacity(0);
             randompane.getChildren().addAll(C,L,M);
             C.setOnMousePressed((javafx.scene.input.MouseEvent me) ->{
                  try { 
                    //  System.out.println(L.getText());
                 FXMLLoader FxmlLoader = new FXMLLoader(SecondaryController.this.getClass().getResource("story.fxml"));
                    Parent root1;
                                       App.k=M.getText();
               
                    root1 = (Parent) FxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root1));
                    stage.show();


                  }
                    catch (IOException ex) {
                    Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
        }});
                     
                     }}
    
    private final FileChooser fileChooser = new FileChooser();
    private static byte[] audioByte = null;
    
    private static void configureFileChooser(
        final FileChooser fileChooser) {      
            fileChooser.setTitle("View Pictures");
            fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
            );                 
            fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac")
            );
    }
    
    @FXML
    void uploadAudio(ActionEvent event) {
         
                    
                    configureFileChooser(fileChooser);
                    Stage primaryStage = new Stage();
                    File file = fileChooser.showOpenDialog(primaryStage);
                    if (file != null) {
                        
                        try {
                            FileInputStream fis = new FileInputStream(file);
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            byte[] buf = new byte[1024];
                            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                                bos.write(buf, 0, readNum);
                            }
                            audioByte = bos.toByteArray();
                            Connection conn1= DBConnection.getConnection();  
                            PreparedStatement ps=conn1.prepareStatement("INSERT INTO audio (Zvuk, Username) VALUES (?,?)");
                            ps.setBytes(1,audioByte);
                            ps.setString(2,us.Username);
                            ps.execute();
                        } catch (Exception ex) {
                            System.out.println(audioByte);
                            System.out.println(ex);
                        }
                    }
            }
    
    
    @FXML
    void newPost(ActionEvent event) {

        try {
            /*Circle C=new Circle (40);
            C.setFill(Color.BLACK);
            C.setLayoutX(150.0);//50+100x
            C.setLayoutY(50.0);
            randompane.getChildren().add(C);*/
            //ako je nesto napisano
            String u=text.getText();
            Connection conn1= DBConnection.getConnection();  Statement statement = conn1.createStatement();
            
            PreparedStatement ps=conn1.prepareStatement("INSERT INTO Objava (Idobj,status,Username,datum,vreme) VALUES (?,?,?,?,?)");
            ResultSet rs=statement.executeQuery("SELECT MAX(Idobj)+1 FROM objava");
            rs.next();
            int i=rs.getInt(1);
            ps.setInt(1,i);
            ps.setString(2,text.getText());
            ps.setString(3,us.Username);
            SimpleDateFormat formatter1= new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());
            ps.setString(4,formatter1.format(date));
            SimpleDateFormat formatter= new SimpleDateFormat("HH:mm:ss");
            ps.setString(5,formatter.format(date) );
            ps.execute();
            i=0;
        List <Objava> obj;
      
        obj = objavarep.getObjava();
        randompane.setPrefHeight(100);
       for(i=0;i<obj.size();i++)
        {
            Circle C=new Circle (40);
            C.setFill(Color.BLACK);
            C.setLayoutX(50+100*(obj.size()-i-1));
            C.setLayoutY(50.0);
        
           
           
          //  randompane.prefWidth(100*(i+1));
            randompane.setMinWidth(100*(i+1));
            randompane.setMaxWidth(100*(i+1));
            Label L=new Label();
            L.setText(obj.get(i).username);
            L.setStyle("-fx-font-size:  18px;-fx-font-weight:Britanic Bold; -fx-background-color: black; -fx-text-fill: #b52020");
            L.setLayoutX(11+100*(obj.size()-i-1));
            L.setLayoutY(100);
            L.setPrefWidth(80);
            L.setPrefHeight(30);
            L.setTextAlignment(TextAlignment.CENTER);
            Label M=new Label();
            M.setText(obj.get(i).Idobj+"");
            M.setOpacity(0);
             randompane.getChildren().addAll(C,L,M);
             C.setOnMousePressed((javafx.scene.input.MouseEvent me) ->{
                  try { 
                    //  System.out.println(L.getText());
                 FXMLLoader FxmlLoader = new FXMLLoader(SecondaryController.this.getClass().getResource("story.fxml"));
                    Parent root1;
                                       App.k=M.getText();
               
                    root1 = (Parent) FxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root1));
                    stage.show();


                  }
                    catch (IOException ex) {
                    Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
        }});
                     
                     }
            
          
           
        } catch (SQLException ex) {
            Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void send(ActionEvent event) throws IOException {
 FXMLLoader FxmlLoader = new FXMLLoader(getClass().getResource("acc.fxml"));
         Parent root1= (Parent) FxmlLoader.load();
         Stage stage = new Stage();
          stage.setScene(new Scene(root1));
          
         stage.show();

    }

}


