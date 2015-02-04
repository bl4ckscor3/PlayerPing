package bl4ckscor3.plugin.playerping.listener;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

public class ChatListener implements Listener
{
	private Plugin plugin;
	
	public ChatListener(Plugin p)
	{
		plugin = p;
	}
	
	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event)
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

				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "playsound " + plugin.getConfig().getString("sound.play") + " " + currentPlayerName + " ~0 ~0 ~0 " + plugin.getConfig().getDouble("sound.volume") + " " + plugin.getConfig().getDouble("sound.pitch"));
				event.getRecipients().remove(onlinePlayerObjects.get(arrayPosition));
				//TODO: Make name yellow if it is not written correctly cased
				p.sendMessage(plugin.getConfig().getString("name.prefix").replace("&", "\u00A7") + event.getPlayer().getDisplayName() + plugin.getConfig().getString("name.suffix").replace("&", "\u00A7") + space() + event.getMessage().replaceAll(currentPlayerName, plugin.getConfig().getString("name.color").replace("&", "\u00A7") + currentPlayerName + ChatColor.RESET));
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
