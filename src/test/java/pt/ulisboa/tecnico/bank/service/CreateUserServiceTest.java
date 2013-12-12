package pt.ulisboa.tecnico.bank.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import pt.ulisboa.tecnico.bank.domain.User;
import pt.ulisboa.tecnico.bank.exceptions.DuplicatedUserException;
import pt.ulisboa.tecnico.bank.security.SecurityMatrixUtil;
import pt.ulisboa.tecnico.bank.services.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring-application-context.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class CreateUserServiceTest {
	
	private static final String ADMIN = "ADMIN";
	private static String USER_NAME = "John Doe";
	private static String PASSWORD = "e12d12312d21";
    private static final String SALT = "salty";
    private static final String ITERATIONS = "10000";
    private static final String MATRIX = new SecurityMatrixUtil(1000).generateJSON();

	@Autowired
	private UserService createUserSerivice;
    private StandardPasswordEncoder encoder = new StandardPasswordEncoder();
	
	@Test
	@Transactional
    @Rollback(true)
	public void testNormalUserCreation() throws DuplicatedUserException {
		User user = createUserSerivice.createNormalUser(USER_NAME, PASSWORD, SALT, ITERATIONS, MATRIX);
		assertEquals(USER_NAME, user.getUsername());
		assertTrue(encoder.matches(PASSWORD, user.getPassword()));
	}
	
	@Test(expected = DuplicatedUserException.class)
	@Transactional
    @Rollback(true)
	public void testUserCreationFail() throws DuplicatedUserException {
		User user = createUserSerivice.createAdminUser(ADMIN, PASSWORD, SALT, ITERATIONS, MATRIX);
		assertEquals(ADMIN, user.getUsername());
		assertTrue(encoder.matches(PASSWORD, user.getPassword()));
	}

	@Test(expected = DuplicatedUserException.class)
	@Transactional
    @Rollback(true)
	public void testUser() throws DuplicatedUserException {
		createUserSerivice.createAdminUser(ADMIN, PASSWORD, SALT, ITERATIONS, MATRIX);
		createUserSerivice.createAdminUser(ADMIN, PASSWORD, SALT, ITERATIONS, MATRIX);
	}
}
