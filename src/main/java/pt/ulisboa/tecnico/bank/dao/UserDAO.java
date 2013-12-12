package pt.ulisboa.tecnico.bank.dao;

import pt.ulisboa.tecnico.bank.domain.Account;
import pt.ulisboa.tecnico.bank.domain.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: Aliaksandra Sankova
 * Date: 11/29/13
 * Time: 3:07 AM
 */
public interface UserDAO extends DAO<User> {

    public User getUserByUserName (String userName);

    public boolean checkUsernameExistence(String userName);
    
    public User createUser(User user);
  
    public List<User> getAllUsers();

    public List<Account> getUserAccounts(String username);
}
