package bl4ckscor3.plugin.playerping.listener;

import java.util.ArrayList;
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
		List<String> onlinePlayers = getOnlinePlayerNames(onlinePlayerObjects);

		for(String s : onlinePlayers)
		{
			String initialS = s;
			
			s = s.toLowerCase();
			
			if(event.getMessage().toLowerCase().contains(s) && !event.getPlayer().getName().equalsIgnoreCase(initialS))
			{
				int arrayPosition = getPlayerArrayPosition(initialS, onlinePlayerObjects);
				
				if(arrayPosition == -1)
					return;

				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "playsound " + plugin.getConfig().getString("sound.play") + " " + initialS + " ~0 ~0 ~0 " + plugin.getConfig().getDouble("sound.volume") + " " + plugin.getConfig().getDouble("sound.pitch"));
				event.getRecipients().remove(onlinePlayerObjects.get(arrayPosition));
				Bukkit.getPlayer(initialS).sendMessage(plugin.getConfig().getString("name.prefix") + event.getPlayer().getDisplayName() + plugin.getConfig().getString("name.suffix") + space() + event.getMessage().replaceAll(initialS, ChatColor.YELLOW + initialS + ChatColor.RESET));
				return;
			}
		}
	}
	
	private List<String> getOnlinePlayerNames(List<Player> players)
	{
		List<String> names = new ArrayList<String>();
		
		for(Player p : players)
		{
			names.add(p.getName());
		}
		
		return names;
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
		if(plugin.getConfig().getBoolean("space"))
			return " ";
		else
			return "";
	}
}
