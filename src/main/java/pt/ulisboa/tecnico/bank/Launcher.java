package pt.ulisboa.tecnico.bank;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import pt.ulisboa.tecnico.bank.domain.User;
import pt.ulisboa.tecnico.bank.exceptions.DuplicatedUserException;
import pt.ulisboa.tecnico.bank.security.SecurityMatrixUtil;
import pt.ulisboa.tecnico.bank.services.AccountService;
import pt.ulisboa.tecnico.bank.services.UserService;

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

    @Autowired
    AccountService accountService;

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
    public void init() throws IOException{
    	SecurityMatrixUtil matrixUtil = new SecurityMatrixUtil(1231231);
    	TreeMap<String, ArrayList<Integer>> userMatrix = matrixUtil.generateMatrix();
    	
    	SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
    	File file = File.createTempFile("matrix " + " " + user_name, "end " + format.format(new Date()));
    	Writer writer = new BufferedWriter( new OutputStreamWriter ( new FileOutputStream(file), "utf-8"));
    	writer.write(matrixUtil.print(userMatrix));
    	writer.close();
    	
        try{
            User administrator = userService.createAdminUser(admin_name, admin_password, admin_salt, admin_iterations, matrixUtil.generateJSON(matrixUtil.generateMatrix()));
            User user = userService.createNormalUser(user_name, user_password, user_salt, user_iterations, matrixUtil.generateJSON(userMatrix));
            accountService.createNewAccount(user, "PT1001", 1000.0);
            accountService.createNewAccount(user, "PT1002", 1248.55);
        }
        catch (DuplicatedUserException e){
            logger.error(e);
        }

    }

}
