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

import pt.ulisboa.tecnico.bank.domain.User;
import pt.ulisboa.tecnico.bank.exceptions.DuplicatedUserException;
import pt.ulisboa.tecnico.bank.services.UserService;
import pt.ulisboa.tecnico.bank.services.NotificationService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring-application-context.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class CreateUserServiceTest {
	
	private static final String ADMIN = "ADMIN";
	private static String USER_NAME = "John Doe";
	private static String PASSWORD = "e12d12312d21";

	@Autowired
	private UserService createUserSerivice;
	
	@Test
	@Transactional
    @Rollback(true)
	public void testNormalUserCreation() throws DuplicatedUserException {
		User user = createUserSerivice.createNormalUser(USER_NAME, PASSWORD);
		assertEquals(USER_NAME, user.getUsername());
		assertEquals(PASSWORD, user.getPassword());
	}
	
	@Test
	@Transactional
    @Rollback(true)
	public void testUserCreationFail() throws DuplicatedUserException {
		User user = createUserSerivice.createAdminUser(ADMIN, PASSWORD);
		assertEquals(ADMIN, user.getUsername());
		assertEquals(PASSWORD, user.getPassword());
	}

	@Test(expected = DuplicatedUserException.class)
	@Transactional
    @Rollback(true)
	public void testUser() throws DuplicatedUserException {
		createUserSerivice.createAdminUser(ADMIN, PASSWORD);
		createUserSerivice.createAdminUser(ADMIN, PASSWORD);
	}
}
