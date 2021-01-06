package net.bbytes.bukkit.version;

import com.mojang.authlib.GameProfile;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface VersionWrapper {

    public ItemStack setNBT(ItemStack item, String key, Object value);
    public Object getNBT(ItemStack item, String key);

    public void sendChatComponent(Player player, String json);

    public void setClientName();

    public Material getMaterial(String material, boolean legacy);

    public GameProfile getProfile(Player player);

}
