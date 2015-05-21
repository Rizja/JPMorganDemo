package ch.bfh.fpelib.jpmorgan.transaction;

import ch.bfh.fpelib.jpmorgan.account.Account;
import ch.bfh.fpelib.jpmorgan.account.AccountRepository;
import ch.bfh.fpelib.jpmorgan.bankAccount.BankAccount;
import ch.bfh.fpelib.jpmorgan.bankAccount.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@Secured("ROLE_USER")
class TransactionController {

    private static final String TRANSACTION_VIEW_NAME = "transaction/transaction";
    private static final String ALL_TRANSACTION_VIEW_NAME = "transaction/allAccounts";

    private TransactionRepository transactionRepository;

    private BankAccountRepository bankAccountRepository;

    private AccountRepository accountRepository;

    @Autowired
    public TransactionController(TransactionRepository transactionRepository, BankAccountRepository bankAccountRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.accountRepository = accountRepository;
    }

    @RequestMapping(value = "transaction", method = RequestMethod.GET)
    public String transaction(Model model, Principal principal) {
        List<BankAccount> bankAccounts = accountRepository.findByEmail(principal.getName()).getAccounts();
        List<List<Transaction>> transactions = new ArrayList<List<Transaction>>();
        for (BankAccount bankAccount : bankAccounts) {
            transactions.add(transactionRepository.findByAccount(bankAccount.getNumber()));
        }
        model.addAttribute("accountList", bankAccounts);
        model.addAttribute("transactionsList", transactions);
        return ALL_TRANSACTION_VIEW_NAME;
    }

    @RequestMapping(value = "transaction/{id}", method = RequestMethod.GET)
    public String transaction(Model model, @PathVariable("id") long id) {
        BankAccount bankAccount = bankAccountRepository.findById(id);
        model.addAttribute("account", bankAccount);
        model.addAttribute("transactions", transactionRepository.findByAccount(bankAccount.getNumber()));
        return TRANSACTION_VIEW_NAME;
    }
}
