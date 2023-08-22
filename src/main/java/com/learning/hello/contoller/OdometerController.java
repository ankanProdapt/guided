package com.learning.hello.contoller;

public class OdometerController {

	private static String DIGITS = "123456789";

	private int reading;

	public OdometerController(int size) {

		if (size < 1 || size > 9) {
			System.out.println("Invalid Length");
			return;
		}

		reading = Integer.valueOf(DIGITS.substring(0, size));
	}
	
	public void reset() {
		reading = 12345;
	}

	public int getSize() {
		return String.valueOf(reading).length();
	}

	public int getReading() {
		return reading;
	}

	public void setReading(int reading) {
		this.reading = reading;
	}

	public static int getMaxReading(int size) {
		if (size == 0)
			return 0;
		return Integer.valueOf(DIGITS.substring(size - 1));
	}

	public static int getMinReading(int size) {
		if (size == 0)
			return 0;
		return Integer.valueOf(DIGITS.substring(0, size));
	}

	// Optimized Next Reading
	public static int getNext(int reading, int prevDigit) {
		if (prevDigit - 1 != reading % 10) {
			return reading + 1;
		}
		reading = getNext(reading / 10, reading % 10);
		return reading * 10 + reading % 10 + 1;
	}

	public void incrementReading() {
		incrementReadingBy(1);
	}

	public void incrementReadingBy(int k) {
		if (k == 0)
			return;

		if (reading % 10 != 9) {
			reading += 1;
		}

		else {
			if (reading == getMaxReading(getSize())) {
				reading = getMinReading(getSize());
			} else {
				int prevDigit = 10;
				reading = getNext(reading, prevDigit);
			}
		}

		incrementReadingBy(k - 1);
	}

	public static int getPrev(int reading, int digitToBeAdded) {
		if (reading % 10 - 1 != (reading / 10) % 10)
			return reading - 1;

		else
			return getPrev(reading / 10, digitToBeAdded - 1) * 10 + digitToBeAdded;
	}

	public void decrementReading() {
		decrementReadingBy(1);
	}

	public void decrementReadingBy(int k) {
		if (k == 0)
			return;

		if (reading == getMinReading(getSize())) {
			reading = getMaxReading(getSize());
		} else {
			int digitToBeAdded = 9;
			reading = getPrev(reading, digitToBeAdded);
		}

		decrementReadingBy(k - 1);
	}

	@Override
	public String toString() {
		return String.valueOf(reading);
	}

}