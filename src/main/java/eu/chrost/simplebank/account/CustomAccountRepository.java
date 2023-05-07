package eu.chrost.simplebank.account;

import java.util.List;

public interface CustomAccountRepository {
    List<Account> findAccountsLockedOrWithNegativeBalance();
}
