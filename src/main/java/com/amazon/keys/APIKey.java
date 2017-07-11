package com.amazon.keys;

import java.util.Map;

public class APIKey {
	
	private String key;
	private Role role;
	private Map<String, Integer> calls;
	
	public APIKey(String key, Role role, Map<String, Integer> calls) {
		super();
		this.key = key;
		this.role = role;
		this.calls = calls;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Map<String, Integer> getCalls() {
		return calls;
	}
	public void setCalls(Map<String, Integer> calls) {
		this.calls = calls;
	}
	
	
	

}
