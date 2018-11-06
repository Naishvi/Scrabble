package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Player extends LetterBag {

	private String currWord;
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

	public static void main(String[] args) {
		String playerWord = "MATH";
		String fName = "Dictionary.txt";

		try (Scanner dictionaryFile = new Scanner(new File(fName))) {
			boolean wordFound = isInDictionary(playerWord, dictionaryFile);
			System.out.println("Word was found: " + wordFound);
		} catch (FileNotFoundException e) {
			System.err.println("Cannot open file " + fName + ". Exiting.");
			System.exit(0);
		}
		System.out.println(getValOfWord(playerWord, new LetterBag()));
	}

}
