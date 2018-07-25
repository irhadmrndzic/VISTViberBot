package com.viber_bot.car_sharing.controller;

import com.viber_bot.car_sharing.model.Admin;
import com.viber_bot.car_sharing.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class AdminController {

    @Autowired
    private AdminRepository adminRepository;


    @RequestMapping(value= "admin", method=RequestMethod.GET)
    public String admin(){
        Admin admin = new Admin();
        admin.setUsername("admin");
        admin.setPassword("admin");
        adminRepository.save(admin);

        return "routes";
    }

}