package com.mulesoft.ht.communication;

public class GameStatus {
    private boolean isStarted;
    private boolean isEnded;
    private String winningTeam;
    private long percentage;

    public GameStatus(boolean isStarted, boolean isEnded) {
        this.isStarted = isStarted;
        this.isEnded = isEnded;
    }

    public String getWinningTeam() {
        return winningTeam;
    }

    public void setWinningTeam(String winningTeam) {
        this.winningTeam = winningTeam;
    }

    public long getPercentage() {
        return percentage;
    }

    public void setPercentage(long percentage) {
        this.percentage = percentage;
    }

    public boolean isEnded() {
        return isEnded;
    }

    public void setEnded(boolean isEnded) {
        this.isEnded = isEnded;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(boolean isStarted) {
        this.isStarted = isStarted;
    }
}
