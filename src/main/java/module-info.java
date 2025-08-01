module org.example.electionapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.electionapp to javafx.fxml;
    exports org.example.electionapp;
}