package application;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import javafx.scene.image.Image;

public class LetterBag {
	HashMap<Character, Integer> letterValue;
	HashMap<Character, Image> letterImage; 
	private static final int capacity = 108;
	private int numberOfLetters;

	private static char[] letterArray = { 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'B', 'B', 'C', 'C', 'D', 'D', 'D', 'D',
			'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'F', 'F', 'G', 'G', 'G', 'H', 'H', 'I', 'I',
			'I', 'I', 'I', 'I', 'I', 'I', 'I', 'J', 'K', 'L', 'L', 'L', 'L', 'M', 'M', 'N', 'N', 'N', 'N', 'N', 'N',
			'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'P', 'P', 'Q', 'R', 'R', 'R', 'R', 'R', 'R', 'S', 'S', 'S', 'S',
			'T', 'T', 'T', 'T', 'T', 'T', 'U', 'U', 'U', 'U', 'V', 'V', 'W', 'W', 'X', 'Y', 'Y', 'Z', ' ', ' ' };
	public static void shuffle() {
		Collections.shuffle(Arrays.asList(letterArray));
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
	}
	public void Image() {
		letterImage = new HashMap<>();
		letterImage.put('A', new Image("letter_A.png"));
		letterImage.put('B', new Image("letter_B.png"));
		letterImage.put('C', new Image("letter_C.png"));
		letterImage.put('D', new Image("letter_D.png"));
		letterImage.put('E', new Image("letter_E.png"));
		letterImage.put('F', new Image("letter_F.png"));
		letterImage.put('G', new Image("letter_G.png"));
		letterImage.put('H', new Image("letter_H.png"));
		letterImage.put('I', new Image("letter_I.png"));
		letterImage.put('J', new Image("letter_J.png"));
		letterImage.put('K', new Image("letter_K.png"));
		letterImage.put('L', new Image("letter_L.png"));
		letterImage.put('M', new Image("letter_M.png"));
		letterImage.put('N', new Image("letter_N.png"));
		letterImage.put('O', new Image("letter_O.png"));
		letterImage.put('P', new Image("letter_P.png"));
		letterImage.put('Q', new Image("letter_Q.png"));
		letterImage.put('R', new Image("letter_R.png"));
		letterImage.put('S', new Image("letter_S.png"));
		letterImage.put('T', new Image("letter_T.png"));
		letterImage.put('U', new Image("letter_U.png"));
		letterImage.put('V', new Image("letter_V.png"));
		letterImage.put('W', new Image("letter_W.png"));
		letterImage.put('X', new Image("letter_X.png"));
		letterImage.put('Y', new Image("letter_Y.png"));
		letterImage.put('Z', new Image("letter_Z.png"));
	}


public int getValue(Character letter) {
	return letterValue.get(letter);
}

public Image getBag(Character letter) {
	return letterImage.get(letter);
	
}
public javafx.scene.image.Image LetterToImage(Image i) {
	return letterImage.get(i); 
}



}
