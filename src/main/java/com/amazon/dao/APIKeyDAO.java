package com.amazon.dao;

import com.amazon.keys.APIKey;
import com.amazon.keys.Role;

public interface APIKeyDAO {
	
	public APIKey getNewKey();
	public boolean isAuthorized(Role target, APIKey key);
	public boolean reachedLimit(String endpoint, APIKey key);
	public void deleteKey(String key);
}