package me.rozza.uriquotes;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup(UriQuotesConfig.GROUP)
public interface UriQuotesConfig extends Config {

    String GROUP = "uriquotes";

    @ConfigItem(
            name = "Change message on failed step",
            keyName = "global.changeonfail",
            description = "Whether to also replace Uri's message when you talk to him with the incorrect items equipped.",
            position = 1
    )
    default boolean changeMessageOnFailedStep() {
        return false;
    }

    @ConfigItem(
            name = "Quotes source",
            keyName = "global.source",
            description = "Where should quotes be retrieved from.",
            position = 2)
    default QuotesSource quotesSource() {
        return QuotesSource.URL;
    }

    @ConfigSection(
            name = "Textbox",
            description = "Options for obtaining quotes from the config textbox.",
            position = 100,
            closedByDefault = true
    )
    String TEXTBOX_LIST = "textboxList";

    @ConfigItem(
            section = TEXTBOX_LIST,
            name = "Quotes",
            keyName = "textbox.quotes",
            description = "The quotes to use. Each quote must be on its own line.",
            position = 101
    )
    default String quotes() {
        return "";
    }

    @ConfigSection(
            name = "URL",
            description = "Options for obtaining quotes from a URL.",
            position = 200,
            closedByDefault = true
    )
    String URL_LIST = "urlList";

    @ConfigItem(
            section = URL_LIST,
            name = "URL",
            keyName = "url.url",
            description = "The URL which points to the quotes to use. Each quote must be on its own line.",
            position = 201
    )
    default String url() {
        return "";
    }
}
