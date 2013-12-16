package pt.ulisboa.tecnico.bank.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import pt.ulisboa.tecnico.bank.dao.UserDAO;
import pt.ulisboa.tecnico.bank.domain.Account;
import pt.ulisboa.tecnico.bank.domain.User;

@Repository
public class UserDAOImpl extends HibernateDAO<User> implements UserDAO {

	 public UserDAOImpl() {
	        super(User.class);
	    }

	public User getUserByUserName(String userName) {
		Session session = sessionFactory.getCurrentSession();
		Criteria cr = session.createCriteria(User.class);
		cr.add(Restrictions.eq("username",userName));
		List<User> results = cr.list();
		if ( results.size() != 0)
			return results.get(0);
		return null;
	}

	public boolean checkUsernameExistence(String userName) {
		Session session = sessionFactory.getCurrentSession();
		Criteria cr = session.createCriteria(User.class);
		cr.add(Restrictions.eq("username",userName));
		List<User> results = cr.list();
		return results.size() != 0;
	}

	public User createUser(User user) {
		if(! checkUsernameExistence(user.getUsername())){
			return read(create(user));
		}
		return null;
	}

    public List<User> getAllUsers() {
        Session session = sessionFactory.getCurrentSession();
        Criteria cr = session.createCriteria(User.class);
        List<User> results = cr.list();
        return results;
    }

    public List<Account> getUserAccounts(String username) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crUser = session.createCriteria(User.class);
        crUser.add(Restrictions.eq("username", username));
        User user = (User)crUser.uniqueResult();

        Criteria crAccount = session.createCriteria(Account.class);
        crAccount.add(Restrictions.eq("owner", user));
        List<Account> results = crAccount.list();
        return results;
    }
}
