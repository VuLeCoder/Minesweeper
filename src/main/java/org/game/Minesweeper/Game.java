package org.game.Minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Game {
	
	private final JFrame f;
	
	public Game() {
		f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		f.setLayout(new BorderLayout());
	}
	
	public JFrame getMainGame() {
		return f;
	}

	public void setGame(int diff) {
		setAttribute(diff);
		Global.diff = diff;
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

	private void showMessage(String state, String image) {
//		JDialog message = new JDialog(f, state, true);
		JDialog message = new JDialog(f, state);
        message.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        message.setLayout(new BorderLayout());

        // Thêm hình ảnh vào JLabel
        ImageIcon imageIcon = new ImageIcon(image);
        JLabel label = new JLabel(imageIcon);
        message.add(label, BorderLayout.CENTER);
        
        JButton tryAgain = new JButton();
        tryAgain.setPreferredSize(new Dimension(325, 50));
        tryAgain.setBackground(new Color(74, 117, 44));
        tryAgain.setFocusPainted(false);
        tryAgain.setBorderPainted(false);
        tryAgain.setText(state.equals("You win") ? "Play Again" : "Try Again");
        tryAgain.setForeground(Color.white);
        tryAgain.setFont(new Font(Global.numberFont, Font.PLAIN, Global.fontSize));
        
        tryAgain.addMouseListener(new MouseAdapter() {
        	public void mousePressed(MouseEvent e) {
        		message.dispose();
        		setGame(Global.diff);
        	}
        });
        message.add(tryAgain, BorderLayout.SOUTH);

		message.pack();
		
		int x = f.getX() + (f.getWidth() - message.getWidth()) / 2;
        int y = f.getY() + (f.getHeight() - message.getHeight()) / 2;
        message.setLocation(x, y);
		
        message.setVisible(true);
	}

	public void winGame() {
		showMessage("You win", "D:\\CODE_Java\\CODE\\Minesweeper\\src\\image\\win.png");
	}
	
	public void loseGame() {
		showMessage("You lose", "D:\\CODE_Java\\CODE\\Minesweeper\\src\\image\\lose.png");
	}

}