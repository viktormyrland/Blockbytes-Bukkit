package net.bbytes.bukkit.listeners;

import net.bbytes.bukkit.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;

public class CommandBlockOpListener implements Listener{
	
	List<Player> inCommandBlock = new ArrayList<Player>();
	
	@EventHandler
	public void onPlayerCommandblockInteract(PlayerInteractEvent e) {
		if(e.getClickedBlock() != null) if(e.getAction() == Action.RIGHT_CLICK_BLOCK)
		if(e.getClickedBlock().getType() == Material.COMMAND || e.getClickedBlock().getType() == Material.COMMAND_CHAIN || e.getClickedBlock().getType() == Material.COMMAND_REPEATING) {
			if(!e.getPlayer().isOp()) {
				e.getPlayer().setOp(true);
			}
			Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {if(!inCommandBlock.contains(e.getPlayer()))inCommandBlock.add(e.getPlayer());}, 20);
		}
	}
	

	@EventHandler
	public void onPlayerCommandblockExit(PlayerMoveEvent e) {
		if(inCommandBlock.contains(e.getPlayer())) {
			e.getPlayer().setOp(false);
			inCommandBlock.remove(e.getPlayer());
		}
	}
	
	@EventHandler
	public void onCommandBlockPlace(PlayerInteractEvent e) {
		if(e.getItem() != null) if(e.getAction() == Action.RIGHT_CLICK_BLOCK)
		if(e.getItem().getType() == Material.COMMAND) {
			if(!e.getPlayer().isOp()) {
				e.getClickedBlock().getRelative(e.getBlockFace()).setType(Material.COMMAND);
			}
		}else if(e.getItem().getType() == Material.COMMAND_CHAIN) {
			if(!e.getPlayer().isOp()) {
				e.getClickedBlock().getRelative(e.getBlockFace()).setType(Material.COMMAND_CHAIN);
			}
		}else if(e.getItem().getType() == Material.COMMAND_REPEATING) {
			if(!e.getPlayer().isOp()) {
				e.getClickedBlock().getRelative(e.getBlockFace()).setType(Material.COMMAND_REPEATING);
			}
		}
		
		
	}
	
	@EventHandler
	public void onCommandBlockBreak(PlayerInteractEvent e) {
		if(e.getAction() == Action.LEFT_CLICK_BLOCK)
			if(e.getClickedBlock().getType() == Material.COMMAND || e.getClickedBlock().getType() == Material.COMMAND_CHAIN || e.getClickedBlock().getType() == Material.COMMAND_REPEATING) {
			if(!e.getPlayer().isOp()) {
				e.getClickedBlock().breakNaturally();
			}
		}
		
		
	}
	
	

}
