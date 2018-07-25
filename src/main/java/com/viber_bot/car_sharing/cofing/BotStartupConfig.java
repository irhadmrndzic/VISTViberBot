package com.viber_bot.car_sharing.cofing;

import com.google.common.util.concurrent.Futures;
import com.viber.bot.ViberSignatureValidator;
import com.viber.bot.api.ViberBot;

//import com.viber_bot.car_sharing.service.ViberBotService;
import com.viber.bot.message.TextMessage;
import com.viber_bot.car_sharing.DemoApplication;
import com.viber_bot.car_sharing.service.ViberBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Inject;
import java.util.Optional;

@Configuration
public class BotStartupConfig implements ApplicationListener<ApplicationReadyEvent> {
    @Inject
    private ViberBot bot;

    @Inject
    private ViberSignatureValidator signatureValidator;

    @Autowired
    private ViberBotService viberBotService;

    @Value("$(application.viber-bot.webhook-url)")
    private String webhookUrl;


    public void onApplicationEvent(ApplicationReadyEvent appReadyEvent) {
        try {
            System.out.println("WEBHOOK GET");

            bot.setWebhook(webhookUrl).get();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // echos everything back
        System.out.println("USAO");
        bot.onMessageReceived((event, message, response) -> response.send(message));
        // send 'Hi UserName' when conversation is started
        bot.onConversationStarted(event -> Futures.immediateFuture(Optional.of(
                new TextMessage("Hi " + event.getUser().getName() + ", welcome to (car)bot2share"))));
        bot.onMessageReceived((event, message, reponse) -> viberBotService.onMessageReceived(event, message, reponse));
        bot.onConversationStarted((event) -> viberBotService.onConversationStarted(event));
        bot.onSubscribe((event, response) -> viberBotService.onSubscribe(event, response));
        bot.onUnsubscribe((event) -> viberBotService.onUnsubscribe(event));
    }
}