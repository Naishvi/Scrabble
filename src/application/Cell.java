package application;

import java.io.File;

import javafx.scene.layout.Pane;

public class Cell extends Pane {

	private boolean hasBonus;
	private int bonusMult;
	private char bonusType;
	private File image;
	private String fullBonus;
	private LetterTile occupyingLetter;
	private Position position;

	public Cell() {
		hasBonus = false;
		image = new File("letters_and_squares/clear_square.png");
		occupyingLetter = null;
	}

	public Cell(int bonusMult, char bonusType) {
		if (bonusMult != 2 && bonusMult != 3) {
			System.out.println("Multiplicator: 2 and 3 only");
			return;
		}
		if (bonusType != 'L' && bonusType != 'W') {
			System.out.println("Bonus type: L and W only");
			return;
		}

		hasBonus = true;
		this.bonusMult = bonusMult;
		this.bonusType = bonusType;

		if (bonusMult == 2) {
			if (bonusType == 'L')
				image = new File("letters_and_squares/square_2L.png");
			if (bonusType == 'W')
				image = new File("letters_and_squares/square_2W.png");
		} else {
			if (bonusType == 'L')
				image = new File("letters_and_squares/square_3L.png");
			if (bonusType == 'W')
				image = new File("letters_and_squares/square_3L.png");
		}
	}

	public void clear() {
		occupyingLetter = null;
	}

	public boolean isClear() {
		return (occupyingLetter == null);
	}

	public void setPosition(int row, int col) {
		position = new Position(row, col);
	}
	
	public Position getPosition() {
		return position;
	}

	public boolean hasBonus() {
		return (!("" + bonusMult + bonusType).equals(""));
	}
	
	public String getBonus() {
		return ("" + bonusMult + bonusType);
	}

	public LetterTile getLetter() {
		return occupyingLetter;
	}

	public void setOccupyingLetter(LetterTile letter) {
		occupyingLetter = letter;
	}

	public LetterTile removeLetter() {
		LetterTile oldLetter = occupyingLetter;
		occupyingLetter = null;
		return oldLetter;
	}

	public String toString() {
		return position.toString() + ", " + occupyingLetter + (hasBonus() ? ", Bonus:" + getBonus() : "");
	}
	

	public static void main(String[] args) {

	}

}
