package application;

import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class Cell extends GridPane {

	private boolean hasBonus;
	private int bonusMult;
	private char bonusType;
	private String image;
	private String hiddenImage;
	private String fullBonus;
	private Character occupyingLetter;
	private Position position;

	public Cell() {
		hasBonus = false;
		image = "clear_square.png";
		occupyingLetter = null;
	}

	public Cell(String fb) {
		int bonusMult = Integer.parseInt(fb.substring(0, 1));
		char bonusType = fb.charAt(1);
		
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
		fullBonus = fb;

		if (bonusMult == 2) {
			if (bonusType == 'L')
				image = "square_2L.png";
			if (bonusType == 'W')
				image = "square_2W.png";
		} else {
			if (bonusType == 'L')
				image = "square_3L.png";
			if (bonusType == 'W')
				image = "square_3W.png";;
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
		return fullBonus;
	}
	
	public void setBonus(String bonus) {
		fullBonus = bonus;
		bonusMult = Integer.parseInt(bonus.substring(0, 1));
		bonusType = bonus.charAt(1);
		hasBonus = true;
	}
	
	public void consumeBonus() {
		hasBonus = false;
		fullBonus = "";
	}

	public Character getLetter() {
		return occupyingLetter;
	}

	public void setOccupyingLetter(Character ltr) {
		occupyingLetter = ltr;
		setImage("letter_" + ltr + ".png");
	}

	public Character removeLetter() {
		Character oldLetter = occupyingLetter;
		occupyingLetter = null;
		return oldLetter;
	}
	
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String newImg) {
		image = newImg;
	}
	
	public String getHiddenImage() {
		return hiddenImage;
	}

	public void setHiddenImage(String newHiddenImage) {
		hiddenImage = newHiddenImage;
	}
	
	public void setFullBonus(String fb) {
		fullBonus = fb;
	}
	
	public String toString() {
		return /*position.toString() + ", " + */occupyingLetter 
				+ (hasBonus() ? ", Bonus: " + getBonus() : "");
	}
	

	public static void main(String[] args) {

	}

}
