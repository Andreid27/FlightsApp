package org.company.Services;

import org.company.Models.User;
import org.company.dao.UserDao;

import java.util.Optional;

public class UserService {
    public static boolean verifyUserIdAndPassword(User firstUser, User secondUser){
        boolean  verified = false;
        if(firstUser.getUserId() == secondUser.getUserId()&&firstUser.getPassword().equals(secondUser.getPassword()))
        {verified=true;}
        return verified;
    }
    public static boolean verifyEmailAndPassword(User firstUser, User secondUser){
        boolean  verified = false;
        if(firstUser.getEmail().equals(secondUser.getEmail())&& firstUser.getPassword().equals(secondUser.getPassword()))
        {verified=true;}
        return verified;
    }



    public static boolean DBverifyUserByIdAndPassword(User user){
        Optional<User> user1 = UserDao.getUserById(user);
        User dbUser = user1.orElseGet(() -> new User(0,null));
        return verifyUserIdAndPassword(user,dbUser);
    }
}
