package com.learning.hello.model;

public class TennisPlayer {
	String name;
	
	public TennisPlayer(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Player: " + name;
	}
}
