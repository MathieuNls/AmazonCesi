package com.amazon.ctrl;

import java.util.Map.Entry;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amazon.dao.APIKeyCache;
import com.amazon.keys.Role;

@RestController
public class HeathController {

	@RequestMapping("/health")
	public String getStatus(@RequestParam(value = "key", required = true) String key) {

		if (!APIKeyCache.getInstance().isAuthorized(Role.ADMIN, key)) {
			return null;
		}

		String status = "";

		for (Entry<String, Integer> entry : APIKeyCache.getInstance().getCalls().entrySet()) {

			status += entry.getKey() + ":" + entry.getValue() + "\n";
		}

		return status;
	}

	@RequestMapping("/ping")
	public String ping() {
		return "alive";
	}

}
