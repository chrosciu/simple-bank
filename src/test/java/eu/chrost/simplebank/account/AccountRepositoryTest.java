package eu.chrost.simplebank.account;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AccountRepositoryTest {
    @Autowired
    private AccountRepository accountRepository;

    @Test
    @Sql("accounts.sql")
    void shouldReturnAllAccounts() {
        var accounts = accountRepository.findAll();

        assertThat(accounts).extracting(Account::getId).containsExactly(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L);
    }

    @Test
    @Sql("accounts.sql")
    void shouldReturnAllAccountsWithBalanceLessOrEqualThanGivenAmount() {
        var accounts = accountRepository.findAccountByBalanceLessThanEqual(BigDecimal.valueOf(1000));

        assertThat(accounts).extracting(Account::getId).containsExactly(1L, 5L, 6L, 7L, 8L, 9L);
    }

    @Test
    @Sql("accounts.sql")
    void shouldReturnAllLockedAccounts() {
        var accounts = accountRepository.findAccountByLockedStatus(true);

        assertThat(accounts).extracting(Account::getId).containsExactly(4L, 7L);
    }

    @Test
    @Sql("accounts.sql")
    void shouldReturnAllAccountsLockedOrWithNegativeBalance() {
        var accounts = accountRepository.findAccountsLockedOrWithNegativeBalance();

        assertThat(accounts).extracting(Account::getId).containsExactly(4L, 7L, 9L);
    }
}
