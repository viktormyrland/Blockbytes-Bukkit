package net.bbytes.bukkit.world;

import net.bbytes.bukkit.Main;
import net.bbytes.bukkit.inventory.GUIInventory;
import net.bbytes.bukkit.project.Project;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;

public class ImportWorldInfo {

    private HoneyfrostWorldType honeyfrostWorldType = HoneyfrostWorldType.NORMAL;
    private World.Environment environment = World.Environment.NORMAL;
    private String displayname = "Uploaded World";
    private Project project = null;
    private String worldID;
    private boolean upload = false;



    public HoneyfrostWorld createWorld(Player player){
        if(upload){
            File SRC = new File(Main.getInstance().getDataFolder() + "/readyToUpload/" + this.worldID);
            File DEST = new File(Bukkit.getWorldContainer() + "/" + this.worldID);

            try {
                FileUtils.copyDirectory(SRC, DEST);
            } catch (IOException e) {
                e.printStackTrace();
                player.sendMessage("§cFailed to upload world. See console for more info.");
                return null;
            }
        }


        HoneyfrostWorld honeyfrostWorld = new HoneyfrostWorld(this.worldID);
        Main.getInstance().getWorldManager().getWorldList().add(honeyfrostWorld);
        honeyfrostWorld.setDisplayname(this.displayname);
        if(this.upload)honeyfrostWorld.setDisplayItem(new ItemStack(Material.BEACON));
        honeyfrostWorld.getWorldProperties().setEnvironment(this.environment);
        honeyfrostWorld.getWorldProperties().setHoneyfrostWorldType(this.honeyfrostWorldType);

        if(project != null) {
            honeyfrostWorld.setProjectID(project.getUUID());
        }

        return honeyfrostWorld;
    }

    public void createWorldTeleport(Player player){
        createWorld(player).enterWorld(player);
    }

    public void createWorldOpenMenu(Player player){
        createWorld(player);

        if(project != null) {
            GUIInventory.PROJECT_OVERVIEW.display(player, project.getUUID().toString(), 1);
        }else{
            GUIInventory.WORLDS_UNCATEGORIZED.display(player, 1);
        }
    }

    public void cancelImport(Player player){
        File f = new File(Main.getInstance().getDataFolder() + "/readyToUpload/" + this.worldID);
        if(!f.exists())return;
        player.sendMessage(Main.getInstance().PREFIX + "§cWorld import cancelled.");
        try {
            FileUtils.deleteDirectory(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(player.getOpenInventory() != null)
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), player::closeInventory, 2);

    }

    public HoneyfrostWorldType getHoneyfrostWorldType() {
        return honeyfrostWorldType;
    }

    public void setHoneyfrostWorldType(HoneyfrostWorldType honeyfrostWorldType) {
        this.honeyfrostWorldType = honeyfrostWorldType;
    }

    public World.Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(World.Environment environment) {
        this.environment = environment;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getWorldID() {
        return worldID;
    }

    public void setWorldID(String worldID) {
        this.worldID = worldID;
    }

    public boolean isUpload() {
        return upload;
    }

    public void setUpload(boolean upload) {
        this.upload = upload;
    }

}
