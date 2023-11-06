package eu.chrost.simplebank;

import eu.chrost.simplebank.account.Account;
import eu.chrost.simplebank.account.AccountRepository;
import eu.chrost.simplebank.transfer.TransferService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

@SpringBootTest(properties = {"simple-bank.money-laundering.threshold=400"})
@ActiveProfiles("test")
public class SimpleBankApplicationTests {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransferService transferService;

    @Test
    void contextLoads() {
    }

    @Test
    void shouldAllowToTransferMoneyBetweenTwoAccounts() {
        var account1 = Account.builder().id(1L).balance(BigDecimal.valueOf(1000)).build();
        var account2 = Account.builder().id(2L).balance(BigDecimal.valueOf(2000)).build();
        accountRepository.save(account1);
        accountRepository.save(account2);

        transferService.makeTransfer(1L, 2L, BigDecimal.valueOf(200));

        assertThat(accountRepository.findById(1L).get().getBalance()).isEqualTo(BigDecimal.valueOf(800));
        assertThat(accountRepository.findById(2L).get().getBalance()).isEqualTo(BigDecimal.valueOf(2200));
    }

    @Test
    void shouldDetectTransfersAboveMoneyLaunderingThreshold() {
        var account1 = Account.builder().id(1L).balance(BigDecimal.valueOf(1000)).build();
        var account2 = Account.builder().id(2L).balance(BigDecimal.valueOf(2000)).build();
        accountRepository.save(account1);
        accountRepository.save(account2);

        assertThatIllegalStateException().isThrownBy(() -> {
            transferService.makeTransfer(1L, 2L, BigDecimal.valueOf(500));
        });
    }
}
