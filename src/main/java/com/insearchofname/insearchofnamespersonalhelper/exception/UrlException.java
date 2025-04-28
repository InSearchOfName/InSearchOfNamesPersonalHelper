package com.insearchofname.insearchofnamespersonalhelper.exception;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class UrlException extends DiscordException {
    public UrlException(String message, SlashCommandInteractionEvent event) {
        super(message, event);
    }
}
