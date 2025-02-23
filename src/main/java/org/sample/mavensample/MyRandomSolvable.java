package org.sample.mavensample;

import java.util.LinkedList;
import java.util.Queue;

public class MyRandomSolvable {
	private static int N, M; // Kích thước bảng

	public static boolean check(int x, int y) {
		N = LogicGame.ROW; M = LogicGame.COL;
		
		if (LogicGame.board[x][y] != 0) {
			return true;
		}

		boolean haveNumber3 = false, isRectangle = true;
		
		// Pair
		class Pair<K, V> {
			public final K first;
			public final V second;

			public Pair(K first, V second) {
				this.first = first;
				this.second = second;
			}
		}

		// Bảng kiểm tra đã tham
		boolean[][] visited = new boolean[N][M];
		for (int i = 0; i < N; ++i) {
			for (int j = 0; j < M; ++j) {
				visited[i][j] = false;
			}
		}
		
		// Hàng đợi để bfs
		Queue<Pair<Integer, Integer>> q = new LinkedList<>();

		q.add(new Pair<>(x, y));
		visited[x][y] = true;
		
		// Tọa độ 4 điểm trên, dưới, trái, phải
		int maxTop = y, maxBot = y, maxLeft = x, maxRight = x;
		
		// Số ô liên thông
		int count = 0;

		int nx, ny, curr_x, curr_y;
		while (!q.isEmpty()) {
			Pair<Integer, Integer> top = q.peek();
			curr_x = top.first;
			curr_y = top.second;
			q.poll();
			count++;
			
			maxTop = Math.min(maxTop, curr_y);
			maxBot = Math.max(maxBot, curr_y);
			maxLeft = Math.min(maxLeft, curr_x);
			maxRight = Math.max(maxRight, curr_x);

			if (LogicGame.board[curr_x][curr_y] != 0) {
				continue;
			}

			for (int dx = -1; dx <= 1; ++dx) {
				nx = curr_x + dx;
				for (int dy = -1; dy <= 1; ++dy) {
					ny = curr_y + dy;

					if (nx >= 0 && ny >= 0 && nx < N && ny < M && !visited[nx][ny]) {
						visited[nx][ny] = true;
						q.add(new Pair<>(nx, ny));
					}
				}
			}
		}
		
		
		if(count != (maxBot - maxTop + 1) * (maxRight - maxLeft + 1)) {
			isRectangle = false;
		}

		return isRectangle;
	}

}
