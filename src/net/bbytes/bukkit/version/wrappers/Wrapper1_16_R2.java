package net.bbytes.bukkit.version.wrappers;


import com.mojang.authlib.GameProfile;
import net.bbytes.bukkit.Main;
import net.bbytes.bukkit.version.VersionWrapper;
import net.minecraft.server.v1_16_R2.*;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R2.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Wrapper1_16_R2 implements VersionWrapper {

    @Override
    public ItemStack setNBT(ItemStack item, String key, Object value) {
        net.minecraft.server.v1_16_R2.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);

        NBTTagCompound itemCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
        if(value instanceof String) itemCompound.setString(key, (String)value);
        else if(value instanceof Integer) itemCompound.setInt(key, (int)value);
        else if(value instanceof Double) itemCompound.setDouble(key, (Double)value);
        else if(value instanceof Long) itemCompound.setLong(key, (Long)value);
        else if(value instanceof Byte) itemCompound.setByte(key, (Byte)value);
        else if(value instanceof Boolean) itemCompound.setByte(key, (byte) ((boolean)value ? 0x1 : 0x0));

        nmsItem.setTag(itemCompound);
        return CraftItemStack.asBukkitCopy(nmsItem);
    }

    @Override
    public Object getNBT(ItemStack item, String key){
        net.minecraft.server.v1_16_R2.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
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

    }

    @Override
    public void setClientName() {
        Main.getInstance().CLIENTNAME = "BUILD16";
        Main.getInstance().setSubversion(16);
    }

    @Override
    public org.bukkit.Material getMaterial(String material, boolean legacy) {
        Material mat =  Material.getMaterial(material);
        if(mat == null) mat = Material.STONE;
        return mat;
//        try {
//            return (org.bukkit.Material) Material.class.getDeclaredMethod("getMaterial", String.class, Boolean.class).invoke(null, material, legacy);
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
