package eu.chrost.simplebank;

import eu.chrost.simplebank.account.Account;
import eu.chrost.simplebank.account.AccountRepository;
import eu.chrost.simplebank.transfer.TransferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;

@Slf4j
public class SimpleBankApplication {

    public static void main(String[] args) {
        try (var applicationContext = new AnnotationConfigApplicationContext("eu.chrost.simplebank")) {
            var accountRepository = applicationContext.getBean(AccountRepository.class);
            var transferService = applicationContext.getBean(TransferService.class);

            var account1 = Account.builder().id(1L).balance(BigDecimal.valueOf(1000)).build();
            var account2 = Account.builder().id(2L).balance(BigDecimal.valueOf(2000)).build();
            accountRepository.save(account1);
            accountRepository.save(account2);

            transferService.makeTransfer(1L, 2L, BigDecimal.valueOf(200));

            account1 = accountRepository.findById(1L).get();
            account2 = accountRepository.findById(2L).get();
            log.info("a1: {}", account1.getBalance());
            log.info("a2: {}", account2.getBalance());
        }
    }

}
