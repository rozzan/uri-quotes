package me.rozza.uriquotes.retrievers;

import lombok.extern.slf4j.Slf4j;
import me.rozza.uriquotes.UriQuotesConfig;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class URLQuoteRetriever implements QuoteRetriever {

    private final HttpClient client = HttpClient.newHttpClient();

    @Override
    public List<String> loadQuotes(UriQuotesConfig config) {
        List<String> quotes = new ArrayList<>();

        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(config.url()))
                .GET()
                .build();
            HttpResponse<String> string = client.send(request, HttpResponse.BodyHandlers.ofString());
            quotes.addAll(Arrays.stream(string.body().split("\n")).collect(Collectors.toList()));
        }
        catch (Exception ex) {
            log.error("Error in fetching quotes from URL!", ex);
        }

        return quotes;
    }
}
