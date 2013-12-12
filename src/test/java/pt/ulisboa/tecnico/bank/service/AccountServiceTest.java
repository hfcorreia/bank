package pt.ulisboa.tecnico.bank.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import pt.ulisboa.tecnico.bank.dao.AccountDAO;
import pt.ulisboa.tecnico.bank.domain.Account;
import pt.ulisboa.tecnico.bank.domain.User;
import pt.ulisboa.tecnico.bank.exceptions.BankException;
import pt.ulisboa.tecnico.bank.exceptions.InsufficientFundsException;
import pt.ulisboa.tecnico.bank.exceptions.InvalidDepositAmountExcepiton;
import pt.ulisboa.tecnico.bank.security.SecurityMatrixUtil;
import pt.ulisboa.tecnico.bank.services.AccountService;
import pt.ulisboa.tecnico.bank.services.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring-application-context.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class AccountServiceTest {
	
	private static final String ADMIN = "ADMIN";
	private static final String ACCOUNT_NUM = "0001";
	private static final String USER_NAME = "John Doe";
	private static final String PASSWORD = "e12d12312d21";
    private static final String SALT = "salty";
    private static final String ITERATIONS = "10000";
	private static final Double BALANCE = 200.00;
	private static final String ACCOUNT_NUM2 = "0002";
	private static final String MATRIX = new SecurityMatrixUtil(1000).generateJSON();

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
		User user = userService.createNormalUser(USER_NAME, PASSWORD, SALT, ITERATIONS, MATRIX);
		Account account = accountService.createNewAccount(user, ACCOUNT_NUM, BALANCE);
		
		assertEquals(account.getOwner().getUsername(), USER_NAME);
		assertEquals(account.getBalance(), new Double(BALANCE));
		assertEquals(account.getNumber(), ACCOUNT_NUM);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testNormalWithdraw() throws BankException {
		User user = userService.createNormalUser(USER_NAME, PASSWORD, SALT, ITERATIONS, MATRIX);
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
		User user = userService.createNormalUser(USER_NAME, PASSWORD, SALT, ITERATIONS, MATRIX);
		Account account = accountService.createNewAccount(user, ACCOUNT_NUM, BALANCE);
		
		Double amount = 390.21;
		accountService.withdraw(ACCOUNT_NUM, amount);
		
		accountDAO.getAccount(ACCOUNT_NUM).getBalance();
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testNormalDeposit() throws BankException {
		User user = userService.createNormalUser(USER_NAME, PASSWORD, SALT, ITERATIONS, MATRIX);
		Account account = accountService.createNewAccount(user, ACCOUNT_NUM, BALANCE);
		
		Double amount = 201.2;
		accountService.deposit(ACCOUNT_NUM, amount);
		
		assertEquals(new Double(BALANCE + amount), accountDAO.getAccount(ACCOUNT_NUM).getBalance());
	}
	
	@Test(expected = InvalidDepositAmountExcepiton.class)
	@Transactional
	@Rollback(true)
	public void testInvalidDeposit() throws BankException {
		User user = userService.createNormalUser(USER_NAME, PASSWORD, SALT, ITERATIONS, MATRIX);
		Account account = accountService.createNewAccount(user, ACCOUNT_NUM, BALANCE);
		
		Double amount = -201.2;
		accountService.deposit(ACCOUNT_NUM, amount);
		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testTransfer() throws BankException {
		User user = userService.createNormalUser(USER_NAME, PASSWORD, SALT, ITERATIONS, MATRIX);
		Account accountFrom = accountService.createNewAccount(user, ACCOUNT_NUM, BALANCE);
		Account accountTo = accountService.createNewAccount(user, ACCOUNT_NUM2, 0.0);
		Double amount = 100.00;
		accountService.transfer(ACCOUNT_NUM, ACCOUNT_NUM2, amount);
		
		assertEquals( new Double(BALANCE - amount), accountDAO.getAccount(ACCOUNT_NUM).getBalance());
		assertEquals( new Double(amount), accountDAO.getAccount(ACCOUNT_NUM2).getBalance());
		
	}

}
