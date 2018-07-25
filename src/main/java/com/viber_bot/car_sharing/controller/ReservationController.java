package com.viber_bot.car_sharing.controller;

import com.viber_bot.car_sharing.model.Reservation;
import com.viber_bot.car_sharing.model.Route;
import com.viber_bot.car_sharing.service.ReservationService;
import com.viber_bot.car_sharing.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ReservationController {
    @Autowired
    private ReservationService reservationService;
    @RequestMapping(value = "/reservations", method = RequestMethod.GET)
    public String getAllReservations(Model model) {
        List<Reservation> rList = new ArrayList<>();
        rList = reservationService.findAll();
        model.addAttribute("reservations",rList);
        return "Reservation/Reservations";
    }

    @RequestMapping(value="/reservations/delete/{id}", method = RequestMethod.GET)
    public String deleteReservation(@PathVariable("id") int id, RedirectAttributes redirectAttributes, Model model){
        reservationService.delete(id);
        List<Reservation> rList = new ArrayList<>();
        rList = reservationService.findAll();
        model.addAttribute("reservations",rList);
        redirectAttributes.addFlashAttribute("message","Reservation " + id + " was deleted!");
        return "redirect:/reservations";
    }
}

