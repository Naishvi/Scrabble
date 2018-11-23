package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Player extends LetterBag {
	static LetterBag letterValue = new LetterBag(); 
	private String currWord;
	private LinkedList<LetterTile> hand;
	// private Map
	//public Player() {
		 
	//}


	public static int getValOfWord(String word) {

		int wordPoints = 0;
		char[] letterArray = word.toCharArray();
		for (int i = 0; i < letterArray.length; i++) {
			wordPoints += letterValue.getValue(letterArray[i]);
		}
		System.out.println(wordPoints);
		return wordPoints;

	}

	/*public boolean validClicked(Position[] positionArray) {

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
	}*/



	public static void main(String[] args) {
			//validClicked(positionArray);
			

		}

	}
	
