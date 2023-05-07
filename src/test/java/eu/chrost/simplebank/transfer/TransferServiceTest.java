package eu.chrost.simplebank.transfer;

import eu.chrost.simplebank.account.Account;
import eu.chrost.simplebank.account.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransferServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TransferService transferService;

    @Test
    void shouldAllowToTransferMoneyBetweenTwoAccounts() {
        //given
        var account1 = Account.builder().id(1L).balance(BigDecimal.valueOf(2000)).build();
        var account2 = Account.builder().id(2L).balance(BigDecimal.valueOf(1000)).build();
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account1));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(account2));

        //when
        transferService.makeTransfer(1L, 2L, BigDecimal.valueOf(100));

        //then
        assertThat(account1.getBalance()).isEqualTo(BigDecimal.valueOf(1900));
        assertThat(account2.getBalance()).isEqualTo(BigDecimal.valueOf(1100));
        verify(accountRepository).save(account1);
        verify(accountRepository).save(account2);
    }

    @Test
    void shouldRaiseErrorIfThereIsNotEnoughBalanceForTransfer() {
        //given
        var account1 = Account.builder().id(1L).balance(BigDecimal.valueOf(2000)).build();
        var account2 = Account.builder().id(2L).balance(BigDecimal.valueOf(1000)).build();
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account1));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(account2));

        //when / then
        assertThatIllegalStateException().isThrownBy(() -> {
            transferService.makeTransfer(1L, 2L, BigDecimal.valueOf(3000));
        });
    }
}
