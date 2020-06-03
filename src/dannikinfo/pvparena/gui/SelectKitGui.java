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

public class SelectKitGui implements Listener{
 	 
   public Inventory inv;
   public PVPArena plugin;
   public static String arenaName;
   public static Arena arn;

   public SelectKitGui(PVPArena Gplugin) {
       inv = Bukkit.createInventory(null, 27, "Выбор набора" );
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
	   for(int i = 0; i < arn.getCommands(); i++) {
		   inv.addItem(new ItemStack(Material.AIR, 1));
		   if(i == 4 || i == 7)
			  inv.addItem(new ItemStack(Material.AIR, 1));
		   inv.addItem(createGuiItem(i+1 + " команда", desc, Material.WOOL, i));
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
   	if (e.getInventory() != inv) return;

       e.setCancelled(true);

       final ItemStack clickedItem = e.getCurrentItem();

       // verify current item is not null
       if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

       final Player p = (Player) e.getWhoClicked();
       
       arn.setPlayerCommand(p, clickedItem.getDurability());
       
       ManageArena ma = new ManageArena(plugin);
       ma.saveTmp(arn);
       
       SelectCommandGui gui = new SelectCommandGui(plugin);
       gui.openInventory(p, arn);
   }
   
}
