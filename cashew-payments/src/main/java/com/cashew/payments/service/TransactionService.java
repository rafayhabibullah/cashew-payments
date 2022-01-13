package com.cashew.payments.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cashew.payments.model.Account;
import com.cashew.payments.model.Transaction;

@Service
public class TransactionService {
	
	private static final Logger log = LoggerFactory.getLogger(TransactionService.class); 
	
	@Autowired
	AccountsService accountsService;
	
	public Account transfer(Transaction transaction) {
		Optional<Account> optTransferer = accountsService.getAccount(transaction.getTransferer());
		Optional<Account> optTransferee = accountsService.getAccount(transaction.getTransferee());
		
		if(optTransferer.isEmpty()) 
			throw new EntityNotFoundException("transferer not found : " + transaction.getTransferer());
		else if (optTransferee.isEmpty())
			throw new EntityNotFoundException("transferee not found : " + transaction.getTransferee());

		Account transferer = optTransferer.get();
		Account transferee = optTransferee.get();
		
		if(transaction.getAmount().compareTo(BigDecimal.ZERO) == 1 
				&& transferer.getBalance().compareTo(transaction.getAmount()) >= 0) {
			log.debug("transfer - Transfering " + transaction.getAmount() + " from " + transaction.getTransferer() + " to " + transaction.getTransferee());
			BigDecimal transfererBalance = transferer.getBalance().subtract(transaction.getAmount());
			BigDecimal transfereeBalance = transferee.getBalance().add(transaction.getAmount());
			transferer.setBalance(transfererBalance);
			transferee.setBalance(transfereeBalance);
			
			accountsService.saveTransfer(Arrays.asList(transferer, transferee));
			log.debug("transfer - Transfered " + transaction.getAmount() + " from " + transaction.getTransferer() + " to " + transaction.getTransferee());
		}
		
		return transferer;
	}
}
