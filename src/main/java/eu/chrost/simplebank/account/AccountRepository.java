package eu.chrost.simplebank.account;

import java.util.Optional;

public interface AccountRepository {
    Account save(Account account);

    Optional<Account> findById(Long id);
}
