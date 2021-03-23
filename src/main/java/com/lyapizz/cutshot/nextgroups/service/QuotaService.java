package com.lyapizz.cutshot.nextgroups.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

@Service
public class QuotaService {

    private static final String QUOTES_CLASS_NAME = "players_header_main_span";

    private final Pattern pattern = Pattern.compile(".*КВОТА: (\\d+).*");

    public List<Integer> findQuotes(Document doc) {
        List<Integer> quotes = new ArrayList<>();
        for (Element quotesSpan : doc.getElementsByClass(QUOTES_CLASS_NAME)) {
            String quotesSpanText = quotesSpan.text();

            Matcher matcher = pattern.matcher(quotesSpanText);
            if (matcher.matches()) {
                quotes.add(Integer.parseInt(matcher.group(1)));
            }

        }
        return quotes;
    }

}
