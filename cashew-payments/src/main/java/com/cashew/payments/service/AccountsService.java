package com.cashew.payments.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cashew.payments.model.Account;
import com.cashew.payments.repository.AccountsRepository;

@Service
public class AccountsService {
	
	@Autowired
	AccountsRepository accountsRepository;
	
	public void saveAccounts(List<Account> accounts) {
		accountsRepository.saveAll(accounts);
	}
	
}