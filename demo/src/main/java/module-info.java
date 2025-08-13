module de.apaschold.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens de.apaschold.demo to javafx.fxml;
    exports de.apaschold.demo;
}