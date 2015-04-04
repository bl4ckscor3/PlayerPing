package bl4ckscor3.plugin.playerping.core;

import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import bl4ckscor3.plugin.bl4ckkitCore.core.bl4ckkitCore;
import bl4ckscor3.plugin.playerping.commands.CMDPlayerPing;
import bl4ckscor3.plugin.playerping.listener.ChatListener;
import bl4ckscor3.plugin.playerping.listener.PlayerLoginListener;

public class PlayerPing extends JavaPlugin
{
	@Override
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(new PlayerLoginListener(this), this);
		getServer().getPluginManager().registerEvents(new ChatListener(this), this);
		Config.createConfig(this);
		bl4ckkitCore.registerPlugin(this);
		bl4ckkitCore.getMessageManager().sendEnabledMessage(this);
	}

	@Override
	public void onDisable()
	{
		bl4ckkitCore.unregisterPlugin(this);
		bl4ckkitCore.getMessageManager().sendDisabledMessage(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		Player p = null;

		if(sender instanceof Player)
			p = (Player)sender;
		else
		{
			bl4ckkitCore.getMessageManager().sendDisallowMessage(this);
			return true;
		}
		
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
}
