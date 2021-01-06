package net.bbytes.bukkit.version.wrappers;


import com.mojang.authlib.GameProfile;
import net.bbytes.bukkit.Main;
import net.bbytes.bukkit.version.VersionWrapper;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Wrapper1_12_R1 implements VersionWrapper {

    @Override
    public ItemStack setNBT(ItemStack item, String key, Object value) {
        net.minecraft.server.v1_12_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound itemCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
        if(value instanceof String) itemCompound.set(key, new NBTTagString((String)value));
        else if(value instanceof Integer) itemCompound.set(key, new NBTTagInt((int)value));
        else if(value instanceof Double) itemCompound.set(key, new NBTTagDouble((Double)value));
        else if(value instanceof Long) itemCompound.set(key, new NBTTagLong((Long)value));
        else if(value instanceof Byte) itemCompound.set(key, new NBTTagByte((Byte)value));
        else if(value instanceof Boolean) itemCompound.set(key, new NBTTagByte((byte) ((boolean)value ? 0x1 : 0x0)));

        nmsItem.setTag(itemCompound);
        return CraftItemStack.asBukkitCopy(nmsItem);
    }

    @Override
    public Object getNBT(ItemStack item, String key){
        net.minecraft.server.v1_12_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound itemCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
        NBTBase base = itemCompound.get(key);

        if(base instanceof NBTTagInt) return itemCompound.getInt(key);
        else if(base instanceof NBTTagString) return itemCompound.getString(key);
        else if(base instanceof NBTTagLong) return itemCompound.getLong(key);
        else if(base instanceof NBTTagByte) return itemCompound.getByte(key);
        else return null;

    }

    @Override
    public void sendChatComponent(Player player, String json) {
        IChatBaseComponent comp = IChatBaseComponent.ChatSerializer
                .a(json);
        PacketPlayOutChat ppoc = new PacketPlayOutChat(comp);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(ppoc);
    }

    @Override
    public void setClientName() {
        Main.getInstance().CLIENTNAME = "BUILD12";
        Main.getInstance().setSubversion(12);
    }

    @Override
    public Material getMaterial(String material, boolean legacy) {
        return Material.getMaterial(material);
//        try {
//            return (Material) Material.class.getDeclaredMethod("getMaterial", String.class).invoke(material);
//        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        return null;
    }

    @Override
    public GameProfile getProfile(Player player) {
        return ((CraftPlayer) player).getHandle().getProfile();
    }


}
