package pt.ulisboa.tecnico.bank.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import pt.ulisboa.tecnico.bank.dao.AccountDAO;
import pt.ulisboa.tecnico.bank.domain.Account;
import pt.ulisboa.tecnico.bank.domain.User;

@Repository
public class AccountDAOImpl extends HibernateDAO<Account> implements AccountDAO {

	public AccountDAOImpl(){
		super(Account.class);
	}
	
	public Account getAccount(String accountName) {
		Session session = sessionFactory.getCurrentSession();
		Criteria cr = session.createCriteria(Account.class);
		cr.add(Restrictions.eq("number",accountName));
		List<Account> results = cr.list();
		
		if( results.size() != 0 ) 
			return results.get(0);
		return null;
	}

	public User getAccountOwner(String accountName) {
		Account account = getAccount(accountName);
		if (account != null)
			return account.getOwner();
		return null;
	}

	public boolean checkAccountExistence(String accountName) {
		Session session = sessionFactory.getCurrentSession();
		Criteria cr = session.createCriteria(Account.class);
		cr.add(Restrictions.eq("number",accountName));
		List<Account> results = cr.list();
		
		return results.size() != 0;
	}

	public Account createAccount(Account account) {
		if(! checkAccountExistence(account.getNumber())){
			return read(create(account));
		}
		return null;
	}

}
