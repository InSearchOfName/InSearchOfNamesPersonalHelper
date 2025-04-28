package com.insearchofname.insearchofnamespersonalhelper.listener;

import com.insearchofname.insearchofnamespersonalhelper.exception.GlobalExceptionHandler;
import com.insearchofname.insearchofnamespersonalhelper.service.CommandService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


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
                case "vid-get":
                    event.getHook().sendFiles(commandService.handleVideoDownloadCommand(event).uploads()).queue();
                    break;
            }

        } catch (Exception e) {
            globalExceptionHandler.handle(e, event);
        }


    }
}
