package eu.chrost.simplebank;

import eu.chrost.simplebank.account.Account;
import eu.chrost.simplebank.account.AccountRepository;
import eu.chrost.simplebank.transfer.TransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@ConditionalOnProperty("simple-bank.runner.active")
@RequiredArgsConstructor
@Slf4j
public class SimpleBankApplicationRunner implements ApplicationRunner {

    private final AccountRepository accountRepository;
    private final TransferService transferService;

    @Override
    public void run(ApplicationArguments args) {
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
