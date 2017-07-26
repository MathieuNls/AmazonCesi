package com.amazon.ctrl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amazon.dao.APIKeyCache;
import com.amazon.keys.Role;

@RestController
public class APIKeyController {

	/**
	 * curl localhost:8080/keys/create
	 * 
	 * Une clef
	 * @return
	 */
	@RequestMapping("/keys/create")
	public String getKey() {
		APIKeyCache akc = APIKeyCache.getInstance();
		return akc.getNewKey().getStringKey();
	}

	/**
	 * curl localhost:8080/keys/delete?key=maKey
	 */
	@RequestMapping("/keys/delete")
	public void deleteKey(@RequestParam(name = "key") String key) {

		if (!APIKeyCache.getInstance().isAuthorized(Role.VISITOR, key)) {
			return;
		}

		APIKeyCache akc = APIKeyCache.getInstance();
		akc.deleteKey(key);
	}
}
