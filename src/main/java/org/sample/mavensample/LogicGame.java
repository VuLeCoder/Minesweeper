package org.sample.mavensample;

import java.util.LinkedList;
import java.util.Queue;

public class LogicGame {
	public static int ROW, COL, mine, diff = 1;

	public static int[][] board;
	public static boolean[][] visited;

	private static final int[] dx = { -1, -1, -1, 0, 0, 1, 1, 1 };
	private static final int[] dy = { -1, 0, 1, -1, 1, -1, 0, 1 };

	// Hàm tìm kiếm tất cả các ô trống khi bấm vào 1 ô trống
	// Định nghĩa pair
	private static class Pair<K, V> {
		public final K first;
		public final V second;

		public Pair(K first, V second) {
			this.first = first;
			this.second = second;
		}
	}

	// bfs
	public static void digAllZeroMine(final int x, final int y) {
		// Hàng đợi để bfs
		Queue<Pair<Integer, Integer>> q = new LinkedList<>();

		q.add(new Pair<>(x, y));

		int nx, ny, curr_x, curr_y;
		while (!q.isEmpty()) {
			Pair<Integer, Integer> top = q.peek();
			curr_x = top.first;
			curr_y = top.second;
			q.poll();

			BoardGame.colorSafeCell(curr_x, curr_y);

			if (board[curr_x][curr_y] != 0)
				continue;

			for (int i = 0; i < 8; ++i) {
				nx = curr_x + dx[i];
				ny = curr_y + dy[i];

				if (nx >= 0 && ny >= 0 && nx < ROW && ny < COL && !visited[nx][ny]) {
					visited[nx][ny] = true;
					q.add(new Pair<>(nx, ny));
				}
			}
		}
	}

	// Đào tất cả các ô xung quanh khi nhấn 2 lần
	public static void digAllSquareAround(final int x, final int y) {

		int nx, ny;
		for (int i = 0; i < 8; ++i) {
			nx = x + dx[i];
			ny = y + dy[i];

			if (nx >= 0 && ny >= 0 && nx < ROW && ny < COL && !visited[nx][ny]) {
//				visited[nx][ny] = true;
				if (board[nx][ny] == 0) {
					digAllZeroMine(nx, ny);
				} else {
					BoardGame.colorCell(nx, ny);
					BoardGame.isEndGame(board[nx][ny] == -1, nx, ny);
				}
			}
		}
	}
	
	private static void createAttribute() {
		System.out.println(diff);
		switch (diff) {
	        case 0 :  { ROW = 8;  COL = 10; mine = 10; break;}
	        case 1 :  { ROW = 12; COL = 16; mine = 30; break;}
	        default : { ROW = 18; COL = 21; mine = 80; break;}
		}

		// Tạo bảng
		board = new int[ROW][COL];
		visited = new boolean[ROW][COL];
		for (int i = 0; i < ROW; ++i) {
			for (int j = 0; j < COL; ++j) {
				board[i][j] = 0;
				visited[i][j] = false;
			}
		}
	}
	
	public static void createGame() {
		createAttribute();
		RandomGame.play();         // Mìn ngẫu nhiên, có thể phải đoán bừa
//		RandomSolvableGameGPT.play(); // Mìn ngẫu nhiên, có thể giải ko cần đoán
	}
	
	public static void printBoard() {
		for(int i=0; i<ROW; ++i) {
			for(int j=0; j<COL; ++j) {
				if(board[i][j] == -1) System.out.print("x ");
				else System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
	}
	
//	public static void main(String []args) {
//		createGame();
//		printBoard();
//	}

}
