package me.rozza.uriquotes.retrievers;

import lombok.extern.slf4j.Slf4j;
import me.rozza.uriquotes.UriQuotesConfig;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class TextBoxQuoteRetriever implements QuoteRetriever {

    @Override
    public List<String> loadQuotes(UriQuotesConfig config) {
        return Arrays.stream(config.quotes().split("\n")).collect(Collectors.toList());
    }
}
