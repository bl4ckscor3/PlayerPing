package bl4ckscor3.plugin.playerping.listener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.Plugin;

public class PlayerLoginListener implements Listener
{
	private static Plugin plugin;

	public PlayerLoginListener(Plugin pl)
	{
		plugin = pl;
	}

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) throws IOException
	{
		File folder = new File(plugin.getDataFolder(), "playerStorage");
		File f = new File(plugin.getDataFolder(), "playerStorage/" + event.getPlayer().getUniqueId() +".yml");
		YamlConfiguration yaml = YamlConfiguration.loadConfiguration(f);
		
		//if the folder doesn't exist, create it
		if(!folder.exists())
			folder.mkdirs();

		//if thats the first time the player is executing this command, do this.
		if(!f.exists())
		{
			List<String> alias = new ArrayList<String>();

			alias.add(event.getPlayer().getName());
			f.createNewFile();
			yaml.addDefault("name", event.getPlayer().getName());
			yaml.addDefault("toggle.sound", true);
			yaml.addDefault("toggle.highlight", true);
			yaml.addDefault("toggle.all", true);
			yaml.addDefault("alias", alias);
			yaml.options().copyDefaults(true);
			yaml.save(f);
			return;
		}
		
		//updating the name
		String oldName = yaml.getString("name");
		List<String> alias = yaml.getStringList("alias");
		
		alias.remove(oldName);
		alias.add(event.getPlayer().getName());
		yaml.set("name", event.getPlayer().getName());
		yaml.set("alias", alias);
		yaml.save(f);
	}
}
