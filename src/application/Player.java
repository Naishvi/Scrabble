package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Player extends LetterBag {
	public int fiveTurns = 0; 
	private static String currWord;
	boolean userClickedOk = false; 
	// private Map

	public static boolean isInDictionary(String playerWord, Scanner dictionary) {

		while (dictionary.hasNextLine()) {
			if (dictionary.nextLine().equalsIgnoreCase(playerWord)) {
				return true;
			}
		}
		return false;
	}

	public static int getValOfWord(String word, LetterBag letterValue) {

		int wordPoints = 0;
		char[] letterArray = word.toCharArray();
		for (int i = 0; i < letterArray.length; i++) {
			wordPoints += letterValue.getValue(letterArray[i]);
		}
		return wordPoints;

	}

	public boolean validClicked(Position[] positionArray) {
	
		if(userClickedOk == true) {

			for(int i =0; i < positionArray.length; i++) {
				if(positionArray[0].getRow() == positionArray[i].getRow()) {
					return true; 
				}
			}
			for(int j = 0; j < positionArray.length; j++) {
				if(positionArray[0].getCol() == positionArray[j].getCol()) {
					return true; 
				}

				}
			}
		else 
			return false; 
	}



public static void main(String[] args) {
	String playerWord = "MATH";
	String fName = "Dictionary.txt";
	
	try (Scanner dictionaryFile = new Scanner(new File(fName))) {
		boolean wordFound = false; 
		while(wordFound != true) {
			if( isInDictionary(playerWord, dictionaryFile) == true) {
				System.out.println("Word was found: " + currWord);
			} 
			else {
				//Reset the squares clicked
				System.out.println("The word is not in the dictionary. Type a new word");
			}
		}
	} catch (FileNotFoundException e) {
		System.err.println("Cannot open file " + fName + ". Exiting.");
		System.exit(0);
	}

	validClicked(positionArray);
	System.out.println(getValOfWord(playerWord, new LetterBag()));
	
}

}
