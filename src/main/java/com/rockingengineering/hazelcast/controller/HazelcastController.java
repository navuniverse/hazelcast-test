/**
 * 
 */
package com.rockingengineering.hazelcast.controller;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hazelcast.core.IMap;

import lombok.extern.log4j.Log4j2;

/**
 * @author naveen
 *
 * @date 08-Sep-2017
 */
@Log4j2
@RestController
@RequestMapping("api")
public class HazelcastController {

	@Resource(name = "commonMap")
	private IMap<String, String> commonMap;

	@GetMapping("/getAll")
	public Set<String> getAllMapKeys() {
		log.debug("Received Get All Hazelcast Keys Request");

		return commonMap.keySet();
	}

	@GetMapping("/get/{key}")
	public String getKey(@PathVariable(name = "key") String key) {
		log.debug("Received Get Hazelcast Key Request: " + key);

		if (commonMap.containsKey(key)) {
			return "Key: " + key + ", Value: " + commonMap.get(key);
		}

		return "Key: " + key + " not found in map";
	}

	@PostMapping("/add")
	public String addKeyToMap(String key) {
		log.debug("Received Add key to Hazelcast Request");

		commonMap.put(key, "");

		return "Added Key: " + key + " in map";
	}

	@DeleteMapping(value = "/remove/{key}")
	public String removeKeyFromMap(@PathVariable(name = "key") String key) {
		log.debug("Received Remove key from Hazelcast Request");

		if (commonMap.containsKey(key)) {
			commonMap.remove(key);
			return "Removed Key: " + key + " from map";
		}

		return "Key: " + key + " not found in map";
	}
}