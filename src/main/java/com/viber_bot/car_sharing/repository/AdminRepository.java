package com.viber_bot.car_sharing.repository;

import com.viber_bot.car_sharing.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
        public Admin findByUsername(String username);

}