package com.cashew.payments.validator;

import java.math.BigDecimal;

import com.cashew.payments.model.Account;
import com.cashew.payments.model.Transaction;

public class PaymentValidator {

	public static boolean isSufficientBalance(Transaction transaction, Account transferer) {
		return transferer.getBalance().compareTo(transaction.getAmount()) >= 0;
	}

	public static boolean isInvalidTransferAmount(Transaction transaction) {
		return transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0;
	}
}
