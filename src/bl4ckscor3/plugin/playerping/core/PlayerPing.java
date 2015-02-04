package bl4ckscor3.plugin.playerping.core;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		Player p = null;

		if(sender instanceof Player)
			p = (Player)sender;

		if(cmd.getName().equalsIgnoreCase("playerpingreload"))
		{
			if(p.hasPermission("playerping.reload"))
			{
				reloadConfig();
				p.sendMessage("[" + ChatColor.BLUE + getDescription().getName() + ChatColor.RESET + "] Reloaded configuration successfully.");
				return true;
			}
		}
		return false;
	}
}
