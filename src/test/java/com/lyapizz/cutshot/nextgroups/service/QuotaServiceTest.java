package com.lyapizz.cutshot.nextgroups.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;

import com.lyapizz.cutshot.nextgroups.utils.FileUtils;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

class QuotaServiceTest {

    QuotaService quotaService = new QuotaService();

    @Test
    void findQuotes_positive() throws IOException {
        Document document = FileUtils.readFromFile("/white_rabbit_whole.html");

        List<Integer> result = quotaService.findQuotes(document);

        assertEquals(List.of(24, 16), result);
    }
}