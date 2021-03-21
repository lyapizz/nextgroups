package com.lyapizz.cutshot.nextgroups.utils;

import java.io.IOException;
import java.io.InputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class FileUtils {

    public static Document readFromFile(String fileName) throws IOException {
        InputStream fileInputStream = FileUtils.class.getResourceAsStream(fileName);
        return Jsoup.parse(fileInputStream, "UTF-8", "http://example.com/");
    }
}
