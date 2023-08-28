package com.learning.hello.contoller;

import com.learning.hello.model.Game2584;

public class Game2584Controller {

    static Game2584 game;

    public Game2584Controller() {
        game = new Game2584();
    }
    
    public void generateRandomTile() {
    	game.generateRandomTile();
    }
    
    public int getScore() {
    	return game.score;
    }
    
    public int getCellValue(int i, int j) {
    	return game.getCellValue(i, j);
    }

    public boolean moveUp() {
        return game.moveUp();
    }
    
    public boolean moveDown() {
        return game.moveDown();
    }
    
    public boolean moveLeft() {
        return game.moveLeft();
    }
    public boolean moveRight() {
        return game.moveRight();
    }
    
    public boolean isGameOver() {
        return game.isGameOver();
    }
    
    public boolean checkWin() {
        return game.checkWin();
    } 
    
    public void reset() {
    	game = new Game2584();
    }

}