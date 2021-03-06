package rs.edu.mg.ivb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import rs.edu.mg.ivb.db.dao.User;
import rs.edu.mg.ivb.db.repository.objavarep;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    public static User u;
    public static User l;
    public static String k;
    public static String k1;
    public static Stage ps;
    public static int IdAudio;

    @Override
    public void start(Stage stage) throws IOException {
        ps = stage;
        scene = new Scene(loadFXML("Login"));
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
        if (fxml.equals("secondary")) {
            ps.setWidth(1200);
            ps.setHeight(750);
        }

    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        Parent p = fxmlLoader.load();
        return p;
    }

    public static void main(String[] args) {
        launch();
    }

}
