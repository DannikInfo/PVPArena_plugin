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
import dannikinfo.pvparena.kitManagment.ManageKit;

public class ShowKitGui implements Listener{
	 
	public Inventory inv;
	public PVPArena plugin;
	public static Arena arn;
	public static String kitName;

  public ShowKitGui(PVPArena Gplugin) {
      inv = Bukkit.createInventory(null, 36, "Набор" + kitName);
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
	   ManageKit mk = new ManageKit(plugin);
	   ArrayList<ItemStack> kit = mk.get(kitName);
	   int i = 0;
	   for(; i < kit.size(); i++) {
		   inv.addItem(kit.get(i));
	   }
	   for(; i < 31; i++)
		   inv.addItem(new ItemStack(Material.AIR, 1));
	   
	   inv.addItem(createGuiItem("Взять набор", desc, Material.STAINED_CLAY, 5));
	   inv.addItem(createGuiItem("", desc, Material.IRON_BARDING, 0));
	   inv.addItem(createGuiItem("Назад", desc, Material.FLINT, 0));
  }

  public void openInventory(Player p, Arena gArn, String gKitName) {
      arn = gArn;
      kitName = gKitName;
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
      
      if(clickedItem.getType() == Material.STAINED_CLAY) {
    	  ManageKit mk = new ManageKit(plugin);
    	  mk.giveKitPlayer(p, kitName);
    	  WaitingGameGui gui = new WaitingGameGui(plugin);
	      gui.openInventory(p, arn);
	  }
      
      if(clickedItem.getType() == Material.FLINT) {
	      SelectKitGui gui = new SelectKitGui(plugin);
	      gui.openInventory(p, arn);
      }
	  
  }
  
}
