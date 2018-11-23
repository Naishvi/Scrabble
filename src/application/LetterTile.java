package application;

public class LetterTile {
	private char letter;
	private int value;
	private Position position;
	private String image;

	public LetterTile(char letter, int value, Position position, String image) {
		this.letter = letter;
		this.value = value;
		this.position = position;
		this.image = image;
	}

	public char getLetter() {
		return letter;
	}

	public int getValue() {
		return value;
	}
	
	public Position getPosition() {
		return position;
	}
	
	public String getImage() {
		return image;
	}
	
}
