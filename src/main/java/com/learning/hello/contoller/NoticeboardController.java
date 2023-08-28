package com.learning.hello.contoller;

import java.sql.SQLException;

import com.learning.hello.model.Notice;
import com.learning.hello.model.NoticeBoard;

public class NoticeboardController {
	NoticeDB ndb = new NoticeDB();
	
	public NoticeBoard getNotices() throws SQLException {
		return ndb.getNotices();
	}
	
	public void addNotice(String title, String content, String name, String phone) throws SQLException {
		Notice notice = new Notice(title, content, name, phone);
		ndb.addNotice(notice);
	}
}
