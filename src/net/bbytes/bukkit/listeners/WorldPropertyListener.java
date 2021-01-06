package net.bbytes.bukkit.listeners;

import net.bbytes.bukkit.Main;
import net.bbytes.bukkit.world.HoneyfrostWorld;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.StructureGrowEvent;

public class WorldPropertyListener implements Listener {

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e){
         HoneyfrostWorld honeyfrostWorld = Main.getInstance().getWorldManager().getWorld(e.getWorld());
         if(honeyfrostWorld != null) if(honeyfrostWorld.getWorldProperties().isWeatherLock())
             e.setCancelled(true);
    }

    @EventHandler
    public void onBlockGravity(BlockPhysicsEvent e){
        HoneyfrostWorld honeyfrostWorld = Main.getInstance().getWorldManager().getWorld(e.getBlock().getWorld());
        if(honeyfrostWorld != null) if(!honeyfrostWorld.getWorldProperties().isBlockGravity()){
                if(e.getBlock().getType() == Material.GRAVEL || e.getBlock().getType() == Material.SAND)
                    e.setCancelled(true);
            }

    }

    @EventHandler
    public void onBlockFromTo(BlockFromToEvent e){
        HoneyfrostWorld honeyfrostWorld = Main.getInstance().getWorldManager().getWorld(e.getBlock().getWorld());
        if(honeyfrostWorld != null) if(!honeyfrostWorld.getWorldProperties().isLiquidFlow()){
            if(e.getBlock().isLiquid()) e.setCancelled(true);
        }
    }

    @EventHandler
    public void onTNTExplode(EntityExplodeEvent e){
        if(e.getEntityType().equals(EntityType.PRIMED_TNT)){
            HoneyfrostWorld honeyfrostWorld = Main.getInstance().getWorldManager().getWorld(e.getLocation().getWorld());
            if(honeyfrostWorld != null) if(!honeyfrostWorld.getWorldProperties().isTntExplode()){
                e.setCancelled(true);
            }
        }

    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent e){
        HoneyfrostWorld honeyfrostWorld = Main.getInstance().getWorldManager().getWorld(e.getLocation().getWorld());
        if(honeyfrostWorld != null) if(!honeyfrostWorld.getWorldProperties().isMobSpawn()){
            if(e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL)
                e.setCancelled(true);
        }
    }

    @EventHandler
    public void onGrassSpread(BlockSpreadEvent e){
        HoneyfrostWorld honeyfrostWorld = Main.getInstance().getWorldManager().getWorld(e.getBlock().getWorld());
        if(honeyfrostWorld != null) if(!honeyfrostWorld.getWorldProperties().isGrowth()){
            if(e.getSource().getType() == Material.GRASS || e.getSource().getType() == Material.MYCEL)
                e.setCancelled(true);
        }
    }

    @EventHandler
    public void onGrassSpread(BlockFormEvent e) {

        HoneyfrostWorld honeyfrostWorld = Main.getInstance().getWorldManager().getWorld(e.getBlock().getWorld());
        if (honeyfrostWorld != null) {
            if (!honeyfrostWorld.getWorldProperties().isSnowFall()) {
                if(e.getNewState().getType() == Material.SNOW
                || e.getNewState().getType() == Material.ICE)
                    e.setCancelled(true);
            }
            if (!honeyfrostWorld.getWorldProperties().isLiquidFlow()) {
                if(e.getNewState().getType() == Material.OBSIDIAN
                        || e.getNewState().getType() == Material.COBBLESTONE
                        || e.getNewState().getType() == Material.STONE)
                    e.setCancelled(true);
            }

        }
    }

    @EventHandler
    public void onTreeGrow(StructureGrowEvent e){
        HoneyfrostWorld honeyfrostWorld = Main.getInstance().getWorldManager().getWorld(e.getWorld());
        if(honeyfrostWorld != null) if(!honeyfrostWorld.getWorldProperties().isGrowth()){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlantGrow(BlockGrowEvent e){
        HoneyfrostWorld honeyfrostWorld = Main.getInstance().getWorldManager().getWorld(e.getBlock().getWorld());
        if(honeyfrostWorld != null) if(!honeyfrostWorld.getWorldProperties().isGrowth()){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onLeavesDecay(LeavesDecayEvent e){
        HoneyfrostWorld honeyfrostWorld = Main.getInstance().getWorldManager().getWorld(e.getBlock().getWorld());
        if(honeyfrostWorld != null) if(!honeyfrostWorld.getWorldProperties().isLeafDecay()){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockFade(BlockFadeEvent e){
        if(e.getBlock().getType().equals(Material.ICE)
        || e.getBlock().getType().equals(Material.PACKED_ICE)
        || e.getBlock().getType().equals(Material.FROSTED_ICE)
        || e.getBlock().getType().equals(Material.SNOW)
        || e.getBlock().getType().equals(Material.SNOW_BLOCK)){
            HoneyfrostWorld honeyfrostWorld = Main.getInstance().getWorldManager().getWorld(e.getBlock().getWorld());
            if(honeyfrostWorld != null) if(!honeyfrostWorld.getWorldProperties().isIceSnowMelting()){
                e.setCancelled(true);
            }
        }

    }

}
