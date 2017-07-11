package com.amazon.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.amazon.keys.APIKey;
import com.amazon.keys.Role;

public class APIKeyCache implements APIKeyDAO {

	private static APIKeyCache instance;

	private List<APIKey> keys = new ArrayList<APIKey>();
	private Map<String, Integer> maxCalls = new HashMap<>();

	private APIKeyCache() {

		maxCalls.put("/book", 10);
		maxCalls.put("/", 50);
	}

	public static APIKeyCache getInstance() {
		if (instance == null) {
			synchronized (BookDAOCache.class) {
				if (instance == null) {
					APIKeyCache.instance = new APIKeyCache();
				}
			}
		}
		return APIKeyCache.instance;
	}

	@Override
	public APIKey getNewKey() {

		Random r = new Random(System.currentTimeMillis() + new Random().nextLong());

		char[] charPossible = "abcdefghijklmnopqrstuvwxyz0123456789-/*$".toCharArray();
		String key = "";
		for (int i = 0; i < 50 + r.nextInt(30); i++) {

			key += charPossible[r.nextInt(charPossible.length)];
		}

		for (APIKey k : this.keys) {
			if (k.getKey().compareTo(key) == 0) {
				return this.getNewKey();
			}
		}

		APIKey k = new APIKey(key, Role.CLIENT, new HashMap<>());
		this.keys.add(k);
		return k;
	}

	@Override
	public boolean isAuthorized(Role target, String key) {

		APIKey k = this.findKey(key);

		if (k == null) {
			return false;
		} else if (k.getRole() == Role.ADMIN) {
			return true;
		} else if (k.getRole() == Role.CLIENT && (target == Role.CLIENT || target == Role.VISITOR)) {
			return true;
		} else if (k.getRole() == Role.VISITOR && target == Role.VISITOR) {
			return true;
		}

		return false;
	}

	public void callSuccess(String endpoint, String key) {
		APIKey k = this.findKey(key);
		int previousCalls = 0;

		if (k.getCalls().containsKey(endpoint)) {
			previousCalls = k.getCalls().get(endpoint);
		}

		k.getCalls().put(endpoint, previousCalls + 1);
	}

	@Override
	public boolean reachedLimit(String endpoint, String key) {
		
		APIKey k = this.findKey(key);
		
		if (k.getCalls().get(endpoint) != null && k.getCalls().get(endpoint) > this.maxCalls.get(endpoint)) {
			return true;
		}

		return false;
	}

	@Override
	public void deleteKey(String key) {
		int i = 0;
		boolean found = false;
		for (i = 0; i < this.keys.size(); i++) {
			if (this.keys.get(i).getKey().compareTo(key) == 0) {
				break;
			}
		}

		if (found) {
			this.keys.remove(i);
		}

	}

	private APIKey findKey(String key) {
		APIKey k = null;

		for (APIKey apiKey : this.keys) {
			if (apiKey.getKey().compareTo(key) == 0) {
				k = apiKey;
				break;
			}
		}
		return k;
	}
}
