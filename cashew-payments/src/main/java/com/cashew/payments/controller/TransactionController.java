package com.cashew.payments.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cashew.payments.model.Account;
import com.cashew.payments.model.Transaction;
import com.cashew.payments.service.TransactionService;
import com.cashew.payments.validator.RequestValidator;

@RestController
public class TransactionController {
	
	@Autowired
	TransactionService transactionService;
	
	@RequestMapping(value = "/transfer", method = RequestMethod.POST)
	public ResponseEntity<Account> transfer(@RequestBody Transaction transaction) {
		if(RequestValidator.isInvalidTransactionRequest(transaction)) 
			return new ResponseEntity<Account>(HttpStatus.UNPROCESSABLE_ENTITY);
			
		Account account = transactionService.transfer(transaction);
		return new ResponseEntity<Account>(account, HttpStatus.CREATED);
	}
}
