package com.lyapizz.cutshot.nextgroups.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;

import com.lyapizz.cutshot.nextgroups.utils.FileUtils;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

class CategoryServiceTest {

    CategoryService categoryService = new CategoryService();

    @Test
    void findCategories_positive() throws IOException {
        Document document = FileUtils.readFromFile("/white_rabbit_whole.html");

        List<String> result = categoryService.findCategories(document);

        assertEquals(List.of("MEN LIGHT", "WOMEN LIGHT"), result);
    }
}