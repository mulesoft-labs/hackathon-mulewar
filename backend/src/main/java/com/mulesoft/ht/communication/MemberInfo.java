package com.mulesoft.ht.communication;

import java.util.HashMap;
import java.util.Map;

public class MemberInfo {

	private final String username;
	private final Map<Integer, Integer> clicks;

	public MemberInfo(String username) {
		this.username = username;
		clicks = new HashMap<Integer,Integer>();
	}

	public void addClick(Integer second, Integer clickCount) {
		clicks.put(second, clickCount);
	}

	public Map<Integer, Integer> getClicks() {
		return clicks;
	}

	public String getUsername() {
		return username;
	}
}
