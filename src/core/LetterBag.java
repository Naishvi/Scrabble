package core;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.image.ImageView;

public class LetterBag {
	HashMap<Character, Integer> letterValue;
	public static HashMap<Character, String> letterImage; 
	private static final int CAPACITY = 108;
	
	List<Character> s = new ArrayList<>();
	

	public static char[] letterArray = { 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'B', 'B', 'C', 'C', 'D', 'D', 'D', 'D',
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
	

	
	public static void main(String[] args) {
		shuffle(letterArray);					
	}


	public LetterBag() {
		letterValue = new HashMap<>();

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


	public int getValue(Character letter) {
		return letterValue.get(letter);
	}

	public static String getBag(Character letter) {
		return letterImage.get(letter);

	}



}
