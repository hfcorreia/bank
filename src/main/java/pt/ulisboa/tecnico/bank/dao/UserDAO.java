package pt.ulisboa.tecnico.bank.dao;

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

    public List<User> getUsersByRoleName(String roleName);

    public Long sizeByRoleName(String roleName);

    public boolean contains (String email);

    public List<User> listByRoleName(int first, int count, String roleName);

    public boolean checkUsernameExistence(String userName, Long userId);

    public boolean checkEmailExistence(String email, Long userId);

    public void evict(User user);
}
