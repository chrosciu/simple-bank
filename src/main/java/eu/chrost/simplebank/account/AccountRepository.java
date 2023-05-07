package eu.chrost.simplebank.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long>, CustomAccountRepository {
    List<Account> findAccountByBalanceLessThanEqual(BigDecimal balance);

    @Query("from Account where locked = :status")
    List<Account> findAccountByLockedStatus(boolean status);
}
