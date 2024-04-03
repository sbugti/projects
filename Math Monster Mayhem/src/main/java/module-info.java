module com.group25 {
    requires javafx.controls;
    requires java.desktop;
    requires javafx.fxml;
    requires javafx.media;
    requires transitive javafx.graphics;
    requires com.fasterxml.jackson.databind;

    opens com.group25 to com.fasterxml.jackson.databind; // Open the package to com.fasterxml.jackson.databind module
    opens com.group25.GUI to javafx.fxml;
    opens com.group25.GUI.controller to javafx.fxml;
    
    exports com.group25;
    exports com.group25.GUI;
    exports com.group25.GUI.controller;
}
