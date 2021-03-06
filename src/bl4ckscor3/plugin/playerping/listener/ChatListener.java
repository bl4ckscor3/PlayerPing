package bl4ckscor3.plugin.playerping.listener;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;

import bl4ckscor3.plugin.bl4ckkitCore.core.bl4ckkitCore;
import bl4ckscor3.plugin.bl4ckkitCore.exception.PluginNotInstalledException;

public class ChatListener implements Listener
{
	private Plugin plugin;
	
	public ChatListener(Plugin p)
	{
		plugin = p;
	}
	
	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event) throws IOException, PluginNotInstalledException
	{
		List<Player> onlinePlayerObjects = Arrays.asList(Bukkit.getServer().getOnlinePlayers());
		User u = ((Essentials)bl4ckkitCore.getPluginManager().getPlugin(plugin, "Essentials")).getUser(event.getPlayer());
		
		if(u.getMuted())
			return;
		
		for(Player p : onlinePlayerObjects)
		{
			if(p.getName().equalsIgnoreCase(event.getPlayer().getName()))
				continue;
		
			File f = new File(plugin.getDataFolder(), "playerStorage/" + p.getUniqueId() +".yml");
			YamlConfiguration yaml = YamlConfiguration.loadConfiguration(f);
			List<String> alias = yaml.getStringList("alias");
			
			for(String s : alias)
			{
				if(event.getMessage().contains(s))
				{
					int arrayPosition = getPlayerArrayPosition(p.getName(), onlinePlayerObjects);
			
					if(arrayPosition == -1)
						return;
					
					if(((Essentials)bl4ckkitCore.getPluginManager().getPlugin(plugin, "Essentials")).getUser(p).isIgnoredPlayer(event.getPlayer().getName()))
						return;
					
					if(yaml.getBoolean("toggle.all"))
					{
						if(yaml.getBoolean("toggle.highlight"))
						{
							event.getRecipients().remove(onlinePlayerObjects.get(arrayPosition));
							p.sendMessage(plugin.getConfig().getString("name.prefix").replace("&", "\u00A7") + event.getPlayer().getDisplayName() + plugin.getConfig().getString("name.suffix").replace("&", "\u00A7") + space() + event.getMessage().replaceAll("(?i)" + s, plugin.getConfig().getString("name.color").replace("&", "\u00A7") + s + ChatColor.RESET));
						}
					
						if(yaml.getBoolean("toggle.sound"))
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "playsound " + plugin.getConfig().getString("sound.play") + " " + p.getName() + " ~0 ~0 ~0 " + plugin.getConfig().getDouble("sound.volume") + " " + plugin.getConfig().getDouble("sound.pitch"));
					}
					
					if(((Essentials)bl4ckkitCore.getPluginManager().getPlugin(plugin, "Essentials")).getUser(p).isAfk())
						bl4ckkitCore.getMessageManager().sendChatMessage(event.getPlayer(), plugin, "This player is currently afk and may not respond.");
				}
			}
		}
	}

	private int getPlayerArrayPosition(String name, List<Player> onlinePlayerObjects)
	{
		int i = 0;
	
		for(Player p : onlinePlayerObjects)
		{
			if(p.getName().equals(name))
				return i;

			i++;
		}
	
		return -1;
	}
	
	private String space()
	{
		if(plugin.getConfig().getBoolean("name.space"))
			return " ";
		else
			return "";
	}
}
