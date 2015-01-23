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

public class ChatListener implements Listener
{
	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event)
	{
		List<String> onlinePlayers = getOnlinePlayerNames(Arrays.asList(Bukkit.getServer().getOnlinePlayers()));
	
		for(String s : onlinePlayers)
		{
			if(event.getMessage().contains(s))
			{
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "playsound random.orb " + s + " ~0 ~0 ~0 1 10");
				event.setMessage(processMessage(event.getMessage(), s));
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
	
	private String processMessage(String message, String name)
	{
		String[] split = message.split(" ");
		
		message = "";
		
		for(String s : split)
		{
			if(s.equalsIgnoreCase(name))
				message += ChatColor.YELLOW + s + ChatColor.RESET + " ";
			else
				message += s + " ";
		}
		
		message = message.substring(0, message.length() - 1);
		
		return message;
	}
}
