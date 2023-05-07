package eu.chrost.simplebank.account;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomAccountRepositoryImpl implements CustomAccountRepository {
    private final EntityManager entityManager;

    @Override
    public List<Account> findAccountsLockedOrWithNegativeBalance() {
        return entityManager.createQuery("from Account where balance < 0 or locked", Account.class).getResultList();
    }
}
