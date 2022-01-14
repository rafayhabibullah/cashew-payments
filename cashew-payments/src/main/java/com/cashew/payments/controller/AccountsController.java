package com.cashew.payments.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cashew.payments.model.Account;
import com.cashew.payments.service.AccountsService;

/**
 * {@code AccountsController} performs operations on accounts
 * 
 * @author rafayhabibullah
 *
 */
@RestController
public class AccountsController {
	
	private static final Logger log = LoggerFactory.getLogger(AccountsController.class);

	@Autowired
	AccountsService accountsService;
	
	/**
	 * Service endpoint to fetch all accounts from database
	 * 
	 * @return List<Account> - returns list of all accounts
	 */
	@RequestMapping(value = "/accounts", method = RequestMethod.GET)
	public ResponseEntity<List<Account>> getAllAccounts() {
		log.info("getAllAccounts service - started");
		List<Account> accounts = accountsService.getAllAccounts();
		if(accounts.isEmpty()) {
			log.info("getAllAccounts service - no content found");
			return new ResponseEntity<List<Account>>(accounts, HttpStatus.NO_CONTENT);
		}
		else {
			log.info("getAllAccounts service - content found");
			log.debug("getAllAccounts service : " + accounts.toString());
			return new ResponseEntity<List<Account>>(accounts, HttpStatus.OK);
		}
	}
}
