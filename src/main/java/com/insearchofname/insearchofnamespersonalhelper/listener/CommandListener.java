package com.insearchofname.insearchofnamespersonalhelper.listener;

import com.insearchofname.insearchofnamespersonalhelper.exception.GlobalExceptionHandler;
import com.insearchofname.insearchofnamespersonalhelper.exception.UrlException;
import com.insearchofname.insearchofnamespersonalhelper.service.CommandService;
import com.insearchofname.insearchofnamespersonalhelper.util.UrlUtil;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class CommandListener extends ListenerAdapter {

    private final CommandService commandService;
    private final GlobalExceptionHandler globalExceptionHandler;

    @Autowired
    public CommandListener(CommandService commandService, GlobalExceptionHandler globalExceptionHandler) {
        this.commandService = commandService;
        this.globalExceptionHandler = globalExceptionHandler;
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        try {
            event.deferReply().queue();
            var eventName = event.getName();
            switch (eventName) {
                case "ping":
                    event.getHook().sendMessage(commandService.handlePingCommand(event)).setEphemeral(true).queue();
                    break;
                case "yt-get":
                    if (UrlUtil.isValidUrl(Objects.requireNonNull(event.getOption("url")).getAsString())) {
                        event.getHook().sendFiles(commandService.handleYoutubeDownloadCommand(event)).queue();
                    } else {
                        throw new UrlException("Not a valid url", event);
                    }
                    break;
            }
        } catch (Exception e) {
            globalExceptionHandler.handle(e, event);
        }


    }
}
