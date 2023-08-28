package com.learning.hello.model;

public class Serve {
	//private TennisPlayer server;
	private TennisPlayer winner;
	
	public Serve(TennisPlayer winner) {
		this.winner = winner;
	}
	
	public TennisPlayer getServeWinner() {
		return winner;
	}
	
	@Override
	public String toString() {
		return winner.name;
	}
}