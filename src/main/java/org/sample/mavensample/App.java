package org.sample.mavensample;

import javax.swing.JFrame;

public class App
{
    public static void main( String[] args )
    {
    	BoardGame.createDefaultBoard();

		JFrame f = new JFrame("X");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		f.add(BoardGame.game);
		f.pack();
		f.setResizable(false);
		f.setVisible(true);
    }
}
