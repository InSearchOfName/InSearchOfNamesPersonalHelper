package com.insearchofname.insearchofnamespersonalhelper.service;

import jakarta.annotation.PostConstruct;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CommandRegistrar {

    private final JDA jda;
    @Value("${discord.guild.token}")
    private String guildToken;

    @Autowired
    public CommandRegistrar(JDA jda) {
        this.jda = jda;
    }

    @PostConstruct
    public void registerCommands() {
        jda.updateCommands()
                .addCommands(
                        Commands.slash("ping", "Pong!"),
                        Commands.slash("vid-get", "Converts video to a downloadable format")
                                .addOptions(
                                        new OptionData(OptionType.STRING, "url", "Url of the video", true)
                                )

                )
                .queue();
        registerGuildCommands();
    }

    // this is for testing
    public void registerGuildCommands() {
        Guild guild = jda.getGuildById(guildToken);
        if (guild != null) {
            guild.updateCommands()
                    .addCommands(
                            Commands.slash("ping", "Pong!"),
                            Commands.slash("vid-get", "Converts video to a downloadable format")
                                    .addOptions(
                                            new OptionData(OptionType.STRING, "url", "Url of the video", true)
                                    )
                    )
                    .queue();
        }
    }
}
