package com.learning.hello.model;

import java.util.ArrayList;
import java.util.List;

public class TennisGame {
	private List<Serve> serves;
	private TennisPlayer winner;
	private TennisPlayer p1;
	private TennisPlayer p2;
	
	public TennisGame(TennisPlayer p1, TennisPlayer p2) {
		serves = new ArrayList<>();
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public List<Serve> getServes(){
		return serves;
	}
	
	public void updateGame(boolean pointToP1) {
		TennisPlayer serveWinner = pointToP1 ? p1 : p2;
		Serve curServe = new Serve(serveWinner);
		serves.add(curServe);
	}
	
	public boolean isGameOver() {
		int p1Wins = 0, p2Wins = 0;
		for(Serve serve: serves) {
			if(serve.getServeWinner().equals(p1)) {
				p1Wins++;
			}
			if(serve.getServeWinner().equals(p2)) {
				p2Wins++;
			}
		}
		
		if(p1Wins >= 4 && p1Wins >= p2Wins + 2) {
			winner = p1;
			return true;
		}
		if(p2Wins >= 4 && p2Wins >= p1Wins + 2) {
			winner = p2;
			return true;
		}
		
		return false;
	}
	
	public TennisPlayer getGameWinner() {
		return winner;
	}
	
	@Override
	public String toString() {
		return serves.toString();
	}
}