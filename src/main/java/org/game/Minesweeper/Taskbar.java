package org.game.Minesweeper;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Taskbar {
	private final Game game;
	
    private final Color BACKGROUND_COLOR = new Color(74, 117, 44);
    private final int HEIGHT_BAR = 60;
    private final int HEIGHT = 46;
    private final int PADDING = (HEIGHT_BAR - HEIGHT) / 2;
    private final int FONT_SIZE = Global.fontSize + 8;
    private final int DROP_DOWN_WITDH = 110;
    private final JTextArea HELP = new JTextArea(
    		"\n" + 
            " Ô trên trái để lựa chọn độ khó.\n \n" +
            " Nút ở giữa là trạng thái chuột, trạng thái chuột gồm đặt cờ và đào.\n \n" +
            " Thay đổi trạng thái bằng cách nhấp vào nút đó hoặc nhấn phím F.\n"
        );
    
    private final JPanel bar;
    private JButton statusPointer;
    private JLabel num;

    public Taskbar(Game game) {
    	this.game = game;
    	
        bar = new JPanel();
        statusPointer = new JButton();
        createHelp();
        createBar();
    }

    public JButton getStatusPointer() {
		return statusPointer;
	}

	public JLabel getNum() {
		return num;
	}
	
	private void createHelp() {
//		HELP.setPreferredSize(new Dimension(500, 100));
		HELP.setEditable(false);
	    HELP.setFocusable(false);
	    HELP.setFont(new Font("Arial", Font.BOLD, 14));
        HELP.setBackground(new Color(240, 240, 240)); // Màu nền sáng
        HELP.setForeground(new Color(50, 50, 50)); // Màu chữ đậm hơn
//		HELP.setWrapStyleWord(true);
//		HELP.setLineWrap(true);
	}

	private void createBar() {
        bar.setPreferredSize(new Dimension(Global.getCol() * Global.squareSize, HEIGHT_BAR));
        bar.setLayout(new BorderLayout());
        bar.setBackground(Color.RED);

        bar.add(createDifficultyDropdown(), BorderLayout.WEST);
        bar.add(createCenterPanel(), BorderLayout.CENTER);
        bar.add(createHelpButton(), BorderLayout.EAST);
    }

    private JPanel createCenterPanel() {
        JPanel main = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, PADDING));
        main.setBackground(BACKGROUND_COLOR);

        setupStatusPointer();
        main.add(statusPointer);
        main.add(createFlagCounter());
        
        return main;
    }

    private void setupStatusPointer() {
        statusPointer.setPreferredSize(new Dimension(HEIGHT, HEIGHT));
        statusPointer.setMargin(new Insets(0, 0, 0, 0));
        statusPointer.setFont(new Font(Global.iconFont, Font.BOLD, Global.fontSize));
        statusPointer.setFocusPainted(false);
        statusPointer.setBorderPainted(false);
        
        statusPointer.setText(Global.dig);
        
        statusPointer.addMouseListener(new MouseAdapter() {
        	public void mousePressed(MouseEvent e) {
        		Global.isFlag = !Global.isFlag;
        		changeStatus();
        	}
        });
    }

    private JPanel createFlagCounter() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBackground(Color.black);

        JLabel flagLabel = new JLabel("🚩", SwingConstants.CENTER);
        flagLabel.setPreferredSize(new Dimension(HEIGHT, HEIGHT));
        flagLabel.setBorder(new EmptyBorder(PADDING, 0, 0, 0));
        flagLabel.setFont(new Font(Global.iconFont, Font.BOLD, FONT_SIZE));
        flagLabel.setForeground(Color.RED);
        flagLabel.setBackground(BACKGROUND_COLOR);
        flagLabel.setOpaque(true);
        panel.add(flagLabel);

        num = new JLabel(String.valueOf(Global.getMines()), SwingConstants.CENTER);
        num.setFont(new Font(Global.numberFont, Font.BOLD, Global.fontSize));
        num.setPreferredSize(new Dimension(HEIGHT, HEIGHT));
        num.setForeground(Color.WHITE);
        num.setBackground(BACKGROUND_COLOR);
        num.setOpaque(true);
        panel.add(num);

        return panel;
    }

    public void changeStatus() {
        statusPointer.setText(Global.isFlag ? Global.flag : Global.dig);
    }

    public void changeNumberFlag(int n) {
        num.setText(n + "");
    }

    private JPanel createDifficultyDropdown() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setPreferredSize(new Dimension(DROP_DOWN_WITDH + 10, HEIGHT_BAR));
        panel.setBackground(BACKGROUND_COLOR);

        String[] difficulty = {"Easy", "Medium", "Hard"};
        JComboBox<String> diffBox = new JComboBox<>(difficulty);
        diffBox.setPreferredSize(new Dimension(DROP_DOWN_WITDH, HEIGHT));
        diffBox.setSelectedIndex(Global.diff);
        diffBox.addItemListener(new ItemListener() {
        	@Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getSource() == diffBox) {
                	if (diffBox.getSelectedItem().equals("Easy")) {
                		if(Global.diff == 0) {
                			return;
                		}
                		game.setGame(0);
                		
                	} else if (diffBox.getSelectedItem().equals("Medium")) {
                		if(Global.diff == 1) {
                			return;
                		}
                		game.setGame(1);
                		
                	} else {
                		if(Global.diff == 2) {
                			return;
                		}
                		game.setGame(2);
                	}
                    
                }
            }
        });
        
        panel.add(diffBox);
        return panel;
    }

    private JPanel createHelpButton() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, PADDING, PADDING));
        panel.setBackground(BACKGROUND_COLOR);

        JButton helpButton = new JButton();
        helpButton.setPreferredSize(new Dimension(HEIGHT, HEIGHT));
        helpButton.setMargin(new Insets(0, 0, 0, 0));
        helpButton.setFont(new Font(Global.iconFont, Font.BOLD, Global.fontSize));
        helpButton.setFocusPainted(false);
        helpButton.setBorderPainted(false);
        helpButton.setBackground(new Color(252, 222, 190));
        helpButton.setText("❓");
        
        helpButton.addMouseListener(new MouseAdapter() {
        	public void mousePressed(MouseEvent e) {
        		JDialog help = new JDialog(game.getMainGame(), "Help", true);
        		help.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        		help.setResizable(false);
        		help.add(HELP);
        		help.pack();
        		
        		int x = game.getMainGame().getX() + (game.getMainGame().getWidth() - help.getWidth()) / 2;
                int y = game.getMainGame().getY() + (game.getMainGame().getHeight() - help.getHeight()) / 2;
                help.setLocation(x, y);
                
                help.setVisible(true);
        	}
        });
        
        panel.add(helpButton);
        return panel;
    }

    public JPanel getBar() {
        return bar;
    }
}
