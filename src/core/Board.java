package core;

public class Board {
	Square[][] squares;
	private static final int ROW_DIMENSION = 15;
	private static final int COL_DIMENSION = ROW_DIMENSION;
	
	
	public void occupyWith(Position pos, LetterTile ltr) {
		int r = pos.getRow();
		int c = pos.getCol();
		if ((r < 0 || r >= ROW_DIMENSION) || (c < 0 || c >= COL_DIMENSION)) {
			throw new IndexOutOfBoundsException("Invalid position");
		}
		
		if (!squares[r][c].isClear())
			removeLetterAt(pos);
		squares[r][c].setOccupyingLetter(ltr.getLetter());
	}
	
	public boolean removeLetterAt(Position pos) {
		int r = pos.getRow();
		int c = pos.getCol();
		if (!squares[r][c].isClear()) {
			squares[r][c] = null;
			refreshGUIBoardState();
			return true;
		}
		return false;
	}

	
	public void clear() {
		for (int i = 0; i < ROW_DIMENSION; i++) {
			for (int j = 0; j < COL_DIMENSION; j++) {
				squares[i][j].removeLetter();
			}
		}
		refreshGUIBoardState();
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		
	}

}
