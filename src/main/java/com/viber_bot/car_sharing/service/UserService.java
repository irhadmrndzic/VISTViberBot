package com.viber_bot.car_sharing.service;

import com.viber_bot.car_sharing.model.User;
import com.viber_bot.car_sharing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService  {

    @Autowired
    UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public String find(String viberId){return viberId;}


    @Transactional
    public void add(User user){
        User userU = userRepository.findByViberId(user.getViberID());
        if (userU == null){
            userRepository.save(user);
        }
    }

    @Transactional
    public void unsubscribe(String viberID){
      User user = userRepository.findByViberId(viberID);
        if (user!= null) {
            user.setSubscribed(false);
            userRepository.save(user);
        }
    }

    @Transactional
    public void subscribe(String viberID){
        User user = userRepository.findByViberId(viberID);
        if (user!= null) {
            user.setSubscribed(true);
            userRepository.save(user);
        }
    }
    public  boolean findByViberId(String viberID){
        if (userRepository.findByViberId(viberID)!=null)
            return true;
        return false;
    }
public  User findUser(String viberID){return userRepository.findByViberId(viberID);}
}
