package tryAgainMineSweeper;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BoardGame {
	// Thông số ma trận trò chơi
	private static int N, M, cellSize = 40, fontSize = 19; // Số hàng, số cột, kích thước 1 ô
	private static JButton[][] buttons;
	private static boolean isFirstClick = true;
	
	// Ma trận tò chơi
	public static JPanel game;

	// Màu nền
	private static Color hoverColor = Color.gray;
	private static Color defaultColor_0 = new Color(162, 209, 73); // (162, 209, 73);
	private static Color clickedColor_0 = new Color(229, 194, 159); // (255, 224, 189);
	private static Color defaultColor_1 = new Color(135, 175, 58);// Color(170, 215, 81); // Xanh lá đậm
	private static Color clickedColor_1 = new Color(215, 184, 153);// (150, 95, 60);

	// Màu số
	private static Color[] numberColor = new Color[4];
	static {
		numberColor[0] = new Color(81, 139, 196);
		numberColor[1] = new Color(56, 142, 60);
		numberColor[2] = new Color(211, 74, 74);
		numberColor[3] = new Color(123, 31, 162);
	}

	// Tạo 1 nút
	private static JButton createButton(int i, int j) {
		// Khởi tạo nút
		JButton button = new JButton(" ");
		button.setMargin(new Insets(0, 0, 0, 0)); // Thêm không gian cho nội dung
		button.setFocusable(false); // Tắt hiệu ứng gì đó
		button.setBorderPainted(false); // Tắt viền
		button.setBackground(((i + j) % 2 == 0) ? defaultColor_1 : defaultColor_0); // Màu nền

		// Hiệu ứng hover
		button.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {

				if (LogicGame.visited[i][j]) {
					return;
				}

				if (button.getBackground().equals(defaultColor_0) || button.getBackground().equals(defaultColor_1)) {
					button.setBackground(hoverColor);
				}
			}

			public void mouseExited(MouseEvent e) {

				if (LogicGame.visited[i][j]) {
					return;
				}

				if (button.getBackground().equals(hoverColor)) {
					button.setBackground((i + j) % 2 == 0 ? defaultColor_1 : defaultColor_0);
				}
			}

			public void mousePressed(MouseEvent e) {

				if (LogicGame.visited[i][j]) {
					return;
				}

				if (e.getButton() == MouseEvent.BUTTON3) {
					button.setText(button.getText().equals("🚩") ? " " : "🚩");
					button.setForeground(Color.RED);
					button.setFont(new Font("Segoe UI Emoji", Font.BOLD, fontSize));
				} else {

					if (button.getText().equals("🚩")) {
						return;
					}
					
					if(isFirstClick) {
						while(LogicGame.board[i][j] != 0) {
							LogicGame.createGame();
						}
						isFirstClick = false;
					}

					if (LogicGame.board[i][j] == 0) {
						LogicGame.digAllZeroMine(i, j);
					} else {
						colorCell(i, j);
					}
				}
			}
		});
		return button;
	}

	// Bảng ban đầu
	private static void createDefaultBoard() {
		
		LogicGame.createGame();
		N = LogicGame.ROW;
		M = LogicGame.COL;
		buttons = new JButton[N][M];
		
		game = new JPanel();
		game.setPreferredSize(new Dimension(M * cellSize - 1, N * cellSize));
		game.setLayout(new GridLayout(N, M));

		for (int i = 0; i < N; ++i) {
			for (int j = 0; j < M; ++j) {
				buttons[i][j] = createButton(i, j);
				game.add(buttons[i][j]);
			}
		}
	}

	// Tô màu ô
	public static void colorCell(int x, int y) {

		if (buttons[x][y].getText().equals("🚩")) {
			return;
		}
		System.out.println('X');
		LogicGame.visited[x][y] = true;
		buttons[x][y].setBackground((x + y) % 2 == 0 ? clickedColor_1 : clickedColor_0);

		if (LogicGame.board[x][y] == 0)
			return;

		if (LogicGame.board[x][y] == -1) {
			buttons[x][y].setText("💣");
			buttons[x][y].setForeground(Color.BLACK);
			buttons[x][y].setFont(new Font("Segoe UI Emoji", Font.BOLD, fontSize));
			buttons[x][y].setBackground(Color.RED);
		} else {
			buttons[x][y].setFont(new Font("Arial Black", Font.BOLD, fontSize));
			buttons[x][y].setText(LogicGame.board[x][y] + "");
			buttons[x][y].setForeground(numberColor[(LogicGame.board[x][y] - 1) % 4]);

			buttons[x][y].addMouseListener(new MouseAdapter() {

				boolean isDoubleClicked = false;

				public void mouseClicked(MouseEvent e) {
					if (!isDoubleClicked && e.getClickCount() == 2) {

//						isDoubleClicked = true;
						LogicGame.digAllSquareAround(x, y);
					}
				}
			});
		}
	}

	
	// Restart game
	public static void startGame() {
		
		
		createDefaultBoard();
	}
	
	
	public static void main(String[] args) {
		
		createDefaultBoard();

		JFrame f = new JFrame("X");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		f.add(game);
		f.pack();
		f.setVisible(true);
	}

}
