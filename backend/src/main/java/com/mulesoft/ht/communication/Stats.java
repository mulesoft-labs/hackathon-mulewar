package com.mulesoft.ht.communication;

public class Stats {

	private int clickCounter;
	private int maxClicksPerSecond;
	private int minClicksPerSecond;
	private double avgClicksPerSecond;
	
	private static final int NO_ACTIVITY = -1;
	
	public Stats() {
	    clickCounter = NO_ACTIVITY;
	    maxClicksPerSecond = NO_ACTIVITY;
	    minClicksPerSecond = NO_ACTIVITY;
	    avgClicksPerSecond = NO_ACTIVITY;
	}
	
	public int getClickCounter() {
		return clickCounter;
	}
	public void setClickCounter(int clickCounter) {
		this.clickCounter = clickCounter;
	}
	public int getMaxClicksPerSecond() {
		return maxClicksPerSecond;
	}
	public void setMaxClicksPerSecond(int maxClicksPerSecond) {
		this.maxClicksPerSecond = maxClicksPerSecond;
	}
	public int getMinClicksPerSecond() {
		return minClicksPerSecond;
	}
	public void setMinClicksPerSecond(int minClicksPerSecond) {
		this.minClicksPerSecond = minClicksPerSecond;
	}
	public double getAvgClicksPerSecond() {
		return avgClicksPerSecond;
	}
	public void setAvgClicksPerSecond(double avgClicksPerSecond) {
		this.avgClicksPerSecond = avgClicksPerSecond;
	}	
}
