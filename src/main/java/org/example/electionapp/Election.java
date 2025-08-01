package org.example.electionapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Election extends Application {
    private ComboBox<String> candidateDropdown;
    private Label votes;
    private Label errorMessage;

    @Override
    public void start(Stage stage) throws Exception {
        candidateDropdown = new ComboBox<String>();
        candidateDropdown.getItems().addAll("Alice", "Bob", "Charlie");
        candidateDropdown.setPromptText("Select a candidate:");

        Button vote = new Button("Vote");
        votes = new Label();
        errorMessage = new Label();

        vote.setOnAction(e -> handleVote());

        VBox layout = new VBox(10, candidateDropdown, vote, votes, errorMessage);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(layout, 300, 200);
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Student Voting App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleVote() {
        String selectedCandidate = candidateDropdown.getValue();

        if (selectedCandidate == null) {
            errorMessage.setText("Choose a candidate!");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Vote");
        confirm.setHeaderText(null);
        confirm.setContentText("Are you sure you want to vote for " + selectedCandidate + "?");

        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                candidateDropdown.setValue(null);
                votes.setText("You voted for: " + selectedCandidate);
                errorMessage.setText("");
            }
        });
    }

    public static void main(String[] args) {
       launch(args);
    }
}
