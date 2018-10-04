/**
 * 
 */
package com.rockingengineering.vault.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author naveen
 *
 * @date 23-Oct-2017
 */
@Api(value = "Vault Controller")
@RestController
@RequestMapping("api")
public class VaultController {

	private static final Logger logger = Logger.getLogger(VaultController.class);

	@ApiOperation(value = "Get All Vault Keys")
	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public Set<String> getAllMapKeys() {
		logger.debug("Received Get All Hazelcast Keys Request");

		return null;
	}

	@ApiOperation(value = "Add key to Vault")
	@RequestMapping(value = "/addKey", method = RequestMethod.POST)
	public String addKeyToVault(String key, String value) {
		logger.debug("Received Add key to Vault Request");

		return "Added Key: " + key + " in Vault";
	}

	@ApiOperation(value = "Remove key to Vault")
	@RequestMapping(value = "/removeKey", method = RequestMethod.POST)
	public String removeKeyFromMap(String key) {
		logger.debug("Received Remove key from Vault Request");

		return "Removed Key: " + key + " from vault";
	}
}