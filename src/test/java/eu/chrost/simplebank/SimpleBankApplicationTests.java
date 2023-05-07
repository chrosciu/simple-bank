package eu.chrost.simplebank;

import eu.chrost.simplebank.account.Account;
import eu.chrost.simplebank.account.AccountRepository;
import eu.chrost.simplebank.transfer.TransferService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class SimpleBankApplicationTests {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransferService transferService;

    @Test
    void contextLoads() {
    }

    @Test
    void shouldAllowToTransferMoneyBetweenTwoAccounts() {
        var account1 = Account.builder().id(1L).balance(BigDecimal.valueOf(2000)).build();
        var account2 = Account.builder().id(2L).balance(BigDecimal.valueOf(1000)).build();
        accountRepository.save(account1);
        accountRepository.save(account2);

        transferService.makeTransfer(1L, 2L, BigDecimal.valueOf(100));

        assertThat(accountRepository.findById(1L).get().getBalance()).isEqualTo(BigDecimal.valueOf(1900));
        assertThat(accountRepository.findById(2L).get().getBalance()).isEqualTo(BigDecimal.valueOf(1100));
    }

}
