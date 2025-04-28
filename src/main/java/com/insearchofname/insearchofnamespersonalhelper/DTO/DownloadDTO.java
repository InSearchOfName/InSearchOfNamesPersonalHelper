package com.insearchofname.insearchofnamespersonalhelper.DTO;

import net.dv8tion.jda.api.utils.FileUpload;

import java.io.File;
import java.util.Collection;

public record DownloadDTO(Collection<? extends FileUpload> uploads, File file) {
}

