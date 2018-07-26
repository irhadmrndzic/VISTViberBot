package com.viber_bot.car_sharing.controller;

import com.viber_bot.car_sharing.model.Route;
import com.viber_bot.car_sharing.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.Time;

@Controller
public class RouteController {
    @Autowired
    private RouteService routeService;
    @RequestMapping(value = "/routes", method = RequestMethod.GET)
    public String getAllRoutes(Model model) {
        List<Route> rList = new ArrayList<>();
//        routeService.save(new Route("Mostar","Sarajevo", null, null, 2));
//        routeService.save(new Route("Sarajevo","Konjic", null, null, 24));
//        routeService.save(new Route("Bihac","Trebinje", null, null, 20));
//        routeService.save(new Route("Zenica","Tuzla", null, null, 12));
//        routeService.save(new Route("Neum","Mostar", null, null, 7));
        rList = routeService.findAll();
        model.addAttribute("routes",rList);
        return "Route/Routes";
    }

    @RequestMapping(value="/routes/delete/{id}", method = RequestMethod.GET)
    public String deleteRoute(@PathVariable("id") int id, RedirectAttributes redirectAttributes, Model model){
        routeService.delete(id);
        List<Route> rList = new ArrayList<>();
        rList = routeService.findAll();
        model.addAttribute("routes",rList);
        redirectAttributes.addFlashAttribute("message","Route " + id + " was deleted!");
        return "redirect:/routes";
    }

    @RequestMapping(value = "/routes/add", method = RequestMethod.GET)
    public String displayAddRoute(Model model) {
        List<Route> rList = new ArrayList<>();
        rList = routeService.findAll();
        model.addAttribute("routes", rList);
        return "Route/AddRoute";
    }

    @RequestMapping(value = "/routes/add", method = RequestMethod.POST)
    public String addRoute(@RequestParam(value = "start") String start,
                           @RequestParam(value = "destination") String destination,
                           @RequestParam(value = "date") String date,
                           @RequestParam(value = "time") String time,
                           @RequestParam(value = "avaliableseats") int avaliableseats,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        Route newRoute = new Route(start, destination, LocalDate.parse(date), LocalTime.parse(time), avaliableseats);
        routeService.save(newRoute);
        List<Route> rList = new ArrayList<>();
        rList = routeService.findAll();
        model.addAttribute("routes", rList);
        redirectAttributes.addFlashAttribute("message", "Route " + newRoute.getId() + " was added!  ");
        return "redirect:/routes";
    }

    @RequestMapping(value = "/routes/edit/{id}", method = RequestMethod.GET)
    public String editRoute(@PathVariable(value = "id") int id, Model model) {
        model.addAttribute("model", routeService.findById(id));

        return "Route/EditRoutes";
    }

    @RequestMapping(value = "/routes/edit/{id}", method = RequestMethod.POST)
    public String seditRoute(@PathVariable(value = "id") int id, Model model, @RequestParam(value = "start") String start, @RequestParam(value = "destination") String destination, @RequestParam( value = "date")
            String date, @RequestParam(value = "time") String time, @RequestParam(value = "avaliableseats") int avaliableseats, RedirectAttributes redirectAttributes) {

        Route route = routeService.findById(id);
        LocalDate editeddate;
        editeddate = LocalDate.parse(date);
        RouteService rs = new RouteService();

        route.setStart(start);
        route.setDestination(destination);
        route.setDate(editeddate);
        route.setTime(LocalTime.parse(time));
        route.setAvaliableseats(avaliableseats);

        List<Route> rList = new ArrayList<>();
        rList = routeService.findAll();
        model.addAttribute("routes", rList);

        redirectAttributes.addFlashAttribute("message", "Route " + id + " was updated!  ");

        routeService.save(route);

        return "redirect:/routes";
    }
}

