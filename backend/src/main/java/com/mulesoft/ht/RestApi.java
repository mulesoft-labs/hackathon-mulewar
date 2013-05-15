package com.mulesoft.ht;

import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.mulesoft.ht.communication.GameStatus;
import com.mulesoft.ht.communication.MemberInfo;
import com.mulesoft.ht.communication.Stats;
import com.mulesoft.ht.communication.TeamStatsResponse;

@Path("/")
public class RestApi {

    @GET
    @Path("/admin/reset")
    @Produces({ "application/json" })
    public int reset() {
        State.redCounter.set(0);
        State.blueCounter.set(0);
        State.blueMemberStatsMap.clear();
        State.redMemberStatsMap.clear();
        State.hasStarted.set(false);
        State.hasEnded.set(false);
        State.gameStartedTime.set(0);
        return State.STATUS_ADMIN_SUCCESS;
    }
    
    @GET
    @Path("/admin/debug/enable")
    @Produces({ "application/json" })
    public int debugEnabled() {
        State.isDebugEnabled.set(true);
        return State.STATUS_ADMIN_SUCCESS;
    }
    
    @GET
    @Path("/admin/debug/disable")
    @Produces({ "application/json" })
    public int debugDisabled() {
        State.isDebugEnabled.set(false);
        return State.STATUS_ADMIN_SUCCESS;
    }

    @GET
    @Path("/admin/start")
    @Produces({ "application/json" })
    public int start() {
        State.hasStarted.getAndSet(true);
        State.gameStartedTime.set(System.currentTimeMillis());
        return State.STATUS_ADMIN_SUCCESS;
    }

    @GET
    @Path("/startedStatus")
    @Produces({ "application/json" })
    public GameStatus getGameStatus() {
        final boolean isGameOver = State.isGameOver();
        final GameStatus status = new GameStatus(State.hasStarted.get(), isGameOver);

        long percentage = State.getPercentage();
        if (percentage == State.STATUS_NO_TEAM_ACTIVITIY) {
            // Nobody made a single click!
            return status;
        } else {
            status.setPercentage(State.getPercentage());
        }

        if (isRedWinning()) {
            status.setWinningTeam(Team.RED.getValue());
        } else {
            status.setWinningTeam(Team.BLUE.getValue());
        }

        return status;
    }

    private boolean isRedWinning() {
        return (State.redCounter.get() > State.blueCounter.get());
    }

    @GET
    @Path("/stats/team/all")
    @Produces({ "application/json" })
    public TeamStatsResponse getTeamStats() {
        return new TeamStatsResponse(State.redCounter.get(), State.blueCounter.get(), State.isGameOver());
    }


    @GET
    @Path("/stats/team/{team}/{username}")
    @Produces({ "application/json" })
    public Stats getTeamMemberStats(@PathParam("team") String teamCandidate, @PathParam("username") String username) {
        final MemberInfo memberInfo;
        final Team team = Team.fromString(teamCandidate);
        switch (team) {
        case RED:
            memberInfo = State.redMemberStatsMap.get(username);
            break;
        case BLUE:
            memberInfo = State.blueMemberStatsMap.get(username);
            break;
        default:
            return new Stats();
        }

        if (memberInfo == null) {
            return new Stats();
        }

        final Stats memberStats = new Stats();
        final StatsHelper helper = StatsHelper.getInstance(memberInfo);
        memberStats.setClickCounter(helper.getClickCount());
        memberStats.setMaxClicksPerSecond(helper.getMaxClicksPerSecond());
        memberStats.setMinClicksPerSecond(helper.getMinClicksPerSecond());
        memberStats.setAvgClicksPerSecond(helper.getAverageClicksPerSecond());

        return memberStats;
    }

    @GET
    @Path("/stats/team/{team}")
    @Produces({ "application/json" })
    public Stats getTeamStats(@PathParam("team") String teamCandidate, @PathParam("username") String username) {
        final Collection<MemberInfo> mapMembers;
        final Team team = Team.fromString(teamCandidate);
        switch (team) {
        case RED:
            mapMembers = State.redMemberStatsMap.values();
            break;
        case BLUE:
            mapMembers = State.blueMemberStatsMap.values();
            break;
        default:
            return new Stats();
        }

        if (mapMembers.isEmpty()) {
            return new Stats();
        }

        final Stats stats = new Stats();

        stats.setClickCounter(getClickCount(mapMembers));
        stats.setMaxClicksPerSecond(getMaxClicksPerSecond(mapMembers));
        stats.setMinClicksPerSecond(getMinClicksPerSecond(mapMembers));
        stats.setAvgClicksPerSecond(getAverageClicksPerSecond(mapMembers));

        return stats;
    }

    private int getClickCount(Collection<MemberInfo> members) {
        int counter = 0;
        for (MemberInfo memberInfo : members) {
            counter += StatsHelper.getInstance(memberInfo).getClickCount();
        }
        return counter;
    }

    private int getMaxClicksPerSecond(Collection<MemberInfo> members) {
        int counter = 0;
        for (MemberInfo memberInfo : members) {
            counter += StatsHelper.getInstance(memberInfo).getMaxClicksPerSecond();
        }
        return counter;
    }

    private int getMinClicksPerSecond(Collection<MemberInfo> members) {
        int counter = 0;
        for (MemberInfo memberInfo : members) {
            counter += StatsHelper.getInstance(memberInfo).getMinClicksPerSecond();
        }
        return counter;
    }

    private double getAverageClicksPerSecond(Collection<MemberInfo> members) {
        double counter = 0;
        for (MemberInfo memberInfo : members) {
            counter += StatsHelper.getInstance(memberInfo).getAverageClicksPerSecond();
        }
        return counter;
    }
}
