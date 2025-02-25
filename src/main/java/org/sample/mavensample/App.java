package org.sample.mavensample;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class App
{
	private static int witdh, height;
	
	public static JButton flagButton = new JButton();
	public static String dig = "⛏️".substring(0, 1), flag = "🚩";
	
	private static JPanel menuBar() {
		JPanel p = new JPanel();
		
		p.setPreferredSize(new Dimension(witdh, 60));
		p.setBackground(new Color(74, 117, 44));
		p.setLayout(new FlowLayout());
		
		flagButton.setPreferredSize(new Dimension(60, 50));
		flagButton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 16));
		flagButton.setText(dig);
		
		flagButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(flagButton.getText().equals(dig)) {
					flagButton.setText(flag);
					BoardGame.isFlag = true;
				} else {
					flagButton.setText(dig);
					BoardGame.isFlag = false;
				}
			}
		});
		
		p.add(flagButton);
		
		return p;
	}
	
    public static void main( String[] args )
    {
    	BoardGame.createDefaultBoard();
    	witdh = (LogicGame.COL * 40); height = (LogicGame.ROW * 40) + 100; 

		JFrame f = new JFrame("X");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		f.setSize(witdh, height);
		
		f.add(menuBar());
		f.add(BoardGame.game);
		
//		f.pack();
		f.setResizable(false);
		f.setVisible(true);
    }
}