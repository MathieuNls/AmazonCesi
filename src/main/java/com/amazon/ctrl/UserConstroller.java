package com.amazon.ctrl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amazon.dao.APIKeyCache;
import com.amazon.dao.UserDAOCache;
import com.amazon.keys.Role;
import com.amazon.pojo.User;

@RestController
public class UserConstroller {

	/**
	 * curl localhost:8080/users/apikey?key=123
	 * 
	 * @param key
	 * @return
	 */
	@RequestMapping("/users/apikey")
	public String userByApiKey(@RequestParam(value = "key", required = true) String key) {

		APIKeyCache akc = APIKeyCache.getInstance();

		if (!akc.isAuthorized(Role.VISITOR, key) || APIKeyCache.getInstance().reachedLimit("/users/apikey", key)) {
			return null;
		}

		return UserDAOCache.getInstance().findByAPIKey(key).toString();
	}

	/**
	 * curl localhost:8080/users/names?firstname=X&lastname=Y&key=Z
	 * 
	 * @param firstname
	 * @param lastname
	 * @param key
	 * @return
	 */
	@RequestMapping("/users/names")
	public String userByNames(@RequestParam(value = "firstname", required = true) String firstname,
			@RequestParam(value = "lastname", required = true) String lastname,
			@RequestParam(value = "key", required = true) String key) {

		APIKeyCache akc = APIKeyCache.getInstance();

		if (!akc.isAuthorized(Role.ADMIN, key) || APIKeyCache.getInstance().reachedLimit("/users/names", key)) {
			return null;
		}

		return UserDAOCache.getInstance().findByName(firstname, lastname).toString();

	}

	@RequestMapping("/users/create")
	public void createUser(@RequestParam(value = "firstname", required = true) String firstname,
			@RequestParam(value = "lastname", required = true) String lastname,
			@RequestParam(value = "key", required = true) String key) {
		
		APIKeyCache akc = APIKeyCache.getInstance();

		if (!akc.isAuthorized(Role.VISITOR, key) || APIKeyCache.getInstance().reachedLimit("/users/create", key)) {
			return;
		}
		
		User u = new User(akc.getKeyByString(key), lastname, firstname);
		
		UserDAOCache.getInstance().createUser(u);
		u.getKey().setRole(Role.CLIENT);
	}
	
	@RequestMapping("/users/delete")
	public void deleteUsersByKey(@RequestParam(value = "key", required = true) String key){
		
		APIKeyCache akc = APIKeyCache.getInstance();

		if (!akc.isAuthorized(Role.CLIENT, key) || APIKeyCache.getInstance().reachedLimit("/users/delete", key)) {
			return;
		}
		
		UserDAOCache.getInstance().deleteUserByAPIKey(key);
	}

}
