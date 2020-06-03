package dannikinfo.pvparena;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import dannikinfo.pvparena.gui.KitsGui;
import dannikinfo.pvparena.gui.SelectArenaModGui;
import dannikinfo.pvparena.gui.SelectCommandGui;
import dannikinfo.pvparena.gui.SelectKitGui;
import dannikinfo.pvparena.gui.ShowKitGui;
import dannikinfo.pvparena.gui.WaitingGameGui;

public class PVPArena extends JavaPlugin{
	
	Logger PAlog = getLogger();
	PACommandsExecutor executor;
	
	@Override
	public void onEnable() {
		executor = new PACommandsExecutor(this);
		getCommand("pa").setExecutor(executor);
		getServer().getPluginManager().registerEvents(new KitsGui(this), this);
		getServer().getPluginManager().registerEvents(new SelectArenaModGui(this), this);
		getServer().getPluginManager().registerEvents(new SelectCommandGui(this), this);
		getServer().getPluginManager().registerEvents(new SelectKitGui(this), this);
		getServer().getPluginManager().registerEvents(new ShowKitGui(this), this);
		getServer().getPluginManager().registerEvents(new WaitingGameGui(this), this);
		PAlog.info("PVPArena has been enabled.");
	}
	
	@Override
	public void onDisable(){
		PAlog.info("PVPArena has been disabled.");
	}

}
