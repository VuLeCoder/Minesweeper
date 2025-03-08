package org.game.Minesweeper;

public class Board_2_0 extends Board {
	
	private final Game game;

	public Board_2_0(Taskbar bar, Game game) {
		super(bar);
		this.game = game;
	}
	
	@Override
	public void checkEndGame(int x, int y) {
		if(super.getSquare(x, y).getText().equals(Global.flag)) {
			return;
		}
		
		super.checkEndGame(x, y);
		
		if (logic.getValueCell(x, y) == -1) {
            game.loseGame();
        }
		
		if (logic.isSafe()) {
            game.winGame();
        }
	}

}
