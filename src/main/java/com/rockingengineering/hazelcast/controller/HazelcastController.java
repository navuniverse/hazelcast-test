/**
 * 
 */
package com.rockingengineering.hazelcast.controller;

import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * @author naveen
 *
 * @date 08-Sep-2017
 */
@Api(value = "Hazelcast Controller", description = "Hazelcast Controller API")
@RestController
@RequestMapping("api")
public class HazelcastController {

	private static final Logger logger = Logger.getLogger(HazelcastController.class);

	@Resource(name = "commonMap")
	private ConcurrentMap<String, String> commonMap;

	@ApiOperation(value = "Get All Hazelcast Keys")
	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public Set<String> getAllMapKeys() {
		logger.debug("Received Get All Hazelcast Keys Request");

		return commonMap.keySet();
	}

	@ApiOperation(value = "Add key to Hazelcast")
	@RequestMapping(value = "/addKey", method = RequestMethod.POST)
	public String addKeyToMap(String key) {
		logger.debug("Received Add key to Hazelcast Request");

		commonMap.put(key, "");

		return "Added Key: " + key + " in map";
	}

	@ApiOperation(value = "Add key to Hazelcast")
	@RequestMapping(value = "/removeKey", method = RequestMethod.POST)
	public String removeKeyFromMap(String key) {
		logger.debug("Received Remove key from Hazelcast Request");

		commonMap.remove(key);

		return "Removed Key: " + key + " from map";
	}
}