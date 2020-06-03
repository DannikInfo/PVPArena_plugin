package dannikinfo.pvparena.kitManagment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import dannikinfo.pvparena.PVPArena;

public class ManageKit {
	
	PVPArena plugin;
	File confFile;
	
	public ManageKit(PVPArena plugin){
		this.plugin = plugin;
	}
	
	public FileConfiguration load(String name) {
		final File dir = new File(plugin.getDataFolder(), "kits");
		if(!dir.exists())
			dir.mkdir();
		confFile = new File(plugin.getDataFolder(), "kits/"+name+".yml");
		FileConfiguration conf = YamlConfiguration.loadConfiguration(confFile);
		return conf;
	}
	
	public FileConfiguration loadTmp(String player) {
		final File dir = new File(plugin.getDataFolder(), "tmp/players");
		if(!dir.exists())
			dir.mkdir();
		confFile = new File(plugin.getDataFolder(), "tmp/players"+player+".yml");
		FileConfiguration conf = YamlConfiguration.loadConfiguration(confFile);
		return conf;
	}
	
	public void create(String name) throws IOException {
		FileConfiguration conf = load(name);
		conf.set("name", name);
		conf.set("kit", new ArrayList<ItemStack>());
		conf.save(confFile);
	}
	
	
	public void remove(CommandSender sender, String name) {
		final File dir = new File(plugin.getDataFolder(), "kits");
		if(dir.exists()) {
			File confFile = new File(plugin.getDataFolder(), "kits/"+name+".yml");
			if(confFile.exists()) {
				confFile.delete();
				sender.sendMessage("Набор "+name+" удален");
			}else {
				sender.sendMessage("Набор "+name+" не существует");
			}
		}else {
			sender.sendMessage("Ранее небыло создано ни одного набора");
		}
	}

	public void add(String name, ItemStack item) throws IOException {
		FileConfiguration conf = load(name);
		ArrayList<ItemStack> kit = (ArrayList<ItemStack>)conf.get("kit");
		kit.add(item);
		conf.set("kit", kit);
		conf.save(confFile);
	}

	public void edit(String name, ArrayList<ItemStack> kit) throws IOException {
		FileConfiguration conf = load(name);
		conf.set("kit", kit);
		conf.save(confFile);
	}
	
	public ArrayList<ItemStack> get(String name){
		FileConfiguration conf = load(name);
		ArrayList<ItemStack> kit = (ArrayList<ItemStack>)conf.get("kit");
		return kit;
	}
	
	public void saveInventoryPlayer(Player player) {
		FileConfiguration conf = loadTmp(player.getDisplayName());
		ArrayList<ItemStack> pInv = new ArrayList<ItemStack>();
		for(ItemStack is : player.getInventory()) {
			pInv.add(is);
		}
		
		conf.set("pName", player.getDisplayName());
		conf.set("inventory", pInv);
		
		try {
			conf.save(confFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void backInventoryPlayer(Player player) {
		FileConfiguration conf = loadTmp(player.getDisplayName());
		ArrayList<ItemStack> gInv = (ArrayList<ItemStack>)conf.get("inventory");
		player.getInventory().clear();
		for(ItemStack is : gInv) {
			player.getInventory().addItem(is);
		}
		
		confFile.delete();
	}

	public void giveKitPlayer(Player player, String kitName) {
		ArrayList<ItemStack> kit = get(kitName);
		saveInventoryPlayer(player);
		player.getInventory().clear();
		for(ItemStack is : kit) {
			player.getInventory().addItem(is);
		}
	}
	
}
