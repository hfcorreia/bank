package pt.ulisboa.tecnico.bank.dao;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.ExpectedException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.sun.org.apache.bcel.internal.generic.ACONST_NULL;

import pt.ulisboa.tecnico.bank.domain.Account;
import pt.ulisboa.tecnico.bank.domain.User;
import pt.ulisboa.tecnico.bank.exceptions.DuplicateAccountException;
import pt.ulisboa.tecnico.bank.exceptions.DuplicatedUserException;
import pt.ulisboa.tecnico.bank.services.AccountService;
import pt.ulisboa.tecnico.bank.services.UserService;
import pt.ulisboa.tecnico.bank.services.NotificationService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring-application-context.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class CreateAccountServiceTest {
	
	private static final String ADMIN = "ADMIN";
	private static final String ACCOUNT_NUM = "0001";
	private static String USER_NAME = "John Doe";
	private static String PASSWORD = "e12d12312d21";

	@Autowired
	private UserService createUserSerivice;
	@Autowired
	private AccountService createAccountService;
	
	
	@Test
	@Transactional
    @Rollback(true)
	public void testNormalAccountCreation() throws DuplicatedUserException, DuplicateAccountException {
		User user = createUserSerivice.createNormalUser(USER_NAME, PASSWORD);
		Account account = createAccountService.createNewAccount(user, ACCOUNT_NUM, 0.0);
		
		assertEquals(account.getOwner().getUsername(), USER_NAME);
		assertEquals(account.getBalance(), new Double(0.0));
		assertEquals(account.getNumber(), ACCOUNT_NUM);
	}
}
