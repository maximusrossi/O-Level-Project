package me.max.main.windows;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class helpWindow {
	public void window(){
		Stage helpStage = new Stage();
		helpStage.setTitle("QuizTime - Help");
		helpStage.initModality(Modality.APPLICATION_MODAL);
		
		Label help1Label = new Label("1) If 'Top Scores' button doesn't open, please restart the program. This only happens on the first time.\n"
				+ "2) If 'Top Scores' opens up an error window, make sure at least one game has been played.");
		
		Button closeButton = new Button("Back");
		closeButton.setOnAction(e -> helpStage.close());
		
		VBox layout = new VBox();
		layout.setSpacing(10);
		layout.setPadding(new Insets(10, 10, 10, 10));
		layout.setAlignment(Pos.CENTER);
		layout.getChildren().addAll(help1Label, closeButton);
		
		Scene helpScene = new Scene(layout, 560, 250);
		helpStage.setScene(helpScene);
		helpStage.show();
	}
}
