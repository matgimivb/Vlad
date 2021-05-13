package rs.edu.mg.ivb.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import rs.edu.mg.ivb.App;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
