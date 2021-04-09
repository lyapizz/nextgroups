package com.lyapizz.cutshot.nextgroups.service;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private static final String CATEGORY_CLASS_NAME = "players_header_main";
    private static final String LINK_CLASS_MAME = "link-E";

    public List<String> findCategories(Document doc) {
        List<String> categories = new ArrayList<>();
        for (Element categoryDiv : doc.getElementsByClass(CATEGORY_CLASS_NAME)) {
            for (Element child : categoryDiv.getElementsByClass(LINK_CLASS_MAME)) {
                categories.add(child.id());
            }
        }
        return categories;
    }

}
