package me.rozza.uriquotes;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
	name = "Uri Quotes",
	description = "Change what Uri says when you complete an Emote Step.",
	tags = {"clue", "uri"}
)
public class UriQuotesPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private UriQuotesConfig config;

	@Override
	protected void startUp() throws Exception
	{
		log.info("Uri Quotes started!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("Uri Quotes stopped!");
	}

	@Provides
	UriQuotesConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(UriQuotesConfig.class);
	}
}
