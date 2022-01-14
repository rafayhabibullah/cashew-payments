package com.cashew.payments.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cashew.payments.model.Account;
import com.cashew.payments.repository.AccountsRepository;

/**
 * {@Code AccountsService} component for providing accounts related save 
 * and get operations
 * @author rafayhabibullah
 *
 */
@Service
public class AccountsService {
	
	@Autowired
	AccountsRepository accountsRepository;
	
	/**
	 * insert list of accounts in database
	 * 
	 * Used for Bulk insert operation
	 * 
	 * @param accounts
	 */
	public void saveAccounts(List<Account> accounts) {
		accountsRepository.saveAll(accounts);
	}
	
	/**
	 * get all accounts from database
	 * @return List<Account>
	 */
	public List<Account> getAllAccounts() {
		return accountsRepository.findAll();
	}
	
	/**
	 * get an account by Id
	 * 
	 * @param id
	 * @return Optional<Account>
	 */
	public Optional<Account> getAccount(String id) {
		return accountsRepository.findById(id);
	}
	
	/**
	 * save transfer request in database
	 * 
	 * update records for transferer and transferee after 
	 * updating account balance
	 * 
	 * @param accounts
	 */
	public void saveTransfer(List<Account> accounts) {
		accountsRepository.saveAll(accounts);
	}
	
}
