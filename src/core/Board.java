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
		
		if (!squares[r][c].isClear()) {
			removeLetter(pos);
			squares[r][c].setOccupyingLetter(ltr.getLetter());
		} else {
			squares[r][c].setOccupyingLetter(ltr.getLetter());
		}
	}
	
	public boolean removeLetter(Position pos, LetterBag ltrBag) {
		int r = pos.getRow();
		int c = pos.getCol();
		if (!squares[r][c].isClear()) {
			ltrBag.add(squares[r][c]);
			squares[r][c] = null;
			refreshGUIBoardState();
			return true;
		}
		return false;
	}

	
	public void clear() {
		for (int i = 0; i < ROW_DIMENSION; i++) {
			for (int j = 0; j < COL_DIMENSION; j++) {
				squares[i][j].clear();
			}
		}
		refreshGUIBoardState();
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		
	}

}
