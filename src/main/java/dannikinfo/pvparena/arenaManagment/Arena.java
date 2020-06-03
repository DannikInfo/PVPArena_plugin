package dannikinfo.pvparena.arenaManagment;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import dannikinfo.pvparena.PVPArena;
import dannikinfo.pvparena.gui.SelectArenaModGui;
import dannikinfo.pvparena.kitManagment.ManageKit;

public class Arena {
	
	//constant data (load from config file)
	private PVPArena plugin;
	private int maxPlayersCount = 0;
	private int commands = 0;
	private ArrayList<Object[]> spawns = new ArrayList<Object[]>();
	private ArrayList<Integer> availableMods = new ArrayList<Integer>();
	private ArrayList<String> availableKits = new ArrayList<String>();
	private String name;

	//variable data (load from tmp file changing in time)
	private int playersCount = 0;
	private HashMap<Integer, Integer> playersCommandMap = new HashMap<Integer, Integer>();//id:command
	private HashMap<Integer, Player> playersMap = new HashMap<Integer, Player>();//player:id
	private boolean inGame = false;
	private int actualyMod = 0;

	private final static int COMMAND = 0, BET = 1, ONEONONE = 2;
	
	public Arena(String name, PVPArena plugin) {
		this.plugin = plugin;
		this.name = name;
		ManageArena ma = new ManageArena(plugin);
		FileConfiguration arn = ma.load(name);
		
		//constant Data load
		maxPlayersCount = arn.getInt("maxPlayers");
		commands = arn.getInt("commandCount");
		availableMods = (ArrayList<Integer>)arn.get("availableMods");
		availableKits = (ArrayList<String>)arn.get("availableKits");
		spawns = (ArrayList<Object[]>)arn.get("spawns");
		
		//Variable Data load
		FileConfiguration tArn = ma.loadTmp(name);
		playersCount = tArn.getInt("playersCount");
		
		ArrayList<HashMap<Integer, Integer>> playersCommandMapAL = (ArrayList<HashMap<Integer, Integer>>)tArn.get("commandMap");
		if(playersCommandMapAL != null)
			playersCommandMap = playersCommandMapAL.get(0);
		
		ArrayList<HashMap<Integer, Player>> playersMapAL = (ArrayList<HashMap<Integer, Player>>)tArn.get("playersMap");
		if(playersMapAL != null)
			playersMap = playersMapAL.get(0);
		
		inGame = tArn.getBoolean("inGameStatus");
		actualyMod = tArn.getInt("gameMod");
	}
	
	public boolean isInGame() {
		return inGame;
	}
	
	public void setInGame(boolean game) {
		inGame = game;
	}
	
	public void join(Player player) {
		playersCount +=1;
		SelectArenaModGui gui = new SelectArenaModGui(plugin);
		gui.openInventory(player, this);
	}
	
	public void leave(Player player) {
		playersCount -=1;
		ManageKit mk = new ManageKit(plugin);
		mk.backInventoryPlayer(player);
		int id = getPlayerID(player);
		playersMap.remove(player);
		playersCommandMap.remove(id, getPlayerCommand(player));
	}
	
	public int getPlayerID(Player player) {
		int id = 0;
		for(int i = 0; i < playersMap.size(); i++) {
			if(playersMap.get(i) == player)
				id = i;
		}
		return id;
	}
	
	public void setPlayerCommand(Player player, int command) {
		playersMap.put(playersMap.size()+1, player);
		playersCommandMap.put(playersMap.size(), command);
	}
	
	public int getPlayerCommand(Player player) {
		int id = getPlayerID(player);
		return playersCommandMap.get(id);
	}

	public void givePlayerKit(Player player, String kit) {
		
	}
	
	public void setMod(Player player, int mod) {
		for(int aMod : availableMods) {
			if(aMod == mod)
				actualyMod = mod;
		}
	}
	
	public int getMod() {
		return actualyMod;
	}
	
	public boolean isAvailableMods(int mode) {
		for(int mod : this.availableMods) {
			if(mod == mode)
				return true;
		}
		return false;
	}
	
	public int getCommands() {
		return commands;
	}
	
	public String getName() {
		return name;
	}
	
	public HashMap<Integer, Player> getIDPlayers(){
		return playersMap;
	}
	
	public HashMap<Integer, Integer> getCommandPlayers(){
		return playersCommandMap;
	}

	public int getPlayersCount() {
		return this.playersCount;
	}
	
	public void setKill(Player player, Player killed) {
		
	}
	
	public void addDamage(Player player, Player damaged, int damage) {
		
	}
	
}
