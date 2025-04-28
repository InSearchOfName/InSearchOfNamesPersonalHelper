package com.insearchofname.insearchofnamespersonalhelper.service;

import com.insearchofname.insearchofnamespersonalhelper.exception.CompressionException;
import com.insearchofname.insearchofnamespersonalhelper.exception.DownloadException;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.util.Objects;
import java.util.UUID;

@Service
public class VideoService {

    public File downloadVideo(SlashCommandInteractionEvent event) throws CompressionException {
        String originalFilename = UUID.randomUUID() + ".mp4";
        try {
            ProcessBuilder downloadBuilder = new ProcessBuilder(
                    "yt-dlp",
                    "-f", "best",
                    "-o", originalFilename,
                    Objects.requireNonNull(event.getOption("url")).getAsString()
            );
            downloadBuilder.redirectErrorStream(true);
            Process downloadProcess = downloadBuilder.start();
            int downloadExitCode = downloadProcess.waitFor();
            if (downloadExitCode != 0) {
                throw new DownloadException("The download failed, exitCode: " + downloadExitCode, event);
            }
            return new File(originalFilename);
        } catch (Exception e) {
            throw new DownloadException(e.getMessage(), event);
        }
    }

    public File compressFile(File file, SlashCommandInteractionEvent event) {
        String compressedFilename = UUID.randomUUID() + "_compressed.mp4";
        try {
            ProcessBuilder compressBuilder = new ProcessBuilder(
                    "ffmpeg",
                    "-i", file.getName(),
                    "-vcodec", "libx264",          // Use H.264 codec
                    "-crf", "28",                   // Set compression quality (lower = better quality, 23 is default, 28 is more compressed)
                    compressedFilename
            );
            compressBuilder.redirectErrorStream(true);
            Process compressProcess = compressBuilder.start();
            int compressExitCode = compressProcess.waitFor();
            if (compressExitCode != 0) {
                throw new Exception("exitCode: " + compressExitCode);
            }
            return new File(compressedFilename);
        } catch (Exception e) {
            throw new CompressionException(e.getMessage(), event);
        }
    }

    public void delete(File file) {
        try {
            Files.deleteIfExists(file.toPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
