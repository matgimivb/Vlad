module rs.edu.mg.ivb {
    requires javafx.controls;
    requires javafx.fxml;

    opens rs.edu.mg.ivb to javafx.fxml;
    exports rs.edu.mg.ivb;
    requires com.jfoenix;
    requires java.base;
}
