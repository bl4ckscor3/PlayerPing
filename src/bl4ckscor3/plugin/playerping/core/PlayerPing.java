package bl4ckscor3.plugin.playerping.core;

import org.bukkit.plugin.java.JavaPlugin;

import bl4ckscor3.plugin.playerping.listener.ChatListener;

public class PlayerPing extends JavaPlugin
{
	@Override
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(new ChatListener(this), this);
		Config.createConfig(this);
		System.out.println("[PlayerPing] v" + getDescription().getVersion() + " enabled!");
	}
	
	@Override
	public void onDisable()
	{
		System.out.println("[PlayerPing] v" + getDescription().getVersion() + " disabled!");
	}
}
