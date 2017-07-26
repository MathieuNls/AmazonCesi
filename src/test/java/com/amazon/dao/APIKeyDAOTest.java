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

		boolean isBetween50And80 = k.getStringKey().length() >= 50 && k.getStringKey().length() <= 80;
		assertTrue(isBetween50And80);

		char[] charPossible = "abcdefghijklmnopqrstuvwxyz0123456789-/*$".toCharArray();

		for (int i = 0; i < k.getStringKey().length(); i++) {
			boolean found = false;
			for (int j = 0; j < charPossible.length; j++) {

				if (k.getStringKey().charAt(i) == charPossible[j]) {
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
	public void testGetByString() {
		APIKeyCache akc = APIKeyCache.getInstance();
		APIKey k = akc.getNewKey();

		assertTrue(k.getStringKey().compareTo(akc.getKeyByString(k.getStringKey()).getStringKey()) == 0);
	}

	@Test
	public void testGetCalls() {
		APIKeyCache akc = APIKeyCache.getInstance();
		APIKey k = akc.getNewKey();

		for (int i = 0; i < 10; i++) {

			akc.callSuccess("/book", k.getStringKey());
		}
		
		for (int i = 0; i < 10; i++) {
			akc.callSuccess("/user", k.getStringKey());
		}
		
		APIKey k2 = akc.getNewKey();
		for (int i = 0; i < 12; i++) {

			akc.callSuccess("/book", k2.getStringKey());
		}
		
		for (int i = 0; i < 22; i++) {
			akc.callSuccess("/user", k2.getStringKey());
		}
		
		for (int i = 0; i < 22; i++) {
			akc.callSuccess("/keys", k2.getStringKey());
		}
		
		assertTrue("{/user=32, /keys=22, /book=22}".compareTo(akc.getCalls().toString()) == 0);
		
	}

	@Test
	public void testIsAuthorized() {
		APIKeyCache akc = APIKeyCache.getInstance();
		APIKey k = akc.getNewKey();

		k.setRole(Role.ADMIN);
		assertTrue(akc.isAuthorized(Role.ADMIN, k.getStringKey()));
		assertTrue(akc.isAuthorized(Role.CLIENT, k.getStringKey()));
		assertTrue(akc.isAuthorized(Role.VISITOR, k.getStringKey()));

		k.setRole(Role.CLIENT);
		assertFalse(akc.isAuthorized(Role.ADMIN, k.getStringKey()));
		assertTrue(akc.isAuthorized(Role.CLIENT, k.getStringKey()));
		assertTrue(akc.isAuthorized(Role.VISITOR, k.getStringKey()));

		k.setRole(Role.VISITOR);
		assertFalse(akc.isAuthorized(Role.ADMIN, k.getStringKey()));
		assertFalse(akc.isAuthorized(Role.CLIENT, k.getStringKey()));
		assertTrue(akc.isAuthorized(Role.VISITOR, k.getStringKey()));
	}

	@Test
	public void testReachedLimit() {
		APIKeyCache akc = APIKeyCache.getInstance();
		APIKey k = akc.getNewKey();

		assertFalse(akc.reachedLimit("/", k.getStringKey()));
		k.getCalls().put("/", 60);
		assertTrue(akc.reachedLimit("/", k.getStringKey()));
	}
}
