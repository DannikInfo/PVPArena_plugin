package dannikinfo.pvparena.gui;

import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import dannikinfo.pvparena.PVPArena;
import dannikinfo.pvparena.kitManagment.ManageKit;

public class KitsGui implements Listener{
    	 
        // Create a new inventory, with no owner, a size of nine, called example
        public Inventory inv;
        public PVPArena plugin;
        public static String name;
     
        public KitsGui(PVPArena Gplugin) {
            inv = Bukkit.createInventory(null, 27, "Kit" );
            plugin = Gplugin;
        }
     
        // Nice little method to create a gui item with a custom name, and description
        public ItemStack createGuiItem(String name, ArrayList<String> desc, Material mat, short meta) {
            ItemStack i = new ItemStack(mat, 1, meta);
            ItemMeta iMeta = i.getItemMeta();
            iMeta.setDisplayName(name);
            iMeta.setLore(desc);
            i.setItemMeta(iMeta);
            return i;
        }
     
        // You can open the inventory with this
        public void openInventory(Player p, String Gname) {
            ManageKit mk = new ManageKit(plugin);
            name = Gname;
            ArrayList<ItemStack> list = mk.get(name);
			for(int i = 0; i < list.size(); i++) {
				inv.addItem(list.get(i));
			}
            p.openInventory(inv);
            return;
        }
     
        // Check for clicks on items
        @EventHandler
        public void onInventoryClose(InventoryCloseEvent e) {
        	if(e.getInventory() != inv) return;
        	
        	ManageKit mk = new ManageKit(plugin);
            try {
            	ArrayList<ItemStack> kit = new ArrayList<ItemStack>();
            	for(int i = 0; i < inv.getSize(); i++) {
            		if(e.getInventory().getItem(i) != null)
            			kit.add(e.getInventory().getItem(i));
				}
    			mk.edit(name, kit);
            } catch (IOException ex) {
				ex.printStackTrace();
			}
        }
        
}