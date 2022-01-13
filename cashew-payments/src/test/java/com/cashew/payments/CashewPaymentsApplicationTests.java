package com.cashew.payments;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.cashew.payments.exception.handler.InvalidInputException;
import com.cashew.payments.model.Account;
import com.cashew.payments.model.Transaction;
import com.cashew.payments.service.AccountsService;
import com.cashew.payments.service.TransactionService;

@SpringBootTest
@AutoConfigureMockMvc
class CashewPaymentsApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private AccountsService accountsService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void contextLoads() {
		assertThat(applicationContext).isNotNull();
	}
	
	@Test
	public void shouldReturnAccounts() throws Exception {
		this.mockMvc.perform(get("/accounts"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	
	@Test
	public void shouldNotTransfer_Zero_BetweenAccounts() throws Exception {
		String transfererId = "3137505b-5076-43c0-9e51-9d204455927f";
		String transfereeId = "b2fd5a7d-d46c-4b57-a167-b802d4d37ffe";
		BigDecimal transferAmount  = new BigDecimal(0.0)
				.setScale(2, RoundingMode.HALF_UP);
		
		Transaction transaction = new Transaction(transfererId, transfereeId, transferAmount);
		assertThrows(InvalidInputException.class, () -> transactionService.transfer(transaction));
	}
	
	@Test
	public void shouldTransfer_Fifty_BetweenAccounts() throws Exception {
		String transfererId = "3137505b-5076-43c0-9e51-9d204455927f";
		String transfereeId = "b2fd5a7d-d46c-4b57-a167-b802d4d37ffe";
		BigDecimal transferAmount  = new BigDecimal(50.00)
				.setScale(2, RoundingMode.HALF_UP);
		
		BigDecimal expectedTransfererBalance = new BigDecimal(4816.69)
				.setScale(2, RoundingMode.HALF_UP);
		BigDecimal expectedTransfereeBalance = new BigDecimal(4868.95)
				.setScale(2, RoundingMode.HALF_UP);
		
		Transaction transaction = new Transaction(transfererId, transfereeId, transferAmount);

		Account transfererTest = transactionService.transfer(transaction);
		Account transfereeTest = accountsService.getAccount(transfereeId).get();

		assertEquals(expectedTransfererBalance, transfererTest.getBalance());
		assertEquals(expectedTransfereeBalance, transfereeTest.getBalance());
	}
	
	@Test
	public void shouldNotTransfer_NegativeAmount_BetweenAccounts() throws Exception {
		String transfererId = "3137505b-5076-43c0-9e51-9d204455927f";
		String transfereeId = "b2fd5a7d-d46c-4b57-a167-b802d4d37ffe";
		BigDecimal transferAmount  = new BigDecimal(-25.00)
				.setScale(2, RoundingMode.HALF_UP);
		
		Transaction transaction = new Transaction(transfererId, transfereeId, transferAmount);
		assertThrows(InvalidInputException.class, () -> transactionService.transfer(transaction));
	}
	
	@Test
	public void shouldTransfer_CompeleteBalance_BetweenAccounts() throws Exception {
		String transfererId = "6b77ca56-a8c5-4a9a-9afa-da7d4b7e38eb";
		String transfereeId = "4cc8cf60-680d-4e84-9d02-3e4eb7b14be5";
		BigDecimal transferAmount  = new BigDecimal(4639.23)
				.setScale(2, RoundingMode.HALF_UP);
		
		BigDecimal expectedTransfererBalance = new BigDecimal(0.0)
				.setScale(2, RoundingMode.HALF_UP);
		BigDecimal expectedTransfereeBalance = new BigDecimal(8103.43)
				.setScale(2, RoundingMode.HALF_UP);
		
		Transaction transaction = new Transaction(transfererId, transfereeId, transferAmount);

		Account transfererTest = transactionService.transfer(transaction);
		Account transfereeTest = accountsService.getAccount(transfereeId).get();

		assertEquals(expectedTransfererBalance, transfererTest.getBalance());
		assertEquals(expectedTransfereeBalance, transfereeTest.getBalance());
	}
	
	
}
