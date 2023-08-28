package com.learning.hello.model;

import java.util.ArrayList;
import java.util.List;

public class TennisSet {
	private List<TennisGame> games;
	private TennisPlayer winner;
	private TennisPlayer p1;
	private TennisPlayer p2;
	
	public TennisSet(TennisPlayer p1, TennisPlayer p2) {
		games = new ArrayList<>();
		games.add(new TennisGame(p1, p2));
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public List<TennisGame> getGames(){
		return games;
	}
	
	
	public void updateSet(boolean pointToP1) {
		games.get(games.size() - 1).updateGame(pointToP1);
		if(!isSetOver() && games.get(games.size() - 1).isGameOver()) {
			games.add(new TennisGame(p1, p2));
		}
	}
	
	public boolean isSetOver(){
		int p1Wins = 0, p2Wins = 0;
		for(TennisGame game: games) {
			if(game.isGameOver()) {
				if(game.getGameWinner().equals(p1)) {
					p1Wins++;
				}
				if(game.getGameWinner().equals(p2)) {
					p2Wins++;
				}
			}
		}
		
		if(p1Wins >= 6 && p1Wins >= p2Wins + 2) {
			winner = p1;
			return true;
		}
		if(p2Wins >= 6 && p2Wins >= p1Wins + 2) {
			winner = p2;
			return true;
		}
		
		return false;
	}
	
	public TennisPlayer getSetWinner() {
		return winner;
	}
	
	@Override
	public String toString() {
		return games.toString();
	}
	
}