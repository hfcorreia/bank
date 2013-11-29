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
public interface AccountDAO extends DAO<Account> {
    public List<Account> listForUser(User user, int first, int count);

    public List<Account> listNonBlockedForUser(User user);

    public Long countForUser(User user);
}
