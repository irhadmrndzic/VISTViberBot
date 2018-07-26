package com.viber_bot.car_sharing.service;

import com.google.common.util.concurrent.Futures;
import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;
import com.viber.bot.Response;
import com.viber.bot.event.incoming.IncomingConversationStartedEvent;
import com.viber.bot.event.incoming.IncomingMessageEvent;
import com.viber.bot.event.incoming.IncomingSubscribedEvent;
import com.viber.bot.event.incoming.IncomingUnsubscribeEvent;
import com.viber.bot.message.*;
import com.viber.bot.profile.UserProfile;
import com.viber_bot.car_sharing.model.Reservation;
import com.viber_bot.car_sharing.model.Route;
import com.viber_bot.car_sharing.model.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.Future;

@Component
public class ViberBotService {

    @Autowired
    private UserService userService;

    @Autowired
    private RouteService routeService;

    @Autowired
    ReservationService reservationService;

    @Autowired
    private LocationService locationService;

    public Future<Optional<Message>> onConversationStarted(IncomingConversationStartedEvent event) {
        System.out.println(reservationService.findAll());

        TrackingData trackingData = createTrackingData("onConversationStarted", null,null,null,null,
                null,null,null,null,
                null,null,null,null);

        ArrayList<Object> buttonArray = new ArrayList<Object>();
        buttonArray.add(makeButtons(2,3,"Create a route", "reply", "createRoute","#45D1D0"));
        buttonArray.add(makeButtons(2,3,"Apply to a route", "reply", "applyToRoute","#45D1D0"));
        buttonArray.add(makeButtons(2,3,"View all active routes", "reply", "allRoutes","#45D1D0"));
        buttonArray.add(makeButtons(2,3,"View my routes", "reply", "myRoutes","#45D1D0"));

        MessageKeyboard keyboard = createKeyboard(buttonArray);

        UserProfile user = event.getUser();
        userService.add(new User(user.getId(), user.getName(), true));

        return Futures.immediateFuture(
                Optional.of(new TextMessage("Hi " + event.getUser().getName() + ", " +
                        "please choose one of the options below: ",
                        keyboard, trackingData)));

    }

    public void onSubscribe(IncomingSubscribedEvent event, Response response) {
        UserProfile user = event.getUser();
        userService.add(new User(user.getId(), user.getName(), true));

        response.send(new TextMessage("You are now subscribed"));
    }

    public void onUnsubscribe(IncomingUnsubscribeEvent event) {
        userService.unsubscribe(event.getUserId());
    }
 int a=1;
    public void onMessageReceived(IncomingMessageEvent event, Message message, Response response) {

        switch (message.getTrackingData().get("step").toString()) {
            case "onConversationStarted":
                onConversationStartedResponse(event, message, response);
                break;
            case "applyToRoute":
                System.out.println("USAO U APPLY");
                applyToRoute(event, message, response);
                break;
            case "createRoute":
                System.out.println("USAO U CREATE");
                createRoute(event, message, response);
                break;
        }

    }

    private void onConversationStartedResponse(IncomingMessageEvent incomingMessageEvent, Message message, Response response) {

        if(message.getMapRepresentation().get("text").equals("allRoutes")){
            List<Route> routes = routeService.findAll();
            ArrayList<Object> buttonArray = new ArrayList<Object>();
            for(Route route : routes){
                String name = StringUtils.substringBefore(route.getStart(), ",") + "-" +
                        StringUtils.substringBefore(route.getDestination(), ",");
                buttonArray.add(makeButtons(1,6, name,
                        "reply", String.valueOf(route.getId()),"#45D1D0"));
            }
            buttonArray.add(makeButtons(1,6,"Go back", "reply", "back","#45D1D0"));
            MessageKeyboard keyboard = createKeyboard(buttonArray);
            TrackingData trackingData = createTrackingData("onConversationStarted",    null,null,null,null,
                    null,null,null,null,
                    null,null,null,null);

            response.send(new TextMessage("Active routes:", keyboard, trackingData));
        }
        else if(message.getMapRepresentation().get("text").equals("applyToRoute")) {
            List<Route> routes = routeService.findAll();
            ArrayList<Object> buttonArray = new ArrayList<Object>();
            for(Route route : routes){
                if(route.getAvaliableseats() < 1) {
                    routes.remove(route);
                }
                else {
                    String name = StringUtils.substringBefore(route.getStart(), ",") + "-" +
                            StringUtils.substringBefore(route.getDestination(), ",");
                    buttonArray.add(makeButtons(1,6,name,
                            "reply", String.valueOf(route.getId()),"#45D1D0"));
                }

            }
            MessageKeyboard keyboard = createKeyboard(buttonArray);
            TrackingData trackingData = createTrackingData("applyToRoute","applyToRoute","init",null,null,
                    null,null,null,null,
                    null,null,null,null);

            response.send(new TextMessage("Active routes: ", keyboard, trackingData));
        }
        else if(message.getMapRepresentation().get("text").equals("createRoute")) {
            TrackingData trackingData = createTrackingData("createRoute", "createRoute", "enterStart",
                    null,null,null,null,null,
                    null,null,null,null,null);
            System.out.println("!!!!!!!");
            response.send(new TextMessage("Enter the start",null, trackingData));

        }
        else if(message.getMapRepresentation().get("text").equals("myRoutes")) {
            List<Route> routes = routeService.findAllByViberId(incomingMessageEvent.getSender().getId());
            if (routes.isEmpty()) {
                createInitMessage(incomingMessageEvent, message, response, "You do not have routes created");
            }
            ArrayList<Object> buttonArray = new ArrayList<Object>();
            for(Route route : routes){
                buttonArray.add(makeButtons(1,6,route.getStart() + "-" + route.getDestination(),
                        "reply", String.valueOf(route.getId()),"#45D1D0"));
            }
            buttonArray.add(makeButtons(1,6,"Go back", "reply", "back","#45D1D0"));
            MessageKeyboard keyboard = createKeyboard(buttonArray);
            TrackingData trackingData = createTrackingData("onConversationStarted",  null,null,null,null,
                    null,null,null,null,
                    null,null,null,null);

            response.send(new TextMessage("test", keyboard, trackingData));
        }
        else if(message.getTrackingData().get("step").equals("onConversationStarted")) {
            String textMessage = "Hi " + incomingMessageEvent.getSender().getName()
                    + ", please choose one of the options below: ";
            createInitMessage(incomingMessageEvent, message, response, textMessage);
        }
    }

    private void createRoute(IncomingMessageEvent event, Message message, Response response) {

        TrackingData trackingData;
        if (message.getTrackingData().get("createRoute").equals("enterStart")) {


            String start = locationService.getLocation(message.getMapRepresentation().get("location"));
            System.out.println("START " + start);
            if(start == null) {
                trackingData = createTrackingData("createRoute", "createRoute", "enterStart",
                        null,null,
                        null,null,null,null,
                        null,null,null,null);

                response.send(new TextMessage("Enter start again", null, trackingData));
            }else{
                trackingData = createTrackingData("createRoute", "createRoute", "enterDestination",
                        "start", start,
                        null, null, null, null,
                        null, null, null, null);

                response.send(new TextMessage("Enter destination", null, trackingData));

            }
        }
        else if(message.getTrackingData().get("createRoute").equals("enterDestination")) {

            String destination = locationService.getLocation(message.getMapRepresentation().get("location"));
            System.out.println("DESTINATION " + destination);
            if(destination == null) {
                trackingData = createTrackingData("createRoute", "createRoute", "enterDestination",
                        "start", message.getTrackingData().get("start").toString(),
                        null, null,
                        null,null,null,null,null,null);
                response.send(new TextMessage("Enter destination again:", null, trackingData));
            }else {
                trackingData = createTrackingData("createRoute", "createRoute", "enterDate",
                        "start", message.getTrackingData().get("start").toString(),
                        "destination", destination,
                        null, null, null, null, null, null);
                response.send(new TextMessage("Enter date:", null, trackingData));
            }
        }
        else if(message.getTrackingData().get("createRoute").equals("enterDate")) {
            Map<String, Object> tdMap = new HashMap<String, Object>();
            ArrayList<Object> buttons = new ArrayList<>();
            tdMap.put("step", "createRoute");
            tdMap.put("createRoute", "enterTime");
            tdMap.put("start", message.getTrackingData().get("start").toString());
            tdMap.put("destination", message.getTrackingData().get("destination").toString());
            tdMap.put("date", message.getMapRepresentation().get("text").toString());
            trackingData = createTrackingData(tdMap);
            for(int i = 0; i < 24; i++) {
                if(i<10)
                    buttons.add(makeButtons(1,6, "0"+ String.valueOf(i) + ":00", "reply", "0" + String.valueOf(i) + ":00","#45D1D0"));
                else
                    buttons.add(makeButtons(1,6,String.valueOf(i) + ":00", "reply", String.valueOf(i) + ":00","#45D1D0"));
            }
            MessageKeyboard keyboard = createKeyboard(buttons);
            response.send(new TextMessage("Enter time: ", keyboard, trackingData));
        }
        else if(message.getTrackingData().get("createRoute").equals("enterTime")) {
            Map<String, Object> tdMap = new HashMap<String, Object>();
            ArrayList<Object> buttons = new ArrayList<>();
            tdMap.put("step", "createRoute");
            tdMap.put("createRoute", "enterSeats");
            tdMap.put("start", message.getTrackingData().get("start").toString());
            tdMap.put("destination", message.getTrackingData().get("destination").toString());
            tdMap.put("date", message.getTrackingData().get("date"));
            tdMap.put("time", message.getMapRepresentation().get("text").toString());
            trackingData = createTrackingData(tdMap);
            for(int i = 0; i < 5; i++) {
                buttons.add(makeButtons(1,6, String.valueOf(i), "reply", String.valueOf(i),"#45D1D0"));
            }
            MessageKeyboard keyboard = createKeyboard(buttons);
            response.send(new TextMessage("Enter available seats: ", keyboard, trackingData));
        }
        else if(message.getTrackingData().get("createRoute").equals("enterSeats")) {
            Map<String, Object> tdMap = new HashMap<String, Object>();
            ArrayList<Object> buttons = new ArrayList<>();
            Route route = routeService.saveRoute(new Route(message.getTrackingData().get("start").toString(),
                    message.getTrackingData().get("destination").toString(),
                    parseDate(message.getTrackingData().get("date").toString()),
                    LocalTime.parse(message.getTrackingData().get("time").toString()),
                    Integer.parseInt(message.getMapRepresentation().get("text").toString()),
                    event.getSender().getId()));
            String routeText = "Start: " + route.getStart() + "\nDestination: " + route.getDestination() +
                    "\nDate: " + route.getDate().toString() + "\nTime: " + route.getTime() +
                    "\nAvailable seats: " + route.getAvaliableseats();

            buttons.add(makeButtons(1,6, "Confirm", "reply", "confirmRoute","#45D1D0"));
            buttons.add(makeButtons(1,6, "Cancel", "reply", "cancelRoute","#45D1D0"));
            tdMap.put("step", "createRoute");
            tdMap.put("createRoute", "confirmRoute");
            tdMap.put("route", route.getId());
            trackingData = createTrackingData(tdMap);
            MessageKeyboard keyboard = createKeyboard(buttons);
            response.send(new TextMessage("Confirm route: \n" + routeText, keyboard, trackingData));
        }
        else if(message.getTrackingData().get("createRoute").equals("confirmRoute")) {
            if(message.getMapRepresentation().get("text").equals("confirmRoute")) {
                createInitMessage(event, message, response, "Route is successfully created!");
            }
            else{
                routeService.delete(Integer.parseInt(message.getTrackingData().get("route").toString()));
                createInitMessage(event, message, response, "Hello");
            }
        }

    }

    private void applyToRoute(IncomingMessageEvent event, Message message, Response response) {
        TrackingData trackingData;
        ArrayList<Object> buttons = new ArrayList<>();
        if(message.getTrackingData().get("applyToRoute").equals("init")) {
            Route route = routeService.findById(Integer.parseInt(message.getMapRepresentation().get("text").toString()));
            trackingData = createTrackingData("applyToRoute", "applyToRoute", "confirm",
                    "routeId", String.valueOf(route.getId()),
                    null,null,null,null,
                    null,null,null,null);
            buttons.add(makeButtons(1,6, "Confirm", "reply", "confirmApply","#45D1D0"));
            buttons.add(makeButtons(1,6, "Cancel", "reply", "cancelApply","#45D1D0"));
            MessageKeyboard keyboard = createKeyboard(buttons);
            response.send(new TextMessage("Apply to this route:\n" +
                    "Start: " + route.getStart() +
                    "\nDestination: " + route.getDestination() +
                    "\nDate: " + route.getDate() +
                    "\nTime: " + route.getTime() +
                    "\nAvailable seats: " + route.getAvaliableseats(),
                    keyboard, trackingData));

        }
        else if(message.getTrackingData().get("applyToRoute").equals("confirm")) {
            if(message.getMapRepresentation().get("text").equals("confirmApply")) {
                Route route = routeService.findById(Integer.parseInt(message.getTrackingData().get("routeId").toString()));
                Reservation reservation = reservationService.getReservationByRoute(route);
                if(reservation == null) {
                    System.out.println("NASAO RUTU");
                    reservation = new Reservation(userService.findUser(event.getSender().getId()), route, 1);
                    reservationService.save(reservation);
                }
                route.setAvaliableseats(route.getAvaliableseats() - 1);
                routeService.edit(route.getId(), route);
                createInitMessage(event, message, response, "You applied successfully!");
            }
            else{
                createInitMessage(event, message, response, "Hello");
            }

        }
    }

    private TrackingData createTrackingData(String event, String key1, String value1,
                                            String key2, String value2,
                                            String key3, String value3,
                                            String key4, String value4,
                                            String key5, String value5,
                                            String key6, String value6) {
        Map<String, Object> tdMap = new HashMap<String, Object>();
        tdMap.put("step", event);
        if(key1 != null)
            tdMap.put(key1, value1);
        if(key2 != null)
            tdMap.put(key2, value2);
        if(key3 != null)
            tdMap.put(key3, value3);
        if(key4 != null)
            tdMap.put(key4, value4);
        if(key5 != null)
            tdMap.put(key5, value5);
        if(key6 != null)
            tdMap.put(key6, value6);
        TrackingData trackingData = new TrackingData(tdMap);

        return trackingData;
    }

    private TrackingData createTrackingData(Map<String, Object> tdMap) {

        TrackingData trackingData = new TrackingData(tdMap);

        return trackingData;
    }

    private MessageKeyboard createKeyboard(ArrayList<Object> buttons) {
        Map<String, Object> keyboardMap = new HashMap<String, Object>();
        keyboardMap.put("Type", "keyboard");
        keyboardMap.put("Buttons", buttons);
        return new MessageKeyboard(keyboardMap);
    }

    private Map<String, Object> makeButtons(Integer rows, Integer columns, String text,
                                            String actionType ,String actionBody,String Bgcolor) {
        Map<String, Object> button = new HashMap<String, Object>();

        button.put("Columns", columns);
        button.put("Rows", rows);
        button.put("Text", text);
        button.put("TextSize", "medium");
        button.put("TextHAlign", "center");
        button.put("TextVAlign", "center");
        button.put("ActionType", actionType);
        button.put("ActionBody", actionBody);
        button.put("ButtonColor",Bgcolor);
        return button;
    }

    private void createInitMessage(IncomingMessageEvent event, Message message, Response response, String textMessage) {
        TrackingData trackingData = createTrackingData("onConversationStarted", null,null,null,null,null,null,
                null,null,null,null,null,null);

        ArrayList<Object> buttonArray = new ArrayList<Object>();
        buttonArray.add(makeButtons(2,3,"Create a route", "reply", "createRoute","#45D1D0"));
        buttonArray.add(makeButtons(2,3,"Apply to a route", "reply", "applyToRoute","#45D1D0"));
        buttonArray.add(makeButtons(2,3,"View all active routes", "reply", "allRoutes","#45D1D0"));
        buttonArray.add(makeButtons(2,3,"View my routes", "reply", "myRoutes","#45D1D0"));

        MessageKeyboard keyboard = createKeyboard(buttonArray);


        response.send(new TextMessage(textMessage,keyboard, trackingData));

    }

    private LocalDate parseDate(String date) {
        try {
            Parser parser = new Parser();
            List<LocalDate> listDaTes = new ArrayList<>();
            List<DateGroup> dateGroups = parser.parse(date);
            for(DateGroup dateGroup : dateGroups) {
                List<Date> dates = dateGroup.getDates();
                for (Date dateToConvert : dates) {
                    listDaTes.add(dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                }
            }

            System.out.println("DATE " + listDaTes.get(0));
            return listDaTes.get(0);
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}