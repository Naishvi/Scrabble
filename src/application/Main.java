package application;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Main extends Application {
	
	
	private static final Font UNIVERSAL_FONT = Font.font("Berlin Sans FB", 16);
	private static final Font UNIVERSAL_FONT_BOLD = Font.font("Berlin Sans FB", FontWeight.BOLD, 14);
	private static final Insets LETTER_INSETS = new Insets(2, 2, 2, 2);
	private static final Insets OPTION_BUTTON_INSETS = new Insets(5, 5, 5, 5);
	private static final String BACKGROUND_COLOR = "-fx-background-color: #ffffff";
	private static final String FIRST_PROMPT = "Choose your action.";
	private static final String PLAY_PROMPT = ".Type your word";
	
	// GAME STATES
	
	private boolean isTurnChoiceStep;
	private boolean isWordTypingStep;
	private boolean isLetterPlacingStep;
	// sub-step of isLetterPlacingStep. Might remain unused. Would make board only allow letter
	// placement along the row or column common the first 2 letters placed.
	private boolean rowOrColIsDetermined; 
	
	

	// GAME COMPONENTS
	
	Board board;
	Player you;
	Player opp;
	
	
	//// UTILITIES ////
	
	public void insertImage(GridPane grid, int row, int col, String img) {
		ImageView newImg = new ImageView(img);
		//if (!newImg.getId().contains("letter_")) {
			newImg.setOnMouseEntered(e -> newImg.setEffect(new Glow(0.7)));
			newImg.setOnMouseExited(e -> newImg.setEffect(new Glow(0)));
		//}
		newImg.setFitWidth(30);
		newImg.setFitHeight(30);
		grid.setGridLinesVisible(false);
		grid.add(newImg, col, row);
		grid.setGridLinesVisible(true);
	}
	
	
	
	//// START ////
	
	
	@Override
	public void start(Stage primaryStage) {
		
		board = new Board();
		you = new Player();
		opp = new Player();
		
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
				board.getSquares()[i][j] = new Cell();
				boardGrid.add(board.getSquares()[i][j], j, i);
				insertImage(boardGrid, i, j, "clear_square.png");
			}
		}
		boardGrid.setGridLinesVisible(true);
		
		insertImage(boardGrid, 7, 7, "middle_square.png");
		
		
		
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
		
		
//		ImageView letterA = new ImageView("letter_A.png");
//		letterA.setFitHeight(60);
//		letterA.setFitWidth(60);								// for testing purposes
//		ImageView letterG = new ImageView("letter_G.png");
//		letterG.setFitHeight(60);
//		letterG.setFitWidth(60);
//		
//		playerHand.add(letterA, 0,0);
//		GridPane.setMargin(letterA, LETTER_INSETS);
//		playerHand.add(letterG, 1,0);
//		GridPane.setMargin(letterG, LETTER_INSETS);
		
		
		//// RIGHT VBOX ////

		// SCORE PANEL
		scorePanel.setPrefHeight(200);
		scorePanel.setGridLinesVisible(true);
		
		// INSTR DISP
		instructionDisplay.setEditable(false);
		VBox.setMargin(instructionDisplay, new Insets(10, 10, 10, 10));
		instructionDisplay.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
		instructionDisplay.setFont(Font.font("Courier New", 16));
		instructionDisplay.setText(FIRST_PROMPT);
		
		// OPTION BUTTONS
		optionBtns.setAlignment(Pos.CENTER);
		
		GridPane.setMargin(playTurnBtn, OPTION_BUTTON_INSETS);
		playTurnBtn.setPrefSize(120, 90);
		playTurnBtn.setFont(UNIVERSAL_FONT);
		playTurnBtn.setOnMouseClicked(e -> {
			instructionDisplay.setText(PLAY_PROMPT);
			isTurnChoiceStep = false;
			isWordTypingStep = true;
			playTurnBtn.setDisable(true);
		}
				);
		
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
		quitBtn.setFont(Font.font("Berlin Sans FB", 14));
	
		
		
		
		//// ACTIONS ////
		
		okBtn.setDisable(isWordTypingStep ? false : true);
		quitBtn.setOnAction(e -> System.exit(0));
		

		
		isTurnChoiceStep = true;
		isWordTypingStep = false;
		isLetterPlacingStep = false;
		rowOrColIsDetermined = false;
		
		windowHBox.setStyle(BACKGROUND_COLOR);
		Scene myScene = new Scene(windowHBox, 800, 600);
		
		primaryStage.setScene(myScene);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Scrabble Game");
		primaryStage.show();
		
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
