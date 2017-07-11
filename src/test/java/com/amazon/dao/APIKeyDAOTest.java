package com.amazon.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.amazon.keys.APIKey;
import com.amazon.keys.Role;

public class APIKeyDAOTest {

	@Test
	public void testGetKey() {

		APIKeyCache akc = APIKeyCache.getInstance();
		APIKey k = akc.getNewKey();

		boolean isBetween50And80 = k.getKey().length() >= 50 && k.getKey().length() <= 80;
		assertTrue(isBetween50And80);

		char[] charPossible = "abcdefghijklmnopqrstuvwxyz0123456789-/*$#%".toCharArray();

		for (int i = 0; i < k.getKey().length(); i++) {
			boolean found = false;
			for (int j = 0; j < charPossible.length; j++) {

				if (k.getKey().charAt(i) == charPossible[j]) {
					found = true;
				}
			}
			assertTrue(found);
		}

		assertEquals(k.getRole(), Role.CLIENT);
		assertEquals(k.getCalls().size(), 0);

		System.out.println(k);

	}

	@Test
	public void testIsAuthorized() {
		APIKeyCache akc = APIKeyCache.getInstance();
		APIKey k = akc.getNewKey();

		k.setRole(Role.ADMIN);
		assertTrue(akc.isAuthorized(Role.ADMIN, k));
		assertTrue(akc.isAuthorized(Role.CLIENT, k));
		assertTrue(akc.isAuthorized(Role.VISITOR, k));

		k.setRole(Role.CLIENT);
		assertFalse(akc.isAuthorized(Role.ADMIN, k));
		assertTrue(akc.isAuthorized(Role.CLIENT, k));
		assertTrue(akc.isAuthorized(Role.VISITOR, k));

		k.setRole(Role.VISITOR);
		assertFalse(akc.isAuthorized(Role.ADMIN, k));
		assertFalse(akc.isAuthorized(Role.CLIENT, k));
		assertTrue(akc.isAuthorized(Role.VISITOR, k));
	}
	
	@Test
	public void testReachedLimit(){
		APIKeyCache akc = APIKeyCache.getInstance();
		APIKey k = akc.getNewKey();
		
		assertFalse(akc.reachedLimit("/", k));
		k.getCalls().put("/", 60);
		assertTrue(akc.reachedLimit("/", k));
	}
}
