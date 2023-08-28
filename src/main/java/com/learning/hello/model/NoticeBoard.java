package com.learning.hello.model;

import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NoticeBoard {
	private HashMap<Integer, Notice> board;
	
	public final int MAX_SIZE = 6;
	private List<Integer> filledIndices;
	
	public NoticeBoard() {
		board = new HashMap<>();
		filledIndices = new ArrayList<>();
	}
	
	
	public int getSize() {
		return board.size();
	}
	
	public int toBePlacedAt() {
		for(int i = 0; i < MAX_SIZE; i++) {
			if(filledIndices.contains(i) == false) {
				return i;
			}
		}
		return -1;
	}
	
	public void addNotice(Notice notice) {
		int index = toBePlacedAt();
		board.put(index, notice);
		
		try {
			FileWriter file = new FileWriter("notice" + index + ".txt");
		    file.write(notice.toString());
		    file.close();
		} catch (IOException e) {
		    System.out.println("An error occurred.");
		    e.printStackTrace();
		}
		filledIndices.add(index);
	}
	
	public void removeOldestNotice() {
		try {
			FileWriter file = new FileWriter("notice" + filledIndices.get(0) + ".txt");
		    file.write("");
		    file.close();
		} catch (IOException e) {
		    System.out.println("An error occurred.");
		    e.printStackTrace();
		}
		
		filledIndices.remove(0);
	}
	
	@Override
	public String toString() {
		return board.toString();
	}
}