package com.insearchofname.insearchofnamespersonalhelper.exception;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class DownloadException extends DiscordException {
    public DownloadException(String message, SlashCommandInteractionEvent event) {
        super(message, event);
    }


}
