package org.sample.mavensample;

import java.util.Random;

public class RandomGame {
	
	private static int N, M;
	private static int MINES;
	private static int[][] board;
	
	private static final int[] dx = { -1, -1, -1, 0, 0, 1, 1, 1 };
	private static final int[] dy = { -1, 0, 1, -1, 1, -1, 0, 1 };
	
	//Đếm mìn 8 ô xung quanh
	private static void countMineAround() {
		// Đếm số mìn 8 ô xung quanh
		int nx, ny, cnt;
		for (int i = 0; i < N; ++i) {
			for (int j = 0; j < M; ++j) {
				// Nếu có mìn thì bỏ qua
				if (board[i][j] == -1)
					continue;

				// Biến đếm mìn
				cnt = 0;
				for (int k = 0; k < 8; ++k) {
					nx = i + dx[k];
					ny = j + dy[k];

					if (nx >= 0 && nx < N && ny >= 0 && ny < M && board[nx][ny] == -1)
						cnt++;
				}
				board[i][j] = cnt;
			}
		}
	}

	// Đặt mìn
	private static void setUpMine() {
		// Khởi tạo biến vị trí
		int x, y;
		Random rand = new Random();

		for (int i = 0; i < MINES; ++i) {
			x = rand.nextInt(N);
			y = rand.nextInt(M);

			// Nếu đã có mìn thì tạo lại
			while (board[x][y] == -1) {
				x = rand.nextInt(N);
				y = rand.nextInt(M);
			}
			board[x][y] = -1;
		}
	}

	
	public static void play() {
    	N = LogicGame.ROW; M = LogicGame.COL;
    	MINES = LogicGame.mine;
    	
        board = new int[N][M];
        setUpMine();
        countMineAround();
        
        LogicGame.board = board;
    }

}