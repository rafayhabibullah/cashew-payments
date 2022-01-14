package com.cashew.payments;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Calendar;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
@TestMethodOrder(OrderAnnotation.class)
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
	@Order(1)
	void contextLoads() {
		assertThat(applicationContext).isNotNull();
	}

	@Test
	@Order(2)
	public void shouldSaveAccounts() {
		Account account1 = new Account("b2fd5a7d-d46c-4b57-a167-b802d4d37ffe1", "Ntags", new BigDecimal(4818.95));
		Account account2 = new Account("3137505b-5076-43c0-9e51-9d204455927f2", "Chatterbridge",
				new BigDecimal(4866.69));
		Account account3 = new Account("4cc8cf60-680d-4e84-9d02-3e4eb7b14be53", "Meetz", new BigDecimal(3464.20));
		Account account4 = new Account("6b77ca56-a8c5-4a9a-9afa-da7d4b7e38eb4", "Fatz", new BigDecimal(4639.23));

		accountsService.saveAccounts(Arrays.asList(account1, account2, account3, account4));

		assertNotNull(accountsService.getAccount("b2fd5a7d-d46c-4b57-a167-b802d4d37ffe1").get());
		assertNotNull(accountsService.getAccount("3137505b-5076-43c0-9e51-9d204455927f2").get());
		assertNotNull(accountsService.getAccount("4cc8cf60-680d-4e84-9d02-3e4eb7b14be53").get());
		assertNotNull(accountsService.getAccount("6b77ca56-a8c5-4a9a-9afa-da7d4b7e38eb4").get());
	}

	@Test
	@Order(3)
	public void shouldReturnAccounts() throws Exception {
		this.mockMvc.perform(get("/accounts")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	@Order(4)
	public void shouldNotTransfer_Zero_BetweenAccounts() throws Exception {
		String transfererId = "3137505b-5076-43c0-9e51-9d204455927f2";
		String transfereeId = "b2fd5a7d-d46c-4b57-a167-b802d4d37ffe1";
		BigDecimal transferAmount = new BigDecimal(0.0).setScale(2, RoundingMode.HALF_UP);

		Transaction transaction = new Transaction(transfererId, transfereeId, transferAmount);
		assertThrows(InvalidInputException.class, () -> transactionService.transfer(transaction));
	}

	@Test
	@Order(5)
	public void shouldTransfer_Fifty_BetweenAccounts() throws Exception {
		String transfererId = "3137505b-5076-43c0-9e51-9d204455927f2";
		String transfereeId = "b2fd5a7d-d46c-4b57-a167-b802d4d37ffe1";
		BigDecimal transferAmount = new BigDecimal(50.00).setScale(2, RoundingMode.HALF_UP);

		BigDecimal expectedTransfererBalance = new BigDecimal(4816.69).setScale(2, RoundingMode.HALF_UP);
		BigDecimal expectedTransfereeBalance = new BigDecimal(4868.95).setScale(2, RoundingMode.HALF_UP);

		Transaction transaction = new Transaction(transfererId, transfereeId, transferAmount);

		Account transfererTest = transactionService.transfer(transaction);
		Account transfereeTest = accountsService.getAccount(transfereeId).get();

		assertEquals(expectedTransfererBalance, transfererTest.getBalance());
		assertEquals(expectedTransfereeBalance, transfereeTest.getBalance());
	}

	@Test
	@Order(6)
	public void shouldNotTransfer_NegativeAmount_BetweenAccounts() throws Exception {
		String transfererId = "3137505b-5076-43c0-9e51-9d204455927f2";
		String transfereeId = "b2fd5a7d-d46c-4b57-a167-b802d4d37ffe1";
		BigDecimal transferAmount = new BigDecimal(-25.00).setScale(2, RoundingMode.HALF_UP);

		Transaction transaction = new Transaction(transfererId, transfereeId, transferAmount);
		assertThrows(InvalidInputException.class, () -> transactionService.transfer(transaction));
	}

	@Test
	@Order(7)
	public void shouldTransfer_ConcurrencyCheck_BetweenAccounts() throws Exception {

		Thread thread1 = new Thread() {
			public void run() {
				System.out.println("Thread 1 " + Calendar.getInstance().getTime());
				sleepForOneSecond();
				String transfererId = "6b77ca56-a8c5-4a9a-9afa-da7d4b7e38eb4";
				String transfereeId = "4cc8cf60-680d-4e84-9d02-3e4eb7b14be53";
				BigDecimal transferAmount = new BigDecimal(10).setScale(2, RoundingMode.HALF_UP);

				BigDecimal expectedTransfererBalance = new BigDecimal(4629.23).setScale(2, RoundingMode.HALF_UP);
				BigDecimal expectedTransfereeBalance = new BigDecimal(3474.20).setScale(2, RoundingMode.HALF_UP);
				sleepForOneSecond();
				Transaction transaction = new Transaction(transfererId, transfereeId, transferAmount);
				
				Account transfererTest = transactionService.transfer(transaction);
				Account transfereeTest = accountsService.getAccount(transfereeId).get();
				System.out.println("THREAD 1 "+expectedTransfererBalance +" : " + transfererTest.getBalance());
				System.out.println(expectedTransfereeBalance +" : " + transfereeTest.getBalance());
				assertEquals(expectedTransfererBalance, transfererTest.getBalance());
				assertEquals(expectedTransfereeBalance, transfereeTest.getBalance());
			}

			private void sleepForOneSecond() {
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		Thread thread2 = new Thread() {
			public void run() {
				System.out.println("Thread 2 " + Calendar.getInstance().getTime());
				String transfererId = "6b77ca56-a8c5-4a9a-9afa-da7d4b7e38eb4";
				String transfereeId = "4cc8cf60-680d-4e84-9d02-3e4eb7b14be53";
				BigDecimal transferAmount = new BigDecimal(20).setScale(2, RoundingMode.HALF_UP);

				BigDecimal expectedTransfererBalance = new BigDecimal(4619.23).setScale(2, RoundingMode.HALF_UP);
				BigDecimal expectedTransfereeBalance = new BigDecimal(3484.20).setScale(2, RoundingMode.HALF_UP);

				Transaction transaction = new Transaction(transfererId, transfereeId, transferAmount);

				Account transfererTest = transactionService.transfer(transaction);
				Account transfereeTest = accountsService.getAccount(transfereeId).get();
				System.out.println(" THREAD 2 "+expectedTransfererBalance +" : " + transfererTest.getBalance());
				System.out.println(expectedTransfereeBalance +" : " + transfereeTest.getBalance());
				assertEquals(expectedTransfererBalance, transfererTest.getBalance());
				assertEquals(expectedTransfereeBalance, transfereeTest.getBalance());
			}
		};
		
		thread1.start();
		thread2.start();
		
		if(!thread1.isAlive() && !thread2.isAlive()) {
			Account transfererTest = accountsService.getAccount("6b77ca56-a8c5-4a9a-9afa-da7d4b7e38eb4").get();
			Account transfereeTest = accountsService.getAccount("4cc8cf60-680d-4e84-9d02-3e4eb7b14be53").get();
			BigDecimal expectedTransfererBalance = new BigDecimal(4619.23).setScale(2, RoundingMode.HALF_UP);
			BigDecimal expectedTransfereeBalance = new BigDecimal(3484.20).setScale(2, RoundingMode.HALF_UP);
			assertEquals(expectedTransfererBalance, transfererTest.getBalance());
			assertEquals(expectedTransfereeBalance, transfereeTest.getBalance());
		}
	}
	
	@Test
	@Order(8)
	public void shouldTransfer_CompeleteBalance_BetweenAccounts() throws Exception {
		String transfererId = "6b77ca56-a8c5-4a9a-9afa-da7d4b7e38eb4";
		String transfereeId = "4cc8cf60-680d-4e84-9d02-3e4eb7b14be53";
		BigDecimal transferAmount = new BigDecimal(4619.23).setScale(2, RoundingMode.HALF_UP);

		BigDecimal expectedTransfererBalance = new BigDecimal(0.0).setScale(2, RoundingMode.HALF_UP);
		BigDecimal expectedTransfereeBalance = new BigDecimal(8103.43).setScale(2, RoundingMode.HALF_UP);

		Transaction transaction = new Transaction(transfererId, transfereeId, transferAmount);

		Account transfererTest = transactionService.transfer(transaction);
		Account transfereeTest = accountsService.getAccount(transfereeId).get();

		assertEquals(expectedTransfererBalance, transfererTest.getBalance());
		assertEquals(expectedTransfereeBalance, transfereeTest.getBalance());
	}

}
