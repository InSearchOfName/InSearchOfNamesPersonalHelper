package com.insearchofname.insearchofnamespersonalhelper.exception;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class CompressionException extends DiscordException {
    public CompressionException(String message, SlashCommandInteractionEvent event) {
        super(message, event);
    }
}
