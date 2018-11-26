package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class Dictionary {
	private HashSet<String> dictionary = new HashSet<>(); 
	
	public Dictionary(String dictionaryFileName) {
		 
		String fName = dictionaryFileName;

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

	public boolean containsWord(String playerWord) {	
		if (dictionary.contains(playerWord)) {
			return true;
		}
		else
			return false;
	}
	
	

}


