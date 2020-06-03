package dannikinfo.pvparena.gui;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import dannikinfo.pvparena.PVPArena;
import dannikinfo.pvparena.arenaManagment.Arena;
import dannikinfo.pvparena.arenaManagment.ManageArena;

public class WaitingGameGui implements Listener{
	 
	public Inventory inv;
	public PVPArena plugin;
	public static Arena arn;

	public WaitingGameGui(PVPArena Gplugin) {
		if(arn != null)
	    	inv = Bukkit.createInventory(null, 36, "Арена " + arn.getName());
		else
			inv = Bukkit.createInventory(null, 36, "Арена");
		plugin = Gplugin;
	}
	
	public ItemStack createGuiItem(String name, ArrayList<String> desc, Material mat, int meta) {
	    ItemStack i = new ItemStack(mat, 1, (short)meta);
	    ItemMeta iMeta = i.getItemMeta();
	    iMeta.setDisplayName(name);
	    iMeta.setLore(desc);
	    i.setItemMeta(iMeta);
	    return i;
	}
	
	public void initializeItems() {
		ArrayList<String> desc = new ArrayList<String>();
		HashMap<Integer, Player> players = arn.getIDPlayers();
		HashMap<Integer, Integer> commands = arn.getCommandPlayers();
		int com1Size = 0;
		int com2Size = 0;
		for(int i = 0; i < players.size(); i++) {
			if(commands.get(players.get(i)) == 1)
				com1Size++;
			else
				com2Size++;
		}
		for(int i = 0; i < 27; i++) {
			if(i == 5 || i == 15 || i == 25)
				inv.addItem(createGuiItem("", desc, Material.IRON_BARDING, 0));
			if(i < 5 || (i > 9 && i < 15) || (i > 19 && i < 20)) {
				if(commands.get(players.get(i)) == 1)
					inv.addItem(createGuiItem(players.get(i).getDisplayName(), desc, Material.SKULL_ITEM, 3));
				if((com1Size < 4 && i > com1Size) || (com1Size < 8 && (i-1)/2 > com1Size) || (com1Size < 12 && (i-2)/3 > com1Size))
					inv.addItem(new ItemStack(Material.AIR, 1));
			}else if(i != 5 && i != 15 && i != 25){
				if(commands.get(players.get(i)) == 2)
					inv.addItem(createGuiItem(players.get(i).getDisplayName(), desc, Material.SKULL_ITEM, 3));
				if((com2Size < 4 && i > com2Size) || (com2Size < 8 && (i-1)/2 > com2Size) || (com2Size < 12 && (i-2)/3 > com2Size))
					inv.addItem(new ItemStack(Material.AIR, 1));
			} 
		}
		   
		for(int j = 0; j < 4; j++)
			inv.addItem(new ItemStack(Material.AIR, 1));
		   
		inv.addItem(createGuiItem("Взять набор", desc, Material.STAINED_CLAY, 5));
		inv.addItem(createGuiItem("", desc, Material.IRON_BARDING, 0));
		inv.addItem(createGuiItem("Назад", desc, Material.FLINT, 0));
	}
	
	public void openInventory(Player p, Arena gArn) {
		arn = gArn;
		initializeItems();
		p.openInventory(inv);
		return;
	}
	 
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		if(arn != null) {
			ManageArena ma = new ManageArena(plugin);
			ma.saveTmp(arn);
		}
	}
	 
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getInventory() != inv) return;
	
		e.setCancelled(true);
		
		final ItemStack clickedItem = e.getCurrentItem();
		
		if (clickedItem == null || clickedItem.getType() == Material.AIR) return;
		
		final Player p = (Player) e.getWhoClicked();
		     
		ManageArena ma = new ManageArena(plugin);
		ma.saveTmp(arn); 
	}
 
}
