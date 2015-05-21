package ch.bfh.fpelib.jpmorgan.bankAccount;

import ch.bfh.fpelib.jpmorgan.bankAccount.BankAccount;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;

@SuppressWarnings("serial")
@Entity
@Table(name = "bankAccount")
@NamedQuery(name = BankAccount.FIND_BY_ID, query = "select a from BankAccount a where a.id = :id")
public class BankAccount implements java.io.Serializable {

    public static final String FIND_BY_ID = "BankAccount.findById";

    public enum AccountType {
        SAVINGS_ACCOUNT("Savings Account"),
        TRANSACTION_ACCOUNT("Transaction Account"),
        TIME_DEPOSIT("Time Deposit"),
        CALL_DEPOSIT("Call Deposit");
        private String type;
        private AccountType(String type) {
            this.type = type;
        }
        public String getType() {
           return type;
        }
    }

	@Id
	@GeneratedValue
	private Long id;

    private String name;

    @Enumerated
    private AccountType type;

    private String number;

    private String currency;

    private BigDecimal balance;

    protected BankAccount() {}

	public BankAccount(String name, AccountType type, String number, String currency, BigDecimal balance) {
		this.name = name;
        this.type = type;
        this.number = number;
        this.currency = currency;
        this.balance = balance;
	}

	public Long getId() {
		return id;
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

}
