package bl4ckscor3.plugin.playerping.core;

import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import bl4ckscor3.plugin.playerping.commands.CMDPlayerPing;
import bl4ckscor3.plugin.playerping.commands.PlayerLoginListener;
import bl4ckscor3.plugin.playerping.listener.ChatListener;

public class PlayerPing extends JavaPlugin
{
	@Override
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(new PlayerLoginListener(this), this);
		getServer().getPluginManager().registerEvents(new ChatListener(this), this);
		Config.createConfig(this);
		System.out.println("[" + getDescription().getName() + "] v" + getDescription().getVersion() + " enabled!");
	}

	@Override
	public void onDisable()
	{
		System.out.println("[" + getDescription().getName() + "] v" + getDescription().getVersion() + " disabled!");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		Player p = null;

		if(sender instanceof Player)
			p = (Player)sender;

		if(cmd.getName().equalsIgnoreCase("playerping"))
		{
			if(args.length == 0 || args.length > 2)
				return false;
			
			try
			{
				CMDPlayerPing.exe(this, p, args);
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			
			return true;
		}
		return false;
	}
}
