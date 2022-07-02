package com.lyapizz.cutshot.nextgroups.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private static final String CATEGORY_CLASS_NAME = "players_header_main";
    private static final String LINK_CLASS_MAME = "link-E";

    public Map<Integer, String> findCategories(Document doc) {
        Map<Integer, String> categories = new HashMap<>();
        int curKeyIndex = 0;
        for (Element categoryDiv : doc.getElementsByClass(CATEGORY_CLASS_NAME)) {
            for (Element child : categoryDiv.getElementsByClass(LINK_CLASS_MAME)) {
                categories.put(curKeyIndex++, child.id());
            }
        }
        return categories;
    }

}
