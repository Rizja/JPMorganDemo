package ch.bfh.fpelib.jpmorgan.home;

import java.security.Principal;

import ch.bfh.fpelib.jpmorgan.account.Account;
import ch.bfh.fpelib.jpmorgan.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    private AccountRepository accountRepository;

    @Autowired
    public HomeController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model, Principal principal) {
		if (principal != null) {
            Account account = accountRepository.findByEmail(principal.getName());
            model.addAttribute("name", principal.getName());
            model.addAttribute("accounts", account.getAccounts());
            return "home/homeSignedIn";
        }
        else {
            return "home/homeNotSignedIn";
        }
	}
}
