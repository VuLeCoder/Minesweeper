package org.sample.mavensample;

import java.util.*;

public class RandomSolvableGameGPT {
    private static final int MINE = -1;
    private static int N, M; // Kích thước bảng
    private static int MINES; // Số mìn

    private static int[][] board;

    private static void generateSolvableBoard() {
        Random rand = new Random();
        int placedMines = 0;
        
        // Bước 1: Đặt mìn nhưng đảm bảo không bắt buộc phải đoán
        while (placedMines < MINES) {
            int x = rand.nextInt(N);
            int y = rand.nextInt(M);

            if (board[x][y] != MINE) {
                board[x][y] = MINE;
                placedMines++;

                // Kiểm tra nếu bảng vẫn có thể giải được
                if (!isSolvable()) {
                    board[x][y] = 0; // Hủy bỏ mìn nếu làm bảng không thể giải
                    placedMines--;
                }
            }
        }

        // Bước 2: Điền số lượng mìn xung quanh mỗi ô
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < M; ++j) {
                if (board[i][j] == MINE) continue;
                board[i][j] = countAdjacentMines(i, j);
            }
        }
    }

    private static int countAdjacentMines(int x, int y) {
        int count = 0;
        for (int dx = -1; dx <= 1; ++dx) {
            for (int dy = -1; dy <= 1; ++dy) {
                int nx = x + dx, ny = y + dy;
                if (nx >= 0 && ny >= 0 && nx < N && ny < M && board[nx][ny] == MINE) {
                    count++;
                }
            }
        }
        return count;
    }

    private static boolean isSolvable() {
        boolean[][] visited = new boolean[N][M];

        // Tìm một ô không phải mìn để bắt đầu
        int startX = -1, startY = -1;
        for (int i = 0; i < N && startX == -1; ++i) {
            for (int j = 0; j < M; ++j) {
                if (board[i][j] != MINE) {
                    startX = i;
                    startY = j;
                    break;
                }
            }
        }

        // Nếu không tìm được ô an toàn nào -> bảng không hợp lệ
        if (startX == -1) return false;

        // Kiểm tra tính liên thông bằng Flood Fill
        explore(startX, startY, visited);

        // Kiểm tra nếu tất cả các ô an toàn đều có thể tiếp cận
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < M; ++j) {
                if (board[i][j] != MINE && !visited[i][j]) {
                    return false; // Có ô không thể mở mà không đoán
                }
            }
        }
        return true;
    }

    private static void explore(int x, int y, boolean[][] visited) {
        if (x < 0 || y < 0 || x >= N || y >= M || visited[x][y] || board[x][y] == MINE) {
            return;
        }

        visited[x][y] = true;

        // Nếu ô này là 0, mở rộng tìm kiếm (Flood Fill)
        if (board[x][y] == 0) {
            for (int dx = -1; dx <= 1; ++dx) {
                for (int dy = -1; dy <= 1; ++dy) {
                    explore(x + dx, y + dy, visited);
                }
            }
        }
    }
    
    public static void play() {
    	N = LogicGame.ROW; M = LogicGame.COL;
    	MINES = LogicGame.mine;
    	
        board = new int[N][M];
        generateSolvableBoard();
        LogicGame.board = board;
    }

}