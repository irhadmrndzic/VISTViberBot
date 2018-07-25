package com.viber_bot.car_sharing.cofing;
import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.viber.bot.Request;
import com.viber.bot.ViberSignatureValidator;
import com.viber.bot.api.ViberBot;
import com.viber.bot.profile.BotProfile;

@Configuration
public class BotConfig {

    @Value("${application.viber-bot.auth-token}")
    private String authToken;

    @Value("${application.viber-bot.name}")
    private String name;

    @Nullable
    @Value("${application.viber-bot.avatar}")
    private String avatar;

    @Bean
    ViberBot viberBot() {
        return new ViberBot(new BotProfile(name, avatar), authToken);
    }

    @Bean
    ViberSignatureValidator signatureValidator() {
        return new ViberSignatureValidator(authToken);
    }

}