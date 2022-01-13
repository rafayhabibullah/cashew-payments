package com.cashew.payments.validator;

import java.math.BigDecimal;

import com.cashew.payments.model.Transaction;

public class RequestValidator {

	public static boolean isInvalidTransactionRequest(Transaction transaction) {
		if(transaction == null || transaction.getTransferer() == null
				|| transaction.getTransferee() == null
				|| transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
			return true;
		}
		return false;
	}
}
