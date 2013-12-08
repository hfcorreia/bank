package pt.ulisboa.tecnico.bank.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.ExpectedException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.sun.org.apache.bcel.internal.generic.ACONST_NULL;

import pt.ulisboa.tecnico.bank.dao.AccountDAO;
import pt.ulisboa.tecnico.bank.domain.Account;
import pt.ulisboa.tecnico.bank.domain.User;
import pt.ulisboa.tecnico.bank.exceptions.BankException;
import pt.ulisboa.tecnico.bank.exceptions.DuplicateAccountException;
import pt.ulisboa.tecnico.bank.exceptions.DuplicatedUserException;
import pt.ulisboa.tecnico.bank.exceptions.InsufficientFundsException;
import pt.ulisboa.tecnico.bank.exceptions.InvalidDepositAmountExcepiton;
import pt.ulisboa.tecnico.bank.services.AccountService;
import pt.ulisboa.tecnico.bank.services.UserService;
import pt.ulisboa.tecnico.bank.services.NotificationService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring-application-context.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class AccountServiceTest {
	
	private static final String ADMIN = "ADMIN";
	private static final String ACCOUNT_NUM = "0001";
	private static final String USER_NAME = "John Doe";
	private static final String PASSWORD = "e12d12312d21";
	private static final Double BALANCE = 200.00;
	

	@Autowired
	private UserService userService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private AccountDAO accountDAO;
	
	
	
	
	@Test
	@Transactional
    @Rollback(true)
	public void testNormalAccountCreation() throws BankException {
		User user = userService.createNormalUser(USER_NAME, PASSWORD);
		Account account = accountService.createNewAccount(user, ACCOUNT_NUM, BALANCE);
		
		assertEquals(account.getOwner().getUsername(), USER_NAME);
		assertEquals(account.getBalance(), new Double(BALANCE));
		assertEquals(account.getNumber(), ACCOUNT_NUM);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testNormalWithdraw() throws BankException {
		User user = userService.createNormalUser(USER_NAME, PASSWORD);
		Account account = accountService.createNewAccount(user, ACCOUNT_NUM, BALANCE);
		
		Double amount = 30.21;
		accountService.withdraw(ACCOUNT_NUM, amount);
		
		Double balance = accountDAO.getAccount(ACCOUNT_NUM).getBalance();
		assertEquals( new Double(BALANCE - amount), balance);
	}
	
	@Test(expected = InsufficientFundsException.class)
	@Transactional
	@Rollback(true)
	public void testInsufficienttWithdraw() throws BankException {
		User user = userService.createNormalUser(USER_NAME, PASSWORD);
		Account account = accountService.createNewAccount(user, ACCOUNT_NUM, BALANCE);
		
		Double amount = 390.21;
		accountService.withdraw(ACCOUNT_NUM, amount);
		
		accountDAO.getAccount(ACCOUNT_NUM).getBalance();
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testNormalDeposit() throws BankException {
		User user = userService.createNormalUser(USER_NAME, PASSWORD);
		Account account = accountService.createNewAccount(user, ACCOUNT_NUM, BALANCE);
		
		Double amount = 201.2;
		accountService.deposit(ACCOUNT_NUM, amount);
		
		assertEquals(new Double(BALANCE + amount), accountDAO.getAccount(ACCOUNT_NUM).getBalance());
	}
	
	@Test(expected = InvalidDepositAmountExcepiton.class)
	@Transactional
	@Rollback(true)
	public void testInvalidDeposit() throws BankException {
		User user = userService.createNormalUser(USER_NAME, PASSWORD);
		Account account = accountService.createNewAccount(user, ACCOUNT_NUM, BALANCE);
		
		Double amount = -201.2;
		accountService.deposit(ACCOUNT_NUM, amount);
		
	}

}
