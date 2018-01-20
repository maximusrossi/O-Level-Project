package me.max.main.windows;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class errorWindow {
	public void generalErrors(String errorStatus) {
		Stage errorStage = new Stage();
		errorStage.setTitle("QuizTime - Error");
		errorStage.initModality(Modality.APPLICATION_MODAL);

		Label errorLabel = new Label("");

		switch (errorStatus) {
			case "nonameError": errorLabel.setText("Please enter a valid username."); break;
			case "fileError": errorLabel.setText("Program encountered an file error."); break;
			case "nothingSelectedError": errorLabel.setText("Please select a question."); break;
			case "twoOrMoreSelectedError": errorLabel.setText("Only one answer may be selected."); break;
			case "usernameLengthError": errorLabel.setText("Username is exceeds character limit."); break;
			case "sumScoreError": errorLabel.setText("Error when summing score."); break;

		}

		Button backButton = new Button("Back");
		backButton.setOnAction(e -> errorStage.close());

		VBox layout = new VBox();
		layout.getChildren().addAll(errorLabel, backButton);
		layout.setAlignment(Pos.CENTER);
		layout.setSpacing(20);

		Scene errorScene = new Scene(layout, 260, 150);
		errorStage.setScene(errorScene);
		errorStage.show();
	}

}
