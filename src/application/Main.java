package application;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
	//Player player1 = new Player(); 
	@Override
	public void start(Stage primaryStage) {
	 
		
	
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/application/Main.fxml"));
			
			Scene scene = new Scene(root,600,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

	public static void main(String [] args) {
		launch(args);
	}

}
