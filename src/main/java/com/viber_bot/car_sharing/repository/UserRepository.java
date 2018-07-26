package com.viber_bot.car_sharing.repository;

import com.viber_bot.car_sharing.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public List<User> findAll();
    public User findByViberId(String viberId);

//    void subscribe(String viberId);
//    void unsubscribe(String viberId);
//    public void add(User user);
//    public  User findUser(String viberID);
}
