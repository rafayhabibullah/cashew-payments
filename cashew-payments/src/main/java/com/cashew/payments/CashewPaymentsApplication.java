package com.cashew.payments;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;

import com.cashew.payments.model.Account;
import com.cashew.payments.service.AccountsService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Spring Boot Application class
 * 
 * @author rafayhabibullah
 *
 */
@SpringBootApplication
public class CashewPaymentsApplication {
	
	private static final Logger log = LoggerFactory.getLogger(CashewPaymentsApplication.class);

	@Autowired
	ResourceLoader resourceLoader;
	
	@Value("${file.path}")
	private String filePath;
	
	public static void main(String[] args) {
		SpringApplication.run(CashewPaymentsApplication.class, args);
	}
	
	/**
	 * Read list of accounts from mock JSON file stored in resources
	 * Save all the accounts for transaction processing
	 * 
	 * @param accountsService
	 */
	@Bean
	CommandLineRunner initAccounts(AccountsService accountsService) {
		return args -> {
			log.info("Initializing account details : " + filePath);
			try (InputStream inputStream = getClass().getResourceAsStream(filePath)){
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				String data = bufferedReader.lines().collect(Collectors.joining());
				List<Account> accounts = Arrays.asList(new ObjectMapper().readValue(
						data, Account[].class));
				accountsService.saveAccounts(accounts);
				log.info("Accounts data populated from : " + filePath);
			} catch (IOException e) {
				log.error("Error fetching accounts from : " + filePath);
				e.printStackTrace();
			}
		};
	}

}
