package com.insearchofname.insearchofnamespersonalhelper.service;


import com.insearchofname.insearchofnamespersonalhelper.DTO.DownloadDTO;
import com.insearchofname.insearchofnamespersonalhelper.exception.UrlException;
import com.insearchofname.insearchofnamespersonalhelper.util.UrlUtil;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.FileUpload;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.Objects;


@Service
public class CommandService {
    private final VideoService videoService;

    public CommandService(VideoService videoService) {
        this.videoService = videoService;
    }

    public String handlePingCommand(SlashCommandInteractionEvent event) {
        long delayMs = Duration.between(event.getTimeCreated().toInstant(), Instant.now()).toMillis();
        return "Delay: " + delayMs + "ms";
    }

    public DownloadDTO handleVideoDownloadCommand(SlashCommandInteractionEvent event) {
        String url = Objects.requireNonNull(event.getOption("url")).getAsString();
        if (UrlUtil.isValidUrl(url) || UrlUtil.isDownloadableUrl(url)) {
            File downloadFile = videoService.downloadVideo(event);
            File compressedFile = videoService.compressFile(downloadFile, event);
            return new DownloadDTO(Collections.singletonList(FileUpload.fromData(compressedFile)), compressedFile);
        } else {
            throw new UrlException("Not a valid url", event);
        }
    }


}
