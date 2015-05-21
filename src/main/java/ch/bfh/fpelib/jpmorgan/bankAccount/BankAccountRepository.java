package ch.bfh.fpelib.jpmorgan.bankAccount;

import ch.bfh.fpelib.jpmorgan.transaction.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class BankAccountRepository {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public BankAccount save(BankAccount bankAccount) {
		entityManager.persist(bankAccount);
		return bankAccount;
	}

    public BankAccount findById(long id) {
        try {
            return entityManager.createNamedQuery(BankAccount.FIND_BY_ID, BankAccount.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (PersistenceException e) {
            return null;
        }
    }
}
