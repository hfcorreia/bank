package pt.ulisboa.tecnico.bank;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import pt.ulisboa.tecnico.bank.domain.Roles;
import pt.ulisboa.tecnico.bank.domain.User;
import pt.ulisboa.tecnico.bank.exceptions.DuplicatedUserException;
import pt.ulisboa.tecnico.bank.services.UserService;

import javax.annotation.PostConstruct;

/**
 * Created with IntelliJ IDEA.
 * Author: Aliaksandra Sankova
 * Date: 11/29/13
 * Time: 6:06 AM
 */
public class Launcher {
    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    UserService userService;

    @Value("#{wiringProperties['admin.username']}")
    private String admin_name;
    @Value("#{wiringProperties['admin.password']}")
    private String admin_password;
    @Value("#{wiringProperties['admin.username']}")
    private String admin_email;
    @Value("#{wiringProperties['admin.salt']}")
    private String admin_salt;
    @Value("#{wiringProperties['admin.iterations']}")
    private String admin_iterations;
    @Value("#{wiringProperties['user.username']}")
    private String user_name;
    @Value("#{wiringProperties['user.password']}")
    private String user_password;
    @Value("#{wiringProperties['user.username']}")
    private String user_email;
    @Value("#{wiringProperties['user.salt']}")
    private String user_salt;
    @Value("#{wiringProperties['user.iterations']}")
    private String user_iterations;

    @PostConstruct
    public void init(){
        try{
            User administrator = userService.createAdminUser(admin_name, admin_password, admin_salt, admin_iterations);
            User user = userService.createNormalUser(user_name, user_password, user_salt, user_iterations);
        }
        catch (DuplicatedUserException e){
            logger.error(e);
        }

    }

}
