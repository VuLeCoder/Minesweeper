package org.game.Minesweeper;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game {
	
	private JFrame f;
	
	public Game(int diff) {
		f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(new BorderLayout());
		
		setGame(diff);
	}
	
	public void setGame(int diff) {
		setAttribute(diff);
		Global.restartGame();
		
		f.getContentPane().removeAll();
		
		Taskbar bar = new Taskbar(this);
		f.add(bar.getBar(), BorderLayout.NORTH);

		JPanel p = new Board_2_0(bar, this).getPanel();
		f.add(p, BorderLayout.CENTER);

		f.pack();
		f.setVisible(true);
	}
	
	private void setAttribute(int diff) {
		switch (diff) {
	        case 0 :  { Global.setRow(8);  Global.setCol(10); Global.setMines(10); break;}
	        case 1 :  { Global.setRow(12); Global.setCol(16); Global.setMines(30); break;}
	        default : { Global.setRow(18); Global.setCol(21); Global.setMines(80); break;}
		}
	}
}
