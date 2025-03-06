package org.game.Minesweeper;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Logic {
	
	private int[][]matrix;
	private int row, col, mines;
	private int safe;
	
	public Logic() {
		row = Global.getRow(); col = Global.getCol();
		mines = Global.getMines();
		safe = row * col - mines;
		
		createGame();
	}

	public int getValueCell(int x, int y) {
		return matrix[x][y];
	}
	
	public boolean isSafe() {
		return safe == 0;
	}
	
	public void setSafe() {
		safe--;
	}
	
	public void createGame() {
		// Tạo bảng
		matrix = new int[row][col];
		for (int i = 0; i < row; ++i) {
			for (int j = 0; j < col; ++j) {
				matrix[i][j] = 0;
			}
		}
		
		setUpMine();
		countMineAround();
	}
	
	// Đếm số mìn 8 ô xung quanh
	private void countMineAround() {
		int nx, ny, cnt;
		for (int i = 0; i < row; ++i) {
			for (int j = 0; j < col; ++j) {
				// Nếu có mìn thì bỏ qua
				if (matrix[i][j] == -1) {
					continue;
				}

				// Biến đếm mìn
				cnt = 0;
				for (int k = 0; k < 8; ++k) {
					nx = i + Global.dx[k];
					ny = j + Global.dy[k];

					if (nx >= 0 && nx < row && ny >= 0 && ny < col && matrix[nx][ny] == -1)
						cnt++;
				}
				matrix[i][j] = cnt;
			}
		}
	}

	// Đặt mìn
	private void setUpMine() {
		// Khởi tạo biến vị trí
		int x, y;
		Random rand = new Random();

		for (int i = 0; i < mines; ++i) {
			x = rand.nextInt(row);
			y = rand.nextInt(col);

			// Nếu đã có mìn thì tạo lại
			while (matrix[x][y] == -1) {
				x = rand.nextInt(row);
				y = rand.nextInt(col);
			}
			matrix[x][y] = -1;
		}
	}
	
	public boolean isSolvable(int x, int y) {
		
		if(matrix[x][y] != 0) {
			return false;
		}
		
		boolean isRectangle = true; //haveNumber3 = false;
		
		boolean[][] visited = new boolean[row][col];
		for (int i = 0; i < row; ++i) {
			for (int j = 0; j < col; ++j) {
				visited[i][j] = false;
			}
		}
		
		Queue<Global.Pair<Integer, Integer>> q = new LinkedList<>();
		q.add(new Global.Pair<>(x, y));
		visited[x][y] = true;
		
		// Tọa độ 4 điểm trên, dưới, trái, phải
		int maxTop = y, maxBot = y, maxLeft = x, maxRight = x;
		
		// Số ô liên thông
		int count = 0;

		int nx, ny, curr_x, curr_y;
		while (!q.isEmpty()) {
			Global.Pair<Integer, Integer> top = q.peek();
			curr_x = top.first;
			curr_y = top.second;
			q.poll();
			count++;
			
			maxTop = Math.min(maxTop, curr_y);
			maxBot = Math.max(maxBot, curr_y);
			maxLeft = Math.min(maxLeft, curr_x);
			maxRight = Math.max(maxRight, curr_x);

			if (matrix[curr_x][curr_y] != 0) {
				continue;
			}

			for (int dx = -1; dx <= 1; ++dx) {
				nx = curr_x + dx;
				for (int dy = -1; dy <= 1; ++dy) {
					ny = curr_y + dy;

					if (nx >= 0 && ny >= 0 && nx < row && ny < col && !visited[nx][ny]) {
						visited[nx][ny] = true;
						q.add(new Global.Pair<>(nx, ny));
					}
				}
			}
		}
		
		
		if(count != (maxBot - maxTop + 1) * (maxRight - maxLeft + 1)) {
			isRectangle = false;
		}

		return !isRectangle;
	}
}