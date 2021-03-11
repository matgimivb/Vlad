package rs.edu.mg.ivb;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import rs.edu.mg.ivb.db.dao.User;
import rs.edu.mg.ivb.db.repository.UserRepository;
import rs.edu.mg.ivb.util.SHA512;

public class LoginController implements Initializable {

    @FXML
    private JFXTextField UsernameEmailTextField;
    @FXML
    private JFXPasswordField PasswordTextField;
    @FXML
    private Label NotValidLoginLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void OnActionUsernameEmailTextField(KeyEvent event) {
        NotValidLoginLabel.setVisible(false);
    }

    @FXML
    private void OnActionPasswordTextField(KeyEvent event) {
        NotValidLoginLabel.setVisible(false);
    }

    @FXML
    private void OnLoginBtnClick(ActionEvent event) throws SQLException, IOException {
        UserRepository userRepository = new UserRepository();

        String hashPasword = SHA512.encryptString(PasswordTextField.getText());
        System.out.println(hashPasword);
        User user = userRepository.getUserByLoginParam(UsernameEmailTextField.getText(), hashPasword);

        if (user != null) {
            App.setRoot("secondary");
        } else {
            PasswordTextField.setText("");
            NotValidLoginLabel.setVisible(true);
        }
    }

}
