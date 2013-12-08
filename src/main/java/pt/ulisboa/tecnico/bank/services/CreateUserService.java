package pt.ulisboa.tecnico.bank.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pt.ulisboa.tecnico.bank.dao.UserDAO;
import pt.ulisboa.tecnico.bank.domain.Roles;
import pt.ulisboa.tecnico.bank.domain.User;
import pt.ulisboa.tecnico.bank.exceptions.BankException;
import pt.ulisboa.tecnico.bank.exceptions.DuplicatedUserException;

@Service("userService")
public class CreateUserService {

	@Autowired
	private UserDAO userDAO;

	@Transactional
	public User createNormalUser(String username, String password) throws DuplicatedUserException {
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setRole(Roles.USER);
		
		user = userDAO.createUser(user);
		if ( user != null ) 
			return user;
		else throw new DuplicatedUserException(username);
	}

	@Transactional
	public User createAdminUser(String username, String password) throws DuplicatedUserException {
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setRole(Roles.ADMIN);
		
		user = userDAO.createUser(user);
		if ( user != null ) 
			return user;
		else throw new DuplicatedUserException(username);
	}
}
