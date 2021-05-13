package rs.edu.mg.ivb;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import rs.edu.mg.ivb.db.DBConnection;
import rs.edu.mg.ivb.db.dao.User;

public class storycontroller extends SecondaryController {

    @FXML
    private ScrollPane adagaja;

    @FXML
    private AnchorPane adagajic;

    @FXML
    private JFXButton dislikedugme;

    @FXML
    private JFXButton likedugme;

    @FXML
    private JFXTextArea statusxd;

    @FXML
    private Label numlike;

    @FXML
    private Label numdislike;

    @FXML
    private JFXButton load;

    @FXML
    private JFXTextField time;

    @FXML
    private JFXButton postcomm;
    @FXML
    private JFXTextField upm;
    @FXML
    private Pane neadagajic;

    static User us = App.u;
    static String s;

    @FXML
    public void initialize() throws SQLException {
        int ex = 0;
        int kon = Integer.parseInt(App.k);
        // System.out.println(kon);
        try ( Connection conn = DBConnection.getConnection();  Statement s = conn.createStatement();) {
            PreparedStatement ps1 = conn.prepareStatement("SELECT status, datum, vreme From objava  WHERE Idobj=?");
            ps1.setInt(1, kon);//nzm kako da prosledim objavu
            ResultSet rs1 = ps1.executeQuery();
            rs1.next();
            statusxd.setText(rs1.getString("status"));
            time.setText("Posted on " + rs1.getString("datum") + ", " + rs1.getString("vreme"));
            PreparedStatement ps2 = conn.prepareStatement("SELECT COUNT(*) From LIKES  WHERE Idobj=? AND tre='L'");
            ps2.setInt(1, kon);//nzm kako da prosledim objavu
            ResultSet rs2 = ps2.executeQuery();
            rs2.next();
            int i = rs2.getInt(1);

            numlike.setText("" + i);
            PreparedStatement ps3 = conn.prepareStatement("SELECT COUNT(*) From LIKES  WHERE Idobj=? AND tre='D'");
            ps3.setInt(1, kon);//nzm kako da prosledim objavu
            ResultSet rs3 = ps3.executeQuery();
            rs3.next();
            i = rs3.getInt(1);
            numdislike.setText("" + i);
            PreparedStatement PS = conn.prepareStatement("SELECT * From komentari  WHERE Idobj=?");
            PS.setInt(1, kon);
            ResultSet RS = PS.executeQuery();
            neadagajic.getChildren().clear();
            neadagajic.setMinHeight(165);
            neadagajic.setMaxHeight(165);

            while (RS.next()) {
                JFXTextArea ta = new JFXTextArea();
                ta.setPrefHeight(18);
                ta.setPrefWidth(270);
                ta.setLayoutX(14);
                ta.setLayoutY(14 + 75 * ex);
                ta.setText(RS.getString("comm"));
                neadagajic.getChildren().add(ta);
                neadagajic.setMinHeight(165 + 75 * ex);
                neadagajic.setMaxHeight(165 + 75 * ex);

                adagajic.setMinHeight(826 + 75 * ex);
                adagajic.setMaxHeight(826 + 75 * ex);
                Label l = new Label();
                l.setLayoutX(300);
                l.setLayoutY(18 + 75 * ex);
                l.setText(RS.getString("Username"));
                l.setStyle("-fx-font-size:  14px;-fx-font-weight:Britanic Bold; -fx-background-color: black; -fx-text-fill: #b52020");
                JFXButton b = new JFXButton();
                b.setLayoutX(260);
                b.setLayoutY(1 + 75 * ex);
                b.setText("DELETE");
                b.setStyle("-fx-font-size: 8px; -fx-font-weight:Britanic Bold; -fx-background-color: black; -fx-text-fill: #b52020");
                neadagajic.getChildren().addAll(b, l);
                ex++;
                int rea = RS.getInt("idcom");
                if ((us.Username).equals(RS.getString("Username"))) {
                    b.setOnMousePressed((javafx.scene.input.MouseEvent me) -> {

                        try {
                            Connection conn1 = DBConnection.getConnection();
                            PreparedStatement ps5 = conn1.prepareStatement("DELETE FROM komentari WHERE idcom=?");
                            ps5.setInt(1, rea);
                            ps5.execute();
                            neadagajic.getChildren().removeAll(ta, b, l);
                        } catch (SQLException ex1) {
                            Logger.getLogger(storycontroller.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                    });
                } else {
                    b.setOpacity(0);
                }
            }

        }
        // System.out.println(s);
        //System.out.println(App.k);
        //  if(App.k==null) initialize();

    }

    //adagaja
    @FXML
    void dislike(ActionEvent event) throws SQLException {
        int kon = Integer.parseInt(App.k);
        try ( Connection conn = DBConnection.getConnection();  Statement s = conn.createStatement();) {
            PreparedStatement PSX = conn.prepareStatement("SELECT Username From objava WHERE Idobj=?");
            PSX.setInt(1, kon);
            ResultSet RSX = PSX.executeQuery();
            RSX.next();
            String marence = RSX.getString("Username");
            PreparedStatement PSX1 = conn.prepareStatement("SELECT * From prijateljstvo WHERE odkoga=? AND zakoga=? AND tre='Y'");
            PSX1.setString(1, marence);
            PSX1.setString(2, us.Username);
            ResultSet RSX1 = PSX1.executeQuery();
            boolean xd = RSX1.next();
            PSX1.setString(1, us.Username);
            PSX1.setString(2, marence);
            ResultSet RSX2 = PSX1.executeQuery();
            boolean rofl = RSX2.next();
            //System.out.println(marence);
            //System.out.println(us.Username);
            if (rofl || xd || us.Username.equals(marence)) {
                PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM likes L WHERE L.Username=? AND L.Idobj=?");
                ps1.setString(1, us.Username);
                ps1.setInt(2, kon);
                ResultSet rs1 = ps1.executeQuery();
                if (rs1.next()) {
                    if (rs1.getString("tre").equals("D")) {

                        PreparedStatement ps2 = conn.prepareStatement("DELETE FROM likes WHERE Username=? AND Idobj=?");
                        ps2.setString(1, us.Username);
                        ps2.setInt(2, kon);
                        ps2.execute();

                    } else {

                        PreparedStatement ps3 = conn.prepareStatement("UPDATE likes SET tre='D' WHERE Username=? AND Idobj=?");
                        ps3.setString(1, us.Username);
                        ps3.setInt(2, kon);
                        ps3.execute();
                    }
                } else {
                    PreparedStatement ps4 = conn.prepareStatement("INSERT INTO likes(Idobj,Username,tre) VALUES (?,?,?)");
                    ps4.setInt(1, kon);
                    ps4.setString(2, us.Username);
                    ps4.setString(3, "D");
                    ps4.execute();
                }

                PreparedStatement ps2 = conn.prepareStatement("SELECT COUNT(*) From LIKES  WHERE Idobj=? AND tre='L'");
                ps2.setInt(1, kon);//nzm kako da prosledim objavu
                ResultSet rs2 = ps2.executeQuery();
                rs2.next();
                int i = rs2.getInt(1);

                numlike.setText("" + i);
                PreparedStatement ps3 = conn.prepareStatement("SELECT COUNT(*) From LIKES  WHERE Idobj=? AND tre='D'");
                ps3.setInt(1, kon);//nzm kako da prosledim objavu
                ResultSet rs3 = ps3.executeQuery();
                rs3.next();
                i = rs3.getInt(1);
                numdislike.setText("" + i);
            } else {
                Alert.AlertType type = Alert.AlertType.INFORMATION;
                Alert alert = new Alert(type, "");
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.getDialogPane().setContentText("What are u doing here");
                alert.getDialogPane().setHeaderText("You are not friends");
                alert.showAndWait();
            }

        }
    }

    @FXML
    void like(ActionEvent event) throws SQLException {
        int kon = Integer.parseInt(App.k);
        try ( Connection conn = DBConnection.getConnection();  Statement s = conn.createStatement();) {
            PreparedStatement PSX = conn.prepareStatement("SELECT Username From objava WHERE Idobj=?");
            PSX.setInt(1, kon);
            ResultSet RSX = PSX.executeQuery();
            RSX.next();
            String marence = RSX.getString("Username");
            PreparedStatement PSX1 = conn.prepareStatement("SELECT * From prijateljstvo WHERE odkoga=? AND zakoga=? AND tre='Y'");
            PSX1.setString(1, marence);
            PSX1.setString(2, us.Username);
            ResultSet RSX1 = PSX1.executeQuery();
            boolean xd = RSX1.next();
            PSX1.setString(1, us.Username);
            PSX1.setString(2, marence);
            ResultSet RSX2 = PSX1.executeQuery();
            boolean rofl = RSX2.next();
            // System.out.println(marence);
            //System.out.println(us.Username);
            if (rofl || xd || us.Username.equals(marence)) {

                PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM likes L WHERE L.Username=? AND L.Idobj=?");
                ps1.setString(1, us.Username);
                ps1.setInt(2, kon);
                ResultSet rs1 = ps1.executeQuery();
                if (rs1.next()) {
                    //System.out.println("da"+rs1.getString("tre"));

                    if (rs1.getString("tre").equals("L")) {

                        PreparedStatement ps2 = conn.prepareStatement("DELETE FROM likes WHERE Username=? AND Idobj=?");
                        ps2.setString(1, us.Username);
                        ps2.setInt(2, kon);
                        ps2.execute();

                    } else {

                        PreparedStatement ps3 = conn.prepareStatement("UPDATE likes SET tre='L' WHERE Username=? AND Idobj=?");
                        ps3.setString(1, us.Username);
                        ps3.setInt(2, kon);
                        ps3.execute();
                    }
                } else {
                    PreparedStatement ps4 = conn.prepareStatement("INSERT INTO likes(Idobj,Username,tre) VALUES (?,?,?)");
                    ps4.setInt(1, kon);
                    ps4.setString(2, us.Username);
                    ps4.setString(3, "L");
                    ps4.execute();
                }

                PreparedStatement ps2 = conn.prepareStatement("SELECT COUNT(*) From LIKES  WHERE Idobj=? AND tre='L'");
                ps2.setInt(1, kon);
                ResultSet rs2 = ps2.executeQuery();
                rs2.next();
                int i = rs2.getInt(1);

                numlike.setText("" + i);
                PreparedStatement ps3 = conn.prepareStatement("SELECT COUNT(*) From LIKES  WHERE Idobj=? AND tre='D'");
                ps3.setInt(1, kon);
                ResultSet rs3 = ps3.executeQuery();
                rs3.next();
                i = rs3.getInt(1);
                numdislike.setText("" + i);
            } else {
                Alert.AlertType type = Alert.AlertType.INFORMATION;
                Alert alert = new Alert(type, "");
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.getDialogPane().setContentText("What are u doing here");
                alert.getDialogPane().setHeaderText("You are not friends");
                alert.showAndWait();
            }
        }
    }

    @FXML
    void loadsto(ActionEvent event) throws SQLException {
        //System.out.println(App.k);
        int ex = 0;
        int kon = Integer.parseInt(App.k);
        System.out.println(kon);
        try ( Connection conn = DBConnection.getConnection();  Statement s = conn.createStatement();) {
            PreparedStatement ps1 = conn.prepareStatement("SELECT status From objava  WHERE Idobj=?");
            ps1.setInt(1, kon);
            ResultSet rs1 = ps1.executeQuery();
            rs1.next();
            statusxd.setText(rs1.getString("status"));
            PreparedStatement ps2 = conn.prepareStatement("SELECT COUNT(*) From LIKES  WHERE Idobj=? AND tre='L'");
            ps2.setInt(1, kon);
            ResultSet rs2 = ps2.executeQuery();
            rs2.next();
            int i = rs2.getInt(1);

            numlike.setText("" + i);
            PreparedStatement ps3 = conn.prepareStatement("SELECT COUNT(*) From LIKES  WHERE Idobj=? AND tre='D'");
            ps3.setInt(1, kon);
            ResultSet rs3 = ps3.executeQuery();
            rs3.next();
            i = rs3.getInt(1);
            numdislike.setText("" + i);
            PreparedStatement PS = conn.prepareStatement("SELECT * From komentari  WHERE Idobj=?");
            PS.setInt(1, kon);
            ResultSet RS = PS.executeQuery();
            neadagajic.getChildren().clear();
            neadagajic.setMinHeight(165);
            neadagajic.setMaxHeight(165);

            while (RS.next()) {
                JFXTextArea ta = new JFXTextArea();
                ta.setPrefHeight(18);
                ta.setPrefWidth(270);
                ta.setLayoutX(14);
                ta.setLayoutY(14 + 75 * ex);
                ta.setText(RS.getString("comm"));
                neadagajic.getChildren().add(ta);
                neadagajic.setMinHeight(165 + 75 * ex);
                neadagajic.setMaxHeight(165 + 75 * ex);

                adagajic.setMinHeight(826 + 75 * ex);
                adagajic.setMaxHeight(826 + 75 * ex);
                Label l = new Label();
                l.setLayoutX(300);
                l.setLayoutY(18 + 75 * ex);
                l.setText(RS.getString("Username"));
                l.setStyle("-fx-font-size:  14px;-fx-font-weight:Britanic Bold; -fx-background-color: black; -fx-text-fill: #b52020");
                JFXButton b = new JFXButton();
                b.setLayoutX(260);
                b.setLayoutY(1 + 75 * ex);
                b.setText("DELETE");
                b.setStyle("-fx-font-size: 8px; -fx-font-weight:Britanic Bold; -fx-background-color: black; -fx-text-fill: #b52020");
                neadagajic.getChildren().addAll(b, l);
                ex++;
                int rea = RS.getInt("idcom");
                if ((us.Username).equals(RS.getString("Username"))) {
                    b.setOnMousePressed((javafx.scene.input.MouseEvent me) -> {

                        try {
                            Connection conn1 = DBConnection.getConnection();
                            PreparedStatement ps5 = conn1.prepareStatement("DELETE FROM komentari WHERE idcom=?");
                            ps5.setInt(1, rea);
                            ps5.execute();
                            neadagajic.getChildren().removeAll(ta, b, l);
                        } catch (SQLException ex1) {
                            Logger.getLogger(storycontroller.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                    });
                } else {
                    b.setOpacity(0);
                }
            }

        }

    }

    @FXML
    void postlol(ActionEvent event) throws SQLException {
        int kon = Integer.parseInt(App.k);
        String str = upm.getText();
        try ( Connection conn = DBConnection.getConnection();  Statement s = conn.createStatement();) {
            PreparedStatement PSX = conn.prepareStatement("SELECT Username From objava WHERE Idobj=?");
            PSX.setInt(1, kon);
            ResultSet RSX = PSX.executeQuery();
            RSX.next();
            String marence = RSX.getString("Username");
            PreparedStatement PSX1 = conn.prepareStatement("SELECT * From prijateljstvo WHERE odkoga=? AND zakoga=? AND tre='Y'");
            PSX1.setString(1, marence);
            PSX1.setString(2, us.Username);
            ResultSet RSX1 = PSX1.executeQuery();
            boolean xd = RSX1.next();
            PSX1.setString(1, us.Username);
            PSX1.setString(2, marence);
            ResultSet RSX2 = PSX1.executeQuery();
            boolean rofl = RSX2.next();
            // System.out.println(marence);
            //System.out.println(us.Username);
            if (rofl || xd || us.Username.equals(marence)) {
                PreparedStatement ps1 = conn.prepareStatement("INSERT INTO komentari(Idobj,Username,comm,idcom) Values(?,?,?,? )");
                ResultSet rs = s.executeQuery("Select Max(idcom)+1 From komentari");
                rs.next();
                int x = rs.getInt(1);
                ps1.setInt(4, rs.getInt(1));
                ps1.setInt(1, kon);
                ps1.setString(2, us.Username);
                ps1.setString(3, str);
                ps1.execute();
                JFXTextArea ta = new JFXTextArea();
                ta.setPrefHeight(18);
                ta.setPrefWidth(270);
                ta.setLayoutX(14);
                ta.setLayoutY(14);
                ta.setText(str);
                Label r = new Label();
                r.setLayoutX(300);
                r.setLayoutY(18);
                r.setText(us.Username);
                r.setStyle("-fx-font-size:  14px;-fx-font-weight:Britanic Bold; -fx-background-color: black; -fx-text-fill: #b52020");
                JFXButton c = new JFXButton();
                c.setLayoutX(260);
                c.setLayoutY(1);
                c.setText("DELETE");
                c.setStyle("-fx-font-size: 6px; -fx-font-weight:Britanic Bold; -fx-background-color: black; -fx-text-fill: #b52020");
                neadagajic.getChildren().clear();
                neadagajic.setPrefHeight(153);
                adagajic.setPrefHeight(583);
                /*       neadagajic.getChildren().addAll(ta,c,r);

         r.setOnMousePressed((javafx.scene.input.MouseEvent me) ->{
         
            try {
                Connection conn1 = DBConnection.getConnection(); 

                PreparedStatement ps4=conn.prepareStatement("DELETE FROM komentari WHERE idcom=?");
                ps4.setInt(1, x);
                ps4.execute();
                 neadagajic.getChildren().removeAll(ta,c,r);
            } catch (SQLException ex1) {
                Logger.getLogger(storycontroller.class.getName()).log(Level.SEVERE, null, ex1);
            }
});*/

                int ex = 0;
                PreparedStatement PS = conn.prepareStatement("SELECT * From komentari  WHERE Idobj=?");
                PS.setInt(1, kon);
                ResultSet RS = PS.executeQuery();

                while (RS.next()) {
                    JFXTextArea t = new JFXTextArea();
                    t.setPrefHeight(18);
                    t.setPrefWidth(270);
                    t.setLayoutX(14);
                    t.setLayoutY(14 + 75 * ex);
                    t.setText(RS.getString("comm"));
                    int y;
                    y = RS.getInt("idcom");
                    neadagajic.getChildren().add(t);
                    neadagajic.setPrefHeight(153 + 75 * ex);
                    adagajic.setMinHeight(826 + 75 * ex);
                    adagajic.setMaxHeight(826 + 75 * ex);
                    Label l = new Label();
                    l.setLayoutX(300);
                    l.setLayoutY(18 + 75 * ex);
                    l.setText(RS.getString("Username"));
                    l.setStyle("-fx-font-size:  14px;-fx-font-weight:Britanic Bold; -fx-background-color: black; -fx-text-fill: #b52020");
                    JFXButton b = new JFXButton();
                    b.setLayoutX(260);
                    b.setLayoutY(1 + 75 * ex);
                    b.setText("DELETE");
                    b.setStyle("-fx-font-size: 8px; -fx-font-weight:Britanic Bold; -fx-background-color: black; -fx-text-fill: #b52020");

                    neadagajic.getChildren().addAll(b, l);
                    ex++;
                    if ((us.Username).equals(RS.getString("Username"))) {
                        b.setOnMousePressed((javafx.scene.input.MouseEvent me) -> {

                            try {
                                Connection conn1 = DBConnection.getConnection();
                                String edited = t.getText();
                                PreparedStatement ps5 = conn1.prepareStatement("DELETE FROM komentari WHERE idcom=?");
                                ps5.setInt(1, y);
                                ps5.execute();
                                neadagajic.getChildren().removeAll(t, b, l);
                            } catch (SQLException ex1) {
                                Logger.getLogger(storycontroller.class.getName()).log(Level.SEVERE, null, ex1);
                            }
                        });
                    } else {
                        b.setOpacity(0);
                    }
                }

            } else {
                Alert.AlertType type = Alert.AlertType.INFORMATION;
                Alert alert = new Alert(type, "");
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.getDialogPane().setContentText("What are u doing here");
                alert.getDialogPane().setHeaderText("You are not friends");
                alert.showAndWait();
            }

        }
    }
}
