package ch.bfh.fpelib.jpmorgan.account;

import java.math.BigDecimal;
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
        BankAccount account1 = bankAccountRepository.save(new BankAccount("Payments", BankAccount.AccountType.TRANSACTION_ACCOUNT, "CH7909000000302663071", "CHF", new BigDecimal(5843.20)));
        BankAccount account2 = bankAccountRepository.save(new BankAccount("Reserve Tax", BankAccount.AccountType.SAVINGS_ACCOUNT, "CH7909000000302663098", "CHF", new BigDecimal(4008.65)));
        BankAccount account3 = bankAccountRepository.save(new BankAccount("Savings", BankAccount.AccountType.CALL_DEPOSIT, "CH7909000000302663018", "CHF", new BigDecimal(23500.00)));
		accountRepository.save(new Account("guest", "demo", "ROLE_USER", Arrays.asList(account1, account2, account3)));
        transactionRepository.save(new Transaction("CH7909000000302663071", "CH7909000000302663098", new BigDecimal(2000.0d), "CHF", Timestamp.valueOf("2014-6-16 20:18:12"), "reserve tax"));
        transactionRepository.save(new Transaction("CH7909000000232753945", "CH7909000000302663071", new BigDecimal(4528.0d), "CHF", Timestamp.valueOf("2014-6-26 03:00:00"), "Wage"));
        transactionRepository.save(new Transaction("CH7909000000302663071", "CH7909000000820983053", new BigDecimal(63.70d), "CHF", Timestamp.valueOf("2014-8-11 15:18:19"), "Zalando shoes"));
        transactionRepository.save(new Transaction("CH7909000000302663071", "CH7909000000302663098", new BigDecimal(2000.0d), "CHF", Timestamp.valueOf("2014-12-13 12:34:12"), "reserve tax"));
        transactionRepository.save(new Transaction("CH7909000000232753945", "CH7909000000302663071", new BigDecimal(4528.0d), "CHF", Timestamp.valueOf("2014-12-26 03:00:00"), "Wage"));
        transactionRepository.save(new Transaction("CH7909000000302663071", "CH7909000000302663018", new BigDecimal(500.0d), "CHF", Timestamp.valueOf("2015-1-26 02:00:00"), "carry-over wage"));
        transactionRepository.save(new Transaction("CH7909000000302663071", "CH7909000000302663018", new BigDecimal(500.0d), "CHF", Timestamp.valueOf("2015-2-26 02:00:00"), "carry-over wage"));
        transactionRepository.save(new Transaction("CH7909000000302663071", "CH7909000000302663018", new BigDecimal(500.0d), "CHF", Timestamp.valueOf("2015-3-26 02:00:00"), "carry-over wage"));
        transactionRepository.save(new Transaction("CH7909000000302663071", "DE7409000000640840237", new BigDecimal(21.30d), "EUR", Timestamp.valueOf("2015-4-1 19:12:32"), "Amazon alarm clock"));
        transactionRepository.save(new Transaction("CH7909000000302663071", "CH7909000000302663018", new BigDecimal(500.0d), "CHF", Timestamp.valueOf("2015-4-26 02:00:00"), "carry-over wage"));
        transactionRepository.save(new Transaction("CH7909000000302663071", "CH7909000000302663018", new BigDecimal(500.0d), "CHF", Timestamp.valueOf("2015-5-26 02:00:00"), "carry-over wage"));
        transactionRepository.save(new Transaction("CH7909000000302663071", "CH7909000000302663018", new BigDecimal(500.0d), "CHF", Timestamp.valueOf("2015-6-26 02:00:00"), "carry-over wage"));
        transactionRepository.save(new Transaction("CH7909000000232753945", "CH7909000000302663071", new BigDecimal(4528.0d), "CHF", Timestamp.valueOf("2014-6-26 03:00:00"), "Wage"));

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
