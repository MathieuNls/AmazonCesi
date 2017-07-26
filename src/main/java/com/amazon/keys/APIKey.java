package com.amazon.keys;

import java.util.List;
import java.util.Map;

public class APIKey {
	
	private String key;
	private Role role;
	private Map<String, List<Long>> calls;
	
	public APIKey(String key, Role role, Map<String, List<Long>> calls) {
		super();
		this.key = key;
		this.role = role;
		this.calls = calls;
	}
	public String getStringKey() {
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
	public Map<String, List<Long>> getCalls() {
		return calls;
	}
	public void setCalls(Map<String, List<Long>> calls) {
		this.calls = calls;
	}
	@Override
	public String toString() {
		return "APIKey [key=" + key + ", role=" + role + ", calls=" + calls + "]";
	}
}
