package ch.bfh.fpelib.jpmorgan.transaction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@SuppressWarnings("serial")
@Entity
@Table(name = "transaction")
@NamedQueries({
        @NamedQuery(name = Transaction.FIND_ALL, query = "select a from Transaction a"),
        @NamedQuery(name = Transaction.FIND_BY_ACCOUNT, query = "select a from Transaction a where a.sourceAccount = :account or a.destAccount = :account"),
        @NamedQuery(name = Transaction.FIND_BY_ID, query = "select a from Transaction a where a.id = :id"),
})
public class Transaction implements java.io.Serializable {

	public static final String FIND_ALL = "Transaction.findAll";
    public static final String FIND_BY_ACCOUNT = "Transaction.findByAccount";
    public static final String FIND_BY_ID = "Transaction.findById";

	@Id
	@GeneratedValue
	private Long id;

    private String sourceAccount;

	private String destAccount;

    private BigDecimal amount;

    private String currency;

	private Timestamp date;

    private String message;

    protected Transaction() {}

	public Transaction(String sourceAccount, String destAccount, BigDecimal amount, String currency, Timestamp date, String message) {
		this.sourceAccount = sourceAccount;
		this.destAccount = destAccount;
		this.amount = amount;
        this.currency = currency;
        this.date = date;
        this.message = message;
	}

	public Long getId() {
		return id;
	}

    public String getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(String sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public String getDestAccount() {
        return destAccount;
    }

    public void setDestAccount(String destAccount) {
        this.destAccount = destAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
