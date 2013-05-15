package com.mulesoft.ht;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import com.mulesoft.ht.communication.MemberInfo;

public class State {
    public static AtomicInteger redCounter = new AtomicInteger(0);
    public static AtomicInteger blueCounter = new AtomicInteger(0);
    public static Map<String, MemberInfo> blueMemberStatsMap = new ConcurrentHashMap<String, MemberInfo>();
    public static Map<String, MemberInfo> redMemberStatsMap = new ConcurrentHashMap<String, MemberInfo>();

    public static AtomicBoolean hasStarted = new AtomicBoolean();
    public static AtomicBoolean hasEnded = new AtomicBoolean();

    public static final int MAX_TIME_WINDOW = 18000; // 30 secs + 3 sec more to take into account UI call.
    public static final int FREE_TIME_PERIOD_WINDOW = 8000; // 5 secs + 3 sec
    public static AtomicLong gameStartedTime = new AtomicLong();

    public static final int STATUS_ENDED = -1;
    public static final int STATUS_NOT_STARTED = -2;
    public static final int STATUS_NO_TEAM_ACTIVITIY = -3;
    public static final int STATUS_ADMIN_SUCCESS = 0;

    public static final int WINNING_THRESHOLD_PERCENTAGE = 50;
    
    public static AtomicBoolean isDebugEnabled =  new AtomicBoolean(true);
    
    public static boolean isGameOver() {
        // Is there already a winner?
        if (State.hasStarted.get() && !State.hasEnded.get() && !State.isDebugEnabled.get() && System.currentTimeMillis() >= (State.FREE_TIME_PERIOD_WINDOW + State.gameStartedTime.get())) {
            if (hasSomeTeamAlreadyWon()) {
                State.hasEnded.set(true);
                return true;
            }
        } 
        if (State.hasStarted.get() && !State.hasEnded.get() && System.currentTimeMillis() >= (State.MAX_TIME_WINDOW + State.gameStartedTime.get())) { // Timeout?
            State.hasEnded.set(true);
            return true;
        }
        State.hasEnded.set(false);
        return false;
    }
    
    public static long getPercentage() {
        final long total = State.redCounter.get() - State.blueCounter.get();
        if (total == 0) {
            return State.STATUS_NO_TEAM_ACTIVITIY;
        }
        return Math.abs(((State.redCounter.get() - State.blueCounter.get()) * 100) / total);
    }

    public static boolean hasSomeTeamAlreadyWon() {
        if (getPercentage() > State.WINNING_THRESHOLD_PERCENTAGE) {
            return true;
        }
        return false;
    }
}
