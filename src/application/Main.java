package application;

import java.util.HashMap;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
	private static final int MINIMUM_WORD_LENGTH = 2;
	private static final int ROW_AND_COL_DIMENSION = 15;
	public static boolean Quit = false; 
	char [] PlayerLetters = new char[7]; 
	public static HashMap<Character, String> letterImage; 
	// GAME COMPONENTS

	Board board;
	GridPane boardGrid;
	Board savedBoardState;
	GridPane savedGridAspect;
	LetterBag letterBag;
	Dictionary dictionary;
	LinkedList<Position> enforcedPositions;
	TextField userInput;
	Button okBtn;
	Button cancelBtn;
	TextArea instructionDisplay;
	Button playTurnBtn;
	Button exchange1Btn;
	Button exchangeAllBtn;
	Button skipTurnBtn;
	ArrayDictionary<Character, Integer> placedLettersTracker;
	
	Player you;
	Cell[] yourHand;
	GridPane playerHand;
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
		enforcedPositions = new LinkedList<>();
		placedLettersTracker = new ArrayDictionary<>();

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
				playerHand = new GridPane();
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
					currSquare = new Cell("2L");
				} else if (is2WSquare(i, j)) {
					currSquare = new Cell("2W");
					// addSquareToBoard(i,j, "square_2W.png");
				} else if (is3LSquare(i, j)) {
					currSquare = new Cell("3L");
					// addSquareToBoard(i,j, "square_3L.png");
				} else if (is3WSquare(i, j)) {
					currSquare = new Cell("3W");
					// addSquareToBoard(i,j, "square_3W.png");
				} else {
					currSquare = new Cell();
					// addSquareToBoard(i,j, "clear_square.png");
				}
				addSquareToBoard(i, j, currSquare.getImage());
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
		VBox.setMargin(playerHand, new Insets(10, 20, 0, 20));
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

		playTurnBtn.setOnMouseClicked(e -> {
			step += 1;
			instructionDisplay.setText(stepPrompt());
			// startPlayerTurn(you);
			// isTurnChoiceStep = false;
			// isWordTypingStep = true;
			playTurnBtn.setVisible(false);
			exchange1Btn.setVisible(false);
			exchangeAllBtn.setVisible(false);
			skipTurnBtn.setVisible(false);
			userInput.setDisable(false);
			// okBtn.setDisable(false);
			cancelBtn.setDisable(false);
		});
		playTurnBtn.setOnMouseEntered(e -> {
			instructionDisplay.setText("Play turn");
		});
		playTurnBtn.setOnMouseExited(e -> {
			instructionDisplay.setText(stepPrompt());
		});
		playTurnBtn.setVisible(step == 1 ? true : false);

		exchange1Btn.setVisible(step == 1 ? true : false);
		exchange1Btn.setOnMouseEntered(e -> {
			instructionDisplay.setText("Exchange one tile");
		});
		exchange1Btn.setOnMouseExited(e -> {
			instructionDisplay.setText(stepPrompt());
		});

		exchangeAllBtn.setOnMouseEntered(e -> {
			instructionDisplay.setText("Exchange all tiles");
		});
		exchangeAllBtn.setOnMouseExited(e -> {
			instructionDisplay.setText(stepPrompt());
		});
		exchangeAllBtn.setVisible(step == 1 ? true : false);

		skipTurnBtn.setOnMouseEntered(e -> {
			instructionDisplay.setText("Skip this turn");
		});
		skipTurnBtn.setOnMouseExited(e -> {
			instructionDisplay.setText(stepPrompt());
		});
		skipTurnBtn.setVisible(step == 1 ? true : false);

		setupOKBtnActions();
		setupCancelBtnActions();
		
		quitBtn.setOnAction(e -> System.exit(0));

		windowHBox.setStyle(BACKGROUND_COLOR);
		Scene myScene = new Scene(windowHBox, 800, 600);

		primaryStage.setScene(myScene);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Scrabble Game");
		primaryStage.show();
	}


	
	

	//// UTILITIES ////


	private void clearNewlyOccupiedSquares() {
		while (!you.getPlayerPositions().isEmpty()) {
			int row = you.getPlayerPositions().getEntry(0).getRow();
			int col = you.getPlayerPositions().getEntry(0).getCol();
			addSquareToBoard(row, col, relevantSquareImage(row, col));
			you.getPlayerPositions().remove(0);
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
	
//	
//	private void saveGrid() {
//		int i = 0;
//		int j = 0;
//		for (Node node : boardGrid.getChildren()) {
//			savedGridAspect.add(new Cell(), j, i);
//			if (j == ROW_AND_COL_DIMENSION) {
//				i++;
//				j = 0;
//			}
//		}
//	}
//
//	private void resetBoard() {
//		for (int i = 0; i < ROW_AND_COL_DIMENSION; i++) {
//			for (int j = 0; j < ROW_AND_COL_DIMENSION; j++) {
//				board.getSquares()[i][j] = savedBoardState.getSquares()[i][j];
//			}
//		}
//	}
//	
//	private void resetGrid() {
//		int i = 0;
//		int j = 0;
//		for (Node node : savedGridAspect.getChildren()) {
//			boardGrid.add(node, j, i);
//		}
//		if (j == ROW_AND_COL_DIMENSION) {
//			i++;
//			j = 0;
//		}
//	}

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
	// Node currPosInHand = playerHand.getChildren().get(i);
	// if ((you.getHand().getEntry(i) == currChar)) {
	// currPosInHand.setEffect(new Lighting());
	// foundChar = true;
	// }
	// }
	// }

	
	private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
		for (Node node : gridPane.getChildren()) {
			if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
				return node;
			}
		}
		return null;
	}

	
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
		insertImage(boardGrid, i, j, currSquare.getImage(), true);
	}

	
	private void addLetterToHand(int i, String imgFile) {
		yourHand[i] = new Cell();
		Cell currLetterSlot = yourHand[i];
		Character currLetter = you.getHand().getEntry(i);
		if (currLetter != ' ')
			currLetterSlot.setImage("letter_" + you.getHand().getEntry(i) + ".png");
		else
			currLetterSlot.setImage("letter_blank.png");
		playerHand.add(currLetterSlot, i, 0);
		insertImage(playerHand, 0, i, currLetterSlot.getImage(), false);
	}

	
	private void addLetterToBoard(int i, int j, String imgFile) {
		String squareType = relevantSquareType(i, j);
		if (squareType.equals(""))
			board.getSquares()[i][j] = new Cell();
		else
			board.getSquares()[i][j] = new Cell(squareType);
		
		Cell currSquare = board.getSquares()[i][j];
		currSquare.setImage(imgFile);
		boardGrid.add(currSquare, j, i);
		insertImage(boardGrid, i, j, currSquare.getImage(), true);
	}

	private void insertImage(GridPane grid, int row, int col, String img, boolean boardFormat) {
		ImageView newImg = new ImageView(new Image(img));

		if (boardFormat) {
			// if (!newImg.getId().contains("letter_")) {
			newImg.setOnMouseEntered(e -> {if (step == 3) newImg.setEffect(new Glow(0.8));});
			newImg.setOnMouseExited(e -> {if (step == 3) newImg.setEffect(new Glow(0));});
			// }
			newImg.setFitWidth(30);
			newImg.setFitHeight(30);
			grid.setGridLinesVisible(false);
			grid.add(newImg, col, row);
			grid.setGridLinesVisible(true);
		} else if (!boardFormat) {
			newImg.setFitWidth(60);
			newImg.setFitHeight(60);
			grid.setGridLinesVisible(false);
			grid.add(newImg, col, row);
			grid.setGridLinesVisible(true);
		}

		newImg.setOnMouseClicked(e -> {
			if (step == 3 && (userWordIndex < you.getWord().length())) {
				letterRow = row;
				letterCol = col;
				// keep in mind: playerHasLetterInHand() is a boolean method that modifies stuff!
				if ((squareClickedContainsLetter() || playerHasLetterInHand())
						&& (userWordIndex == 0 || perClickRestrictionsFollowed())
						&& (userClickedOnNewSquarePosition())) {
					Cell currSquare = board.getSquares()[letterRow][letterCol];
					Character currChar = you.getWord().charAt(userWordIndex++);
					currSquare.setOccupyingLetter(currChar);
					String image = currSquare.getImage();
					addLetterToBoard(letterRow, letterCol, image);
					darkenLetterInHand(currChar, newImg);
					// condition: only add to player positions if clicked square was not already
					// occupied with a letter
					you.addToPlayerPositions(new Position(letterRow, letterCol));
					// only place letter on board if square clicked does not contain letter
					// placed during this turn
				} else if (playerHasJokerInHand()
						&& (userWordIndex == 0 || perClickRestrictionsFollowed())
						&& (userClickedOnNewSquarePosition())) {
					Cell currSquare = board.getSquares()[letterRow][letterCol];
					Character currChar = you.getWord().charAt(userWordIndex++);
					currSquare.setOccupyingLetter(' ');
					String image = "letter_blank.png";
					addLetterToBoard(letterRow, letterCol, image);
					darkenLetterInHand(currChar, newImg);
					// condition: only add to player positions if clicked square was not already
					// occupied with a letter
					// EDIT: last player position needs to be added even if letter already on
					// board was clicked, to make following clicks possible (only difference:
					// points)
					you.addToPlayerPositions(new Position(letterRow, letterCol));
					you.addToJokerCount(-1);
				}
			}
			
			if (step == 3 && userWordIndex == you.getWord().length()) {
				if (perTurnRestrictionsFollowed()) {
					step = 4;
					okBtn.setDisable(false);
					instructionDisplay.setText(stepPrompt());
				}
			}
			System.out.println(board.getSquares()[letterRow][letterCol].toString());
		});
	}

	private boolean userClickedOnNewSquarePosition() {
		Position clickedPosition = new Position(letterRow, letterCol);
		LinkedList<Position> playerPositions = you.getPlayerPositions();
		
		for (int i = 0; i < playerPositions.getLength(); i++) {
			Position currPos = you.getPlayerPositions().getEntry(i);
			if (currPos.toString().equals(clickedPosition.toString()))
				return false;
		}
		
		return true;
	}

	private boolean playerHasJokerInHand() {
		return you.getJokerCount() >= 1;
	}

	private void darkenLetterInHand(Character currChar, ImageView newImg) {
		int i = 0;
		for (Node child : playerHand.getChildren()) {
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
	}
	
	private boolean squareClickedContainsLetter() {
		if (firstTurn)
			return false;
		
		String currPosString = "(" + letterRow + ", " + letterCol + ")";
		
		for (int i = 0; i < enforcedPositions.getLength(); i++) {
			if (enforcedPositions.getEntry(i).toString().equals(currPosString))
				return true;
		}
		return false;
	}

	private boolean playerHasLetterInHand() {
		Character currWordChar = you.getWord().charAt(userWordIndex);
		Integer subarrayFirstIndex = 0;
		
		if (placedLettersTracker.contains(currWordChar))
			subarrayFirstIndex = placedLettersTracker.getValue(currWordChar) + 1;
		
		if (userWordIndex == 0 || perClickRestrictionsFollowed()) {
			
			for (int i = subarrayFirstIndex; i < 7; i++) {
				Character currLetter = you.getHand().getEntry(i);
				if (currLetter == currWordChar) {
					placedLettersTracker.add(currLetter, i);
					return true;
				}
			}
		}
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
						.equals(enforcedPositions.getEntry(j).toString())) {
					return true;
				}
			}
		}
		return false;
	}

	private void addMultipleLettersToHand() {
		int lastIndex = you.getHand().getLength() - 1;
		while (you.getHand().getLength() < you.getFinalHandSize()) {
			Character newLetter = letterBag.fetchOneLetter();
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
				if (currWordChar == currHandChar) {
					you.getHand().remove(j);
				}
				// OR
				//addLetterToHand(j, "" + letterBag.fetchOneLetter());
			}
		}
	}

	private void makeJokersTakeLetterValue() {
		// TODO Auto-generated method stub
		
	}

	private void refreshEnforcedPositions() {
		for (int i = 0; i < you.getPlayerPositions().getLength(); i++) {
			enforcedPositions.add(you.getPlayerPositions().remove(0));
		}
	}

	private void setupOKBtnActions() {
		okBtn.setDisable(true);
		okBtn.setOnAction(e -> {
			you.setWord(userInput.getText());
			if (step == 2) {
				if (dictionary.containsWord(you.getWord())) {
					step = 3;
					// userInput.clear();
					userInput.setDisable(true);
					okBtn.setDisable(true);
					int val = Player.getValOfWord(you.getWord()); 
					//System.out.println(val);
					// setBoardClickable();
					
					// for (int i = 0; i < ROW_AND_COL_DIMENSION; i++) {
					// for (int j = 0; j < ROW_AND_COL_DIMENSION; j++) {
					// letterRow = i;
					// letterCol = j;
					// //Cell currSquare = board.getSquares()[letterRow][letterCol];
					// board.getSquares()[letterRow][letterCol].setOnMouseClicked(event -> {
					// System.exit(0);
					// char currChar = you.getWord().charAt(userWordIndex);
					// board.getSquares()[letterRow][letterCol].setOccupyingLetter(currChar);
					// userWordIndex++;
					// String img = "letter_J.png";
					// //currSquare.setImage(img);
					// addLetterToBoard(letterRow,letterCol, img);
					// darkenLetterInHand(currChar);
					// });
					// }
					// }
					
					instructionDisplay.setText(stepPrompt());
				} else {
					instructionDisplay.setText(INVALID_WORD_PROMPT);
				}
			} else if (step == 4) {
				// confirm the word
				// give points to player
				you.setActualJokerCount(you.getJokerCount());
				removeLettersFromHand();
				addMultipleLettersToHand();
				makeJokersTakeLetterValue();
				refreshEnforcedPositions(); // somewhat refreshes board state
				step = 1;
				playTurnBtn.setVisible(true);
				exchange1Btn.setVisible(true);
				exchangeAllBtn.setVisible(true);
				skipTurnBtn.setVisible(true);
				userInput.clear();
				userInput.setDisable(true);
				okBtn.setDisable(true);
				cancelBtn.setDisable(true);
				instructionDisplay.setText(stepPrompt());
			}
		});
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
				you.setJokerCount(you.getActualJokerCount());
				clearNewlyOccupiedSquares();
//				resetBoard();
//				resetGrid();
			} else if (step == 4) {
				step = 3;
				instructionDisplay.setText(stepPrompt());
				userWordIndex = 0;
				placedLettersTracker.clear();
				you.setJokerCount(you.getActualJokerCount());
				clearNewlyOccupiedSquares();
				okBtn.setDisable(true);
			}
		});
	}
	
	
	/*for(int i= 0; i<= 6; i++) {
		char playerLetters = LetterBag.shuffledArray[i]; 
		System.out.println(playerLetters);
		PlayerLetters[i] = playerLetters; 
	}
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
	}*/
	
	public static void main(String[] args) {
		launch(args);
	}

}