module de.apaschold.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens de.apaschold.demo to javafx.fxml;
    exports de.apaschold.demo;
    exports de.apaschold.demo.ui;
    opens de.apaschold.demo.ui to javafx.fxml;
    exports de.apaschold.demo.test;
    opens de.apaschold.demo.test to javafx.fxml;
}