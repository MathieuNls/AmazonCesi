package com.amazon.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.amazon.keys.APIKey;
import com.amazon.keys.Role;

public class APIKeyCache implements APIKeyDAO {

	private static APIKeyCache instance;

	private List<APIKey> keys = new ArrayList<APIKey>();

	private APIKeyCache() {
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

		char[] charPossible = "abcdefghijklmnopqrstuvwxyz0123456789-/*$#%".toCharArray();
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
		return k;
	}

	@Override
	public boolean isAuthorized(Role target, APIKey key) {
		
		if(key.getRole() == Role.ADMIN){
			return true;
		}else if(key.getRole() == Role.CLIENT && (target == Role.CLIENT || target == Role.VISITOR)){
			return true;
		}else if(key.getRole() == Role.VISITOR && target == Role.VISITOR){
			return true;
		}

		return false;
	}

	@Override
	public boolean reachedLimit(String endpoint, APIKey key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void deleteKey(APIKey key) {
		// TODO Auto-generated method stub

	}

}
