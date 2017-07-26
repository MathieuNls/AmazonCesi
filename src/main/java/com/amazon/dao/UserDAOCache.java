package com.amazon.dao;

import java.util.ArrayList;
import java.util.List;

import com.amazon.pojo.User;

public class UserDAOCache implements UserDAO {

	private static UserDAOCache instance;
	private List<User> users = new ArrayList<>();

	private UserDAOCache() {
	}

	public static UserDAOCache getInstance() {

		if (instance == null) {
			synchronized (UserDAOCacheTest.class) {
				if (instance == null) {
					instance = new UserDAOCache();
				}
			}
		}
		return instance;
	}

	@Override
	public User findByAPIKey(String apiKey) {

		for (User user : this.users) {

			if (user.getKey().getStringKey().compareTo(apiKey) == 0) {
				return user;
			}
		}

		return null;
	}

	@Override
	public User findByName(String firstname, String lastname) {

		for (User user : this.users) {

			if (user.getName().compareToIgnoreCase(lastname) == 0
					&& user.getFirstname().compareToIgnoreCase(firstname) == 0) {
				return user;
			}
		}

		return null;
	}

	@Override
	public void createUser(User user) {

		if (!this.users.contains(user)) {
			this.users.add(user);
		}

	}

	@Override
	public void deleteUserByAPIKey(String apiKey) {
		int i = 0;
		boolean found = false;
		for (i = 0; i < this.users.size(); i++) {
			
			if(this.users.get(i).getKey().getStringKey().compareTo(apiKey) == 0){
				found = true;
				break;
			}
		}
		
		if(found){
			// 1 2 3 4 5 7 8 9 10
			this.users.remove(i);
		}

	}

}
