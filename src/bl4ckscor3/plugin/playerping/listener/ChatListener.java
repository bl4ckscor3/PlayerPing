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

import bl4ckscor3.plugin.playerping.core.PlayerPing;

public class ChatListener implements Listener
{
	private Plugin plugin;

	public ChatListener(Plugin p)
	{
		plugin = p;
	}

	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event) throws IOException
	{
		List<Player> onlinePlayerObjects = Arrays.asList(Bukkit.getServer().getOnlinePlayers());

		for(Player p : onlinePlayerObjects)
		{
			String currentPlayerName = p.getName();
			String lowerCasePlayerName = currentPlayerName.toLowerCase();

			if(event.getMessage().toLowerCase().contains(lowerCasePlayerName) && !event.getPlayer().getName().equalsIgnoreCase(currentPlayerName))
			{
				int arrayPosition = getPlayerArrayPosition(currentPlayerName, onlinePlayerObjects);

				if(arrayPosition == -1)
					return;

				File folder = new File(plugin.getDataFolder(), "playerStorage");
				File f = new File(plugin.getDataFolder(), "playerStorage/" + p.getUniqueId() +".yml");
				YamlConfiguration player = null;

				if(!folder.exists() || !f.exists())
					PlayerPing.setupPlayerFile(player, f, folder, p);

				player = YamlConfiguration.loadConfiguration(f);

				if(player.getBoolean("toggle.all"))
				{
					if(player.getBoolean("toggle.highlight"))
					{
						event.getRecipients().remove(onlinePlayerObjects.get(arrayPosition));
						//TODO: Make name colored if it is not written correctly cased
						p.sendMessage(plugin.getConfig().getString("name.prefix").replace("&", "\u00A7") + event.getPlayer().getDisplayName() + plugin.getConfig().getString("name.suffix").replace("&", "\u00A7") + space() + event.getMessage().replaceAll(currentPlayerName, plugin.getConfig().getString("name.color").replace("&", "\u00A7") + currentPlayerName + ChatColor.RESET));
					}

					if(player.getBoolean("toggle.sound"))
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "playsound " + plugin.getConfig().getString("sound.play") + " " + currentPlayerName + " ~0 ~0 ~0 " + plugin.getConfig().getDouble("sound.volume") + " " + plugin.getConfig().getDouble("sound.pitch"));
				}
				return;
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
