package com.mulesoft.ht;

import java.util.HashMap;
import java.util.Map;

public enum Team {
    
    RED("red"),
    BLUE("blue");
    
    private static Map<String, Team> resolverMap = new HashMap<String, Team>();
    
    static {
        for (Team team : Team.values()) {
            resolverMap.put(team.getValue(), team);
        }
    }
    
    private String team;
    
    public String getValue() {
        return team;
    }
    
    private Team (String team) {
        this.team = team;
    }
    
    public static Team fromString(String teamCandidate) {
        final Team team = resolverMap.get(teamCandidate);
        if (team == null) {
            throw new IllegalArgumentException();
        }
        return team;
    }
}
