package rs.edu.mg.ivb;

import java.io.IOException;
import javafx.fxml.FXML;
import rs.edu.mg.ivb.App;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}