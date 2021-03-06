// Most recent version

package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Player extends Thread {

	private int score;
	private int id;
	private String currWord;
	private LinkedList<Character> hand;
	private static final int MAX_HAND_SIZE = 7;
	private LinkedList<Position> letterPlacements; // only keeps track of squares where player
												   // placed a letter
	private LinkedList<Position> validClicksSequence; // keeps track of all squares clicked
													  // to determine placement validity
	private int jokerCount; // refreshed at every action, even cancellable ones
	private int originalJokerCount; // actual number of jokers before/after turn
	private Player opponent;
	
	Socket socket;
	BufferedReader input;
	PrintWriter output;
	
	public Player(Socket newSocket, int givenId, LetterBag letters) {
		score = 0;
		socket = newSocket;
		id = givenId;
		currWord = "";
		hand = new LinkedList<>();
		addLettersToHand(letters);
		letterPlacements = new LinkedList<>();
		validClicksSequence = new LinkedList<>();
		opponent = null;
		
		try {
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(socket.getOutputStream(), true);
			output.println("You are Player " + id);
			output.println("Waiting for opponent to connect...");
		} catch (IOException e) {
			System.out.println("Player died: " + e);
		}
	}
	
	public void run() {
		try {
			output.println("All players connected");
			
			if (id == 1) {
				output.println("Your move");
			}
			
			while (true) {
				String encode = 
			}
		} finally {
			try {
				socket.close();
			} catch (IOException e) {}
		}
	}
	
	public int getScore() {
		return score;
	}
	
	public void addToScore(int value) {
		score += value;
	}
	
	public void setScore(int value) {
		score = value;
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
		validClicksSequence.add(pos);
	}
	
	public LinkedList<Position> getPlayerPositions() {
		return validClicksSequence;
	}
	
	public void addToLetterPlacements(Position pos) {
		letterPlacements.add(pos);
	}
	
	public LinkedList<Position> getLetterPlacements() {
		return letterPlacements;
	}
	
	public Player getOpponent() {
		return opponent;
	}
	
	public void setOpponent(Player opp) {
		opponent = opp;
	}
	
	private boolean wordIsInDictionary(Scanner dictionary) {
		while (dictionary.hasNextLine()) {
			if (dictionary.nextLine().equalsIgnoreCase(currWord)) {
				return true;
			}
		}
		return false;
	}

	public static int getValOfWord(String word, LetterBag letterValue) {
		int wordPoints = 0;
		char[] letterArray = word.toCharArray();
		for (int i = 0; i < letterArray.length; i++) {
			wordPoints += letterValue.getValueOf(letterArray[i]);
		}
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
		System.out.println(getValOfWord(playerWord, new LetterBag()));
	}

}
