package com.insearchofname.insearchofnamespersonalhelper.config;

import com.insearchofname.insearchofnamespersonalhelper.listener.CommandListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumSet;

@Configuration
public class JDAConfig {

    private final CommandListener commandListener;
    @Value("${discord.bot.token}")
    private String botToken;


    @Autowired
    public JDAConfig(CommandListener commandListener) {
        this.commandListener = commandListener;
    }

    @Bean
    public JDA jda() throws Exception {
        return JDABuilder.createDefault(botToken)
                .enableIntents(EnumSet.of(
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.MESSAGE_CONTENT,
                        GatewayIntent.GUILD_MEMBERS))
                .addEventListeners(commandListener)
                .build()
                .awaitReady(); // Waits until JDA is fully loaded
    }


}

