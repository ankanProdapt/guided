package com.learning.hello.model;

import java.util.ArrayList;
import java.util.List;

public class TennisMatch {
	public TennisPlayer player1;
	public TennisPlayer player2;
	private List<TennisSet> sets;
	private TennisPlayer winner;
	
	public int servesCount = 0;
	
	public TennisMatch(String p1, String p2) {
		player1 = new TennisPlayer(p1);
		player2 = new TennisPlayer(p2);
		sets = new ArrayList<>();
		sets.add(new TennisSet(player1, player2));
	}
	
	public void updateMatch(boolean pointToP1) {
		if(isMatchOver()) {
			return;
		}
		
		servesCount++;
		sets.get(sets.size() - 1).updateSet(pointToP1);
		if(!isMatchOver() && sets.get(sets.size() - 1).isSetOver()) {
			sets.add(new TennisSet(player1, player2));
		}
	}
	
	public List<TennisSet> getSets(){
		return sets;
	}
	
	
	public boolean isMatchOver() {
		int p1Wins = 0, p2Wins = 0;
		for(TennisSet set: sets) {
			if(set.isSetOver()) {
				if(set.getSetWinner().equals(player1)) {
					p1Wins++;
				}
				if(set.getSetWinner().equals(player2)) {
					p2Wins++;
				}
			}
		}
		
		if(p1Wins >= 3) {
			winner = player1;
			return true;
		}
		if(p2Wins >= 3) {
			winner = player2;
			return true;
		}
		
		return false;
	}
	
	public TennisPlayer getMatchWinner() {
		return winner;
	}
	
	@Override
	public String toString() {
		return sets.toString();
	}
	
}
