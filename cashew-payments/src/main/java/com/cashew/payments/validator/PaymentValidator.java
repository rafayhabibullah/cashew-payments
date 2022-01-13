package com.cashew.payments.validator;

import java.math.BigDecimal;

import com.cashew.payments.model.Account;
import com.cashew.payments.model.Transaction;

public class PaymentValidator {

	public static boolean isValidTransfer(Transaction transaction, Account transferer) {
		return isValidTransferAmount(transaction) 
				&& isSufficientBalance(transaction, transferer);
	}

	private static boolean isSufficientBalance(Transaction transaction, Account transferer) {
		return transferer.getBalance().compareTo(transaction.getAmount()) >= 0;
	}

	private static boolean isValidTransferAmount(Transaction transaction) {
		return transaction.getAmount().compareTo(BigDecimal.ZERO) == 1;
	}
}
