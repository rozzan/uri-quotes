package me.rozza.uriquotes;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class UriQuotesPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(UriQuotesPlugin.class);
		RuneLite.main(args);
	}
}