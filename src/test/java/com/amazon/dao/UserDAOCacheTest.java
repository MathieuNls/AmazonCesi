package com.amazon.dao;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

import com.amazon.keys.APIKey;
import com.amazon.keys.Role;
import com.amazon.pojo.User;

public class UserDAOCacheTest {

	@Test
	public void testCreateUser() {

		UserDAO uDAO = UserDAOCache.getInstance();
		User tmp = new User(new APIKey("122", Role.CLIENT, new HashMap<>()), "Nayrolles", "Mathieu");
		uDAO.createUser(tmp);
		
		assertTrue(tmp.equals(uDAO.findByAPIKey("122")));
	}
	
	@Test
	public void testFindByNames(){
		UserDAO uDAO = UserDAOCache.getInstance();
		User tmp = new User(new APIKey("121", Role.CLIENT, new HashMap<>()), "Jack", "Bony");
		
		uDAO.createUser(tmp);
		
		assertTrue(tmp.equals(uDAO.findByName("Bony", "Jack")));
	}

}
