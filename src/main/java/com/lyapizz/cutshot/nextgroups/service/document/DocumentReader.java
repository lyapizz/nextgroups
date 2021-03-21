package com.lyapizz.cutshot.nextgroups.service.document;

import java.io.IOException;

import org.jsoup.nodes.Document;

public interface DocumentReader {

    public Document read(String url) throws IOException;
}
