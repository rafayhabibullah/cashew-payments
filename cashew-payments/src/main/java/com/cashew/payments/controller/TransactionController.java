package com.cashew.payments.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private static final Logger log = LoggerFactory.getLogger(TransactionController.class);
	
	@Autowired
	TransactionService transactionService;
	
	@RequestMapping(value = "/transfer", method = RequestMethod.POST)
	public ResponseEntity<Account> transfer(@RequestBody Transaction transaction) {
		log.info("transfer service - started");
		if(RequestValidator.isInvalidTransactionRequest(transaction)) {
			log.info("transfer service - invalid request");
			return new ResponseEntity<Account>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
			
		Account account = transactionService.transfer(transaction);
		log.info("transfer service - successfully processed");
		return new ResponseEntity<Account>(account, HttpStatus.CREATED);
	}
}
