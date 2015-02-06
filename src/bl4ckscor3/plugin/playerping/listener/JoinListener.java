package bl4ckscor3.plugin.playerping.listener;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.Plugin;

import bl4ckscor3.plugin.playerping.core.PlayerPing;

public class JoinListener implements Listener
{
	private Plugin plugin;
	
	public JoinListener(Plugin pl)
	{
		plugin = pl;
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) throws IOException
	{
		File folder = new File(plugin.getDataFolder(), "/playerStorage/");
		File f = new File(plugin.getDataFolder(), "/playerStorage/" + event.getPlayer().getUniqueId() +".yml");
		
		PlayerPing.setupPlayerFile(new YamlConfiguration(), f, folder, event.getPlayer());
	}
}
