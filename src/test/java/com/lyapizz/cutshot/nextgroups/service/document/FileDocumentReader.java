package com.lyapizz.cutshot.nextgroups.service.document;

import java.io.IOException;

import com.lyapizz.cutshot.nextgroups.utils.FileUtils;
import org.jsoup.nodes.Document;

public class FileDocumentReader implements DocumentReader{

    @Override
    public Document read(String url) throws IOException {
        return FileUtils.readFromFile(url);
    }
}
