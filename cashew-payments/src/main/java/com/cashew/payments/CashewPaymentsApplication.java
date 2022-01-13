package com.cashew.payments;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.cashew.payments.model.Account;
import com.cashew.payments.service.AccountsService;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	
	@Bean
	CommandLineRunner initAccounts(AccountsService accountsService) {
		return args -> {
			log.info("Initializing account details : " + filePath);
			try {
				Resource resource = resourceLoader.getResource(filePath);
				List<Account> accounts = Arrays.asList(new ObjectMapper().readValue(
						resource.getFile(), Account[].class));
				accountsService.saveAccounts(accounts);
				log.info("Accounts data populated from : " + filePath);
			} catch (IOException e) {
				log.error("Error fetching accounts from : " + filePath);
				e.printStackTrace();
			}
		};
	}

}
