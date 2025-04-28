package com.insearchofname.insearchofnamespersonalhelper.exception;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class DiscordException extends RuntimeException {
    private final SlashCommandInteractionEvent event;

    public DiscordException(String message, SlashCommandInteractionEvent event) {
        super(message);
        this.event = event;
    }

    public SlashCommandInteractionEvent getEvent() {
        return event;
    }
}
