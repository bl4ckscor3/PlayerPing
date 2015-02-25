package bl4ckscor3.plugin.playerping.commands;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

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
					toggleAll(p, plugin);
					return;
				}
			}

			if(args[1].equals("sound")) //only toggle sound
			{
				if(p.hasPermission("playerping.toggle.sound"))
				{
					toggleSound(p, plugin);
					return;
				}
			}
			else if(args[1].equalsIgnoreCase("highlight"))
			{
				if(p.hasPermission("playerping.toggle.highlight"))
				{
					toggleHighlight(p, plugin);
					return;
				}
			}
			return;
		}
		else if(args[0].equalsIgnoreCase("add"))
		{
			if(args.length != 2)
			{
				p.sendMessage("[" + ChatColor.BLUE + plugin.getDescription().getName() + ChatColor.RESET + "] You need to specify a name to work with.");
				return;
			}

			addAlias(p, plugin, args);
			return;
		}
		else if(args[0].equalsIgnoreCase("remove"))
		{
			if(args.length != 2)
			{
				p.sendMessage("[" + ChatColor.BLUE + plugin.getDescription().getName() + ChatColor.RESET + "] You need to specify a name to work with.");
				return;
			}

			removeAlias(p, plugin, args);
			return;
		}
		else if(args[0].equalsIgnoreCase("list"))
		{
			listAlias(p, plugin);
			return;
		}
	}

	/*
	 * COMMAND EXECUTOR METHODS
	 */

	private static void toggleAll(Player p, Plugin plugin) throws IOException
	{
		File f = new File(plugin.getDataFolder(), "playerStorage/" + p.getUniqueId() + ".yml");
		YamlConfiguration yaml = YamlConfiguration.loadConfiguration(f);
		
		yaml.set("name", p.getName()); //also change name value incase the player changed his name

		//switch around value and send player a message
		if(!yaml.getBoolean("toggle.all"))
		{
			yaml.set("toggle.all", true);
			p.sendMessage("[" + ChatColor.BLUE + plugin.getDescription().getName() + ChatColor.RESET + "] You turned chat notifications " + ChatColor.GREEN + "ON" + ChatColor.RESET + ".");
		}
		else
		{
			yaml.set("toggle.all", false);
			p.sendMessage("[" + ChatColor.BLUE + plugin.getDescription().getName() + ChatColor.RESET + ChatColor.RESET + "] You turned chat notifications " + ChatColor.RED + "OFF" + ChatColor.RESET + ".");
		}

		yaml.save(f);
	}

	private static void toggleSound(Player p, Plugin plugin) throws IOException
	{
		File f = new File(plugin.getDataFolder(), "/playerStorage/" + p.getUniqueId() + ".yml");
		YamlConfiguration yaml = YamlConfiguration.loadConfiguration(f);
		
		yaml.set("name", p.getName()); //also change name value incase the player changed his name

		if(!yaml.getBoolean("toggle.sound"))
		{
			yaml.set("toggle.sound", true);
			p.sendMessage("[" + ChatColor.BLUE + plugin.getDescription().getName() + ChatColor.RESET + "] You turned chat notification sound " + ChatColor.GREEN + "ON" + ChatColor.RESET + ".");
		}
		else
		{
			yaml.set("toggle.sound", false);
			p.sendMessage("[" + ChatColor.BLUE + plugin.getDescription().getName() + ChatColor.RESET + "] You turned chat notification sound " + ChatColor.RED + "OFF" + ChatColor.RESET + ".");
		}

		yaml.save(f);
	}

	private static void toggleHighlight(Player p, Plugin plugin) throws IOException
	{
		File f = new File(plugin.getDataFolder(), "/playerStorage/" + p.getUniqueId() + ".yml");
		YamlConfiguration yaml = YamlConfiguration.loadConfiguration(f);
		
		yaml.set("name", p.getName()); //also change name value incase the player changed his name

		if(!yaml.getBoolean("toggle.highlight"))
		{
			yaml.set("toggle.highlight", true);
			p.sendMessage("[" + ChatColor.BLUE + plugin.getDescription().getName() + ChatColor.RESET + "] You turned chat highlighting " + ChatColor.GREEN + "ON" + ChatColor.RESET + ".");
		}
		else
		{
			yaml.set("toggle.highlight", false);
			p.sendMessage("[" + ChatColor.BLUE + plugin.getDescription().getName() + ChatColor.RESET + "] You turned chat highlighting " + ChatColor.RED + "OFF" + ChatColor.RESET + ".");
		}

		yaml.save(f);
	}

	private static void addAlias(Player p, Plugin plugin, String[] args) throws IOException
	{
		File f = new File(plugin.getDataFolder(), "/playerStorage/" + p.getUniqueId() + ".yml");
		YamlConfiguration yaml = YamlConfiguration.loadConfiguration(f);;
		List<String> alias;

		alias = (ArrayList<String>)yaml.getStringList("alias");

		if(alias.contains(args[1]))
		{
			p.sendMessage("[" + ChatColor.BLUE + plugin.getDescription().getName() + ChatColor.RESET + "] This name was already in your alias list. Use /pp list to see which ones you've added.");
			return;
		}

		alias.add(args[1]);
		yaml.set("alias", alias);
		yaml.save(f);
		p.sendMessage("[" + ChatColor.BLUE + plugin.getDescription().getName() + ChatColor.RESET + "] \"" + args[1] + "\" was successfully added to your alias list.");
	}

	private static void removeAlias(Player p, Plugin plugin, String[] args) throws IOException
	{
		if(args[1].equals(p.getName()))
		{
			p.sendMessage("[" + ChatColor.BLUE + plugin.getDescription().getName() + ChatColor.RESET + "] You cannot remove your name from the list. To turn off the plugin, use /pp toggle.");
			return;
		}

		File f = new File(plugin.getDataFolder(), "/playerStorage/" + p.getUniqueId() + ".yml");
		YamlConfiguration yaml = YamlConfiguration.loadConfiguration(f);
		List<String> alias;

		alias = (ArrayList<String>)yaml.getStringList("alias");

		if(!alias.remove(args[1]))
		{
			p.sendMessage("[" + ChatColor.BLUE + plugin.getDescription().getName() + ChatColor.RESET + "] This name was not in your alias list. Use /pp list to see which ones you've added.");
			return;
		}

		yaml.set("alias", alias);
		yaml.save(f);
		p.sendMessage("[" + ChatColor.BLUE + plugin.getDescription().getName() + ChatColor.RESET + "] \"" + args[1] + "\" was successfully removed from your alias list.");
	}

	private static void listAlias(Player p, Plugin plugin) throws IOException
	{
		File f = new File(plugin.getDataFolder(), "/playerStorage/" + p.getUniqueId() + ".yml");
		YamlConfiguration player = YamlConfiguration.loadConfiguration(f);
		List<String> alias;
		String words = "";
		
		player = YamlConfiguration.loadConfiguration(f);
		alias = (ArrayList<String>)player.getStringList("alias");

		for(String s : alias)
		{
			words += ChatColor.GRAY + s + ChatColor.GRAY + ", ";
		}

		p.sendMessage("[" + ChatColor.BLUE + plugin.getDescription().getName() + ChatColor.RESET + "] You will get notified of these words: " + words.substring(0, words.length() - 2));
	}
}
