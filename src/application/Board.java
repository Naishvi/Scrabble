package application;

public class Board {
	Square[][] board;
	
	public Board(int dimensions) {
		board = new Square[dimensions][dimensions];
	}
}
