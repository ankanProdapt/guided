package com.learning.hello.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Game2584 {
	public int[][] grid;
	public Fibonacci fibSeq;
	public int score;

	private final int BIAS = 5; // 5% probability of generating 2
	private final int SIZE = 4;

	public Game2584() {
		grid = new int[SIZE][SIZE];
		
		fibSeq = new Fibonacci(25);
		score = 0;
		generateRandomTile();
		generateRandomTile();
	}

	public int getCellValue(int row, int col) {
		return grid[row][col];
	}

	public boolean generateRandomTile() {
		List<Integer> emptyCells = getEmptyCells();

		if (emptyCells.isEmpty()) {
			return false;
		}

		Random r = new Random();
		int cellNo = emptyCells.get(r.nextInt(0, emptyCells.size()));

		int row = cellNo / SIZE;
		int col = cellNo % SIZE;

		int prob = r.nextInt(100);
		grid[row][col] = (prob < BIAS ? 2 : 1);

		return true;
	}

	public List<Integer> getEmptyCells() {
		List<Integer> emptyCells = new ArrayList<>();

		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (grid[i][j] == 0) {
					emptyCells.add(i * SIZE + j);
				}
			}
		}

		return emptyCells;
	}
	
	public List<Integer> getCompressedList(List<Integer> list){
		List<Integer> compressed = new ArrayList<>();
		for(int i: list) {
			if(i > 0) {
				compressed.add(i);
			}
		}
		
		List<Integer> superCompressed = new ArrayList<>();
		for(int i = 0; i < compressed.size(); i++) {
			if(i < compressed.size() - 1 && fibSeq.checkIfConsecutive(compressed.get(i), compressed.get(i + 1))) {
				superCompressed.add(compressed.get(i) + compressed.get(i + 1));
				score += compressed.get(i) + compressed.get(i + 1);
				i++;
			}
			else {
				superCompressed.add(compressed.get(i));
			}
		}
		
		while(superCompressed.size() < SIZE) {
			superCompressed.add(0);
		}
		
		return superCompressed;
	}

	public boolean moveUp() {
		boolean gridChanged = false;
		for(int col = 0; col < SIZE; col++) {
			List<Integer> list = new ArrayList<>();
			for(int row = 0; row < SIZE; row++) {
				list.add(grid[row][col]);
			}
			List<Integer> compressed = getCompressedList(list);
			for(int row = 0; row < SIZE; row++) {
				if(grid[row][col] != compressed.get(row)) {
					grid[row][col] = compressed.get(row);
					gridChanged = true;
				}
			}
		}
		return gridChanged;
	}

	public boolean moveDown() {
		boolean gridChanged = false;
		for(int col = 0; col < SIZE; col++) {
			List<Integer> list = new ArrayList<>();
			for(int row = SIZE - 1; row >= 0; row--) {
				list.add(grid[row][col]);
			}
			List<Integer> compressed = getCompressedList(list);
			Collections.reverse(compressed);
			for(int row = SIZE - 1; row >= 0; row--) {
				if(grid[row][col] != compressed.get(row)) {
					grid[row][col] = compressed.get(row);
					gridChanged = true;
				}
			}
		}
		return gridChanged;
	}

	public boolean moveLeft() {
		boolean gridChanged = false;
		for (int row = 0; row < SIZE; row++) {
			List<Integer> list = new ArrayList<>();
			for (int col = 0; col < SIZE; col++) {
				list.add(grid[row][col]);
			}
			List<Integer> compressed = getCompressedList(list);
			for(int col = 0; col < SIZE; col++) {
				if(grid[row][col] != compressed.get(col)) {
					grid[row][col] = compressed.get(col);
					gridChanged = true;
				}
			}
		}
		return gridChanged;
	}

	public boolean moveRight() {
		boolean gridChanged = false;
		for (int row = 0; row < SIZE; row++) {
			List<Integer> list = new ArrayList<>();
			for (int col = SIZE - 1; col >= 0; col--) {
				list.add(grid[row][col]);
			}
			List<Integer> compressed = getCompressedList(list);
			Collections.reverse(compressed);
			for(int col = SIZE - 1; col >= 0; col--) {
				if(grid[row][col] != compressed.get(col)) {
					grid[row][col] = compressed.get(col);
					gridChanged = true;
				}
			}
		}
		return gridChanged;
	}

	public boolean checkWin() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (grid[i][j] == 2584) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isGameOver() {
		if (getEmptyCells().size() == 0) {
			for (int i = 0; i < SIZE - 1; i++) {
				for (int j = 0; j < SIZE; j++) {
					if (fibSeq.checkIfConsecutive(grid[i][j], grid[i + 1][j])) {
						return false;
					}
				}
			}

			for (int i = 0; i < SIZE; i++) {
				for (int j = 0; j < SIZE - 1; j++) {
					if (fibSeq.checkIfConsecutive(grid[i][j], grid[i][j + 1])) {
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}

}