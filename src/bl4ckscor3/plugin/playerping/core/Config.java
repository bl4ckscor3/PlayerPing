package bl4ckscor3.plugin.playerping.core;

import org.bukkit.plugin.Plugin;

public class Config
{
	public static void createConfig(Plugin plugin)
	{
		plugin.reloadConfig();
		plugin.getConfig().options().header("Add the symbol(s) you want to prepend to a player's name under \"name.prefix\", and the ones you like to append under \"name.suffix\". Both are strings.\n"
				+ "E.g.: The vanilla chat format is \"<playerName>\". In this case, \"name.prefix\" would default to '<' and \"name.suffix\" would default to '>'.\n"
				+ "A space between the suffix and the message is automatically added. If you don't want that to happen, set \"name.space\" to \"false\"\n"
				+ "To modify the color of the highlighted name within the chat, change the value of \"name.color\" to a different color from &0-9 or &0-f.\n"
				+ "Bold = &l, strikethrough = &m, underlined = &n and italics = &o. You can also combine these and the colors.\n"
				+ "\"sound.play\" defines which sound is played to the player if they are mentioned in the chat.\n"
				+ "\"sound.volume is the volume of the played sound. \"1.0\" defines the client volume.\n"
				+ "\"sound.pitch\" is the pitch of the played sound. 0.0 means no pitch. Higher values create a higher tone.");
		plugin.getConfig().addDefault("name.prefix", "<");
		plugin.getConfig().addDefault("name.suffix", ">");
		plugin.getConfig().addDefault("name.space", true);
		plugin.getConfig().addDefault("name.color", "&e");
		plugin.getConfig().addDefault("sound.play", "random.orb");
		plugin.getConfig().addDefault("sound.volume", 2.0D);
		plugin.getConfig().addDefault("sound.pitch", 0.0D);
		plugin.getConfig().options().copyDefaults(true);
		plugin.saveConfig();
		System.out.println("[PlayerPing] Configuration created/enabled!");
	}
}
