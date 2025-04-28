package com.insearchofname.insearchofnamespersonalhelper.util;

import java.net.URL;

public class UrlUtil {

    public static boolean isValidUrl(String urlString) {
        try {
            new URL(urlString).toURI(); // Attempt to create a URL object
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
