package pt.ulisboa.tecnico.bank.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pt.ulisboa.tecnico.bank.dao.UserDAO;
import pt.ulisboa.tecnico.bank.domain.Account;
import pt.ulisboa.tecnico.bank.domain.Role;
import pt.ulisboa.tecnico.bank.domain.Roles;
import pt.ulisboa.tecnico.bank.domain.User;
import pt.ulisboa.tecnico.bank.exceptions.DuplicatedUserException;

@Service("userService")
public class UserService {

	@Autowired
	private UserDAO userDAO;
    private StandardPasswordEncoder encoder = new StandardPasswordEncoder();

	@Transactional
	public User createNormalUser(String username, String password, String salt, String iterations, String matrix) throws DuplicatedUserException {
		User user = new User();
        Role role = new Role();
        role.setName(Roles.USER.name());
		user.setUsername(username);
		user.setPassword(encoder.encode(password));
		user.setRole(role);
        user.setSalt(salt);
        user.setIterations(iterations);
        user.setMatrix(matrix);
		
		user = userDAO.createUser(user);
		if ( user != null ) 
			return user;
		else throw new DuplicatedUserException(username);
	}

	@Transactional
	public User createAdminUser(String username, String password, String salt, String iterations, String matrix) throws DuplicatedUserException {
		User user = new User();
        Role role = new Role();
        role.setName(Roles.ADMINISTRATOR.name());
		user.setUsername(username);
		user.setPassword(encoder.encode(password));
		user.setRole(role);
        user.setSalt(salt);
        user.setIterations(iterations);
        user.setMatrix(matrix);
		
		user = userDAO.createUser(user);
		if ( user != null ) 
			return user;
		else throw new DuplicatedUserException(username);
	}

    @Transactional
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Transactional
    public List<Account> getUserAccounts(String username) {
        return userDAO.getUserAccounts(username);
    }
}
