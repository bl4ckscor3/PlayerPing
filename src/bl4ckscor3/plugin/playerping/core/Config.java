package bl4ckscor3.plugin.playerping.core;

import org.bukkit.plugin.Plugin;

public class Config
{
	public static void createConfig(Plugin plugin)
	{
		plugin.reloadConfig();
		plugin.getConfig().options().header("Add the symbol(s) you want to prepend to a player's name under name.prefix, and the ones you like to append under name.suffix. Both are strings.\nE.g.: The vanilla chat format is \"<playerName>\". In this case, name.prefix would default to '<' and name.suffix would default to '>'.\nA space between the suffix and the message is automatically added.\nIf you don't want that to happen, setDefault \"space\" to \"false\"");
		plugin.getConfig().addDefault("name.prefix", "<");
		plugin.getConfig().addDefault("name.suffix", ">");
		plugin.getConfig().addDefault("space", true);
		plugin.getConfig().options().copyDefaults(true);
		plugin.saveConfig();
		System.out.println("[PlayerPing] Configuration created/enabled!");
	}
}
