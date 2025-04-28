package com.insearchofname.insearchofnamespersonalhelper.exception;

import com.insearchofname.insearchofnamespersonalhelper.util.UrlUtil;
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
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Please keep in mind this bot CANNOT download images \n");
        stringBuilder.append("Please use one of the following urls: \n");
        stringBuilder.append(">>> ");
        for (String url : UrlUtil.getURLS()){
            stringBuilder.append("<").append(url).append("> ");
            stringBuilder.append("\n");
        }
        ex.getEvent().getHook().sendMessage(stringBuilder.toString()).setEphemeral(true).queue();
    }

    private void handleDownloadException(DownloadException ex) {
        ex.getEvent().getHook().sendMessage("Download failed: " + ex.getMessage()).setEphemeral(true).queue();
    }

    private void handleGenericException(Exception ex, SlashCommandInteractionEvent event) {
        event.getHook().sendMessage("An unexpected error occurred: " + ex.getMessage()).setEphemeral(true).queue();
        ex.printStackTrace();
    }
}
