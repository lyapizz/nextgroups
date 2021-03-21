package com.lyapizz.cutshot.nextgroups.utils;

import java.io.IOException;
import java.io.InputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HttpUtils {

    public static Document readFromLink(String link) throws IOException {
        return Jsoup.connect(link)
                .userAgent("Mozilla")
                .timeout(3000)
                .post();
    }

}
