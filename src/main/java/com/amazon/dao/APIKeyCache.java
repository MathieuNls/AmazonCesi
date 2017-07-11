package com.amazon.dao;

import com.amazon.keys.APIKey;
import com.amazon.keys.Role;

public class APIKeyCache implements APIKeyDAO {

	@Override
	public APIKey getNewKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAuthorized(Role target, APIKey key) {
		// TODO Auto-generated method stub
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
