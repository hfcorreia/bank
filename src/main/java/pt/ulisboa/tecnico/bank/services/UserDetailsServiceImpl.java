package pt.ulisboa.tecnico.bank.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pt.ulisboa.tecnico.bank.dao.UserDAO;
import pt.ulisboa.tecnico.bank.domain.User;

/**
 * Created by Aliaksandra Sankova on 12/11/13.
 */
@Component("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, DataAccessException {
        User user = userDAO.getUserByUserName(userName);

        if(user == null) {
            throw new UsernameNotFoundException(userName + " not found");
        }

        return user;
    }
}
