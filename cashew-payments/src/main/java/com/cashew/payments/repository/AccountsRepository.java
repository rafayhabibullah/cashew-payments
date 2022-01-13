package com.cashew.payments.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cashew.payments.model.Account;

public interface AccountsRepository extends JpaRepository<Account, String>{

}
