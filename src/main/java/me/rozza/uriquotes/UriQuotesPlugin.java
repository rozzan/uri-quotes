package me.rozza.uriquotes;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import me.rozza.uriquotes.retrievers.QuoteRetriever;
import net.runelite.api.Client;
import net.runelite.api.events.*;
import net.runelite.api.widgets.ComponentID;
import net.runelite.api.widgets.InterfaceID;
import net.runelite.api.widgets.Widget;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import org.apache.commons.lang3.StringUtils;

@Slf4j
@PluginDescriptor(
	name = "Uri Quotes",
	description = "Change what Uri says when you complete an Emote Step.",
	tags = {"clue", "uri"}
)
public class UriQuotesPlugin extends Plugin {
	private static final String FAIL_TEXT = "I do not believe we have any business, Comrade.";
	private static final String NPC_NAME = "Uri";

	@Inject
	private Client client;

	@Inject
	private UriQuotesConfig config;

	private Random rng;
	private QuoteRetriever quoteRetriever;
	private List<String> quotes;
	private boolean queueUriQuote = false;

	@Override
	protected void startUp() throws Exception {
		log.info("Uri Quotes started!");
		this.rng = new Random();
		createLoadRetriever(this.config.quotesSource().name());
	}

	@Override
	protected void shutDown() throws Exception {
		this.rng = null;
		this.quoteRetriever = null;
		this.quotes = null;
		this.queueUriQuote = false;
		log.info("Uri Quotes stopped!");
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event) throws Exception {
		if (!UriQuotesConfig.GROUP.equals(event.getGroup())) {
			return;
		}
		if (!Objects.equals(event.getOldValue(), event.getNewValue()) && event.getKey().equals("global.source")) {
			createLoadRetriever(event.getNewValue());
		}
	}

	@Subscribe
	public void onWidgetLoaded(WidgetLoaded event) throws Exception {
		if (event.getGroupId() == InterfaceID.DIALOG_NPC) {
			this.queueUriQuote = true;
		}
	}

	@Subscribe
	public void onClientTick(ClientTick event) {
		if (this.queueUriQuote &&
				this.client.getWidget(ComponentID.DIALOG_NPC_NAME).getText().contains(NPC_NAME)) {
			this.queueUriQuote = false;
			handleUriDialog();
		}
	}

	private void createLoadRetriever(String enumValue) throws Exception {
		Class<? extends QuoteRetriever> retrieverClass = QuotesSource.valueOf(enumValue).getRetrieverClass();
		this.quoteRetriever = retrieverClass.getConstructor().newInstance();
	}

	private void loadQuotes() {
		this.quotes = this.quoteRetriever.loadQuotes(this.config);
		log.info(String.format("%d quote(s) loaded using %s",
				this.quotes.size(),
				this.quoteRetriever.getClass().getSimpleName()));
	}

	private void handleUriDialog() {
		Widget widget = this.client.getWidget(ComponentID.DIALOG_NPC_TEXT);
		String currentText = widget.getText();
		if (currentText.equals(FAIL_TEXT) && !this.config.changeMessageOnFailedStep()) {
			return;
		}

		loadQuotes();
		String quote = this.quotes.get(this.rng.nextInt(this.quotes.size()));
		widget.setText(quote);
		widget.setLineHeight(this.getLineHeight(quote));
	}

	private int getLineHeight(final String text)
	{
		final int count = StringUtils.countMatches(text, "<br>");

		if (count == 1) {
			return 28;
		} else if (count == 2) {
			return 20;
		}

		return 16;
	}

	@Provides
	UriQuotesConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(UriQuotesConfig.class);
	}
}
