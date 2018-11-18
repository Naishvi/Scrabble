package core;

public class Square {
	private Position position;
	private String bonus;
	private LetterTile occupyingLetter;
	
	public Square(Position givenPos, String bonus) {
		position = givenPos;
		this.bonus = bonus;
		occupyingLetter = null;
	}
	
	public void clear() {
		occupyingLetter = null;
	}

	public boolean isClear() {
		return (occupyingLetter == null);
	}
	
	public Position getPosition() {
		return position;
	}
	
	public String getBonus() {
		return bonus;
	}
	
	public LetterTile getLetter() {
		return occupyingLetter;
	}

	public void setOccupyingLetter(char letter) {
		occupyingLetter = new LetterTile(letter);
	}
	
	public String toString() {
		return position.toString() + occupyingLetter.get + bonus;
	}
	
	static void main(String[] args) {
		
	}

}
