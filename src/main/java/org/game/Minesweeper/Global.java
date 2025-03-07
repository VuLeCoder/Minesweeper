package org.game.Minesweeper;

public class Global {
	public static final String numberFont = "Arial Black", iconFont = "Segoe UI Emoji";
	public static final String dig = "⛏️".substring(0, 1), flag = "🚩", bomb = "💣";
	
 	public static int squareSize = 50, fontSize = 21;
 	private static int row, col, mines;
	
	public static boolean isFlag = false;
	public static boolean isFirstClick = true;
	public static int diff = 1;
	
    public static final int[] dx = { -1, -1, -1, 0, 0, 1, 1, 1 };
    public static final int[] dy = { -1, 0, 1, -1, 1, -1, 0, 1 };
    
    public static final class Pair<K, V> {
        public final K first;
        public final V second;

        public Pair(K first, V second) {
            this.first = first;
            this.second = second;
        }
    }

	public static int getRow() {
		return row;
	}

	public static void setRow(int row) {
		Global.row = row;
	}

	public static int getCol() {
		return col;
	}

	public static void setCol(int col) {
		Global.col = col;
	}

	public static int getMines() {
		return mines;
	}

	public static void setMines(int mines) {
		Global.mines = mines;
	}
	
	public static void restartGame() {
		isFirstClick = true;
		isFlag = false;
	}
	
	public static int x = 0;
	
}