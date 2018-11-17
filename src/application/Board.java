package application;

public class Board {
	Square[][] squares;
	public static final int ROW_DIMENSION = 15;
	public static final int COL_DIMENSION = ROW_DIMENSION;
	
	public void occupyWith(Position pos, Letter ltr) {
		
	}
	
	public boolean removeLetter(Position pos) {
		
	}

	
	public void clear() {
		for (int i = 0; i < ROW_DIMENSION; i++) {
			for (int j = 0; j < COL_DIMENSION; j++) {
				squares[i][j].clear();
			}
		}
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		
	}

}
