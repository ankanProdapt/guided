package com.learning.hello.contoller;

import java.util.Random;

public class HiLoController {
  private int N;
  private int guess;
  
  public void setGuess(int guess) {
    this.guess = guess;
  }
  
  public void reset() {
    this.N = new Random().nextInt(0, 100);
  }
  
  public int judge() {
    return Integer.compare(guess, N);
  }
  
  public String feedback() {
	if(judge() == 0) {
		return "Correct!";
	}
	else if(judge() == 1) {
		return "Guess Lower";
	}
	else {
		return "Guess Higher";
	}
  }
}