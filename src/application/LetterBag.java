package application;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class LetterBag {
	private HashMap<Character, Integer> letterValue;
	public static HashMap<Character, String> letterImage; 
	private LinkedList<Character> allLetters;
	private static final int CAPACITY = 100;
	private int numberOfLetters;

	private static char[] letterArray = { 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'B', 'B', 'C', 'C', 'D', 'D', 'D', 'D',
			'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'F', 'F', 'G', 'G', 'G', 'H', 'H', 'I', 'I',
			'I', 'I', 'I', 'I', 'I', 'I', 'I', 'J', 'K', 'L', 'L', 'L', 'L', 'M', 'M', 'N', 'N', 'N', 'N', 'N', 'N',
			'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'P', 'P', 'Q', 'R', 'R', 'R', 'R', 'R', 'R', 'S', 'S', 'S', 'S',
			'T', 'T', 'T', 'T', 'T', 'T', 'U', 'U', 'U', 'U', 'V', 'V', 'W', 'W', 'X', 'Y', 'Y', 'Z', ' ', ' ' };

	public static char[] shuffledArray = shuffle(letterArray); 
	
	public static char[] shuffle(char [] letterArray) {
		Random r = new Random();  // Random number generator			
		 
		for (int i=0; i<letterArray.length; i++) {
		    int randomPosition = r.nextInt(letterArray.length);
		    char temp = letterArray[i];
		    letterArray[i] = letterArray[randomPosition];
		    letterArray[randomPosition] = temp;
		}
		return letterArray;
	}
	public LetterBag() {
		letterValue = new HashMap<>();
		allLetters = new LinkedList<>();
		constructHashmap();
		fillBag();
		numberOfLetters = CAPACITY;
		allLetters.shuffle();
		letterImage = new HashMap<>();
		letterImage.put('A', "letter_A.png");
		letterImage.put('B', "letter_B.png");
		letterImage.put('C', "letter_C.png");
		letterImage.put('D', "letter_D.png");
		letterImage.put('E', "letter_E.png");
		letterImage.put('F', "letter_F.png");
		letterImage.put('G', "letter_G.png");
		letterImage.put('H', "letter_H.png");
		letterImage.put('I', "letter_I.png");
		letterImage.put('J', "letter_J.png");
		letterImage.put('K', "letter_K.png");
		letterImage.put('L', "letter_L.png");
		letterImage.put('M', "letter_M.png");
		letterImage.put('N', "letter_N.png");
		letterImage.put('O', "letter_O.png");
		letterImage.put('P', "letter_P.png");
		letterImage.put('Q', "letter_Q.png");
		letterImage.put('R', "letter_R.png");
		letterImage.put('S', "letter_S.png");
		letterImage.put('T', "letter_T.png");
		letterImage.put('U', "letter_U.png");
		letterImage.put('V', "letter_V.png");
		letterImage.put('W', "letter_W.png");
		letterImage.put('X', "letter_X.png");
		letterImage.put('Y', "letter_Y.png");
		letterImage.put('Z', "letter_Z.png");
	}
	
	public void constructHashmap() {
		letterValue.put('A', 1);
		letterValue.put('B', 3);
		letterValue.put('C', 3);
		letterValue.put('D', 2);
		letterValue.put('E', 1);
		letterValue.put('F', 4);
		letterValue.put('G', 2);
		letterValue.put('H', 4);
		letterValue.put('I', 1);
		letterValue.put('J', 8);
		letterValue.put('K', 5);
		letterValue.put('L', 1);
		letterValue.put('M', 3);
		letterValue.put('N', 1);
		letterValue.put('O', 1);
		letterValue.put('P', 3);
		letterValue.put('Q', 10);
		letterValue.put('R', 1);
		letterValue.put('S', 1);
		letterValue.put('T', 1);
		letterValue.put('U', 1);
		letterValue.put('V', 4);
		letterValue.put('W', 4);
		letterValue.put('X', 8);
		letterValue.put('Y', 4);
		letterValue.put('Z', 10);
		letterValue.put(' ', 0);
	}
	
	private void fillBag() {
		allLetters.addMultiple('A', 9);
		allLetters.addMultiple('B', 2);
		allLetters.addMultiple('C', 2);
		allLetters.addMultiple('D', 4);
		allLetters.addMultiple('E', 12);
		allLetters.addMultiple('F', 2);
		allLetters.addMultiple('G', 3);
		allLetters.addMultiple('H', 2);
		allLetters.addMultiple('I', 9);
		allLetters.addMultiple('J', 1);
		allLetters.addMultiple('K', 1);
		allLetters.addMultiple('L', 4);
		allLetters.addMultiple('M', 2);
		allLetters.addMultiple('N', 6);
		allLetters.addMultiple('O', 8);
		allLetters.addMultiple('P', 2);
		allLetters.addMultiple('Q', 1);
		allLetters.addMultiple('R', 6);
		allLetters.addMultiple('S', 4);
		allLetters.addMultiple('T', 6);
		allLetters.addMultiple('U', 4);
		allLetters.addMultiple('V', 2);
		allLetters.addMultiple('W', 2);
		allLetters.addMultiple('X', 1);
		allLetters.addMultiple('Y', 2);
		allLetters.addMultiple('Z', 1);
		allLetters.addMultiple(' ', 2);
	}

	public int getValueOf(Character letter) {
		return letterValue.get(letter);
	}
	
	public Character fetchOneLetter() {
		numberOfLetters--;
		return allLetters.remove(0);
	}
	
	
	public static void main(String[] args) {
		LetterBag letters = new LetterBag();
		for (int i = 0; i < CAPACITY; i++) {
			Character myLetter = letters.fetchOneLetter();
			System.out.println(myLetter + "(" + letters.getValueOf(myLetter) + ")");
		}
		
	}

	public static String getBag(Character letter) {
		return letterImage.get(letter);

	}
}
