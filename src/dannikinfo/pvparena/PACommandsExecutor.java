package dannikinfo.pvparena;

import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dannikinfo.pvparena.arenaManagment.Arena;
import dannikinfo.pvparena.arenaManagment.ManageArena;
import dannikinfo.pvparena.gui.KitsGui;
import dannikinfo.pvparena.kitManagment.ManageKit;

public class PACommandsExecutor implements CommandExecutor {

	private PVPArena plugin;

	public PACommandsExecutor(PVPArena plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if(cmd.getName().equalsIgnoreCase("pa")){
			if(args.length > 0) {
				//----------- админские команды настройки арены
				if(args[0].equals("c")) {
					if(args.length == 3 && !args[1].equals("") && !args[2].equals("")) {
						ManageArena cr = new ManageArena(plugin);
						try {
							cr.create(args[1], Integer.parseInt(args[2]));
							sender.sendMessage("арена "+args[1]+" создана");
						} catch (IOException e) {
							sender.sendMessage("Ошибка! арена "+args[1]+" не создана");
							e.printStackTrace();
						}
						return true;
					}else {
						sender.sendMessage("Используйте /pa c [название арены] [кол-во команд]");
						return true;
					}
				}
				if(args[0].equals("r")) {
					if(args.length == 2 && !args[1].equals("")) {
						ManageArena cr = new ManageArena(plugin);
						cr.remove(sender, args[1]);
						return true;
					}else {
						sender.sendMessage("Используйте /pa r [название арены]");
						return true;
					}
				}
				if(args[0].equals("ss")) {
					if(args.length == 3 && !args[1].equals("") && !args[2].equals("")) {
						ManageArena cr = new ManageArena(plugin);
						if(sender instanceof Player) {
							cr.setSpawn(args[1], (Player)sender, Integer.parseInt(args[2]));
						}else {
							sender.sendMessage("Может использовать только игрок");
						}
						return true;
					}else {
						sender.sendMessage("Используйте /pa ss [название арены] [номер команды]");
						return true;
					}
				}
				if(args[0].equals("add")) {
					if(args.length == 4) {
						if(args[1].equals("kit")) {
							if(!args[2].equals("")) {
								ManageArena ma = new ManageArena(plugin);
								ma.addKit(args[2], sender, args[3]);
								return true;
							}else {
								sender.sendMessage("Используйте /pa add kit [арена] [набор]");
								return true;
							}
						}
					}
					if(args.length == 4) {
						if(args[1].equals("gm")) {
							if(!args[2].equals("")) {
								ManageArena ma = new ManageArena(plugin);
								ma.addGameMode(args[2], sender, Integer.parseInt(args[3]));
								return true;
							}else {
								sender.sendMessage("Используйте /pa add gm [арена] [0|1|2] где 0 - командная игра, 1 - на ставку, 2 - 1 на 1");
								return true;
							}
						}
					}
				}
				if(args[0].equals("del")) {
					if(args.length == 4) {
						if(args[1].equals("kit")) {
							if(!args[2].equals("")) {
								ManageArena ma = new ManageArena(plugin);
								ma.delKit(args[2], sender, args[3]);
								return true;
							}else {
								sender.sendMessage("Используйте /pa del kit [арена] [набор]");
								return true;
							}
						}
					}
					if(args.length == 4) {
						if(args[1].equals("gm")) {
							if(!args[2].equals("")) {
								ManageArena ma = new ManageArena(plugin);
								ma.delGameMode(args[2], sender, Integer.parseInt(args[3]));
								return true;
							}else {
								sender.sendMessage("Используйте /pa del gm [арена] [0|1|2] где 0 - командная игра, 1 - на ставку, 2 - 1 на 1");
								return true;
							}
						}
					}
				}
				//------------ команды наборов
				if(args[0].equals("kit")) {
					if(args.length == 3) {
						if(args[1].equals("c")) {
							if(!args[2].equals("")) {
								ManageKit cr = new ManageKit(plugin);
								try {
									cr.create(args[2]);
									sender.sendMessage("Набор "+args[2]+" создан");
								} catch (IOException e) {
									e.printStackTrace();
								}
								return true;
							}else {
								sender.sendMessage("Используйте /pa kit c [name]");
								return true;
							}
						}
						if(args[1].equals("r")) {
							if(!args[2].equals("")) {
								ManageKit cr = new ManageKit(plugin);
								cr.remove(sender, args[2]);
								return true;
							}else {
								sender.sendMessage("Используйте /pa kit r [name]");
								return true;
							}
						}
						if(args[1].equals("add")) {
							if(!args[2].equals("") ) {
								ManageKit cr = new ManageKit(plugin);
								try {
									if(sender instanceof Player) {
										Player p = (Player)sender;
										cr.add(args[2], p.getInventory().getItemInHand());
										sender.sendMessage(p.getInventory().getItemInHand().getType() + " Добавлен в набор " + args[2]);
									}
								} catch (IOException e) {
									e.printStackTrace();
								} 
								return true;
							}else {
								sender.sendMessage("Используйте /pa kit add [name] и держите блок/предмет в руке");
								return true;
							}
						}
						if(args[1].equals("edit")) {
							if(!args[2].equals("")) {
								if(sender instanceof Player) {
									KitsGui gui = new KitsGui(plugin);
									gui.openInventory((Player)sender, args[2]);
								}else {
									sender.sendMessage("Может использовать только игрок");
								}
								return true;
							}else {
								sender.sendMessage("Используйте /pa edit [name]");
								return true;
							}
						}
						return true;
					}else {
						sender.sendMessage("/pa kit c [name] - создать набор");
						sender.sendMessage("/pa kit r [name] - удалить набор");
						sender.sendMessage("/pa kit add [name] (держите блок в руке) - добавить в набор блок или предмет");
						sender.sendMessage("/pa kit edit [name] - редактировать набор");
						return true;
					}
				}				
				//-------- Пользовательские команды
				if(args[0].equals("join")) {
					if(args.length == 2 && !args[1].equals("")) {
						if(sender instanceof Player) {
							Arena arn = new Arena(args[1], plugin);
							arn.join((Player) sender);
						}else {
							sender.sendMessage("Может использовать только игрок");
						}
						return true;
					}else {
						sender.sendMessage("Используйте /pa join [название арены]");
						return true;
					}
				}
			}else {
				sender.sendMessage("Помощь в настройке:");
				sender.sendMessage("/pa c [name] [кол-во команд] - создать арену c названием name и кол-вом команд");
				sender.sendMessage("/pa r [name] - удалить арену c названием name");
				sender.sendMessage("/pa ss [name] [номер команды] - уставновить точку спавна для [номер] команды на арене name");
				sender.sendMessage("/pa add kit [name] [набор] - добавить набор к арене name");
				sender.sendMessage("/pa add gm [name] [0|1|2] - добавить режим игры к арене name");
				sender.sendMessage("/pa del kit [name] [набор] - удалить набор с арены name");
				sender.sendMessage("/pa del gm [name] [0|1|2] - удалить режим игры с арены name");
				sender.sendMessage("/pa kit c [name] - создать набор");
				sender.sendMessage("/pa kit r [name] - удалить набор");
				sender.sendMessage("/pa kit add [name] (держите блок в руке) - добавить в набор блок или предмет");
				sender.sendMessage("/pa kit edit [name] - редактировать набор");
				return true;
			}
		}
		return true; 
	}

}
