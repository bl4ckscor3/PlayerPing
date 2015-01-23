package bl4ckscor3.plugin.playerping.listener;

import org.anjocaido.groupmanager.GroupManager;
import org.anjocaido.groupmanager.permissions.AnjoPermissionsHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class GroupManagerHook implements Listener
{
	private static GroupManager groupManager;
	private Plugin plugin;
	private static boolean loaded = false;
	
	public GroupManagerHook(Plugin plugin)
	{
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPluginEnable(PluginEnableEvent event)
	{
		PluginManager pluginManager = plugin.getServer().getPluginManager();
		Plugin GMplugin = pluginManager.getPlugin("GroupManager");

		if (GMplugin != null && GMplugin.isEnabled())
		{
			groupManager = (GroupManager)GMplugin;
			loaded = true;
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPluginDisable(PluginDisableEvent event)
	{
		if (groupManager != null)
		{
			if (event.getPlugin().getDescription().getName().equals("GroupManager"))
			{
				groupManager = null;
				loaded = false;
			}
		}
	}

	public static String getPrefix(Player player)
	{
		AnjoPermissionsHandler handler = groupManager.getWorldsHolder().getWorldPermissions(player);

		if (handler == null)
			return null;
		
		return handler.getUserPrefix(player.getName());
	}

	public static String getSuffix(Player player)
	{
		AnjoPermissionsHandler handler = groupManager.getWorldsHolder().getWorldPermissions(player);
		
		if (handler == null)
			return null;
		
		return handler.getUserSuffix(player.getName());
	}
	
	public static boolean isLoaded()
	{
		return loaded;
	}
}
