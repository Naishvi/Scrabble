package application;
import java.io.File;

public class Square {
	
	private boolean hasBonus;
	private int bonusMult;
	private char bonusType;
	private File image;
	private String fullBonus;
	
	public Square() {
		hasBonus = false;
		image = new File("letters_and_squares/clear_square.png");
	}
	
	public Square(int bonusMult, char bonusType) {
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

	public static void main(String[] args) {
		
	}

}
