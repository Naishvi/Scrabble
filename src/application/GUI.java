package application;

import javafx.application.Application;
import javafx.stage.Stage;

public class GUI extends Application {

	public GUI() {
		start();
		setTitle("Scrabble");
	}
	
	public void init() {
		GCanvas canvas = new GCanvas();
	}
	
	public static void main(String[] args) {

	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
