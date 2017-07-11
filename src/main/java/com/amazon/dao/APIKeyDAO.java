package com.amazon.dao;

import com.amazon.keys.APIKey;
import com.amazon.keys.Role;

public interface APIKeyDAO {
	
	public APIKey getNewKey();
	public boolean isAuthorized(Role target, String key);
	public boolean reachedLimit(String endpoint, String key);
	public void deleteKey(String key);
}