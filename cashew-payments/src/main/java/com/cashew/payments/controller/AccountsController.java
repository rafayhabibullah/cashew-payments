package com.cashew.payments.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cashew.payments.model.Account;
import com.cashew.payments.service.AccountsService;

@RestController
public class AccountsController {

	@Autowired
	AccountsService accountsService;
	
	@RequestMapping(value = "/accounts", method = RequestMethod.GET)
	public ResponseEntity<List<Account>> getAllAccounts() {
		List<Account> accounts = accountsService.getAllAccounts();
		if(accounts.isEmpty())
			return new ResponseEntity<List<Account>>(accounts, HttpStatus.NO_CONTENT);
		else 
			return new ResponseEntity<List<Account>>(accounts, HttpStatus.OK);
	}
}
