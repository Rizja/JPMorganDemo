package ch.bfh.fpelib.jpmorgan.account;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;

import javax.annotation.PostConstruct;

import ch.bfh.fpelib.jpmorgan.bankAccount.BankAccount;
import ch.bfh.fpelib.jpmorgan.bankAccount.BankAccountRepository;
import ch.bfh.fpelib.jpmorgan.transaction.Transaction;
import ch.bfh.fpelib.jpmorgan.transaction.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;

public class UserService implements UserDetailsService {
	
	@Autowired
	private AccountRepository accountRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private TransactionRepository transactionRepository;
	
	@PostConstruct	
	protected void initialize() {
        BankAccount account1 = bankAccountRepository.save(new BankAccount("Payments", BankAccount.AccountType.TRANSACTION, "CH7909000000302663071", "CHF", new BigInteger("5843200")));
        BankAccount account2 = bankAccountRepository.save(new BankAccount("Reserve Tax", BankAccount.AccountType.SAVINGS, "CH9300762011623852957", "CHF", new BigInteger("4008650")));
        BankAccount account3 = bankAccountRepository.save(new BankAccount("Savings", BankAccount.AccountType.CALL, "CH6309000000250097798", "CHF", new BigInteger("23500000")));
		accountRepository.save(new Account("guest", "demo", "ROLE_USER", Arrays.asList(account1, account2, account3)));
        transactionRepository.save(new Transaction("CH7909000000302663071", "CH9300762011623852957", new BigInteger("2000000"), "CHF", Timestamp.valueOf("2014-6-16 20:18:12.0"), "reserve tax"));
        transactionRepository.save(new Transaction("CH3908704016075473007", "CH7909000000302663071", new BigInteger("4528000"), "CHF", Timestamp.valueOf("2014-6-26 03:00:00.0"), "Wage"));
        transactionRepository.save(new Transaction("CH7909000000302663071", "CH8209000000603591064", new BigInteger("63700"), "CHF", Timestamp.valueOf("2014-8-11 15:18:19.0"), "Zalando shoes"));
        transactionRepository.save(new Transaction("CH7909000000302663071", "CH9300762011623852957", new BigInteger("2000000"), "CHF", Timestamp.valueOf("2014-12-13 12:34:12.0"), "reserve tax"));
        transactionRepository.save(new Transaction("CH3908704016075473007", "CH7909000000302663071", new BigInteger("4528000"), "CHF", Timestamp.valueOf("2014-12-26 03:00:00.0"), "Wage"));
        transactionRepository.save(new Transaction("CH7909000000302663071", "CH6309000000250097798", new BigInteger("500000"), "CHF", Timestamp.valueOf("2015-1-26 02:00:00.0"), "carry over wage"));
        transactionRepository.save(new Transaction("CH7909000000302663071", "CH6309000000250097798", new BigInteger("500000"), "CHF", Timestamp.valueOf("2015-2-26 02:00:00.0"), "carry over wage"));
        transactionRepository.save(new Transaction("CH7909000000302663071", "CH6309000000250097798", new BigInteger("500000"), "CHF", Timestamp.valueOf("2015-3-26 02:00:00.0"), "carry over wage"));
        transactionRepository.save(new Transaction("CH7909000000302663071", "DE89370400440532013000", new BigInteger("21300"), "EUR", Timestamp.valueOf("2015-4-1 19:12:32.0"), "Amazon alarm clock"));
        transactionRepository.save(new Transaction("CH7909000000302663071", "CH6309000000250097798", new BigInteger("500000"), "CHF", Timestamp.valueOf("2015-4-26 02:00:00.0"), "carry over wage"));
        transactionRepository.save(new Transaction("CH7909000000302663071", "CH6309000000250097798", new BigInteger("500000"), "CHF", Timestamp.valueOf("2015-5-26 02:00:00.0"), "carry over wage"));
        transactionRepository.save(new Transaction("CH7909000000302663071", "CH6309000000250097798", new BigInteger("500000"), "CHF", Timestamp.valueOf("2015-6-26 02:00:00.0"), "carry over wage"));
        transactionRepository.save(new Transaction("CH3908704016075473007", "CH7909000000302663071", new BigInteger("4528000"), "CHF", Timestamp.valueOf("2014-6-26 03:00:00.0"), "Wage"));

	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountRepository.findByEmail(username);
		if(account == null) {
			throw new UsernameNotFoundException("user not found");
		}
		return createUser(account);
	}
	
	public void signin(Account account) {
		SecurityContextHolder.getContext().setAuthentication(authenticate(account));
	}
	
	private Authentication authenticate(Account account) {
		return new UsernamePasswordAuthenticationToken(createUser(account), null, Collections.singleton(createAuthority(account)));		
	}
	
	private User createUser(Account account) {
		return new User(account.getEmail(), account.getPassword(), Collections.singleton(createAuthority(account)));
	}

	private GrantedAuthority createAuthority(Account account) {
		return new SimpleGrantedAuthority(account.getRole());
	}

}
