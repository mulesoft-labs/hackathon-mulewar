package com.mulesoft.ht;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.mulesoft.ht.communication.MemberInfo;

@Path("/")
public class BlueRestApi {
    
    @GET
    @Path("/{username}/{second}/{clickCount}")
    @Produces({ "application/json" })
    public int updateBlue(@PathParam("username") String username, @PathParam("second") Integer second, @PathParam("clickCount") Integer clickCount) {
        if (!State.hasStarted.get()) {
            return State.STATUS_NOT_STARTED;
        }

        if (State.isGameOver()) {
            return State.STATUS_ENDED;
        }

        final MemberInfo memberStat = State.blueMemberStatsMap.get(username);
        if (memberStat != null) {
            memberStat.addClick(second, clickCount);
        } else {
            final MemberInfo newMember = new MemberInfo(username);
            newMember.addClick(second, clickCount);
            State.blueMemberStatsMap.put(username, newMember);
        }

        return State.blueCounter.addAndGet(clickCount);
    }
}
