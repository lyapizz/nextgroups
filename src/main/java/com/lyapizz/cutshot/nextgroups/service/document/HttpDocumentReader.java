package com.lyapizz.cutshot.nextgroups.service.document;

import java.io.IOException;

import com.lyapizz.cutshot.nextgroups.utils.HttpUtils;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Component
public class HttpDocumentReader implements DocumentReader {

    @Override
    public Document read(String url) throws IOException {
        return HttpUtils.readFromLink(url);
    }
}
