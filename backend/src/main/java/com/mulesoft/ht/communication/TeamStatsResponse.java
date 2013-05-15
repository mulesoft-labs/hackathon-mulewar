package com.mulesoft.ht.communication;

public class TeamStatsResponse {

	private int redTeamCounter;
	private int blueTeamCounter;
	private boolean hasGameEnded;

	public TeamStatsResponse(int redTeamCounter, int blueTeamCounter, boolean hasGameEnded) {
		this.redTeamCounter = redTeamCounter;
		this.blueTeamCounter = blueTeamCounter;
		this.hasGameEnded = hasGameEnded;
	}

	public int getRedTeamCounter() {
		return redTeamCounter;
	}

	public void setRedTeamCounter(int redTeamCounter) {
		this.redTeamCounter = redTeamCounter;
	}

	public int getBlueTeamCounter() {
		return blueTeamCounter;
	}

	public void setBlueTeamCounter(int blueTeamCounter) {
		this.blueTeamCounter = blueTeamCounter;
	}

	public boolean isHasGameEnded() {
		return hasGameEnded;
	}

	public void setHasGameEnded(boolean hasGameEnded) {
		this.hasGameEnded = hasGameEnded;
	}
}
