package me.rozza.uriquotes.retrievers;

import me.rozza.uriquotes.UriQuotesConfig;

import java.util.List;

public interface QuoteRetriever {

    List<String> loadQuotes(UriQuotesConfig config);
}
