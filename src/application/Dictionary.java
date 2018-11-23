package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class Dictionary {
	static HashSet<String> dictionary = new HashSet<>(); 
	public Dictionary() {
		 
		String fName = "Dictionary.txt";

		try (Scanner dictionaryFile = new Scanner(new File(fName))) {
			while(dictionaryFile.hasNextLine()) {
				dictionary.add(dictionaryFile.nextLine()); 
			}

		}
		catch (FileNotFoundException e) {
			System.err.println("Cannot open file " + fName + ". Exiting.");
			System.exit(0);
		}

	}

	public static boolean containsWord(String playerWord) {	
		if (dictionary.contains(playerWord)) {
			System.out.println("Yes the word is in the dictionary!");
			return true;
		}
		else
			return false;
	}
	
	

}
