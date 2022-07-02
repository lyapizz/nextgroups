package com.lyapizz.cutshot.nextgroups.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.lyapizz.cutshot.nextgroups.utils.FileUtils;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

class CategoryServiceTest {

    CategoryService categoryService = new CategoryService();

    @Test
    void findCategories_positive() throws IOException {
        Document document = FileUtils.readFromFile("/white_rabbit_whole.html");

        Map<Integer, String> result = categoryService.findCategories(document);

        assertEquals(Map.of(0, "MEN LIGHT",1,  "WOMEN LIGHT"), result);
    }
}