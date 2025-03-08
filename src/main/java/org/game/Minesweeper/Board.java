package org.game.Minesweeper;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JPanel;

public class Board {
	final Logic logic;
	private final Square[][] board;

	private int numberFlag = 0;
	private final JPanel panel;

	public Board(Taskbar bar) {
		logic = new Logic();
		board = new Square[Global.getRow()][Global.getCol()];
		int width = Global.getCol() * Global.squareSize;
		int height = Global.getRow() * Global.squareSize;

		panel = new JPanel();
		panel.setPreferredSize(new Dimension(width, height));
		panel.setLayout(new GridLayout(Global.getRow(), Global.getCol()));

		for (int i = 0; i < Global.getRow(); ++i) {
			for (int j = 0; j < Global.getCol(); ++j) {
				board[i][j] = new Square(i, j, this, bar);
				panel.add(board[i][j].getButton());
			}
		}

	}

	public void endGame() {
		for (int i = 0; i < Global.getRow(); ++i) {
			for (int j = 0; j < Global.getCol(); ++j) {
				board[i][j].removeMouseEvent();
			}
		}
	}

	public JPanel getPanel() {
		return panel;
	}

	public int getNumberFlag() {
		return numberFlag;
	}

	public void setNumberFlag(int numberFlag) {
		this.numberFlag = numberFlag;
	}

	public Square getSquare(int x, int y) {
		return board[x][y];
	}

	// Kết thúc game
	private void showAllMine() {
		for (int i = 0; i < Global.getRow(); ++i) {
			for (int j = 0; j < Global.getCol(); ++j) {
				if (board[i][j].getButton().getText().equals(Global.flag) && logic.getValueCell(i, j) != -1) {
					board[i][j].setWrongFlag();
				}

				if (logic.getValueCell(i, j)  == -1) {
					board[i][j].color();
				}
			}
		}
	}

	public void checkEndGame(int x, int y) {
		if(board[x][y].getButton().getText().equals("🚩")) {
			return;
		}
		
		if (logic.getValueCell(x, y) == -1) {
			showAllMine();
			endGame();
		}

		if (logic.isSafe()) {
			endGame();
		}
	}

}