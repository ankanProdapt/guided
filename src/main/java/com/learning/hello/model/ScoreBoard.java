package com.learning.hello.model;

public class ScoreBoard {
	public int p1Score;
	public int p2Score;
	public String p1Name;
	public String p2Name;
	public int p1GamesWon;
	public int p2GamesWon;
	public int p1SetsWon;
	public int p2SetsWon;
	public String comment;

	public ScoreBoard(TennisMatch match, int servesCount) {
		if(match.servesCount < servesCount) {
			servesCount = match.servesCount;
		}
		
		int curServe = 0;
		p1Score = 0;
		p2Score = 0;
		p1Name = match.player1.name;
		p2Name = match.player2.name;
		p1GamesWon = 0;
		p2GamesWon = 0;
		p1SetsWon = 0;
		p2SetsWon = 0;
		comment = "Match Started between " + p1Name + " and " + p2Name + ".";
		
		if(servesCount == 0) {
			return;
		}
		
		TennisMatch matchRecap = new TennisMatch(p1Name, p2Name);
		
		for(TennisSet set: match.getSets()) {
			for(TennisGame game: set.getGames()) {
				for(Serve serve: game.getServes()) {
					curServe++;
					boolean pointToP1 = serve.getServeWinner().name.equals(p1Name) ? true : false;
					matchRecap.updateMatch(pointToP1);
					
					if(curServe == servesCount) {
						updateCurrentScoreBoard(matchRecap);
					}
				}
			}
		}
		
	}
	
	
	
	public void updateCurrentScoreBoard(TennisMatch match) {
		for(TennisSet set: match.getSets()) {
			for(TennisGame game: set.getGames()) {
				for(Serve serve: game.getServes()) {
					updateServeData(serve);
				}
				updateGameData(game);
			}
			updateSetData(set);
		}
		updateMatchData(match);
		
	}
	
	
	
	public void updateServeData(Serve serve) {
		if(serve.getServeWinner().name.equals(p1Name)) {
			p1Score++;
			comment = p1Name + " won the serve.";
		}
		if(serve.getServeWinner().name.equals(p2Name)) {
			p2Score++;
			comment = p2Name + " won the serve.";
		}
	}
	
	
	public void updateGameData(TennisGame game) {
		if(!game.isGameOver()) {
			return;
		}
		
		p1Score = 0;
		p2Score = 0;
		if(game.getGameWinner().name.equals(p1Name)) {
			p1GamesWon++;
			comment = " " + p1Name + " won the game.";
		}
		if(game.getGameWinner().name.equals(p2Name)) {
			p2GamesWon++;
			comment = " " + p2Name + " won the game.";
		}
	}
	
	
	public void updateSetData(TennisSet set) {
		if(!set.isSetOver()) {
			return;
		}
		p1GamesWon = 0;
		p2GamesWon = 0;
		if(set.getSetWinner().name.equals(p1Name)) {
			p1SetsWon++;
			comment = " " + p1Name + " won the set.";
		}
		if(set.getSetWinner().name.equals(p2Name)) {
			p2SetsWon++;
			comment = " " + p2Name + " won the set.";
		}
	}
	
	public void updateMatchData(TennisMatch match) {
		if(!match.isMatchOver()) {
			return;
		}
		comment = " " + match.getMatchWinner() + " won the match";
	}
	
}
