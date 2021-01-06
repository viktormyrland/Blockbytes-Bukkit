package net.bbytes.bukkit.command;

import net.bbytes.bukkit.Main;
import net.bbytes.bukkit.message.Message;
import net.bbytes.bukkit.user.User;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class SetSkinCommand implements CommandExecutor,TabCompleter, Listener {


	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if(!(sender instanceof Player)){
			return true;
		}
		if(!sender.hasPermission("honeyfrost.admin")) {
			sender.sendMessage(Message.NO_PERMISSION.get(sender));
			return true;
		}

		if(args.length == 0){
			sender.sendMessage("§cUsage: §7/setskin <skin> [player]");
		}else{
			(new BukkitRunnable() {

				@Override
				public void run() {
					UUID uuid = Main.getInstance().getUTNUtils().getUUIDFromName_Sync(args[0]);
					if(uuid == null){
						sender.sendMessage("§cInvalid skin");
						return;
					}

					Player player = (Player) sender;

					if(args.length > 1) if(Bukkit.getPlayer(args[1]) != null){
						player = Bukkit.getPlayer(args[1]);
					}

					User u = User.getUser(player);

					if(u == null){
						sender.sendMessage("§cInvalid player");
						return;
					}

					sender.sendMessage("UUID: " + uuid.toString());

					u.setSkin(uuid);
				}
			}).runTaskAsynchronously(Main.getInstance());


		}







		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {

		
		return new ArrayList<String>();
	}

	
}
