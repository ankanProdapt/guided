package com.learning.hello.model;

import cards.Card;

public class Player {
	public String name;
	public int bet;
	public Card chosenCard;
	public String position;
	
	public Player(String name, int bet, String abbr, String position) {
		this.name = name;
		this.bet = bet;
		this.chosenCard = new Card(abbr);
		this.position = position;
	}
	
	public boolean checkWin(String position, Card card) {
		return this.position.equals(position) && card.equals(chosenCard);
	}
	
	@Override
	public String toString() {
		String result = "Name: " + name + ", ";
		result += "Bet: " + bet + ", ";
		result += "Card: " + chosenCard + ", ";
		result += position;
		return result;
	}
}
