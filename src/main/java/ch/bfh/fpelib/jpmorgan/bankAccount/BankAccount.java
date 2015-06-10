package ch.bfh.fpelib.jpmorgan.bankAccount;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigInteger;

@SuppressWarnings("serial")
@Entity
@Table(name = "bankAccount")
@NamedQuery(name = BankAccount.FIND_BY_ID, query = "select a from BankAccount a where a.id = :id")
public class BankAccount implements java.io.Serializable {

    public static final String FIND_BY_ID = "BankAccount.findById";

    public enum AccountType {
        SAVINGS("Savings Account"),
        TRANSACTION("Transaction Account"),
        TIME("Time Deposit"),
        CALL("Call Deposit");
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

// encrypted = true, because always decrypted as soon as in application
// only false when unencrypted in database before read/written first time
private boolean encrypted = true;

@Type(type="ch.bfh.fpelib.jpmorgan.encUserTypes.BankAccountName")
private String name;

@Enumerated(value=EnumType.STRING)
@Type(type="ch.bfh.fpelib.jpmorgan.encUserTypes.BankAccountType")
private AccountType type;

@Type(type="ch.bfh.fpelib.jpmorgan.encUserTypes.IBAN")
private String number;

@Type(type="ch.bfh.fpelib.jpmorgan.encUserTypes.Currency")
private String currency;

@Type(type="ch.bfh.fpelib.jpmorgan.encUserTypes.Amount")
private BigInteger balance;

    protected BankAccount() {}

	public BankAccount(String name, AccountType type, String number, String currency, BigInteger balance) {
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

    public BigInteger getBalance() {
        return balance;
    }

    public void setBalance(BigInteger balance) {
        this.balance = balance;
    }

}
