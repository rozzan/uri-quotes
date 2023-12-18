package me.rozza.uriquotes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.rozza.uriquotes.retrievers.*;

@AllArgsConstructor
@Getter
public enum QuotesSource {
    TEXTBOX(TextBoxQuoteRetriever.class),
    URL(URLQuoteRetriever.class);

    private final Class<? extends QuoteRetriever> retrieverClass;
}
