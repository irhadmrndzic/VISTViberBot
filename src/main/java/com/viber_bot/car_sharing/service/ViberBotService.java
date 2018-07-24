package com.viber_bot.car_sharing.service;

import com.viber.bot.Response;
import com.viber.bot.event.incoming.IncomingConversationStartedEvent;
import com.viber.bot.event.incoming.IncomingMessageEvent;
import com.viber.bot.event.incoming.IncomingSubscribedEvent;
import com.viber.bot.event.incoming.IncomingUnsubscribeEvent;
import com.viber.bot.message.Message;
import com.viber.bot.message.TextMessage;
import com.viber.bot.profile.UserProfile;
import com.viber_bot.car_sharing.model.User;

import java.util.Optional;
import java.util.concurrent.Future;

public class ViberBotService {


//    public void onSubscribed(IncomingSubscribedEvent event, Response response) {
//        UserProfile user = event.getUser();
//        userService.add(new User(user.getId(), user.getName(), true));
//        response.send (new TextMessage("You are now subscribed"));
//    }
//
//    public void onUnsubcribed(IncomingUnsubscribeEvent event) {
//        userService.unsubscribe(event.getUserId());
//    }
//
//    public Future<Optional<Message>> onConversationStarted(IncomingConversationStartedEvent event) {
//    }
//
//    public void onUnsubcribed(IncomingUnsubscribeEvent event) {
//    }
//
//    public void onMessageRecived(IncomingMessageEvent event, Message message, Response reponse) {
//    }


}
