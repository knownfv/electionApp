package org.example.electionapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class Election extends Application {
    private ComboBox<String> candidateDropdown;
    private Label votes;
    private Label errorMessage;
    private Map<String, Integer> voteCounts;
    private Label winnerLabel;

    @Override
    public void start(Stage stage) throws Exception {
        // Initialize vote counts
        voteCounts = new HashMap<>();
        voteCounts.put("Liberal", 0);
        voteCounts.put("Conservative", 0);
        voteCounts.put("NDP", 0);
        voteCounts.put("Green Party", 0);

        candidateDropdown = new ComboBox<>();
        candidateDropdown.getItems().addAll("Liberal", "Conservative", "NDP", "Green Party");
        candidateDropdown.setPromptText("Select a candidate:");

        Button voteButton = new Button("Vote");
        Button showWinnerButton = new Button("Show Winner");

        votes = new Label(updateVoteText());
        errorMessage = new Label();
        winnerLabel = new Label();

        voteButton.setOnAction(e -> handleVote());
        showWinnerButton.setOnAction(e -> showWinner());

        VBox layout = new VBox(10, candidateDropdown, voteButton, showWinnerButton, votes, errorMessage, winnerLabel);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(layout, 300, 300);
        stage.setTitle("Student Voting App");
        stage.setScene(scene);
        stage.show();
    }

    private void handleVote() {
        String selectedCandidate = candidateDropdown.getValue();

        if (selectedCandidate == null) {
            errorMessage.setText("❗ Please choose a candidate!");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Vote");
        confirm.setHeaderText(null);
        confirm.setContentText("Are you sure you want to vote for " + selectedCandidate + "?");

        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Increment vote count
                voteCounts.put(selectedCandidate, voteCounts.get(selectedCandidate) + 1);

                candidateDropdown.setValue(null);
                votes.setText(updateVoteText());
                errorMessage.setText("");
                winnerLabel.setText(""); // Clear previous winner on new vote
            }
        });
    }

    private String updateVoteText() {
        return String.format("Votes:\nLiberal: %d\nConservative: %d\nNDP: %d\nGreen Party: %d",
                voteCounts.get("Liberal"),
                voteCounts.get("Conservative"),
                voteCounts.get("NDP"),
                voteCounts.get("Green Party"));
    }


    private void showWinner() {
        int maxVotes = -1;
        String winner = "It's a tie!";
        boolean tie = false;

        for (Map.Entry<String, Integer> entry : voteCounts.entrySet()) {
            int count = entry.getValue();
            if (count > maxVotes) {
                maxVotes = count;
                winner = entry.getKey();
                tie = false;
            } else if (count == maxVotes) {
                tie = true;
            }
        }

        if (tie) {
            winnerLabel.setText("🏆 It's a tie!");
        } else {
            winnerLabel.setText("🏆 Winner: " + winner);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
