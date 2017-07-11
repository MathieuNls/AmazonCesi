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

		char[] charPossible = "abcdefghijklmnopqrstuvwxyz0123456789-/*$".toCharArray();

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
		assertTrue(akc.isAuthorized(Role.ADMIN, k.getKey()));
		assertTrue(akc.isAuthorized(Role.CLIENT, k.getKey()));
		assertTrue(akc.isAuthorized(Role.VISITOR, k.getKey()));

		k.setRole(Role.CLIENT);
		assertFalse(akc.isAuthorized(Role.ADMIN, k.getKey()));
		assertTrue(akc.isAuthorized(Role.CLIENT,k.getKey()));
		assertTrue(akc.isAuthorized(Role.VISITOR, k.getKey()));

		k.setRole(Role.VISITOR);
		assertFalse(akc.isAuthorized(Role.ADMIN, k.getKey()));
		assertFalse(akc.isAuthorized(Role.CLIENT, k.getKey()));
		assertTrue(akc.isAuthorized(Role.VISITOR, k.getKey()));
	}

	@Test
	public void testReachedLimit() {
		APIKeyCache akc = APIKeyCache.getInstance();
		APIKey k = akc.getNewKey();

		assertFalse(akc.reachedLimit("/", k.getKey()));
		k.getCalls().put("/", 60);
		assertTrue(akc.reachedLimit("/", k.getKey()));
	}
}
