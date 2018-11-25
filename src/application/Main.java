package application;

import java.util.HashMap;

import core.LetterBag;
import core.Player;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Main extends Application {
	Dictionary dictionary = new Dictionary(); 
	LetterBag letters = new LetterBag(); 
	Player player = new Player(); 
	public static final Font UNIVERSAL_FONT = Font.font("Berlin Sans FB", 14);
	public static final Font UNIVERSAL_FONT_BOLD = Font.font("Berlin Sans FB", FontWeight.BOLD, 14);
	public static final Insets LETTER_INSETS = new Insets(2, 2, 2, 2);
	public static final Insets OPTION_BUTTON_INSETS = new Insets(5, 5, 5, 5);
	public static HashMap<Character, String> letterImage; 
	//boolean start = true; 
	char [] PlayerLetters = new char[7]; 

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("Scrabble Game");

		Group root = new Group();

		HBox windowHBox = new HBox();
		VBox leftVBox = new VBox();
		GridPane boardGrid = new GridPane();
		HBox tfOkCancel = new HBox();
		TextField userInput = new TextField();
		Button okBtn = new Button("OK");
		Button cancelBtn = new Button("Cancel");
		tfOkCancel.getChildren().addAll(userInput, okBtn, cancelBtn); // adding
		GridPane playerHand = new GridPane();
		leftVBox.getChildren().addAll(boardGrid, tfOkCancel, playerHand); // adding
		VBox rightVBox = new VBox();
		GridPane scorePanel = new GridPane();
		TextArea instructionDisplay = new TextArea();
		GridPane optionBtns = new GridPane();
		Button playTurnBtn = new Button("Play");
		Button exchange1Btn = new Button("Exchange 1");
		Button exchangeAllBtn = new Button("Exchange all");
		Button skipTurnBtn = new Button("Skip");
		Button quitBtn = new Button("Quit");
		optionBtns.add(playTurnBtn, 0,0);
		optionBtns.add(exchange1Btn, 1,0);
		optionBtns.add(exchangeAllBtn, 0,1);
		optionBtns.add(skipTurnBtn, 1,1);
		rightVBox.getChildren().addAll(scorePanel, instructionDisplay, optionBtns, quitBtn); // adding
		windowHBox.getChildren().addAll(leftVBox, rightVBox); // adding

		// BOARD
		VBox.setMargin(boardGrid, new Insets(10, 10, 10, 10));
		boardGrid.setMaxSize(400, 400);
		boardGrid.setPrefWidth(400);
		boardGrid.setPrefHeight(400);
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				ImageView img = new ImageView("clear_square.png");
				img.setFitWidth(30);
				img.setFitHeight(30);
				boardGrid.add(img, j, i);
			}
		}
		boardGrid.setGridLinesVisible(true);

		//// LEFT VBOX ////

		// INPUT FIELD
		userInput.setPromptText("Type your word here");
		userInput.setPrefColumnCount(10);
		userInput.setPrefHeight(30);
		userInput.setFont(Font.font("Bahnschrift", 20));
		userInput.setTextFormatter(new TextFormatter<>((change) -> {
			change.setText(change.getText().toUpperCase());
			return change;
		}));
		userInput.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
		HBox.setMargin(userInput, new Insets(0, 10, 10, 10));
		tfOkCancel.setAlignment(Pos.CENTER_RIGHT);

		// OK BUTTON
		okBtn.setPrefSize(80, 45);
		okBtn.setFont(UNIVERSAL_FONT);
		HBox.setMargin(okBtn, new Insets(0, 0, 10, 0));

		// CANCEL BUTTON
		cancelBtn.setPrefSize(80, 45);
		cancelBtn.setFont(UNIVERSAL_FONT);
		HBox.setMargin(cancelBtn, new Insets(0, 10, 10, 10));

		// PLAYER HAND
		VBox.setMargin(playerHand, new Insets(10, 20, 0, 20));
		for (int i = 0; i < 7; i++) {
			ImageView letter = new ImageView();
			GridPane.setMargin(letter, LETTER_INSETS);
			playerHand.add(letter, i,0);
		}


		//if(start == true) {
			//Adding the letter at the start of the game

			for(int i= 0; i<= 6; i++) {
				char playerLetters = LetterBag.shuffledArray[i]; 
				System.out.println(playerLetters);
				PlayerLetters[i] = playerLetters; 
			}

			//}

			//Letters in players hand
			//Letter1
			int i1 = 0; 
			ImageView letter1 = new ImageView(LetterBag.getBag(PlayerLetters[i1]));
			letter1.setFitHeight(40);
			letter1.setFitWidth(40); 
			playerHand.add(letter1, 0, 0);
			GridPane.setMargin(letter1, LETTER_INSETS);
			//Letter2
			int i2 = 1; 
			ImageView letter2 = new ImageView(LetterBag.getBag(PlayerLetters[i2]));
			letter2.setFitHeight(40);
			letter2.setFitWidth(40); 
			playerHand.add(letter2, 1, 0);
			GridPane.setMargin(letter2, LETTER_INSETS);
			//Letter3
			int i3 = 2; 
			ImageView letter3 = new ImageView(LetterBag.getBag(PlayerLetters[i3]));
			letter3.setFitHeight(40);
			letter3.setFitWidth(40); 
			playerHand.add(letter3, 2, 0);
			GridPane.setMargin(letter1, LETTER_INSETS);
			//Letter4
			int i4 = 3; 
			ImageView letter4 = new ImageView(LetterBag.getBag(PlayerLetters[i4]));
			letter4.setFitHeight(40);
			letter4.setFitWidth(40); 
			playerHand.add(letter4, 3, 0);
			GridPane.setMargin(letter4, LETTER_INSETS);
			//Letter5
			int i5 = 4; 
			ImageView letter5 = new ImageView(LetterBag.getBag(PlayerLetters[i5]));
			letter5.setFitHeight(40);
			letter5.setFitWidth(40); 
			playerHand.add(letter5, 4, 0);
			GridPane.setMargin(letter5, LETTER_INSETS);
			//Letter6
			int i6 = 5; 
			ImageView letter6 = new ImageView(LetterBag.getBag(PlayerLetters[i6]));
			letter6.setFitHeight(40);
			letter6.setFitWidth(40); 
			playerHand.add(letter6, 5, 0);
			GridPane.setMargin(letter6, LETTER_INSETS);
			//Letter7
			int i7 = 6; 
			ImageView letter7 = new ImageView(LetterBag.getBag(PlayerLetters[i7]));
			letter7.setFitHeight(40);
			letter7.setFitWidth(40); 
			playerHand.add(letter7, 6, 0);
			GridPane.setMargin(letter7, LETTER_INSETS);
		





		//// RIGHT VBOX ////

		// SCORE PANEL
		scorePanel.setPrefHeight(200);
		scorePanel.setGridLinesVisible(true);

		// INSTR DISP
		instructionDisplay.setEditable(false);
		VBox.setMargin(instructionDisplay, new Insets(10, 10, 10, 10));

		// OPTION BUTTONS
		optionBtns.setAlignment(Pos.CENTER);

		GridPane.setMargin(playTurnBtn, OPTION_BUTTON_INSETS);
		playTurnBtn.setPrefSize(120, 90);
		playTurnBtn.setFont(UNIVERSAL_FONT);

		exchange1Btn.setPrefSize(120, 90);
		GridPane.setMargin(exchange1Btn, OPTION_BUTTON_INSETS);
		exchange1Btn.setFont(UNIVERSAL_FONT);

		exchangeAllBtn.setPrefSize(120, 90);
		GridPane.setMargin(exchangeAllBtn, OPTION_BUTTON_INSETS);
		exchangeAllBtn.setFont(UNIVERSAL_FONT);

		skipTurnBtn.setPrefSize(120, 90);
		GridPane.setMargin(skipTurnBtn, OPTION_BUTTON_INSETS);
		skipTurnBtn.setFont(UNIVERSAL_FONT);

		quitBtn.setPrefSize(50, 45);
		VBox.setMargin(quitBtn, new Insets(5, 5, 5, 280));
		quitBtn.setFont(UNIVERSAL_FONT);

		//quitBtn.setAlignment(Pos.CENTER_RIGHT);



		//// ACTIONS ////

		quitBtn.setOnAction(e -> System.exit(0)); 


		Scene myScene = new Scene(windowHBox, 800, 600);
		primaryStage.setScene(myScene);
		primaryStage.setResizable(false);
		primaryStage.show();

		okBtn.setOnAction(e -> {
			String word = userInput.getText(); 
			char [] wordArray = word.toCharArray(); 
			boolean contains = true; 
			for(int i =0; i<= wordArray.length; i++) {
				for(int j = 0; j< 7; j++) {
					if(wordArray[j] == PlayerLetters[i]) {
						contains = true; 
					}

					
				}

			}

			if((Dictionary.containsWord(word)==true) && (contains == true)) {
				Player.getValOfWord(word); 
			} 
			else if((Dictionary.containsWord(word)==false) && (contains == true)) {
				Alert errorAlert = new Alert(AlertType.ERROR);
				errorAlert.setHeaderText("Invalid word");
				errorAlert.setContentText("The word is not in the dictionary. Try again.");
				errorAlert.showAndWait();
			}

		}); 

	}


	public static void main(String [] args) {
		launch(args);
	}

}
