package ch.bfh.fpelib.jpmorgan.account;

import javax.persistence.*;

import ch.bfh.fpelib.jpmorgan.bankAccount.BankAccount;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@SuppressWarnings("serial")
@Entity
@Table(name = "account")
@NamedQuery(name = Account.FIND_BY_EMAIL, query = "select a from Account a where a.email = :email")
public class Account implements java.io.Serializable {

	public static final String FIND_BY_EMAIL = "Account.findByEmail";

	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = true)
	private String email;
	
	@JsonIgnore
	private String password;

	private String role = "ROLE_USER";

    @ElementCollection
    private List<BankAccount> accounts;

    protected Account() {

	}
	
	public Account(String email, String password, String role, List<BankAccount> accounts) {
		this.email = email;
		this.password = password;
		this.role = role;
        this.accounts = accounts;
	}

	public Long getId() {
		return id;
	}

    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

    public List<BankAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<BankAccount> accounts) {
        this.accounts = accounts;
    }

}
