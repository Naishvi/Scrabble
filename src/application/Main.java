package application;


import java.io.IOException;
import java.util.HashMap;

import java.util.List;

import javax.swing.JButton;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Main extends Application {

	private static final Font UNIVERSAL_FONT = Font.font("Berlin Sans FB", 16);
	private static final Font UNIVERSAL_FONT_BOLD = Font.font("Berlin Sans FB", FontWeight.BOLD, 14);
	private static final Insets LETTER_INSETS = new Insets(2, 2, 2, 2);
	private static final Insets OPTION_BUTTON_INSETS = new Insets(5, 5, 5, 5);
	private static final String BACKGROUND_COLOR = "-fx-background-color: #ffffff";

	private static final String FIRST_PROMPT = "Choose your action";
	private static final String PLAY_PROMPT = "Type your word. If you want to use letters "
			+ "already on the board as part of your word, just have them included in the word "
			+ "you are going to type ";
	private static final String PLACE_LETTERS_PROMPT = "Place your letters on the board \n"
			+ "Conditions: at least one letter already on board is used (or star square for "
			+ "the first turn); all letters are adjacent; word is written out from top to bottom ";
	private static final String CONFIRM_PROMPT = "Confirm or cancel";
	private static final String INVALID_WORD_PROMPT = "! Word is not in dictionary !";
	private static final String EXCHANGE_ONE_PROMPT = "Select a tile to exchange and press OK";
	private static final String EXCHANGE_ALL_PROMPT = "Exchange all your tiles?";
	private static final String SPECTATING_PROMPT = "Opponent's turn";
	private static final String OPPONENT_ACTION_NOTIF_1 = "It's your turn";
	private static final String OPPONENT_ACTION_NOTIF_2 = "Oppoent exchanged one of their tiles";
	private static final String OPPONENT_ACTION_NOTIF_3 = "Opponent exchanged all their tiles";
	private static final String OPPONENT_ACTION_NOTIF_4 = "Opponent skipped their turn";

	private static final int MINIMUM_WORD_LENGTH = 2;
	private static final int ROW_AND_COL_DIMENSION = 15;
	public static boolean Quit = false; 
	char [] PlayerLetters = new char[7]; 
	public static HashMap<Character, String> letterImage; 
	public static LinkedList<Character> linkedLetters = new LinkedList<>(); 
	public static LinkedList<Integer> linkedRow= new LinkedList<>();  
	public static LinkedList<Integer> linkedCol= new LinkedList<>(); 
	public static boolean ok = false; 
	static // GAME COMPONENTS

	Board board;
	GridPane boardGrid;
	Board savedBoardState;
	GridPane savedGridAspect;
	LetterBag letterBag;
	Dictionary dictionary;
	static LinkedList<Position> enforcedPositions = new LinkedList<>();
	TextField userInput;
	Button okBtn;
	Button cancelBtn;
	TextArea instructionDisplay;
	Button playTurnBtn;
	Button exchange1Btn;
	Button exchangeAllBtn;
	Button skipTurnBtn;
	public static JButton send = new JButton("Send");
	public static JButton update = new JButton("Update"); 
	
	ArrayDictionary<Character, Integer> placedLettersTracker;
	LinkedList<Character> jokerUseDeterminer;
	public static boolean quit = false;

	public Player you;
	Cell[] yourHand;

	ObservableList handCharacters;
	ListView playerHand;
	Player opponent;

	int step;
	boolean firstTurn;
	int userWordIndex;
	int letterRow;
	int letterCol;
	int lengthCounter;

	//// START ////

	@Override
	public void start(Stage primaryStage) {

		board = new Board();
		savedBoardState = new Board();
		savedGridAspect = new GridPane();
		letterBag = new LetterBag();
		dictionary = new Dictionary("Dictionary.txt");
		LinkedList<Position> enforcedPos = new LinkedList<>();
		placedLettersTracker = new ArrayDictionary<>();
		jokerUseDeterminer = new LinkedList<>();

		you = new Player(letterBag);
		yourHand = new Cell[you.getFinalHandSize()];
		opponent = new Player(letterBag);

		step = 1;
		firstTurn = true;
		userWordIndex = 0;
		lengthCounter = 0;

		Group root = new Group();

		HBox windowHBox = new HBox();
		VBox leftVBox = new VBox();
		boardGrid = new GridPane();
		HBox tfOkCancel = new HBox();
		userInput = new TextField();
		okBtn = new Button("OK");
		cancelBtn = new Button("Cancel");
		tfOkCancel.getChildren().addAll(userInput, okBtn, cancelBtn); // adding
		playerHand = new ListView<>();
		leftVBox.getChildren().addAll(boardGrid, tfOkCancel, playerHand); // adding
		VBox rightVBox = new VBox();
		GridPane scorePanel = new GridPane();
		instructionDisplay = new TextArea();
		GridPane optionBtns = new GridPane();
		playTurnBtn = new Button("Play");
		exchange1Btn = new Button("Exchange 1");
		exchangeAllBtn = new Button("Exchange all");
		skipTurnBtn = new Button("Skip");
		Button quitBtn = new Button("Quit");
		optionBtns.add(playTurnBtn, 0, 0);
		optionBtns.add(exchange1Btn, 1, 0);
		optionBtns.add(exchangeAllBtn, 0, 1);
		optionBtns.add(skipTurnBtn, 1, 1);
		rightVBox.getChildren().addAll(scorePanel, instructionDisplay, optionBtns, quitBtn); // adding
		windowHBox.getChildren().addAll(leftVBox, rightVBox); // adding

		//// LEFT VBOX ////

		// BOARD
		VBox.setMargin(boardGrid, new Insets(10, 10, 10, 10));
		boardGrid.setMaxSize(400, 400);
		boardGrid.setPrefWidth(400);
		boardGrid.setPrefHeight(400);
		for (int i = 0; i < ROW_AND_COL_DIMENSION; i++) {
			for (int j = 0; j < ROW_AND_COL_DIMENSION; j++) {
				Cell currSquare = board.getSquares()[i][j];
				if (is2LSquare(i, j)) {
					board.getSquares()[i][j] = new Cell("2L");
				} else if (is2WSquare(i, j)) {
					board.getSquares()[i][j] = new Cell("2W");
					// addSquareToBoard(i,j, "square_2W.png");
				} else if (is3LSquare(i, j)) {
					board.getSquares()[i][j] = new Cell("3L");
					// addSquareToBoard(i,j, "square_3L.png");
				} else if (is3WSquare(i, j)) {
					board.getSquares()[i][j] = new Cell("3W");
					// addSquareToBoard(i,j, "square_3W.png");
				} else {
					board.getSquares()[i][j] = new Cell();
					// addSquareToBoard(i,j, "clear_square.png");
				}
				addSquareToBoard(i, j, board.getSquares()[i][j].getImage());
			}
		}
		boardGrid.setGridLinesVisible(true);

		addSquareToBoard(7, 7, "middle_square.png");
		enforcedPositions.add(new Position(7,7));

		saveBoard();
		//		saveGrid();


		// INPUT FIELD
		userInput.setPromptText("Type your word here");
		userInput.setPrefColumnCount(10);
		userInput.setPrefHeight(30);
		userInput.setFont(Font.font("Bahnschrift", 20));
		userInput.setTextFormatter(new TextFormatter<>((change) -> {
			change.setText(change.getText().toUpperCase());
			return change;
		}));
		userInput.setOnKeyReleased(e -> {
			okBtn.setDisable(userInput.getText().length() < MINIMUM_WORD_LENGTH ? true : false);
		});
		userInput.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
		HBox.setMargin(userInput, new Insets(0, 10, 10, 10));

		userInput.setDisable(true);
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
		playerHand.setOrientation(Orientation.HORIZONTAL);
		//playerHand.getItems().get(0);
		//		playerHand.setPrefHeight(60);
		//		playerHand.setPrefWidth(420);
		VBox.setMargin(playerHand, new Insets(10, 20, 10, 20));
		for (int i = 0; i < you.getFinalHandSize(); i++) {
			String imgFile = "letter_" + you.getHand().getEntry(i) + ".png";
			addLetterToHand(i, imgFile);
			// ImageView letter = new ImageView();
			// GridPane.setMargin(letter, LETTER_INSETS);
			// playerHand.add(letter, i,0);
		}

		//// RIGHT VBOX ////

		// SCORE PANEL
		scorePanel.setPrefHeight(200);
		scorePanel.add(new Label("Your score"), 0, 0);
		scorePanel.add(new Label("Opponent score"), 1, 0);
		scorePanel.add(new Label("0"), 0, 1);
		scorePanel.add(new Label("0"), 1, 1);
		scorePanel.setGridLinesVisible(true);

		// INSTR DISP
		instructionDisplay.setEditable(false);
		VBox.setMargin(instructionDisplay, new Insets(10, 10, 10, 10));
		instructionDisplay.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
		instructionDisplay.setFont(Font.font("Courier New", FontWeight.BLACK, 16));
		instructionDisplay.setText(stepPrompt());
		instructionDisplay.setWrapText(true);

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
		quitBtn.setFont(Font.font("Berlin Sans FB", 14));

		//// ACTIONS ////


		playTurnBtn.setOnMouseEntered(e -> {
			instructionDisplay.setText("Play turn");
		});
		playTurnBtn.setOnMouseExited(e -> {
			instructionDisplay.setText(stepPrompt());
		});
		playTurnBtn.setOnMouseClicked(e -> {
			step = 2;
			instructionDisplay.setText(stepPrompt());
			// startPlayerTurn(you);
			// isTurnChoiceStep = false;
			// isWordTypingStep = true;
			setOptionButtonsVisible(false);
			userInput.setDisable(false);
			// okBtn.setDisable(false);
			cancelBtn.setDisable(false);
		});

		exchange1Btn.setOnMouseEntered(e -> {
			instructionDisplay.setText("Exchange one tile");
		});
		exchange1Btn.setOnMouseExited(e -> {
			instructionDisplay.setText(stepPrompt());
		});
		exchange1Btn.setOnMouseClicked(e -> {
			step = 10;
			setOptionButtonsVisible(false);
			cancelBtn.setDisable(false);
			instructionDisplay.setText(stepPrompt());
			okBtn.setDisable(false);

		});

		exchangeAllBtn.setOnMouseEntered(e -> {
			instructionDisplay.setText("Exchange all tiles");
		});
		exchangeAllBtn.setOnMouseExited(e -> {
			instructionDisplay.setText(stepPrompt());
		});
		exchangeAllBtn.setOnMouseClicked(e -> {
			step = 20;
			setOptionButtonsVisible(false);
			cancelBtn.setDisable(false);
			instructionDisplay.setText(stepPrompt());
			okBtn.setDisable(false);
		});



		skipTurnBtn.setOnMouseEntered(e -> {
			instructionDisplay.setText("Skip this turn");
		});
		skipTurnBtn.setOnMouseExited(e -> {
			setOptionButtonsVisible(false);
			instructionDisplay.setText(stepPrompt());
		});

		setupOKBtnActions();
		setupCancelBtnActions();

		quitBtn.setOnAction(e -> {quit = true; System.exit(0);});

		windowHBox.setStyle(BACKGROUND_COLOR);
		Scene myScene = new Scene(windowHBox, 800, 600);

		primaryStage.setScene(myScene);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Scrabble Game");
		primaryStage.show();
	}





	//// UTILITIES ////


	private void clearNewlyOccupiedSquares() {
		while (!you.getLetterPlacements().isEmpty()) {
			int row = you.getLetterPlacements().getEntry(0).getRow();
			int col = you.getLetterPlacements().getEntry(0).getCol();
			addSquareToBoard(row, col, relevantSquareImage(row, col));
			you.getLetterPlacements().remove(0);
		}
	}

	private String relevantSquareImage(int r, int c) {
		if (is2LSquare(r, c)) {
			return "square_2L.png";
		} else if (is2WSquare(r, c)) {
			return "square_2W.png";
		} else if (is3LSquare(r, c)) {
			return "square_3L.png";
		} else if (is3WSquare(r, c)) {
			return "square_3W.png";
		} else if (r == 7 && c == 7) {
			return "middle_square.png";
		}
		return "clear_square.png";
	}

	private String relevantSquareType(int r, int c) {
		if (is2LSquare(r, c)) {
			return "2L";
		} else if (is2WSquare(r, c)) {
			return "2W";
		} else if (is3LSquare(r, c)) {
			return "3L";
		} else if (is3WSquare(r, c)) {
			return "3W";
		}
		return "";
	}

	private void saveBoard() {
		//		for (int i = 0; i < ROW_AND_COL_DIMENSION; i++) {
		//			for (int j = 0; j < ROW_AND_COL_DIMENSION; j++) {
		//				//savedBoardState.getSquares()[i][j] = new Cell();
		//				savedBoardState.getSquares()[i][j] = board.getSquares()[i][j];
		//			}
		//		}
		savedBoardState = board;
	}

	private void saveGrid() {
		int i = 0;
		int j = 0;
		for (Node node : boardGrid.getChildren()) {
			savedGridAspect.add(new Cell(), j, i);
			if (j == ROW_AND_COL_DIMENSION) {
				i++;
				j = 0;
			}
		}
	}

	private void resetBoard() {
		for (int i = 0; i < ROW_AND_COL_DIMENSION; i++) {
			for (int j = 0; j < ROW_AND_COL_DIMENSION; j++) {
				board.getSquares()[i][j] = savedBoardState.getSquares()[i][j];
			}
		}
	}

	private void resetGrid() {
		int i = 0;
		int j = 0;
		for (Node node : savedGridAspect.getChildren()) {
			boardGrid.add(node, j, i);
		}
		if (j == ROW_AND_COL_DIMENSION) {
			i++;
			j = 0;
		}
	}

	private void setBoardClickable() {
		for (int i = 0; i < ROW_AND_COL_DIMENSION; i++) {
			for (int j = 0; j < ROW_AND_COL_DIMENSION; j++) {
				letterRow = i;
				letterCol = j;
				// Cell currSquare = board.getSquares()[letterRow][letterCol];
				board.getSquares()[letterRow][letterCol].setOnMouseClicked(e -> {
					System.exit(0);
					char currChar = you.getWord().charAt(userWordIndex);
					board.getSquares()[letterRow][letterCol].setOccupyingLetter(currChar);
					userWordIndex++;
					String img = "letter_J.png";
					// currSquare.setImage(img);
					addLetterToBoard(letterRow, letterCol, img);
					// darkenLetterInHand(currChar);
				});
			}
		}
	}

	// TODO
	// private void darkenLetterInHand(Character currChar, ImageView newImg) {
	// boolean foundChar = false;
	// for (int i = 0; i < 7 && foundChar == false; i++) {
	// Node currPosInHand = playerHand.getChildren().getEntry(i);
	// if ((you.getHand().getEntry(i) == currChar)) {
	// currPosInHand.setEffect(new Lighting());
	// foundChar = true;
	// }
	// }
	// }



	private String stepPrompt() {
		String prompt = "";
		if (step == 1)
			prompt = FIRST_PROMPT;
		if (step == 2)
			prompt = PLAY_PROMPT;
		if (step == 3)
			prompt = PLACE_LETTERS_PROMPT;
		if (step == 4)
			prompt = CONFIRM_PROMPT;
		if (step == 10)
			prompt = EXCHANGE_ONE_PROMPT;
		if (step == 20)
			prompt = EXCHANGE_ALL_PROMPT;

		return prompt;
	}

	private boolean is2LSquare(int i, int j) {
		return ((i == 3 && (j == 0 || j == 7 || j == 14)) || (i == 6 && (j == 2 || j == 6 || j == 8 || j == 12))
				|| (i == 8 && (j == 2 || j == 6 || j == 8 || j == 12)) || (i == 11 && (j == 0 || j == 7 || j == 14))
				|| (j == 3 && (i == 0 || i == 7 || i == 14)) || (j == 6 && (i == 2 || i == 12))
				|| (j == 8 && (i == 2 || i == 12)) || (j == 11 && (i == 0 || i == 7 || i == 14)));
	}

	private boolean is2WSquare(int i, int j) {
		return (((i == j) || (14 - i == j)) && (i != 0 && i != 14) && (i <= 4 || i >= 10));
	}

	private boolean is3LSquare(int i, int j) {
		return ((i == 5 && (j == 1 || j == 5 || j == 9 || j == 13))
				|| (i == 9 && (j == 1 || j == 5 || j == 9 || j == 13)) || (j == 5 && (i == 1 || i == 13))
				|| (j == 9 && (i == 1 || i == 13)));
	}

	private boolean is3WSquare(int i, int j) {
		return ((i == 0 && (j == 0 || j == 7 || j == 14)) || (i == 7 && (j == 0 || j == 14))
				|| (i == 14 && (j == 0 || j == 7 || j == 14)));
	}

	private void addSquareToBoard(int i, int j, String imgFile) {
		board.getSquares()[i][j] = new Cell();
		Cell currSquare = board.getSquares()[i][j];
		currSquare.setImage(imgFile);
		boardGrid.add(currSquare, j, i);
		insertImageInGrid(boardGrid, i, j, currSquare.getImage());
	}

	protected void addLetterToBoard(int i, int j, String imgFile) {
		String squareType = relevantSquareType(i, j);
		if (squareType.equals(""))
			board.getSquares()[i][j] = new Cell();
		else
			board.getSquares()[i][j] = new Cell(squareType);

		Cell currSquare = board.getSquares()[i][j];
		currSquare.setImage(imgFile);
		boardGrid.add(currSquare, j, i);
		insertImageInGrid(boardGrid, i, j, currSquare.getImage());
	}

	private void addLetterToHand(int i, String imgFile) {
		yourHand[i] = new Cell();
		Cell currLetterSlot = yourHand[i];
		Character currLetter = you.getHand().getEntry(i);
		if (currLetter != ' ')
			currLetterSlot.setImage("letter_" + you.getHand().getEntry(i) + ".png");
		else {
			currLetterSlot.setImage("letter_blank.png");
			you.addToActualJokerCount(1);
		}
		//playerHand.getItems().add(i, currLetterSlot.getLetter()); TODO
		insertImageInList(playerHand, 0, i, currLetterSlot.getImage());
	}

	private void addLettersToHandFromBag() {
		int lastIndex = you.getHand().getLength();
		while (you.getHand().getLength() < you.getFinalHandSize()) {
			Character newLetter = letterBag.fetchOneLetter();
			if (newLetter == ' ')
				you.addToActualJokerCount(1);
			you.getHand().add(newLetter);
			addLetterToHand(lastIndex, "letter_" + newLetter + ".png");
			lastIndex++;
		}
	}

	private void removeLettersFromHand() {
		for (int i = 0; i < you.getWord().length(); i++) {
			for (int j = you.getHand().getLength() - 1; j >= 0; j--) {
				Character currWordChar = you.getWord().charAt(i);
				Character currHandChar = you.getHand().getEntry(j);
				//int occurrencesOfThisLetterInWord = placedLettersTracker.getValue(currWordChar);
				if (currWordChar == currHandChar 
						/*&& occurrencesOfThisLetterInWord > 0*/) {
					you.getHand().remove(j);
					playerHand.getItems().remove(j);
					break;
					//placedLettersTracker.add(currWordChar, occurrencesOfThisLetterInWord - 1);
				} else if (currHandChar == ' ' && jokerUseDeterminer.contains(currWordChar)) {
					you.getHand().remove(j);
					playerHand.getItems().remove(j);
					jokerUseDeterminer.remove(currWordChar);
					break;
				}
				// OR
				//addLetterToHand(j, "" + letterBag.fetchOneLetter());
			}
		}
	}





	private void insertImageInList(ListView list, int row, int col, String img) {
		ImageView newImg = new ImageView(img);

		newImg.setFitWidth(45);
		newImg.setFitHeight(45);
		//playerHand.setGridLinesVisible(false);
		playerHand.getItems().add(col, newImg); //TODO
		//playerHand.set(true);
	}

	private void insertImageInGrid(GridPane grid, int row, int col, String img) {
		ImageView newImg = new ImageView(new Image(img));

		// if (!newImg.getId().contains("letter_")) {
		newImg.setOnMouseEntered(e -> {if (step == 3) newImg.setEffect(new Glow(0.8));});
		newImg.setOnMouseExited(e -> {if (step == 3) newImg.setEffect(new Glow(0));});
		// }
		newImg.setFitWidth(30);
		newImg.setFitHeight(30);
		grid.setGridLinesVisible(false);
		grid.add(newImg, col, row);
		grid.setGridLinesVisible(true);

		newImg.setOnMouseClicked(e -> {
			if (step == 3 && (userWordIndex < you.getWord().length())) {
				letterRow = row;
				letterCol = col;
				// keep in mind: playerHasLetterInHand() is a boolean method that modifies stuff!
				if ((userWordIndex == 0 || perClickRestrictionsFollowed())
						&& (userClickedOnNewSquarePosition())) {
					if (squareClickedContainsLetter()) {
						// checks whether the clicked tile matches the current letter trying to
						// be placed on the board
						Character currWordChar = you.getWord().charAt(userWordIndex);
						Character currBoardChar = board.getSquares()[letterRow][letterCol].getLetter();
						if (currWordChar == currBoardChar) {
							you.addToPlayerPositions(new Position(letterRow, letterCol));
							userWordIndex++;
						}
						System.out.println("wudup1");
					}
					else if (playerHasLetterInHand()) {
						Cell currSquare = board.getSquares()[letterRow][letterCol];
						Character currWordChar = you.getWord().charAt(userWordIndex++);
						currSquare.setOccupyingLetter(currWordChar);
						String image = currSquare.getImage();
						addLetterToBoard(letterRow, letterCol, image);
						// darkenLetterInHand(currChar, newImg);
						// condition: only add to player positions if clicked square was not already
						// occupied with a letter
						you.addToLetterPlacements(new Position(letterRow, letterCol));
						you.addToPlayerPositions(new Position(letterRow, letterCol));
						// only place letter on board if square clicked does not contain letter
						// placed during this turn
						System.out.println("wudup2");
					} else if (playerHasJokerInHand()) {
						Cell currSquare = board.getSquares()[letterRow][letterCol];
						Character currWordChar = you.getWord().charAt(userWordIndex++);
						currSquare.setOccupyingLetter(' ');
						String image = "letter_blank.png";
						addLetterToBoard(letterRow, letterCol, image);
						// darkenLetterInHand(currChar, newImg);
						// condition: only add to player positions if clicked square was not already
						// occupied with a letter
						// EDIT: last player position needs to be added even if letter already on
						// board was clicked, to make following clicks possible (only difference:
						// points)
						you.addToLetterPlacements(new Position(letterRow, letterCol));
						you.addToPlayerPositions(new Position(letterRow, letterCol));
						you.addToJokerCount(-1);
						jokerUseDeterminer.add(currWordChar);
						System.out.println("wudup3");
					}
					System.out.println("Trying to place letter...");
				}
			}
			System.out.println(printHandContent());
			if (step == 3 && userWordIndex == you.getWord().length()) {
				if (perTurnRestrictionsFollowed()) {
					step = 4;
					okBtn.setDisable(false);
					instructionDisplay.setText(stepPrompt());
				}
			}
			//System.out.println(userWordIndex + " on " + (you.getWord().length()) + " (indices)");
		});
	}

	private boolean userClickedOnNewSquarePosition() {
		Position clickedPosition = new Position(letterRow, letterCol);
		LinkedList<Position> alreadyClicked = you.getPlayerPositions();

		for (int i = 0; i < alreadyClicked.getLength(); i++) {
			Position currPos = you.getPlayerPositions().getEntry(i);
			if (currPos.toString().equals(clickedPosition.toString()))
				return false;
		}

		return true;
	}

	private boolean playerHasLetterInHand() {
		Character currWordChar = you.getWord().charAt(userWordIndex);
		Integer subarrayFirstIndex = 0;

		if (placedLettersTracker.contains(currWordChar))
			subarrayFirstIndex = placedLettersTracker.getValue(currWordChar) + 1;

		for (int i = subarrayFirstIndex; i < you.getHand().getLength(); i++) {
			Character currLetter = you.getHand().getEntry(i);
			if (currLetter == currWordChar) {
				placedLettersTracker.add(currLetter, i);
				return true;
			}
		}

		return false;
	}





	private boolean playerHasJokerInHand() {
		return you.getJokerCount() >= 1;
	}

/*	private void darkenLetterInHand(Character currChar, ImageView newImg) {
		int i = 0;
		for (Node child : handCharacters.getChildren()) {
			Integer r = GridPane.getRowIndex(child);
			Integer c = GridPane.getColumnIndex(child);
			int row = (r == null ? 0 : r);
			int column = (c == null ? 0 : c);
			if (row == 0 && column == i && (child instanceof Cell)) {
				Cell currSquare = (Cell) child;
				System.out.println(currSquare.getImage());
				//currSquare.getImage().setEffect(new Lighting());
				// playerHand.getChildren().remove(child);
				//playerHand.add(newImg, column, row);
				break;
			}
			System.out.println(i++);
		}
	}*/

	private boolean squareClickedContainsLetter() {
		if (firstTurn)
			return false;	
		Position currPos = new Position(letterRow, letterCol);

		for (int i = 0; i < enforcedPositions.getLength(); i++) {
			if (enforcedPositions.getEntry(i).toString().equals(currPos.toString())) {
				System.out.println("Clicked square with letter tile on it!");
				return true;
			}
		}
		System.out.println("Enforced positions: " + enforcedPositions.toString());
		return false;
	}

	private boolean perClickRestrictionsFollowed() {
		boolean adjacent = false;
		boolean continuous = false;
		int lastPositionIndex = you.getPlayerPositions().getLength() - 1;
		Position lastPlayerPosition = you.getPlayerPositions().getEntry(lastPositionIndex);

		// 1: check adjacency (happens to check whether word is typed left-right/top-bottom
		if ((letterRow == lastPlayerPosition.getRow() + 1 && letterCol == lastPlayerPosition.getCol()) 
				|| (letterCol == lastPlayerPosition.getCol() + 1 && letterRow == lastPlayerPosition.getRow())) {
			adjacent = true;
		}

		if (userWordIndex >= 2) {
			int firstLetterRow = you.getPlayerPositions().getEntry(lastPositionIndex - 1).getRow();
			int firstLetterCol = you.getPlayerPositions().getEntry(lastPositionIndex - 1).getCol();
			int secondLetterRow = you.getPlayerPositions().getEntry(lastPositionIndex).getRow();
			int secondLetterCol = you.getPlayerPositions().getEntry(lastPositionIndex).getCol();
			if (firstLetterRow == secondLetterRow)
				continuous = (letterCol == secondLetterCol + 1);
			else if (firstLetterCol == secondLetterCol)
				continuous = (letterRow == secondLetterRow + 1);
		} else if (userWordIndex < 2)
			continuous = true;
		// 2a: (if second letter) check that letter is on same row or same column
		// 2b: (if more than second letter) check that letter is placed exactly on the intended
		// square
		return adjacent && continuous;
	}

	private boolean perTurnRestrictionsFollowed() {
		// 1: (if first turn) check that one letter is on the star square
		//if (firstTurn == true) {
		return checkPlacementUsesAtLeastOneEnforcedPosition();
		//}
		// 2: (otherwise) check that at least one letter uses letter already on board
		//		else {
		//			
		//		}
		//		return false;
	}

	private boolean checkPlacementUsesAtLeastOneEnforcedPosition() {
		int playerPosLength = you.getPlayerPositions().getLength();
		int enforcedPosLength = enforcedPositions.getLength();
		for (int i = 0; i < playerPosLength; i++) {
			for (int j = 0; j < enforcedPosLength; j++) {
				if (you.getPlayerPositions().getEntry(i).toString()
						.equals(enforcedPositions.getEntry(j).toString()))
					return true;
			}
		}
		return false;
	}

	private void makeJokersTakeLetterValue() {

	}

	private void refreshEnforcedPositions() {
		while (!you.getLetterPlacements().isEmpty()) {
			enforcedPositions.add(you.getLetterPlacements().remove(0));
		}
	}

	private void setupOKBtnActions() {
		okBtn.setDisable(true);
		okBtn.setOnAction(e -> {

			you.setWord(userInput.getText());
			if (step == 2) {
				if (dictionary.containsWord(you.getWord())) {
					step = 3;
					userInput.setDisable(true);
					okBtn.setDisable(true);
					//System.out.println(val);
					// setBoardClickable();
					instructionDisplay.setText(stepPrompt());
				} else {
					instructionDisplay.setText(INVALID_WORD_PROMPT);
				}
			} else if (step == 4) {
				// confirm the word
				// give points to player
				you.setActualJokerCount(you.getJokerCount());
				System.out.println("Before removing (actual hand): " + printHandContent());
				System.out.println("Before removing (displayed hand): " + printDisplayedHandContent());
				removeLettersFromHand();
				System.out.println("After removing (actual hand):  " + printHandContent());
				System.out.println("After removing (displayed hand):  " + printDisplayedHandContent());
				addLettersToHandFromBag();
				System.out.println("After adding (actual hand):    " + printHandContent());
				System.out.println("After adding (displayed hand):    " + printHandContent());
				makeJokersTakeLetterValue();
				refreshLetterValueOfSquares();
				refreshEnforcedPositions(); // somewhat refreshes board state
				ok = true; 
				encode(); 
				you.getPlayerPositions().clear();
				you.getLetterPlacements().clear();
				step = 1;
				userWordIndex = 0;
				firstTurn = false;
				setOptionButtonsVisible(true);
				userInput.clear();
				userInput.setDisable(true);
				okBtn.setDisable(true);
				cancelBtn.setDisable(true);
				instructionDisplay.setText(stepPrompt());

			} else if (step == 10) {
				int selectedTileIndex = playerHand.getSelectionModel().getSelectedIndex();
				letterBag.putBackLetter(you.getHand().remove(selectedTileIndex));
				playerHand.getItems().remove(selectedTileIndex);
				setOptionButtonsVisible(true);
				okBtn.setDisable(true);
				cancelBtn.setDisable(true);
				addLettersToHandFromBag(); // well, only 1 in this case
				step = 1;
				instructionDisplay.setText(stepPrompt());
			} else if (step == 20) {
				putBackAllPlayerTiles();
				setOptionButtonsVisible(true);
				okBtn.setDisable(true);
				cancelBtn.setDisable(true);
				addLettersToHandFromBag();
				step = 1;
				instructionDisplay.setText(stepPrompt());
			}

		});
	}

	private void setOptionButtonsVisible(boolean b) {
		playTurnBtn.setVisible(b);
		exchange1Btn.setVisible(b);
		exchangeAllBtn.setVisible(b);
		skipTurnBtn.setVisible(b);		
	}


	private void putBackAllPlayerTiles() {
		while (!you.getHand().isEmpty()) {
			letterBag.putBackLetter(you.getHand().remove(0));
			playerHand.getItems().remove(0);
		}
	}

	private void setupCancelBtnActions() {
		cancelBtn.setDisable(true);
		cancelBtn.setOnAction(e -> {
			if (step == 2) {
				step = 1;
				userInput.clear();
				userInput.setDisable(true);
				instructionDisplay.setText(stepPrompt());
				okBtn.setDisable(true);
				playTurnBtn.setVisible(true);
				exchange1Btn.setVisible(true);
				exchangeAllBtn.setVisible(true);
				skipTurnBtn.setVisible(true);
				cancelBtn.setDisable(true);
			} else if (step == 3) {
				step = 2;
				userInput.setDisable(false);
				instructionDisplay.setText(stepPrompt());
				userWordIndex = 0;
				placedLettersTracker.clear();
				you.getPlayerPositions().clear();
				you.setJokerCount(you.getActualJokerCount());
				clearNewlyOccupiedSquares(); // ! clears letterPlacements field in Player
				//				resetBoard();
				//				resetGrid();
			} else if (step == 4) {
				step = 3;
				instructionDisplay.setText(stepPrompt());
				userWordIndex = 0;
				placedLettersTracker.clear();
				you.getPlayerPositions().clear();
				you.setJokerCount(you.getActualJokerCount());
				clearNewlyOccupiedSquares(); // ! clears letterPlacements field in Player
				okBtn.setDisable(true);
			} else if (step == 10) {
				step = 1;
				instructionDisplay.setText(stepPrompt());
				okBtn.setDisable(true);
				cancelBtn.setDisable(true);
				setOptionButtonsVisible(true);
			} else if (step == 20) {
				step = 1;
				instructionDisplay.setText(stepPrompt());
				okBtn.setDisable(true);
				cancelBtn.setDisable(true);
				setOptionButtonsVisible(true);
			}
		});		
	}

	private void refreshLetterValueOfSquares() {
		for (int i = 0; i < you.getPlayerPositions().getLength(); i++) {
			int currPosRow = you.getPlayerPositions().getEntry(i).getRow();
			int currPosCol = you.getPlayerPositions().getEntry(i).getCol();
			String currSquareImage = board.getSquares()[currPosRow][currPosCol].getImage();
			if (!currSquareImage.equals("letter_blank.png")) {
				Character currSquareLetter = currSquareImage.charAt(7);
				board.getSquares()[currPosRow][currPosCol].setOccupyingLetter(currSquareLetter);
			}
		}
	}

	private String printDisplayedHandContent() {
		String fullString = "";
		for (int i = 0; i < yourHand.length; i++) {
			fullString += yourHand[i].getLetter() + " ";
		}
		return fullString;
	}

	private String printHandContent() {
		String fullString = "";
		for (int i = 0; i < you.getHand().getLength(); i++) {
			fullString += you.getHand().getEntry(i) + " ";
		}
		return fullString;
	}



	private boolean enforcedPositionsContainThisPosition(int row, int col) {
		Client c = new Client();
		for (int i = 0; i < c.getRowList().getLength(); i++) {
			if (c.getRowList().getEntry(i) == row && c.getColList().getEntry(i) == col) {
				return true;
			}
		}
		return false;
	}
	public static String encode() {
		String encodeString = "";

		// LinkedList of positions enforcedPositions (Main.java) always happens
		// to have a duplicate entry at the beginning. It should be removed
		enforcedPositions.remove(new Position(7, 7));
		for (int i = 0; i < enforcedPositions.getLength(); i++) {
			Position aPos = enforcedPositions.getEntry(i);
			int posRow = aPos.getRow();
			int posCol = aPos.getCol();
			Character charAtPos = board.getSquares()[posRow][posCol].getLetter();
			String toConcatenate = charAtPos + "," + posRow + "," + posCol;
			encodeString += toConcatenate + (i < enforcedPositions.getLength() - 1 ? "|" : "");
		}

		return encodeString;

	}

	public static void decode(String encode) {
		String[] letters = encode.split("\\|"); 
		//[("T,4,2"), ("O,5,2"), ("E,6,2")]
		for(int i = 0; i< letters.length; i++) {	
			String[] positionOfLetter = letters[i].split(",");
			linkedLetters.add(positionOfLetter[0].charAt(0));  
			linkedRow.add(Integer.parseInt(positionOfLetter[1]));
			linkedCol.add(Integer.parseInt(positionOfLetter[2]));  


		}
	}

		

	}




