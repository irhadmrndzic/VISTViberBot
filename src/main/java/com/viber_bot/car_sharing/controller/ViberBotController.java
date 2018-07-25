package com.viber_bot.car_sharing.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;


import com.google.common.base.Charsets;
import com.viber.bot.Request;
import com.viber.bot.ViberSignatureValidator;
import com.viber.bot.api.ViberBot;
import com.viber.bot.profile.BotProfile;

import com.google.common.io.CharStreams;
import com.viber.bot.Preconditions;
import com.viber.bot.Request;
import com.viber.bot.ViberSignatureValidator;
import org.hibernate.service.spi.InjectService;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import javax.inject.Inject;
import java.lang.String;

@RestController
public class ViberBotController {

    @Inject
    private ViberBot bot;

    @Inject
    private ViberSignatureValidator viberSignatureValidator;

    @PostMapping(value = "/webhook", produces = "application/json")
    public String incoming(@RequestBody String json,
                           @RequestHeader("X-Viber-Content-Signature") String serverSideSignature)
            throws ExecutionException, InterruptedException, IOException {
        com.google.common.base.Preconditions.checkState(viberSignatureValidator.isSignatureValid(serverSideSignature, json), "invalid signature");
        @javax.annotation.Nullable InputStream response = bot.incoming(Request.fromJsonString(json)).get();
        return response != null ? CharStreams.toString(new InputStreamReader(response, Charsets.UTF_16)) : null;
    }
}




