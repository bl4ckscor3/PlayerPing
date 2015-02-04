package bl4ckscor3.plugin.playerping.core;

import java.io.File;
import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import bl4ckscor3.plugin.playerping.commands.CMDPlayerPing;
import bl4ckscor3.plugin.playerping.listener.ChatListener;

public class PlayerPing extends JavaPlugin
{
	@Override
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(new ChatListener(this), this);
		Config.createConfig(this);
		System.out.println("[PlayerPing] v" + getDescription().getVersion() + " enabled!");
	}

	@Override
	public void onDisable()
	{
		System.out.println("[PlayerPing] v" + getDescription().getVersion() + " disabled!");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		Player p = null;

		if(sender instanceof Player)
			p = (Player)sender;

		if(cmd.getName().equalsIgnoreCase("playerping"))
		{
			if(args.length == 0 || args.length > 2)
				return false;
			
			try
			{
				CMDPlayerPing.exe(this, p, args);
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			
			return true;
		}
		return false;
	}
	
	public static void setupPlayerFile(YamlConfiguration yaml, File f, File folder, Player p) throws IOException
	{
		//if the folder doesn't exist, create it
		if(!folder.exists())
			folder.mkdirs();

		//if thats the first time the player is executing this command, do this.
		if(!f.exists())
		{
			f.createNewFile();
			yaml = YamlConfiguration.loadConfiguration(f);
			yaml.addDefault("name", p.getName());
			yaml.addDefault("toggle.sound", true);
			yaml.addDefault("toggle.all", true);
			yaml.options().copyDefaults(true);
			yaml.save(f);
		}
	}
}
