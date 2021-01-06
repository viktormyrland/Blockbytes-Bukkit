package net.bbytes.bukkit.command;

import net.bbytes.bukkit.Main;
import net.bbytes.bukkit.message.Message;
import net.luckperms.api.node.Node;
import net.luckperms.api.util.Tristate;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class RankCommand implements CommandExecutor,TabCompleter{

	static List<String> groups = Main.getInstance().getLuckPerms().getTrackManager().getTrack("perms").getGroups();


	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(sender instanceof Player)
			if(!sender.hasPermission("honeyfrost.rank.set")) {
				sender.sendMessage(Message.NO_PERMISSION.get(sender));
				return true;
			}

		//prefix <user> <prefix>
		if(args.length < 2) {
			sender.sendMessage("§cUsage: §7/rank <user> <prefix>");

			StringBuilder prefixList = new StringBuilder();
			prefixList.append("§cRanks: ");

			for(String prefix : groups) {
				prefixList.append(prefix.toUpperCase() + ", ");

			}

			String rank = prefixList.toString().substring(0, prefixList.length() - 2);


			sender.sendMessage(rank);

		}else if(Bukkit.getOfflinePlayer(args[0]) == null) {
			sender.sendMessage("§7Could not find player '§c" + args[0] + "§7'");
		}else if(!groups.contains(args[1]) && !args[1].equalsIgnoreCase("none")) {
			sender.sendMessage("§7Invalid rank: '§c" + args[1] + "§7'");
		}else {

			Main.getInstance().getLuckPerms().getUserManager().loadUser(Bukkit.getOfflinePlayer(args[0]).getUniqueId()).thenAccept((user) -> {

				if(user == null){
					sender.sendMessage("§cError: §7This player needs to join the server at least once before you can execute any commands on them");
					return;
				}
				for(String group : groups) {
					if(user.getCachedData().getPermissionData().checkPermission("group." + group) == Tristate.TRUE) {
						user.data().remove(Node.builder("group." + group).build());
					}
				}

				user.data().add(Node.builder("group." + args[1]).build());

				Main.getInstance().getLuckPerms().getUserManager().saveUser(user);

				if(args[1].equalsIgnoreCase("none")) {
					sender.sendMessage("§aPlayer §b" + Bukkit.getOfflinePlayer(args[0]).getName() + " §ahas been taken their rank away.");
					if(Bukkit.getOfflinePlayer(args[0]).isOnline() && !Bukkit.getOfflinePlayer(args[0]).getName().equalsIgnoreCase(sender.getName()))
						Bukkit.getOfflinePlayer(args[0]).getPlayer().sendMessage("§aYour rank was been taken away.");
				}else {
					String prefix = Main.getInstance().getLuckPerms().getGroupManager().getGroup(args[1]).getCachedData().getMetaData().getPrefix();
					sender.sendMessage("§aPlayer §b" + Bukkit.getOfflinePlayer(args[0]).getName() + " §ahas been given the rank: " + prefix.replaceAll("&", "§"));
					if(Bukkit.getOfflinePlayer(args[0]).isOnline() && !Bukkit.getOfflinePlayer(args[0]).getName().equalsIgnoreCase(sender.getName()))
						Bukkit.getOfflinePlayer(args[0]).getPlayer().sendMessage("§aYou have been given the rank: " + prefix.replaceAll("&", "§"));
				}
			});

		}


		return true;
	}

	public static void updateGroups(){
		groups = Main.getInstance().getLuckPerms().getTrackManager().getTrack("perms").getGroups();
	}
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> list = new ArrayList<String>();
		if(sender.hasPermission("honeyfrost.rank.set")) {
			if(args.length == 1)
				for(Player all : Bukkit.getOnlinePlayers())
					list.add(all.getName());
			else if(args.length == 2)
				for(String g : groups)
					list.add(g);

		}

		List<String> returnList = new ArrayList<String>();
		for(String str : list) {
			if(str.toLowerCase().startsWith(args[args.length-1].toLowerCase()))
				returnList.add(str);
		}
		//Collections.sort(returnList, String.CASE_INSENSITIVE_ORDER);

		return returnList;
	}

}
