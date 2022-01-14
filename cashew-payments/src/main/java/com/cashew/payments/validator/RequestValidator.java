package com.cashew.payments.validator;

import com.cashew.payments.model.Transaction;

/**
 * REST request validator
 * 
 * @author rafayhabibullah
 *
 */
public class RequestValidator {

	/**
	 * Validates transfer request
	 * 
	 * @param transaction
	 * @return true - if transaction request has missing params
	 */
	public static boolean isInvalidTransactionRequest(Transaction transaction) {
		if(transaction == null || transaction.getTransferer() == null
				|| transaction.getTransferee() == null || transaction.getAmount() == null) {
			return true;
		}
		return false;
	}
}
