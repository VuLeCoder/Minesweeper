package org.game.Minesweeper;

public class Board_2_0 extends Board {
	
	private Game game;

	public Board_2_0(Taskbar bar, Game game) {
		super(bar);
		this.game = game;
	}
	
	@Override
	public void checkEndGame(int x, int y) {
		if (logic.getValueCell(x, y) == -1) {
            super.checkEndGame(x, y);
            
            // Gọi game show giao diện thua
        } else {
            super.checkEndGame(x, y);
            // Gọi game show giao diện thắng
        }
	}

}
