package ch.bfh.fpelib.jpmorgan.transaction;

import ch.bfh.fpelib.jpmorgan.bankAccount.BankAccount;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class TransactionRepository {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public Transaction save(Transaction transaction) {
		entityManager.persist(transaction);
		return transaction;
	}
	
	public Transaction findById(long id) {
        try {
            return entityManager.createNamedQuery(Transaction.FIND_BY_ID, Transaction.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (PersistenceException e) {
            return null;
        }
    }

    public List<Transaction> findByAccount(String number) {
        try {
            return entityManager.createNamedQuery(Transaction.FIND_BY_ACCOUNT, Transaction.class)
                    .setParameter("account", number)
                    .getResultList();
        } catch (PersistenceException e) {
            return null;
        }
    }

    public List<Transaction> findAll() {
        try {
            return entityManager.createNamedQuery(Transaction.FIND_ALL, Transaction.class)
                    .getResultList();
        } catch (PersistenceException e) {
            return null;
        }
    }

	
}
