package dannikinfo.pvparena.gui;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import dannikinfo.pvparena.PVPArena;
import dannikinfo.pvparena.arenaManagment.Arena;
import dannikinfo.pvparena.arenaManagment.ManageArena;

public class SelectArenaModGui implements Listener{
   	 
		public Inventory inv;
        public PVPArena plugin;
        public static String arenaName;
        public static Arena arn;
     
        public SelectArenaModGui(PVPArena Gplugin) {
            inv = Bukkit.createInventory(null, 9, "Выбор режима игры" );
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
        	if(arn.isAvailableMods(0)) {
        		desc.clear();
        		desc.add("ТИПА ОПИСАНИЕ");
        		inv.setItem(2, createGuiItem("Командная игра", desc, Material.ANVIL, 0));
        	}
        	if(arn.isAvailableMods(1)) {
        		desc.clear();
        		desc.add("ТИПА ОПИСАНИЕ");
        		inv.setItem(4, createGuiItem("Игра на ставку", desc, Material.GOLD_INGOT, 0));
        	}
        	if(arn.isAvailableMods(2)) {
        		desc.clear();
        		desc.add("ТИПА ОПИСАНИЕ");
        		inv.setItem(6, createGuiItem("1 на 1", desc, Material.DIAMOND_SWORD, 0));
        	}
        }
     
        public void openInventory(Player p, Arena gArn) {
            arn = gArn;
            initializeItems();
            p.openInventory(inv);
            return;
        }
        
        @EventHandler
        public void onInventoryClick(InventoryClickEvent e) {
        	if (e.getWhoClicked().getInventory() == e.getInventory()) return;
        	
            e.setCancelled(true);

            final ItemStack clickedItem = e.getCurrentItem();

            // verify current item is not null
            if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

            final Player p = (Player) e.getWhoClicked();
            
            if(clickedItem.getType() == Material.ANVIL)
            	arn.setMod(p, 0);
            if(clickedItem.getType() == Material.GOLD_INGOT)
            	arn.setMod(p, 1);
            if(clickedItem.getType() == Material.DIAMOND_SWORD)
            	arn.setMod(p, 2);
            
            ManageArena ma = new ManageArena(plugin);
            ma.saveTmp(arn);
            
    		SelectCommandGui gui = new SelectCommandGui(plugin);
    		gui.openInventory(p, arn);
        }
        
        @EventHandler
    	public void onInventoryClose(InventoryCloseEvent e) {
    		if(arn != null) {
    			ManageArena ma = new ManageArena(plugin);
    			ma.saveTmp(arn);
    		}
    	}

}
