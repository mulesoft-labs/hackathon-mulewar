package com.mulesoft.ht;

import java.util.Collection;

import com.mulesoft.ht.communication.MemberInfo;

import edu.emory.mathcs.backport.java.util.Collections;

public class StatsHelper {

    private final MemberInfo memberInfo;

    public static StatsHelper getInstance(MemberInfo memberInfo) {
        return new StatsHelper(memberInfo);
    }

    private StatsHelper(MemberInfo memberInfo) {
        this.memberInfo = memberInfo;
    }

    public int getClickCount() {
        int counter = 0;
        for (Integer value : memberInfo.getClicks().values()) {
            counter += value;
        }
        return counter;
    }

    public int getMaxClicksPerSecond() {
        return (int) Collections.max(memberInfo.getClicks().values());
    }

    public int getMinClicksPerSecond() {
        return (int) Collections.min(memberInfo.getClicks().values());
    }

    public double getAverageClicksPerSecond() {
        Collection<Integer> values = memberInfo.getClicks().values();
        Integer sum = 0;
        for (Integer value : values) {
            sum += value;
        }
        return sum.doubleValue() / values.size();
    }
}
