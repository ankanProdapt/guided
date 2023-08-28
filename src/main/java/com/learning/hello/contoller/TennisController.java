package com.learning.hello.contoller;

import org.thymeleaf.context.WebContext;

import com.learning.hello.model.ScoreBoard;
import com.learning.hello.model.TennisMatch;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TennisController {
	
	private TennisMatch match;
	private int curServe;
	private int lastServe;
	private int matchId;
	
	public WebContext performAction(String action, HttpServletRequest req, HttpServletResponse resp, WebContext ctx, TennisDB DB) {
		if(action.equals("Start Recording")) {
			String p1 = req.getParameter("player1");
			String p2 = req.getParameter("player2");
			match = new TennisMatch(p1, p2);
			curServe = 0;
			lastServe = 0;
			matchId = DB.createMatch(p1, p2);
			resp.addCookie(new Cookie("game_id", String.valueOf(matchId)));  
		}
		else if(action.equals("🟨")) {
			if(!match.isMatchOver()) {
				DB.updateMatch(matchId, true);
				match.updateMatch(true);
				lastServe++;
				curServe = lastServe;
			}
		}
		else if(action.equals("🟦")) {
			if(!match.isMatchOver()) {
				DB.updateMatch(matchId, false);
				match.updateMatch(false);
				lastServe++;
				curServe = lastServe;
			}
		}
		else if(action.equals("⏪︎")) {
			curServe = 0;
		}
		else if(action.equals("⏩︎")) {
			curServe = lastServe;
		}
		else if(action.equals("◀")) {
			curServe = Math.max(0, curServe - 1);
		}
		else if(action.equals("▶")) {
			curServe = Math.min(lastServe, curServe + 1);
		}
		else if(action.startsWith("Match")) {
			matchId = Integer.valueOf(action.split(" ")[1]);
			match = DB.getMatch(matchId);
			curServe = match.servesCount;
			lastServe = match.servesCount;
			resp.addCookie(new Cookie("game_id", String.valueOf(matchId)));
		}
		
		ScoreBoard scoreBoard = new ScoreBoard(match, curServe);
		return getUpdatedCtx(scoreBoard, ctx);
	}
	
	public WebContext getUpdatedCtx(ScoreBoard scoreBoard, WebContext ctx) {
		ctx.setVariable("p1Name", scoreBoard.p1Name);
		ctx.setVariable("p2Name", scoreBoard.p2Name);
		ctx.setVariable("p1Score", getScoreString(scoreBoard.p1Score, scoreBoard.p2Score));
		ctx.setVariable("p2Score", getScoreString(scoreBoard.p2Score, scoreBoard.p1Score));
		ctx.setVariable("p1GamesWon", scoreBoard.p1GamesWon);
		ctx.setVariable("p2GamesWon", scoreBoard.p2GamesWon);
		ctx.setVariable("p1SetsWon", scoreBoard.p1SetsWon);
		ctx.setVariable("p2SetsWon", scoreBoard.p2SetsWon);
		ctx.setVariable("comment", scoreBoard.comment);
		if(matchId > 0)
			ctx.setVariable("matchHeading", "Match " + matchId);
		return ctx;
	}
	
	public String getScoreString(int score1, int score2) {
		String[] scoreStrings = {"LOVE", "15", "30", "40"};
		if(score1 > 3) {
			if(score1 > score2)
				return "ADV";
			else if(score1 == score2)
				return "DEUCE";
			else
				return "40";
		}
		return scoreStrings[score1];
		
	}
}
