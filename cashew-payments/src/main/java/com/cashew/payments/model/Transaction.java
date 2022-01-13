package com.cashew.payments.model;

import java.math.BigDecimal;

public class Transaction {
	private String transferer;
	private String transferee;
	private BigDecimal amount;
	
	public String getTransferer() {
		return transferer;
	}
	public void setTransferer(String transferer) {
		this.transferer = transferer;
	}
	public String getTransferee() {
		return transferee;
	}
	public void setTransferee(String transferee) {
		this.transferee = transferee;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}
