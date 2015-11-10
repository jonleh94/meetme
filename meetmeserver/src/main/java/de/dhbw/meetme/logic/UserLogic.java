package de.dhbw.meetme.logic;

import de.dhbw.meetme.database.dao.UserDao;
import de.dhbw.meetme.domain.User;
import de.dhbw.meetme.rest.UserService;
import de.dhbw.meetme.servlet.UserServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;


public class UserLogic {

    private static final Logger log = LoggerFactory.getLogger(UserLogic.class);

    @Inject
    UserDao userDao;


    public boolean checkPassword(String username, String password) {
        boolean check = false;

        try {
            User thisuser = userDao.findByUserName(username);
            if (thisuser.getPassword().equals(UserServlet.getMD5(password))) {
                check = true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception exe) {
            exe.printStackTrace();
        }

        return check;

    }


}
