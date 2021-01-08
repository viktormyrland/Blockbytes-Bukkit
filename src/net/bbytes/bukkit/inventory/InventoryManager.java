package net.bbytes.bukkit.inventory;

import net.bbytes.bukkit.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class InventoryManager {

    public static final String ITEM_KEY_NAME = "BlockbytesItem";


    public InventoryManager(){
        // Assigning a unique ID to every GUIInventory
        int i = 0;
        for(GUIInventory inv : GUIInventory.values()) {
            inv.setID(i); i++;
        }

    }

    public boolean isGUIItem(ItemStack item) {
        return getGUIItem(item) != null;
    }

    public GUIItem getGUIItem(ItemStack item) {
//        net.minecraft.server.v1_12_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
//        NBTTagCompound itemCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
//        String keyValue = itemCompound.getString(ITEM_KEY_NAME);

        String keyValue = (String) Main.getInstance().getWrapper().getNBT(item, ITEM_KEY_NAME);

        for(GUIItem guiItem : GUIItem.values())
            if(guiItem.name().equalsIgnoreCase(keyValue))
                return guiItem;
        return null;
    }



    public ItemStack applyGUIItemTags(ItemStack itemStack, GUIItem guiItem){
//        net.minecraft.server.v1_12_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(itemStack);
//        NBTTagCompound itemCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
//        itemCompound.set("HoneyfrostItem", new NBTTagString(guiItem.name()));
//        nmsItem.setTag(itemCompound);
//        return CraftItemStack.asBukkitCopy(nmsItem);
        return Main.getInstance().getWrapper().setNBT(itemStack, ITEM_KEY_NAME, guiItem.name());
    }
    private int getID(InventoryView view) {
        return Integer.parseInt(Main.getInstance().getHiddenStringUtils().extractHiddenString(view.getTitle()));
    }

//    public GUIInventory getGUIInventory(InventoryView view){
//        int ID;
//        try{
//            ID = getID(view);
//        }catch(NullPointerException | NumberFormatException e){
//            return null;
//        }
//
//        for(GUIInventory guiInv : GUIInventory.values()){
//            if(guiInv.getID() == ID)
//                return guiInv;
//        }
//
//        return null;
//    }

    public GUIInventory getGUIInventory(Inventory inv){
        for(int i = 0; i < inv.getSize(); i++){
            String str = (String) Main.getInstance().getWrapper().getNBT(inv.getItem(i), "BlockbytesGUI");
            if(str != null){
                GUIInventory guiInventory = GUIInventory.get(str);
                return guiInventory;
            }
        }
        return null;
    }

    public Object getNBT(ItemStack item, String key){
        return Main.getInstance().getWrapper().getNBT(item, key);

    }

    public void updateInventory(Player p, Inventory inv) {
        Inventory pInv = p.getOpenInventory().getTopInventory();


        if(pInv.getSize() != inv.getSize()) {
            Bukkit.getScheduler().callSyncMethod(Main.getInstance(), () -> p.openInventory(inv));
            return;
        }


        for(int i = 0; i < pInv.getSize(); i++) {
            ItemStack pItem = pInv.getItem(i);
            ItemStack item = inv.getItem(i);
            if(pItem != item) {
                p.getOpenInventory().getTopInventory().setItem(i, item);
            }
        }
    }

    public void updateItemsInInventory(Player p, Inventory inv, GUIInventory inventory) {

        inv = applyGUIInventory(inv, inventory);
        Inventory pInv = p.getOpenInventory().getTopInventory();

        if(pInv == null ) {
            return;
        }

        if(pInv.getSize() != inv.getSize()) {
            return;
        }


        try {
            for (int i = 0; i < pInv.getSize(); i++) {
                ItemStack pItem = pInv.getItem(i);
                ItemStack item = inv.getItem(i);
                if (pItem != item) {
                    p.getOpenInventory().getTopInventory().setItem(i, item);
                }
            }
        }catch(ArrayIndexOutOfBoundsException ignored){
        }

    }

    public Inventory applyGUIInventory(Inventory inv, GUIInventory guiInventory) {
        for(int i = 0; i < inv.getSize(); i++)
            if(inv.getItem(i) != null)
                if(inv.getItem(i).getType() != Material.AIR){
                    inv.setItem(i, Main.getInstance().getWrapper().setNBT(inv.getItem(i), "BlockbytesGUI", guiInventory.name()));
                    break;
                }


        return inv;
    }
}
