package bl4ckscor3.plugin.playerping.core;

import org.bukkit.plugin.java.JavaPlugin;

import bl4ckscor3.plugin.playerping.listener.ChatListener;
import bl4ckscor3.plugin.playerping.listener.GroupManagerHook;

public class PlayerPing extends JavaPlugin
{
	@Override
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(new ChatListener(), this);
		getServer().getPluginManager().registerEvents(new GroupManagerHook(this), this);
		System.out.println("[PlayerPing] v" + getDescription().getVersion() + " enabled!");
	}
	
	@Override
	public void onDisable()
	{
		System.out.println("[PlayerPing] v" + getDescription().getVersion() + " disabled!");
	}
}
