package com.learning.hello.contoller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.learning.hello.model.Notice;
import com.learning.hello.model.NoticeBoard;

public class NoticeDB {
	Connection cnx = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	public NoticeBoard getNotices() throws SQLException {
		NoticeBoard noticeBoard = new NoticeBoard();
		cnx = DriverManager.getConnection("jdbc:mysql://localhost: 3306/myDB", "ankan2001prodapt", "we1c@me1");
		stmt = cnx.createStatement();
		String query = "SELECT * FROM Notices, contacts WHERE Notices.contactId=contacts.id ORDER BY date_created desc limit 6";
		rs = stmt.executeQuery(query);
		while(rs.next()) {
			String title = rs.getString("title");
			String content = rs.getString("content");
			String name = rs.getString("name");
			String phone = rs.getString("phone");
			
			noticeBoard.addNotice(new Notice(title, content, name, phone));
		}
		
		return noticeBoard;
	}
	
	public void addNotice(Notice notice) throws SQLException {
		cnx = DriverManager.getConnection("jdbc:mysql://localhost: 3306/myDB", "ankan2001prodapt", "we1c@me1");
		stmt = cnx.createStatement();
		int contactId = addContact(notice.name, notice.phone);
		//String query = "INSERT INTO Notices (title, content, contactId, date_created) values ('world', 'www', 1, now())";
		String query = "INSERT INTO Notices (title, content, contactId, date_created) VALUES " +
						"('" + notice.title + "', '" + notice.content + "', " + contactId + ", NOW())";
		stmt.executeUpdate(query);
	}
	
	public int addContact(String name, String phone) throws SQLException {
		cnx = DriverManager.getConnection("jdbc:mysql://localhost: 3306/myDB", "ankan2001prodapt", "we1c@me1");
		stmt = cnx.createStatement();
		
		String query = "SELECT * FROM contacts WHERE name='" + name + "'";
		rs = stmt.executeQuery(query);
		
		if(rs.next()) {
			return rs.getInt("id");
		}
		else {
			String query2 = "INSERT INTO contacts (name, phone) VALUES ('" + name + "', '" + phone + "')";
			stmt.executeUpdate(query2);
			rs = stmt.executeQuery(query);
			rs.next();
			return rs.getInt("id");
		}
	}
}
