package net.bbytes.bukkit.inventory;

import net.bbytes.bukkit.Main;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum GUIItem {

	PLUGIN_INFO("Honeyfrost Worlds", Material.ANVIL),

	PROJECTS_PLOT_WORLD("Plot World", Material.GRASS),
	PROJECTS_SPAWN("Spawn", Material.ENDER_PEARL),
	PROJECTS_UNCATEGORIZED_WORLDS("Uncategorized Worlds", Material.CHEST),
	PROJECTS_NEW_PROJECT("New Project", Material.GOLD_AXE),
	PROJECTS_RECYCLE_BIN("Recycle Bin", Material.CAULDRON_ITEM),

	PROJECT_PLACEHOLDER("PROJECT_PLACEHOLDER", Material.BOOKSHELF),
	PROJECT_EDIT("Edit Project", Material.DIAMOND_AXE),
	PROJECT_NEW_WORLD("New World", Material.IRON_AXE),

	PROJECT_DELETE("§4Delete Project", Material.BARRIER),
	PROJECT_CONFIRM_DELETE("§4Confirm Delete Project", Material.BARRIER),
	PROJECT_EDIT_ACCESS("Edit Membership", Material.BOOK),
	PROJECT_EDIT_NAME("Edit Name", Material.NAME_TAG),
	PROJECT_EDIT_LEAD("Edit Project Lead", Material.NAME_TAG),

	WORLD_PLACEHOLDER("WORLD_PLACEHOLDER", Material.LOG),
	WORLD_DOWNLOAD("Download World", Material.BEACON),
	WORLD_TRANSFER("Transfer World", Material.GLOWSTONE_DUST),
	WORLD_EDIT_NAME("Edit Name", Material.NAME_TAG),
	WORLD_DELETE("§4Delete World", Material.BARRIER),
	WORLD_CONFIRM_DELETE("§4Confirm Delete World", Material.BARRIER),
	WORLD_MOVE_PROJECT("Change Project", Material.BOOK),
	WORLD_CLONE("Clone World", Material.BOOK_AND_QUILL),

	WORLD_MOVE_UNASSIGN("Unassign World", Material.BARRIER),

	WORLD_TRANSFER_BUILD15("Transfer to 1.15", new ItemStack(Material.PRISMARINE, 15, (short) 1)),
	WORLD_TRANSFER_BUILD16("Transfer to 1.16", new ItemStack(Material.PRISMARINE, 16, (short) 2)),

	WORLD_PROPERTY_TIMELOCK("Time Lock", Material.WATCH),
	WORLD_PROPERTY_WEATHERLOCK("Weather Lock", Main.getInstance().getItemStackUtils().getItemStack(Material.STAINED_GLASS, MaterialColor.LIGHT_BLUE)),
	WORLD_PROPERTY_MOB_SPAWN("Mob Spawn", new ItemStack(Material.MONSTER_EGG, 1, EntityType.ZOMBIE.getTypeId())),
	WORLD_PROPERTY_BLOCK_GRAVITY("Block Gravity", Material.GRAVEL),
	WORLD_PROPERTY_FIRE_SPREAD("Fire Spread", Material.FLINT_AND_STEEL),
	WORLD_PROPERTY_TNT_EXPLODE("TNT Explode", Material.TNT),
	WORLD_PROPERTY_PLANT_GROWTH("Plant Growth", new ItemStack(Material.LONG_GRASS, 1, (short) 1)),
	WORLD_PROPERTY_LIQUID_FLOW("Water & Lava Flow", Material.WATER_BUCKET),
	WORLD_PROPERTY_ICE_SNOW_MELT("Ice & Snow Melting", Material.PACKED_ICE),
	WORLD_PROPERTY_MOB_GRIEFING("Mob Griefing", new ItemStack(Material.MONSTER_EGG, 1, EntityType.CREEPER.getTypeId())),
	WORLD_PROPERTY_SNOW_FORMING("Snow Falling", Material.SNOW),
	WORLD_PROPERTY_LEAF_DECAY("Leaf Decay", Material.LEAVES),

	NEW_WORLD_VOID("Void World", Material.BARRIER),
	NEW_WORLD_FLAT("Flat World", Material.GRASS),
	NEW_WORLD_NORMAL("Normal World", Material.STONE),
	NEW_WORLD_UPLOAD("Upload World", Material.BEACON),
	NEW_WORLD_IMPORT("Import World", Material.BOOKSHELF),


	IMPORT_SELECT_PLACEHOLDER("IMPORT_SELECT_PLACEHOLDER", Material.CHEST),

	IMPORT_INFO_UPLOAD("World Settings", Material.BEACON),
	IMPORT_INFO_IMPORT("World Settings", Material.BOOKSHELF),
	IMPORT_TYPE_NORMAL("Normal World", Material.STONE),
	IMPORT_TYPE_FLAT("Flat World", Material.GRASS),
	IMPORT_TYPE_VOID("Void World", Material.BARRIER),
	IMPORT_ENV_OVERWORLD("Overworld", Material.GRASS),
	IMPORT_ENV_NETHER("Nether", Material.NETHERRACK),
	IMPORT_ENV_THE_END("The End", Material.ENDER_STONE),
	IMPORT_ENABLED("§aEnabled", Main.getInstance().getItemStackUtils().getItemStack(Material.INK_SACK, MaterialColor.LIME)),
	IMPORT_DISABLED("§cDisabled", Main.getInstance().getItemStackUtils().getItemStack(Material.INK_SACK, MaterialColor.DARK_GRAY)),
	IMPORT_CREATE("Create World", Material.IRON_AXE),


	WARP_INFO("Warp Menu", Material.ENDER_PEARL),
	WARP_UNCATEGORIZED("Uncategorized Warps", Material.CHEST),
	WARP_PLACEHOLDER("WARP_PLACEHOLDER", Material.ENDER_PEARL),

	ACCESS_PLACEHOLDER("ACCESS_PLACEHOLDER", Material.PAPER),
	ACCESS_NEW("New Member", Material.BOOK_AND_QUILL),

	GENERIC_PAGES("§ePage: §6{page}/{pages}", Material.PAPER),
	GENERIC_PAGE_NEXT("§eNext Page", Material.ARROW),
	GENERIC_PAGE_PREV("§ePrevious Page", Material.ARROW),
	GENERIC_GO_BACK("§aGo Back", Main.getInstance().getItemStackUtils().getItemStack(Material.INK_SACK, 1)),
	GENERIC_BORDER("§7", Main.getInstance().getItemStackUtils().getItemStack(Material.STAINED_GLASS_PANE, MaterialColor.BLACK)),
	GENERIC_EMPTY("§cEmpty", Material.BEDROCK),

	NOTHING("NOTHING", Material.AIR);


	private String displayname;
	private ItemStack item;
	private Moveable clickable = Moveable.FALSE;


	GUIItem(String displayname, ItemStack item) {
		this.displayname = displayname;
		this.item = item;
	}

	GUIItem(String displayname, ItemStack item, Moveable clickable) {
		this.displayname = displayname;
		this.item = item;
		this.clickable = clickable;
	}

	GUIItem(String displayname, Material item) {
		this.displayname = displayname;
		this.item = new ItemStack(item);
	}

	GUIItem(String displayname, Material item, Moveable clickable) {
		this.displayname = displayname;
		this.item = new ItemStack(item);
		this.clickable = clickable;
	}

	public String getDisplayname() {
		return (displayname.startsWith("§") ? displayname : "§6" + displayname);
		//return HiddenStringUtils.encodeString("WCITEM;" + this.toString() + ";") + displayname;
	}

	public Moveable getMoveable() {
		return clickable;
	}

	public ItemStack getItem() {
		ItemStack i = Main.getInstance().getInventoryManager().applyGUIItemTags(item, this);

		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(getDisplayname());
		i.setItemMeta(meta);
		return i;
	}

	public GUIItem getItem(String enumName) {
		for (GUIItem wcItem : values()) {
			if (wcItem.name().equalsIgnoreCase(enumName))
				return wcItem;
		}
		return null;
	}

	public enum Moveable {
		TRUE,FALSE
	}

}
