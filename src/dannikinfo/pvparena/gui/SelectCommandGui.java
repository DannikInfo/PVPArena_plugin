package dannikinfo.pvparena.gui;

import java.util.ArrayList;

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

public class SelectCommandGui implements Listener{
  	 
    public Inventory inv;
    public PVPArena plugin;
    public static String arenaName;
    public static Arena arn;
 
    public SelectCommandGui(PVPArena Gplugin) {
        inv = Bukkit.createInventory(null, 27, "Выбор команды" );
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
    
    public ArrayList<Integer> createSlotMap(int count) {
    	ArrayList<Integer> map = new ArrayList<Integer>();//WARNING BITCH INDUSIAN CODE
    	if(count == 2) {
    		map.add(12);
    		map.add(14);
    	}
    	if(count == 3) {
    		map.add(11);
    		map.add(13);
    		map.add(15);
    	}
    	if(count == 4) {
    		map.add(10);
    		map.add(12);
    		map.add(14);
    		map.add(16);
    	}
    	if(count == 5) {
    		map.add(4);
    		map.add(10);
    		map.add(12);
    		map.add(14);
    		map.add(16);
    	}
    	if(count == 6) {
    		map.add(3);
    		map.add(5);
    		map.add(10);
    		map.add(12);
    		map.add(14);
    		map.add(16);
    	}
    	if(count == 7) {
    		map.add(2);
    		map.add(4);
    		map.add(6);
    		map.add(10);
    		map.add(12);
    		map.add(14);
    		map.add(16);
    	}
    	if(count == 8) {
    		map.add(1);
    		map.add(3);
    		map.add(5);
    		map.add(7);
    		map.add(10);
    		map.add(12);
    		map.add(14);
    		map.add(16);
    	}
    	if(count == 9) {
    		map.add(1);
    		map.add(3);
    		map.add(5);
    		map.add(7);
    		map.add(10);
    		map.add(12);
    		map.add(14);
    		map.add(16);
    		map.add(22);
    	}
    	if(count == 10) {
    		map.add(1);
    		map.add(3);
    		map.add(5);
    		map.add(7);
    		map.add(10);
    		map.add(12);
    		map.add(14);
    		map.add(16);
    		map.add(21);
    		map.add(23);
    	}
    	if(count == 11) {
    		map.add(1);
    		map.add(3);
    		map.add(5);
    		map.add(7);
    		map.add(10);
    		map.add(12);
    		map.add(14);
    		map.add(16);
    		map.add(20);
    		map.add(22);
    		map.add(24);
    	}
    	if(count == 12) {
    		map.add(1);
    		map.add(3);
    		map.add(5);
    		map.add(7);
    		map.add(10);
    		map.add(12);
    		map.add(14);
    		map.add(16);
    		map.add(19);
    		map.add(21);
    		map.add(23);
    		map.add(25);
    	}
    	return map;
    }
    
    public void initializeItems() {
    	ArrayList<String> desc = new ArrayList<String>();
    	ArrayList<Integer> map = createSlotMap(arn.getCommands());
    	for(int i = 0; i < arn.getCommands(); i++) {
    		inv.setItem(map.get(i) ,createGuiItem(i+1 + " команда", desc, Material.WOOL, i));
    	}
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
    	if (e.getInventory() == e.getWhoClicked().getInventory()) return;

        e.setCancelled(true);

        final ItemStack clickedItem = e.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

        final Player p = (Player) e.getWhoClicked();
        
        arn.setPlayerCommand(p, clickedItem.getDurability());
        
        ManageArena ma = new ManageArena(plugin);
        ma.saveTmp(arn);
        
        SelectKitGui gui = new SelectKitGui(plugin);
		gui.openInventory(p, arn);
    }
}
