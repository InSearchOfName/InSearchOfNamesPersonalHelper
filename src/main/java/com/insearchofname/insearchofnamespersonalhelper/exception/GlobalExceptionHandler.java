package com.insearchofname.insearchofnamespersonalhelper.exception;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.stereotype.Component;

@Component
public class GlobalExceptionHandler {

    public void handle(Exception ex, SlashCommandInteractionEvent event) {
        if (ex instanceof UrlException) {
            handleUrlException((UrlException) ex);
        } else if (ex instanceof DownloadException) {
            handleDownloadException((DownloadException) ex);
        } else {
            handleGenericException(ex, event);
        }
    }

    private void handleUrlException(UrlException ex) {
        ex.getEvent().getHook().sendMessage("Invalid URL: " + ex.getMessage()).setEphemeral(true).queue();
    }

    private void handleDownloadException(DownloadException ex) {
        ex.getEvent().getHook().sendMessage("Download failed: " + ex.getMessage()).setEphemeral(true).queue();
    }

    private void handleGenericException(Exception ex, SlashCommandInteractionEvent event) {
        event.getHook().sendMessage("An unexpected error occurred: " + ex.getMessage()).setEphemeral(true).queue();
        ex.printStackTrace();
    }
}
