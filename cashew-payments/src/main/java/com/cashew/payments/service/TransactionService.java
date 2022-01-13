package com.cashew.payments.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cashew.payments.exception.handler.InSufficientBalanceException;
import com.cashew.payments.exception.handler.InvalidInputException;
import com.cashew.payments.model.Account;
import com.cashew.payments.model.Transaction;
import com.cashew.payments.validator.PaymentValidator;

@Service
public class TransactionService {
	
	private static final Logger log = LoggerFactory.getLogger(TransactionService.class); 
	
	@Autowired
	AccountsService accountsService;
	
	public Account transfer(Transaction transaction) {
		if(PaymentValidator.isInvalidTransferAmount(transaction))
			throw new InvalidInputException("Invalid transfer amount");
		
		Optional<Account> optTransferer = accountsService.getAccount(transaction.getTransferer());
		Optional<Account> optTransferee = accountsService.getAccount(transaction.getTransferee());
		
		if(optTransferer.isEmpty()) 
			throw new EntityNotFoundException("transferer not found : " + transaction.getTransferer());
		else if (optTransferee.isEmpty())
			throw new EntityNotFoundException("transferee not found : " + transaction.getTransferee());

		Account transferer = optTransferer.get();
		Account transferee = optTransferee.get();
		
		if(PaymentValidator.isSufficientBalance(transaction, transferer)) {
			log.debug("transfer - Transfering " + transaction.getAmount() + " from " + transaction.getTransferer() + " to " + transaction.getTransferee());
			BigDecimal transfererBalance = transferer.getBalance().subtract(transaction.getAmount());
			BigDecimal transfereeBalance = transferee.getBalance().add(transaction.getAmount());
			transferer.setBalance(transfererBalance);
			transferee.setBalance(transfereeBalance);
			
			accountsService.saveTransfer(Arrays.asList(transferer, transferee));
			log.debug("transfer - Transfered " + transaction.getAmount() + " from " + transaction.getTransferer() + " to " + transaction.getTransferee());
		} else {
			log.debug("transfer - " + transaction.getTransferer() + " has in-sufficient balance");
			throw new InSufficientBalanceException(transaction.getTransferer() + " has in-sufficient balance");
		}
		
		return transferer;
	}
}
