package eu.chrost.simplebank.account;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
@Profile("!test")
public class AccountPopulator implements ApplicationRunner {
    private final AccountRepository accountRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        accountRepository.save(Account.builder().id(1L).balance(BigDecimal.valueOf(1000)).build());
        accountRepository.save(Account.builder().id(2L).balance(BigDecimal.valueOf(2000)).build());
    }
}
