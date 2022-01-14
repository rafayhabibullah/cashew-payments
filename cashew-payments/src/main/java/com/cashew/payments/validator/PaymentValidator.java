package com.cashew.payments.validator;

import java.math.BigDecimal;

import com.cashew.payments.model.Account;
import com.cashew.payments.model.Transaction;

/**
 * Performs validations on payment object
 * 
 * @author rafayhabibullah
 *
 */
public class PaymentValidator {

	/**
	 * Validate if account has sufficient balance
	 * 
	 * @param transaction
	 * @param transferer
	 * @return true - if transferrer has sufficient balance
	 */
	public static boolean isSufficientBalance(Transaction transaction, Account transferer) {
		return transferer.getBalance().compareTo(transaction.getAmount()) >= 0;
	}

	/**
	 * Validate if user has entered amount as zero or less 
	 * then zero
	 * 
	 * @param transaction
	 * @return true - if user entered invalid amount
	 */
	public static boolean isInvalidTransferAmount(Transaction transaction) {
		return transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0;
	}
}
