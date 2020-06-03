package dannikinfo.pvparena.arenaManagment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import dannikinfo.pvparena.PVPArena;

public class ManageArena {

	PVPArena plugin;
	File confFile;
	
	public ManageArena(PVPArena plugin){
		this.plugin = plugin;
	}

	public FileConfiguration load(String name) {
		final File dir = new File(plugin.getDataFolder(), "arenas");
		if(!dir.exists())
			dir.mkdir();
		confFile = new File(plugin.getDataFolder(), "arenas/"+name+".yml");
		FileConfiguration conf = YamlConfiguration.loadConfiguration(confFile);
		return conf;
	}
	
	public FileConfiguration loadTmp(String name) {
		final File dir = new File(plugin.getDataFolder(), "tmp/arenas");
		if(!dir.exists())
			dir.mkdir();
		confFile = new File(plugin.getDataFolder(), "tmp/arenas/"+name+".yml");
		FileConfiguration conf = YamlConfiguration.loadConfiguration(confFile);
		return conf;
	}
	
	public void create(String name, int command) throws IOException {
		FileConfiguration conf = load(name);
		conf.set("name", name);
		conf.set("commandCount", command);
		conf.set("maxPlayersCount", 24);
		conf.set("availableMods", new ArrayList<Integer>());
		conf.set("availableKits", new ArrayList<String>());
		conf.set("spawns", new ArrayList<Object[]>());
		conf.save(confFile);
	}
	
	
	public void remove(CommandSender sender, String name) {
		final File dir = new File(plugin.getDataFolder(), "arenas");
		if(dir.exists()) {
			File confFile = new File(plugin.getDataFolder(), "arenas/"+name+".yml");
			if(confFile.exists()) {
				confFile.delete();
				sender.sendMessage("Арена "+name+" удалена");
			}else {
				sender.sendMessage("Арены "+name+" не существует");
			}
		}else {
			sender.sendMessage("Ранее небыло создано ни одной арены");
		}
	}
	
	public void setSpawn(String name, Player player, int command) {
		FileConfiguration conf = load(name);
		ArrayList<Object[]> spawns = (ArrayList<Object[]>)conf.get("spawns");
		if(spawns == null)
			spawns = new ArrayList<Object[]>(); 
		spawns.add(new Object[] {command, player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ()});
		player.sendMessage("Точка спавна для "+command+" команды установлена");
		conf.set("spawns", spawns);
		try {
			conf.save(confFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addGameMode(String name, CommandSender player, int mode) {
		FileConfiguration conf = load(name);
		ArrayList<Integer> aMods = (ArrayList<Integer>)conf.get("availableMods");
		if(aMods == null)
			aMods = new ArrayList<Integer>(); 
		aMods.add(mode);
		player.sendMessage(mode +" режим игры добавлен к арене " +name);
		conf.set("availableMods", aMods);
		try {
			conf.save(confFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void delGameMode(String name, CommandSender player, int mode) {
		FileConfiguration conf = load(name);
		ArrayList<Integer> aMods = (ArrayList<Integer>)conf.get("availableMods");
		if(aMods == null) {
			player.sendMessage("У арены " +name+ " не было добавлено ни одного режима игры");
			return;
		}
		for(int i = 0; i < aMods.size(); i++)
			if(aMods.get(i) == mode)
				aMods.remove(i);
		
		player.sendMessage(mode + " режим удален с арены " +name);
		conf.set("availableMods", aMods);
		try {
			conf.save(confFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addKit(String name, CommandSender player, String kit) {
		FileConfiguration conf = load(name);
		ArrayList<String> aKits = (ArrayList<String>)conf.get("availableKits");
		if(aKits == null)
			aKits = new ArrayList<String>(); 
		aKits.add(kit);
		player.sendMessage(kit +" набор игры добавлен к арене " +name);
		conf.set("availableKits", aKits);
		try {
			conf.save(confFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void delKit(String name, CommandSender player, String kit) {
		FileConfiguration conf = load(name);
		ArrayList<String> aKits = (ArrayList<String>)conf.get("availableKits");
		if(aKits == null) {
			player.sendMessage("У арены " +name+ " не было добавлено ни одного набора");
			return;
		}
		for(int i = 0; i < aKits.size(); i++)
			if(aKits.get(i) == kit)
				aKits.remove(i);
		
		player.sendMessage(kit + " режим удален с арены " +name);
		conf.set("availableMods", aKits);
		try {
			conf.save(confFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveTmp(Arena arn) {
		FileConfiguration conf = loadTmp(arn.getName());
		conf.set("playersCount", arn.getPlayersCount());
		
		ArrayList<HashMap<Integer, Integer>> playersCommandMapAL = new ArrayList<HashMap<Integer, Integer>>();
		playersCommandMapAL.add(arn.getCommandPlayers());
		
		ArrayList<HashMap<Integer, Player>> playersMapAL = new ArrayList<HashMap<Integer, Player>>();
		playersMapAL.add(arn.getIDPlayers());
		
		conf.set("commandMap", playersCommandMapAL);
		conf.set("playersMap", playersMapAL);
		conf.set("inGameStatus", arn.isInGame());
		conf.set("gameMod", arn.getMod());
		try {
			conf.save(confFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
