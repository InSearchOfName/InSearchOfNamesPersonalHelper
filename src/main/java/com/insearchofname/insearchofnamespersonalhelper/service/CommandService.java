package com.insearchofname.insearchofnamespersonalhelper.service;


import com.insearchofname.insearchofnamespersonalhelper.exception.DownloadException;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.FileUpload;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;


@Service
public class CommandService {

    public String handlePingCommand(SlashCommandInteractionEvent event) {
        long delayMs = Duration.between(event.getTimeCreated().toInstant(), Instant.now()).toMillis();
        return "Delay: " + delayMs + "ms";
    }
    //TODO add a compression mechanic so it actually downscales the size but for now not
    //TODO check the file size before download so it doesnt waste unnescarry download and processing
    public Collection<? extends FileUpload> handleVideoDownloadCommand(SlashCommandInteractionEvent event) {
        String filename = UUID.randomUUID() + ".mp4";

        try {
            ProcessBuilder builder = new ProcessBuilder(
                    "yt-dlp",
                    "-f", "best",
                    "-o", filename,
                    Objects.requireNonNull(event.getOption("url")).getAsString()
            );

            builder.redirectErrorStream(true);
            Process process = builder.start();
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new DownloadException("The download failed, exitCode: " + exitCode, event);
            }


        } catch (Exception e) {
            throw new DownloadException("Error: " + e.getMessage(), event);
        }

        File file = new File(filename);
        if (8 < file.length() / (1024.0 * 1024.0)) {
            throw new DownloadException("The file is too large, please download a shorter video", event);
        } else {
            return Collections.singletonList(FileUpload.fromData(file));
        }
    }

}
