package com.cashew.payments.validator;

import com.cashew.payments.model.Transaction;

public class RequestValidator {

	public static boolean isInvalidTransactionRequest(Transaction transaction) {
		if(transaction == null || transaction.getTransferer() == null
				|| transaction.getTransferee() == null || transaction.getAmount() == null) {
			return true;
		}
		return false;
	}
}
