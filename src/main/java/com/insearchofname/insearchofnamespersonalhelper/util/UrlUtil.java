package com.insearchofname.insearchofnamespersonalhelper.util;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class UrlUtil {

    private static final List<String> URLS = Arrays.asList(
            "https://www.youtube.com/watch?v=",
            "https://www.youtube.com/shorts",
            "https://youtube.com/shorts",
            "https://youtu.",
            "https://x.com/",
            "https://www.instagram.com/reels"
    );

    public static boolean isValidUrl(String urlString) {
        try {
            new URL(urlString).toURI(); // Attempt to create a URL object
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isDownloadableUrl(String urlString) {
        for (String url : URLS) {
            if (urlString.startsWith(url)) {
                return true;
            }
        }
        return false;
    }

    public static List<String> getURLS() {
        return URLS;
    }
}
