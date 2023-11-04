package eu.chrost.simplebank.account;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CustomAccountRepositoryImpl implements CustomAccountRepository {
    private final EntityManager entityManager;

    @Override
    public List<Account> findAccountsLockedOrWithNegativeBalance() {
        return entityManager.createQuery("from Account where balance < 0 or locked", Account.class).getResultList();
    }
}
