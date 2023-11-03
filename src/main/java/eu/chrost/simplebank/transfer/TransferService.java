package eu.chrost.simplebank.transfer;

import eu.chrost.simplebank.account.Account;
import eu.chrost.simplebank.account.AccountRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class TransferService {
    private final AccountRepository accountRepository;

    public void makeTransfer(long fromAccountId, long toAccountId, BigDecimal amount) {
        Account to = accountRepository.findById(toAccountId).get();
        to.setBalance(to.getBalance().add(amount));
        accountRepository.save(to);
        Account from = accountRepository.findById(fromAccountId).get();
        if (from.getBalance().compareTo(amount) < 0) {
            throw new IllegalStateException("Cannot withdraw amount greater than balance");
        }
        from.setBalance(from.getBalance().subtract(amount));
        accountRepository.save(from);

    }
}
