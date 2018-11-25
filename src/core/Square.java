package core;

import application.LetterTile;
import application.Position;

public class Square {
	private Position position;
	private String bonus;
	private LetterTile occupyingLetter;
	
	public Square(Position givenPos, String bonus) {
		position = givenPos;
		this.bonus = bonus;
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

	public void setOccupyingLetter(LetterTile letter) {
		occupyingLetter = letter;
	}
	
	public LetterTile removeLetter() {
		LetterTile oldLetter = occupyingLetter;
		occupyingLetter = null;
		return oldLetter;
	}
	
	public String toString() {
		return position.toString() + 
				", " + occupyingLetter + 
				(bonus == "" ? "" : ", Bonus:" + bonus);
	}
	
	static void main(String[] args) {
		
	}

}
