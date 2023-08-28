package com.learning.hello.contoller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.learning.hello.model.TennisMatch;

public class TennisDB {
	private Connection cnx = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	
	public TennisDB() {
		try {
			cnx = DriverManager.getConnection("jdbc:mysql://localhost: 3306/Tennis", "ankan2001prodapt", "we1c@me1");
			stmt = cnx.createStatement();
			System.out.println("Connected Successfully");
		} catch (SQLException e) {
			System.out.println("Something is wrong with db connection");
			e.printStackTrace();
		}
	}
	
	public int createMatch(String p1, String p2) {
		int p1Id = getId(p1);
		int p2Id = getId(p2);
		
		List<Integer> matchIds = getMatchIds();
		int maxx = 0;
		for(int id: matchIds) {
			maxx = Math.max(maxx, id);
		}
		int matchId = maxx + 1;
		
		String query = "INSERT INTO matches (id, player1Id, player2Id) VALUES (" + 
						matchId + ", " + p1Id + ", " + p2Id + ")";
		try {
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return matchId;
		
	}
	
	public void updateMatch(int matchId, boolean pointToP1) {
		String query = "SELECT * FROM matches WHERE id = " + matchId;
		try {
			rs = stmt.executeQuery(query);
			rs.next();
			int p1Id = rs.getInt("player1Id");
			int p2Id = rs.getInt("player2Id");
			int winnerId = pointToP1 ? p1Id : p2Id;
			
			query = "INSERT INTO serves (matchId, winnerId) VALUES (" +
					 matchId + ", " + winnerId + ")";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Integer> getMatchIds(){
		List<Integer> matchIds = new ArrayList<>();
		String query = "SELECT id FROM matches ORDER BY id";
		try {
			rs = stmt.executeQuery(query);
			while(rs.next()) {
				matchIds.add(rs.getInt("id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return matchIds;
	}
	
	public List<Integer> getPlayerIds(){
		List<Integer> playerIds = new ArrayList<>();
		String query = "SELECT id FROM players ORDER BY id";
		try {
			rs = stmt.executeQuery(query);
			while(rs.next()) {
				playerIds.add(rs.getInt("id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return playerIds;
	}
	
	public TennisMatch getMatch(int matchId) {
		TennisMatch match;
		String query = "SELECT * FROM matches WHERE id = " + matchId;
		try {
			rs = stmt.executeQuery(query);
			rs.next();
			int p1Id = rs.getInt("player1Id");
			int p2Id = rs.getInt("player2Id");
			String p1 = getName(p1Id);
			String p2 = getName(p2Id);
			match = new TennisMatch(p1, p2);
			query = "SELECT * FROM serves WHERE matchId = " + matchId;
			rs = stmt.executeQuery(query);
			while(rs.next()) {
				int winnerId = rs.getInt("winnerId");
				match.updateMatch(winnerId == p1Id);
			}
			return match;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public String getName(int id) {
		String query = "SELECT * FROM players WHERE id = " + id;
		try {
			rs = stmt.executeQuery(query);
			rs.next();
			return rs.getString("name");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	
	public int getId(String name) {
		String query = "SELECT * FROM players WHERE name = '" + name + "'";
		try {
			rs = stmt.executeQuery(query);
			while(rs.next()) {
				if(rs.getString("name").equals(name)) {
					return rs.getInt("id");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<Integer> playerIds = getPlayerIds();
		int maxx = 0;
		for(int id: playerIds) {
			maxx = Math.max(maxx, id);
		}
		int playerId = maxx + 1;
		query = "INSERT INTO players (id, name) VALUES (" + playerId + ", '" + name + "')";
		try {
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return playerId;
	}
	
}
