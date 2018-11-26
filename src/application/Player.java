package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Player extends LetterBag {

	private static String currWord;
	private LinkedList<Character> hand;
	private static final int MAX_HAND_SIZE = 7;
	private LinkedList<Position> currPlacement;
	private int jokerCount; // refreshed at every action, even cancellable ones
	private int originalJokerCount; // actual number of jokers before/after turn
	static LetterBag letterValue = new LetterBag(); 
	public Player(LetterBag letters) {
		String currWord = "";
		hand = new LinkedList<>();
		addLettersToHand(letters);
		currPlacement = new LinkedList<>();
	}

	public void addLettersToHand(LetterBag letterBag) {
		while (hand.getLength() < MAX_HAND_SIZE) {
			Character currLetter = letterBag.fetchOneLetter();
			getHand().add(currLetter);
			if (currLetter == ' ') {
				addToJokerCount(1);
				addToActualJokerCount(1);
			}
		}
	}

	public String getWord() {
		return currWord;
	}
	
	public void setWord(String newWord) {
		currWord = newWord;
	}
	
	public LinkedList<Character> getHand() {
		return hand;
	}
	
	public int getFinalHandSize() {
		return MAX_HAND_SIZE;
	}
	
	public boolean handIsFull() {
		return hand.getLength() == MAX_HAND_SIZE;
	}
	
	public int getJokerCount() {
		return jokerCount;
	}
	
	public void setJokerCount(int value) {
		jokerCount = value;
	}
	
	public void addToJokerCount(int value) {
		jokerCount += value;
	}
	
	public int getActualJokerCount() {
		return originalJokerCount;
	}
	
	public void setActualJokerCount(int value) {
		originalJokerCount = value;
	}
	
	public void addToActualJokerCount(int value) {
		originalJokerCount += value;
	}
	
	public void addToPlayerPositions(Position pos) {
		currPlacement.add(pos);
	}
	
	public LinkedList<Position> getPlayerPositions() {
		return currPlacement;
	}
	
	private static boolean wordIsInDictionary(Scanner dictionary) {
		while (dictionary.hasNextLine()) {
			if (dictionary.nextLine().equalsIgnoreCase(currWord)) {
				return true;
			}
		}
		return false;
	}


	public static int getValOfWord(String word) {

		int wordPoints = 0;
		char[] letterArray = word.toCharArray();
		for (int i = 0; i < letterArray.length; i++) {
			wordPoints += letterValue.getValueOf(letterArray[i]);
		}
		System.out.println(wordPoints);
		return wordPoints;

	}
	
	

	public static void main(String[] args) {
		String playerWord = "MATH";
		String fName = "Dictionary.txt";

		try (Scanner dictionaryFile = new Scanner(new File(fName))) {
			boolean wordFound = wordIsInDictionary(dictionaryFile);
			System.out.println("Word was found: " + wordFound);
		} catch (FileNotFoundException e) {
			System.err.println("Cannot open file " + fName + ". Exiting.");
			System.exit(0);
		}
		System.out.println(getValOfWord(playerWord));
	}

}
