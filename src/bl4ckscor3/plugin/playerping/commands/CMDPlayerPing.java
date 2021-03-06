package bl4ckscor3.plugin.playerping.commands;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import bl4ckscor3.plugin.bl4ckkitCore.core.bl4ckkitCore;

public class CMDPlayerPing
{
	public static void exe(Plugin plugin, Player p, String[] args) throws IOException
	{
		if(args[0].equalsIgnoreCase("reload"))
		{
			if(p.hasPermission("playerping.reload"))
			{
				plugin.reloadConfig();
				bl4ckkitCore.getMessageManager().sendChatMessage(p, plugin, "Reloaded configuration successfully.");
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
				bl4ckkitCore.getMessageManager().sendChatMessage(p, plugin, "You need to specify a name to work with.");
				return;
			}

			addAlias(p, plugin, args);
			return;
		}
		else if(args[0].equalsIgnoreCase("remove"))
		{
			if(args.length != 2)
			{
				bl4ckkitCore.getMessageManager().sendChatMessage(p, plugin, "You need to specify a name to work with.");
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
			bl4ckkitCore.getMessageManager().sendChatMessage(p, plugin, "You turned chat notifications " + ChatColor.GREEN + "ON" + ChatColor.RESET + ".");
		}
		else
		{
			yaml.set("toggle.all", false);
			bl4ckkitCore.getMessageManager().sendChatMessage(p, plugin, "You turned chat notifications " + ChatColor.RED + "OFF" + ChatColor.RESET + ".");
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
			bl4ckkitCore.getMessageManager().sendChatMessage(p, plugin, "You turned chat notification sound " + ChatColor.GREEN + "ON" + ChatColor.RESET + ".");
		}
		else
		{
			yaml.set("toggle.sound", false);
			bl4ckkitCore.getMessageManager().sendChatMessage(p, plugin, "You turned chat notification sound " + ChatColor.RED + "OFF" + ChatColor.RESET + ".");
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
			bl4ckkitCore.getMessageManager().sendChatMessage(p, plugin, "You turned chat highlighting " + ChatColor.GREEN + "ON" + ChatColor.RESET + ".");
		}
		else
		{
			yaml.set("toggle.highlight", false);
			bl4ckkitCore.getMessageManager().sendChatMessage(p, plugin, "You turned chat highlighting " + ChatColor.GREEN + "ON" + ChatColor.RESET + ".");
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
			bl4ckkitCore.getMessageManager().sendChatMessage(p, plugin, "\"" + args[1] + "\" was already in your alias list. Use /pp list to see which ones you've added.");
			return;
		}

		alias.add(args[1]);
		yaml.set("alias", alias);
		yaml.save(f);
		bl4ckkitCore.getMessageManager().sendChatMessage(p, plugin, "\"" + args[1] + "\" was successfully added to your alias list.");
	}

	private static void removeAlias(Player p, Plugin plugin, String[] args) throws IOException
	{
		if(args[1].equals(p.getName()))
		{
			bl4ckkitCore.getMessageManager().sendChatMessage(p, plugin, "You cannot remove your own name from the list. To turn off the plugin, use /pp toggle.");
			return;
		}

		File f = new File(plugin.getDataFolder(), "/playerStorage/" + p.getUniqueId() + ".yml");
		YamlConfiguration yaml = YamlConfiguration.loadConfiguration(f);
		List<String> alias;

		alias = (ArrayList<String>)yaml.getStringList("alias");

		if(!alias.remove(args[1]))
		{
			bl4ckkitCore.getMessageManager().sendChatMessage(p, plugin, "\"" + args[1] + "\" was not in your alias list. Use /pp list to see which ones you've added.");
			return;
		}

		yaml.set("alias", alias);
		yaml.save(f);
		bl4ckkitCore.getMessageManager().sendChatMessage(p, plugin, "\"" + args[1] + "\" was successfully removed from your alias list.");
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
			words += ChatColor.RESET + s + ChatColor.GRAY + ", ";
		}

		bl4ckkitCore.getMessageManager().sendChatMessage(p, plugin, "You will get notified of these words: " + words.substring(0, words.length() - 2));
	}
}
