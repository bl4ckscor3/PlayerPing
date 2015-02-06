package bl4ckscor3.plugin.playerping.commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import bl4ckscor3.plugin.playerping.core.PlayerPing;

public class CMDPlayerPing
{
	public static void exe(Plugin plugin, Player p, String[] args) throws IOException
	{
		if(args[0].equalsIgnoreCase("reload"))
		{
			if(p.hasPermission("playerping.reload"))
			{
				plugin.reloadConfig();
				p.sendMessage("[" + ChatColor.BLUE + plugin.getDescription().getName() + ChatColor.RESET + "] Reloaded configuration successfully.");
				return;
			}
		}
		else if(args[0].equalsIgnoreCase("toggle")) 
		{
			if(args.length == 1) //toggle the whole feature
			{
				if(p.hasPermission("playerping.toggle.all"))
				{
					File f = new File(plugin.getDataFolder(), "playerStorage/" + p.getUniqueId() +".yml");
					YamlConfiguration player = YamlConfiguration.loadConfiguration(f);

					player.set("name", p.getName()); //also change name value incase the player changed his name

					//switch around value and send player a message
					if(!player.getBoolean("toggle.all"))
					{
						player.set("toggle.all", true);
						p.sendMessage("[" + ChatColor.BLUE + plugin.getDescription().getName() + ChatColor.RESET + "] You turned chat notifications " + ChatColor.GREEN + "ON" + ChatColor.RESET + ".");
					}
					else
					{
						player.set("toggle.all", false);
						p.sendMessage("[" + ChatColor.BLUE + plugin.getDescription().getName() + ChatColor.RESET + ChatColor.RESET + "] You turned chat notifications " + ChatColor.RED + "OFF" + ChatColor.RESET + ".");
					}

					player.save(f);
					return;
				}
			}

			if(args[1].equals("sound")) //only toggle sound
			{
				if(p.hasPermission("playerping.toggle.sound"))
				{
					File f = new File(plugin.getDataFolder(), "/playerStorage/" + p.getUniqueId() +".yml");
					YamlConfiguration player = YamlConfiguration.loadConfiguration(f);

					player.set("name", p.getName()); //also change name value incase the player changed his name

					if(!player.getBoolean("toggle.sound"))
					{
						player.set("toggle.sound", true);
						p.sendMessage("[" + ChatColor.BLUE + plugin.getDescription().getName() + ChatColor.RESET + "] You turned chat notification sound " + ChatColor.GREEN + "ON" + ChatColor.RESET + ".");
					}
					else
					{
						player.set("toggle.sound", false);
						p.sendMessage("[" + ChatColor.BLUE + plugin.getDescription().getName() + ChatColor.RESET + "] You turned chat notification sound " + ChatColor.RED + "OFF" + ChatColor.RESET + ".");
					}

					player.save(f);
					return;
				}
			}
			else if(args[1].equalsIgnoreCase("highlight"))
			{
				if(p.hasPermission("playerping.toggle.highlight"))
				{
					File f = new File(plugin.getDataFolder(), "/playerStorage/" + p.getUniqueId() +".yml");
					YamlConfiguration player = YamlConfiguration.loadConfiguration(f);

					player.set("name", p.getName()); //also change name value incase the player changed his name

					if(!player.getBoolean("toggle.highlight"))
					{
						player.set("toggle.highlight", true);
						p.sendMessage("[" + ChatColor.BLUE + plugin.getDescription().getName() + ChatColor.RESET + "] You turned chat highlighting " + ChatColor.GREEN + "ON" + ChatColor.RESET + ".");
					}
					else
					{
						player.set("toggle.highlight", false);
						p.sendMessage("[" + ChatColor.BLUE + plugin.getDescription().getName() + ChatColor.RESET + "] You turned chat highlighting " + ChatColor.RED + "OFF" + ChatColor.RESET + ".");
					}

					player.save(f);
					return;
				}
			}
		}
	}
}
