package com.learning.hello.model;

import java.util.ArrayList;
import java.util.List;

import cards.Card;
import cards.Deck;

public class Mangata {
	public List<Player> playerList;
	public Deck deck;
	public Card inPileTop;
	public Card outPileTop;
	public String winner = "";
	
	private String position = "Out";
	
	public Mangata() {
		playerList = new ArrayList<Player>();
		deck = new Deck();
		deck.shuffle();
	}
	
	public void addPlayer(String name, int bet, String abbr, String position) {
		playerList.add(new Player(name, bet, abbr, position));
	}
	
	public List<Player> getPlayers(){
		return playerList;
	}
	
	public String drawCard() {
		if(winner.length() != 0) {
			return "";
		}
		Card card = null;
		if(position.equals("In")) {
			position = "Out";
			outPileTop = deck.drawTop();
			card = outPileTop;
		}
		else {
			position = "In";
			inPileTop = deck.drawTop();
			card = inPileTop;
		}
		
		return position + " " + card.toString();
	}
	
	public String getWinner() {
		if(winner.length() != 0) {
			return winner;
		}
		for(Player player: playerList) {
			if(position.equals("In")) {
				if(player.checkWin(position, inPileTop)) {
					return player.name + " won!";
				}
			}
			else {
				if(player.checkWin(position, outPileTop)) {
					return player.name + " won!";
				}
			}
		}
		
		return "";
	}
}
