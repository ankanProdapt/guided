package com.learning.hello.model;

import java.util.ArrayList;
import java.util.List;

public class Fibonacci {
	List<Integer> sequence;
	
	public Fibonacci(int size) {
		sequence = new ArrayList<>();
		sequence.add(1);
		sequence.add(1);
		for(int i = 0; i < size - 2; i++) {
			sequence.add(sequence.get(i) + sequence.get(i + 1));
		}
		System.out.println(sequence);
	}
	
	public boolean checkIfConsecutive(int x, int y) {
		if(x > y) {
			int temp = x;
			x = y;
			y = temp;
		}
		
		for(int i = 0; i < sequence.size() - 1; i++) {
			if(sequence.get(i) == x && sequence.get(i + 1) == y) {
				return true;
			}
		}
		
		return false;
	}
}