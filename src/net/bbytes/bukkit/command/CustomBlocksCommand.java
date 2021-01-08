package net.bbytes.bukkit.command;

import net.bbytes.bukkit.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;


public class CustomBlocksCommand implements CommandExecutor,TabCompleter, Listener {

	private Inventory customBlocksInventory = null;
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		
		Player p = (Player) sender;

		p.openInventory(getCustomBlocksInventory());

			
		
		return true;
	}

	
	public Inventory getCustomBlocksInventory() {
		
		if(customBlocksInventory != null) return customBlocksInventory;
		
		customBlocksInventory = Bukkit.createInventory(null, 45, "§cOmega Parkour Custom Blocks");
		
	    this.customBlocksInventory.addItem(Main.getInstance().getItemStackUtils().getItemStack(Material.HUGE_MUSHROOM_2, "§7Red Mushroom", "100:14"));
	    this.customBlocksInventory.addItem(Main.getInstance().getItemStackUtils().getItemStack(Material.QUARTZ_BLOCK, "§7White Mushroom", "100:15"));
	    this.customBlocksInventory.addItem(Main.getInstance().getItemStackUtils().getItemStack(Material.STAINED_CLAY, 4, "§7Tan Mushroom", "99:5"));
	    this.customBlocksInventory.addItem(Main.getInstance().getItemStackUtils().getItemStack(Material.STAINED_CLAY, 0, "§7Dotted Mushroom", "100:0"));
	    this.customBlocksInventory.addItem(Main.getInstance().getItemStackUtils().getItemStack(Material.QUARTZ_BLOCK, "§7Pure Marble", "99:14"));
	    this.customBlocksInventory.addItem(Main.getInstance().getItemStackUtils().getItemStack(Material.QUARTZ_BLOCK, "§7Smooth Marble", "99:2"));
	    this.customBlocksInventory.addItem(Main.getInstance().getItemStackUtils().getItemStack(Material.QUARTZ_BLOCK, "§7Marble Cobblestone", "99:1"));
	    this.customBlocksInventory.addItem(Main.getInstance().getItemStackUtils().getItemStack(Material.QUARTZ_BLOCK, "§7Marble Stone Bricks", "99:0"));
	    this.customBlocksInventory.addItem(Main.getInstance().getItemStackUtils().getItemStack(Material.STONE, "§7Light Stone", "99:3"));
	    this.customBlocksInventory.addItem(Main.getInstance().getItemStackUtils().getItemStack(Material.SMOOTH_BRICK, "§7Light Stone Bricks", "99:6"));
	    this.customBlocksInventory.addItem(Main.getInstance().getItemStackUtils().getItemStack(Material.STONE, "§7Light Gray Stone", "100:1"));
	    this.customBlocksInventory.addItem(Main.getInstance().getItemStackUtils().getItemStack(Material.STONE, "§7Gray Stone", "100:2"));
	    this.customBlocksInventory.addItem(Main.getInstance().getItemStackUtils().getItemStack(Material.STONE, "§7Dark Gray Stone", "100:3"));
	    this.customBlocksInventory.addItem(Main.getInstance().getItemStackUtils().getItemStack(Material.COAL_BLOCK, "§7Basalt", "99:8"));
	    this.customBlocksInventory.addItem(Main.getInstance().getItemStackUtils().getItemStack(Material.COAL_BLOCK, "§7Smooth Basalt", "99:10"));
	    this.customBlocksInventory.addItem(Main.getInstance().getItemStackUtils().getItemStack(Material.COAL_BLOCK, "§7Basalt Cobblestone", "99:9"));
	    this.customBlocksInventory.addItem(Main.getInstance().getItemStackUtils().getItemStack(Material.COAL_BLOCK, "§7Basalt Stone Bricks", "100:10"));
	    this.customBlocksInventory.addItem(Main.getInstance().getItemStackUtils().getItemStack(Material.COAL_BLOCK, "§7Basalt Chiseled Stone Bricks", "100:7"));
	    this.customBlocksInventory.addItem(Main.getInstance().getItemStackUtils().getItemStack(Material.WOOL, 15, "§7Void Block", "100:9"));
	    this.customBlocksInventory.addItem(Main.getInstance().getItemStackUtils().getItemStack(Material.DIRT, "§7Cracked Dirt", "100:8"));
	    this.customBlocksInventory.addItem(Main.getInstance().getItemStackUtils().getItemStack(Material.QUARTZ_BLOCK, "§7Bone Block", "100:6"));
	    this.customBlocksInventory.addItem(Main.getInstance().getItemStackUtils().getItemStack(Material.ENDER_STONE, "§7Endstone Bricks", "99:7"));
	    this.customBlocksInventory.addItem(Main.getInstance().getItemStackUtils().getItemStack(Material.WOOL, 14, "§7Flesh Block", "99:15"));
	    this.customBlocksInventory.addItem(Main.getInstance().getItemStackUtils().getItemStack(Material.NETHER_BRICK, "§7Bloody Nether Bricks", "100:5"));
	    this.customBlocksInventory.addItem(Main.getInstance().getItemStackUtils().getItemStack(Material.STAINED_CLAY, 14, "§7Magma Block", "99:4"));
	    this.customBlocksInventory.addItem(Main.getInstance().getItemStackUtils().getItemStack(Material.DIAMOND_BLOCK, "§7Essentia Block", "100:4"));
	    this.customBlocksInventory.addItem(Main.getInstance().getItemStackUtils().getItemStack(Material.WOOD, "§7Witchwood Planks", "97:3"));
	    this.customBlocksInventory.addItem(Main.getInstance().getItemStackUtils().getItemStack(Material.LOG, "§7Witchwood Log", "97:2"));
	    this.customBlocksInventory.addItem(Main.getInstance().getItemStackUtils().getItemStack(Material.LAPIS_ORE, "§7Mythril Ore", "97:0"));
	    this.customBlocksInventory.addItem(Main.getInstance().getItemStackUtils().getItemStack(Material.LAPIS_BLOCK, "§7Magic Wall", "97:1"));
	    this.customBlocksInventory.addItem(Main.getInstance().getItemStackUtils().getItemStack(Material.SEA_LANTERN, "§7Celestial Block", "97:5"));
	    this.customBlocksInventory.addItem(Main.getInstance().getItemStackUtils().getItemStack(Material.DIAMOND_ORE, "§7Celestial Ore", "97:4"));
	    this.customBlocksInventory.addItem(Main.getInstance().getItemStackUtils().getItemStack(Material.STEP, "§7Double Stone Slab", "43:8"));
	    this.customBlocksInventory.addItem(Main.getInstance().getItemStackUtils().getItemStack(Material.SANDSTONE, "§7Smooth Sandstone", "43:9"));
	    this.customBlocksInventory.addItem(Main.getInstance().getItemStackUtils().getItemStack(Material.RED_SANDSTONE, "§7Smooth Red Sandstone", "181:8"));
	    this.customBlocksInventory.addItem(Main.getInstance().getItemStackUtils().getItemStack(Material.LOG, "§7Full Sided Oak Log", "17:12"));
	    this.customBlocksInventory.addItem(Main.getInstance().getItemStackUtils().getItemStack(Material.LOG, 1, "§7Full Sided Spruce Log", "17:13"));
	    this.customBlocksInventory.addItem(Main.getInstance().getItemStackUtils().getItemStack(Material.LOG, 2, "§7Full Sided Birch Log", "17:14"));
	    this.customBlocksInventory.addItem(Main.getInstance().getItemStackUtils().getItemStack(Material.LOG, 3, "§7Full Sided Jungle Log", "17:15"));
	    this.customBlocksInventory.addItem(Main.getInstance().getItemStackUtils().getItemStack(Material.LOG_2, "§7Full Sided Acacia Log", "162:12"));
	    this.customBlocksInventory.addItem(Main.getInstance().getItemStackUtils().getItemStack(Material.LOG_2, 1, "§7Full Sided Dark Oak Log", "162:13"));
	    this.customBlocksInventory.addItem(Main.getInstance().getItemStackUtils().getItemStack(Material.MOB_SPAWNER, "§7Mob Spawner"));
	    this.customBlocksInventory.addItem(Main.getInstance().getItemStackUtils().getItemStack(Material.BARRIER, 1, "§7Barrier"));
	    this.customBlocksInventory.addItem(Main.getInstance().getItemStackUtils().getItemStack(Material.COMMAND, "§7Command Block"));
		
		return customBlocksInventory;
		
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {

		
		return new ArrayList<String>();
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e)
	{
		if (e.getItemInHand() == null) {
			return;
		}
		if (e.getItemInHand().getItemMeta().getLore() == null) {
			return;
		}
		if (e.getItemInHand().getItemMeta().getLore().get(0) == null) {
			return;
		}

		int id;
		int subid;

		try
		{
			String[] ids = ((String)e.getItemInHand().getItemMeta().getLore().get(0)).split(":");
			id = Integer.parseInt(ids[0]);
			subid = Integer.parseInt(ids[1]);
		}
		catch (Exception ex)
		{
			return;
		}
		Material mat = null;
		if (id == 99) {
			mat = Material.HUGE_MUSHROOM_1;
		} else if (id == 100) {
			mat = Material.HUGE_MUSHROOM_2;
		} else {
			mat = Material.getMaterial(id);
		}
		e.getBlockPlaced().setType(mat);
		e.getBlockPlaced().setData((byte)subid);
	}
	
}
