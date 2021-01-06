package net.bbytes.bukkit.inventory.guis;

import net.bbytes.bukkit.Main;
import net.bbytes.bukkit.inventory.GUIItem;
import net.bbytes.bukkit.message.Message;
import net.bbytes.bukkit.warp.Warp;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class UncategorizedWarpsInventory extends BaseInventory {


    DecimalFormat decimalFormat = new DecimalFormat("0.00");

    @Override
    public Inventory getInventory(Player p, Object... options){
        setPlayer(p);
        int page = (int) options[0];
        Inventory inv = createInventory(6, getMessage(Message.WARP_MENU_TITLE));

        List<Warp> uncategorizedWarps = Main.getInstance().getWarpManager().getUncategorizedWarps();

        setItem(inv, itemStackUtils.getItemStack(GUIItem.WARP_UNCATEGORIZED, getMessage(Message.WARP_UNCATEGORIZED), new String[]{
                Message.FORMAT_DIVIDER.getRaw(),
                "§8§l» §7" + getMessage(Message.WORD_WARPS) + ": §b" + uncategorizedWarps.size(),
                "",
                "\\" + getMessage(Message.WARP_UNCATEGORIZED_INFO),
        }), 1, 5);


        setItem(inv, itemStackUtils.getItemStack(GUIItem.GENERIC_GO_BACK, getMessage(Message.GO_BACK)), 6, 1);


        List<ItemStack> itemStackList = new ArrayList<>();

        for(int i = (page-1)*28; i < uncategorizedWarps.size(); i++){
            Warp warp = uncategorizedWarps.get(i);
            ItemStack item = itemStackUtils.getItemStack(warp.getDisplayItem(), "§6" + warp.getName(), new String[]{
                    Message.FORMAT_DIVIDER.getRaw(),
                    "§8» §7" + getMessage(Message.WORD_WORLD) + ": §b" + (warp.getHoneyfrostWorld() != null ? warp.getHoneyfrostWorld().getDisplayname() : warp.getWorld()),
                    "§8» §7" + getMessage(Message.WORD_LOCATION) + ": §b("
                            + decimalFormat.format(warp.getLocation().getX()) + " "
                            + decimalFormat.format(warp.getLocation().getY()) + " "
                            + decimalFormat.format(warp.getLocation().getZ()) + ")",
                    "",
                    "§2§l» §a" + getMessage(Message.CLICK_WARP),
            });

            item = applyGUIItem(item, GUIItem.WARP_PLACEHOLDER);
            item = setNBT(item, "warp", warp.getName());
            itemStackList.add(item);

        }

        if(itemStackList.size() == 0)
            return null;

        for(int i = 0; i < 28; i++){
            if(i >= itemStackList.size()) break;
            setItem(inv, itemStackList.get(i), (i/7)+2, (i%7)+2);
        }

        int pages = ((itemStackList.size()-1)/28)+1 + (page-1);

        //  Project Paging

        ItemStack item = itemStackUtils.getItemStack(
                GUIItem.GENERIC_PAGES.getItem(),
                Message.PAGES.get(p).replace("{page}", "" + page).replace("{pages}", "" + pages));

        item = setNBT(item, "page", page);
        item.setAmount(Math.min(page, 64));

        setItem(inv, item, 6, 5);

        if(page < pages){
            item = itemStackUtils.getItemStack(GUIItem.GENERIC_PAGE_NEXT.getItem(), Message.PAGE_NEXT.get(p));
            item.setAmount(Math.min(page+1, 64));
            setItem(inv, item, 6, 7);
        }

        if(page > 1){
            item = itemStackUtils.getItemStack(GUIItem.GENERIC_PAGE_PREV.getItem(), Message.PAGE_PREVIOUS.get(p));
            item.setAmount(Math.min(page-1, 64));
            setItem(inv, item, 6, 3);
        }


        addBorder(inv);
        return inv;
    }


}
