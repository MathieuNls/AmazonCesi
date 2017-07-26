package com.amazon.dao;

import com.amazon.pojo.User;

public interface UserDAO {
	
	public User findByAPIKey(String apiKey);
	public User findByName(String firstname, String lastname);
	public void createUser(User user);
	public void deleteUserByAPIKey(String apiKey);

}
