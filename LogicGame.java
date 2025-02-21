package tryAgainMineSweeper;

import java.util.*;

public class LogicGame {
	public static int ROW, COL, mine, diff = 0;

	public static int[][] board;
	public static boolean[][] visited;

	private static final int[] dx = { -1, -1, -1, 0, 0, 1, 1, 1 };
	private static final int[] dy = { -1, 0, 1, -1, 1, -1, 0, 1 };

	// Đếm mìn 8 ô xung quanh
	private static void countMineAround() {
		// Đếm số mìn 8 ô xung quanh
		int nx, ny, cnt;
		for (int i = 0; i < ROW; ++i) {
			for (int j = 0; j < COL; ++j) {
				// Nếu có mìn thì bỏ qua
				if (board[i][j] == -1)
					continue;

				// Biến đếm mìn
				cnt = 0;
				for (int k = 0; k < 8; ++k) {
					nx = i + dx[k];
					ny = j + dy[k];

					if (nx >= 0 && nx < ROW && ny >= 0 && ny < COL && board[nx][ny] == -1)
						cnt++;
				}
				board[i][j] = cnt;
			}
		}
	}

	// Đặt mìn
	private static void setUpMine(int num) {
		// Khởi tạo biến vị trí
		int x, y;
		Random rand = new Random();

		for (int i = 0; i < num; ++i) {
			x = rand.nextInt(ROW);
			y = rand.nextInt(COL);

			// Nếu đã có mìn thì tạo lại
			while (board[x][y] == -1) {
				x = rand.nextInt(ROW);
				y = rand.nextInt(COL);
			}
			board[x][y] = -1;
		}
	}

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

			BoardGame.colorCell(curr_x, curr_y);

			if (board[curr_x][curr_y] != 0)
				continue;

			for (int i = 0; i < 8; ++i) {
				nx = curr_x + dx[i];
				ny = curr_y + dy[i];

				if (nx >= 0 && ny >= 0 && nx < ROW && ny < COL && !visited[nx][ny]) {
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

	public static void createGame() {
		// kích thước bảng ứng với độ khó
		switch (diff) {
	        case 0 ->  { ROW = 8;  COL = 10; mine = 10; }
	        case 1 ->  { ROW = 10; COL = 15; mine = 30; }
	        default -> { ROW = 15; COL = 20; mine = 100; }
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

		// Đặt mìn
		setUpMine(mine);

		// Hoàn thiện bảng
		countMineAround();
	}

}