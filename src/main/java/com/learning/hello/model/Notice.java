package com.learning.hello.model;

public class Notice {
	public String title;
	public String content;
	public String name;
	public String phone;
	
	public Notice(String title, String content, String name, String phone) {
		this.title = title;
		this.content = content;
		this.name = name;
		this.phone = phone;
	}
	
	@Override
	public String toString() {
		return String.format("Title: %s\nContent: %s\nContact: %s\n\n", title, content, name + " (" + phone + ")");
	}
}
