package org.game.Minesweeper;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.JButton;

public class Square {
	private final Board board;
	private final Taskbar bar;

    private final Color HOVER_COLOR = Color.GRAY;
    private final Color DEFAULT_COLOR_0 = new Color(162, 209, 73);  // Đậm
    private final Color CLICKED_COLOR_0 = new Color(229, 194, 159);
    private final Color DEFAULT_COLOR_1 = new Color(135, 175, 58);  // Nhạt
    private final Color CLICKED_COLOR_1 = new Color(215, 184, 153);
    
    private final Color[] NUMBER_COLORS = {
        new Color(81, 139, 196),
        new Color(56, 142, 60),
        new Color(211, 74, 74),
        new Color(123, 31, 162)
    };
    
    private final JButton button;
    private boolean isColored = false;
    private final int x, y;

    public Square(int x, int y, Board board, Taskbar bar) {
        this.x = x;
        this.y = y;
        this.button = new JButton(" ");
        this.board = board;
		this.bar = bar;
		
        initializeButton();
        addMouseEvent();
    }

    private void initializeButton() {
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setBackground((x + y) % 2 == 0 ? DEFAULT_COLOR_1 : DEFAULT_COLOR_0);
    }

    public JButton getButton() {
		return button;
	}

	public boolean isColored() {
        return isColored;
    }
	
	public String getText() {
		return this.button.getText();
	}

    private void digAllZeroMine(int x, int y) {
        Queue<Global.Pair<Integer, Integer>> queue = new LinkedList<>();
        queue.add(new Global.Pair<>(x, y));

        while (!queue.isEmpty()) {
            Global.Pair<Integer, Integer> cell = queue.poll();
            int currX = cell.first, currY = cell.second;
            
            board.getSquare(currX, currY).color();
            board.checkEndGame(currX, currY);

            if (board.logic.getValueCell(currX, currY) != 0) continue;

            for (int i = 0; i < 8; ++i) {
                int nx = currX + Global.dx[i];
                int ny = currY + Global.dy[i];

                if (nx >= 0 && ny >= 0 && nx < Global.getRow() && ny < Global.getCol() && !board.getSquare(nx, ny).isColored()) {
                    queue.add(new Global.Pair<>(nx, ny));
                    
                    if(board.getSquare(nx, ny).getText().equals(Global.flag)) {
                    	board.getSquare(nx, ny).setWrongFlag();
                    	
                    	board.setNumberFlag(board.getNumberFlag() - 1);
                        bar.changeNumberFlag(Global.getMines() - board.getNumberFlag());
                    }
                    
                    
                }
            }
        }
    }

    private void digAllSquareAround(final int x, final int y) {
		int nx, ny;
		for (int i = 0; i < 8; ++i) {
			nx = x + Global.dx[i];
			ny = y + Global.dy[i];

			if (nx >= 0 && ny >= 0 && nx < Global.getRow() && ny < Global.getCol() && !board.getSquare(nx, ny).isColored()) {
				if (board.logic.getValueCell(nx, ny) == 0) {
					digAllZeroMine(nx, ny);
				} else {
					board.getSquare(nx, ny).color();
			        board.checkEndGame(nx, ny);
				}
			}
		}
	}
    
    private void addDoubleClickEvent() {
    	button.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2 && !Global.isFlag) {
					digAllSquareAround(x, y);
				}
			}
			
			public void mouseEntered(MouseEvent e) {
				button.requestFocus();
			}
		});
    }

    private void addMouseEvent() {
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (!isColored && (button.getBackground().equals(DEFAULT_COLOR_0) || button.getBackground().equals(DEFAULT_COLOR_1))) {
                    button.setBackground(HOVER_COLOR);
                }
            }

            public void mouseExited(MouseEvent e) {
                if (!isColored && button.getBackground().equals(HOVER_COLOR)) {
                    button.setBackground((x + y) % 2 == 0 ? DEFAULT_COLOR_1 : DEFAULT_COLOR_0);
                }
            }

            public void mousePressed(MouseEvent e) {
                if (isColored) {
                	return;
                }

                if (Global.isFlag) {
                    toggleFlag();
                } else {
                	if(button.getText().equals(Global.flag)) return;
                	
                    if (Global.isFirstClick) {
                    	while(!board.logic.isSolvable(x, y)) {
                    		board.logic.createGame();
                    	}
                        Global.isFirstClick = false;
                    }

                    if (board.logic.getValueCell(x, y) == 0) {
                        digAllZeroMine(x, y);
                    } else {
                        color();
                        board.checkEndGame(x, y);
                    }
                }
            }
        });

        button.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_F) {
                    Global.isFlag = !Global.isFlag;
                    bar.changeStatus();
                }
            }
        });
    }
    
    public void removeMouseEvent() {
    	for (MouseListener al : button.getMouseListeners()) {
    		button.removeMouseListener(al);
    	}
    	
    	for (KeyListener kl : button.getKeyListeners()) {
    		button.removeKeyListener(kl);
    	}
    }
    
    private void toggleFlag() {
        if (button.getText().equals("🚩")) {
            button.setText(" ");
            board.setNumberFlag(board.getNumberFlag() - 1);
            bar.changeNumberFlag(Global.getMines() - board.getNumberFlag());
        } else {
            button.setText("🚩");
            button.setForeground(Color.RED);
            button.setFont(new Font(Global.iconFont, Font.BOLD, Global.fontSize));
            
            board.setNumberFlag(board.getNumberFlag() + 1);
            bar.changeNumberFlag(Global.getMines() - board.getNumberFlag());
        }
    }
	
    public void setWrongFlag() {
    	button.setText("X");
		button.setFont(new Font("Arial", Font.PLAIN, 30));
	}

    public void color() {
    	if(button.getText().equals(Global.flag) || this.isColored()) {
    		return;
    	}
    	this.isColored = true;
    	button.setText("");
    	
        if(board.logic.getValueCell(x, y) == -1) {
			button.setText(Global.bomb);
			button.setForeground(Color.BLACK);
			button.setFont(new Font(Global.iconFont, Font.BOLD, Global.fontSize));
			button.setBackground(Color.RED);
        	
        } else {
        	button.setBackground((x + y) % 2 == 0 ? CLICKED_COLOR_1 : CLICKED_COLOR_0);
        	
        	if(board.logic.getValueCell(x, y) != 0) {
	        	button.setFont(new Font(Global.numberFont, Font.BOLD, Global.fontSize));
	    		button.setText(board.logic.getValueCell(x, y) + "");
	    		button.setForeground(NUMBER_COLORS[(board.logic.getValueCell(x, y) - 1) % 4]);
	    		
	    		addDoubleClickEvent();
        	}
        	
        	board.logic.setSafe();
        }
    }

}
