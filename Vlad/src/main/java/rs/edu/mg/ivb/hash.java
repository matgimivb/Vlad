package rs.edu.mg.ivb;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import static rs.edu.mg.ivb.SecondaryController.us;
import rs.edu.mg.ivb.db.DBConnection;
import static rs.edu.mg.ivb.storycontroller.us;

public class hash {

    private Stage stage;
    public int IdAudio;

    @FXML
    private JFXButton tagButton;

    @FXML
    private JFXTextArea hash;

    @FXML
    private JFXButton finishButton;

    @FXML
    public void initialize() {

    }

    @FXML
    void addTag(ActionEvent event) {
        try {
            String hashText = hash.getText();
            IdAudio = App.IdAudio;
            Connection conn1 = DBConnection.getConnection();
            PreparedStatement ps = conn1.prepareStatement("INSERT INTO hashtable (IdAudio, Sadrzaj) VALUES (?,?)");
            ps.setInt(1, IdAudio);
            ps.setString(2, hashText);
            ps.execute();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setHeaderText("Hash tag added");
            alert.setContentText("Your hash tag was successfully recorded. You can insert more hashtags if you want.");
            alert.showAndWait();
        } catch (SQLException ex) {
            Logger.getLogger(hash.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void finish(ActionEvent event) {
        stage = App.ps;
        stage.close();
    }

}
