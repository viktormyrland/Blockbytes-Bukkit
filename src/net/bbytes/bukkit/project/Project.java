package net.bbytes.bukkit.project;

import net.bbytes.bukkit.Main;
import net.bbytes.bukkit.warp.Warp;
import net.bbytes.bukkit.world.HoneyfrostWorld;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Project {

    private UUID uuid;
    private String name = "Unnamed Project";
    private String projectManager = "Unknown";
    private ItemStack displayItem = new ItemStack(Material.BOOKSHELF);
    private List<UUID> memberList = new ArrayList<>();

    public Project(UUID uuid){
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public List<HoneyfrostWorld> getWorlds() {
        List<HoneyfrostWorld> returnList = new ArrayList<>();
        for(HoneyfrostWorld world : Main.getInstance().getWorldManager().getWorldList())
            if(world.getProjectID() != null)
                if(world.getProjectID().toString().equals(uuid.toString()))
                    returnList.add(world);
            return returnList;
    }

    public void saveProject(){
        ExecutorService ex = Executors.newSingleThreadExecutor();
        ex.execute(() -> {
            saveProject_Sync();
        });
        ex.shutdown();
    }

    public void saveProject_Sync(){
        System.out.println("Saving " + this.name + "...");
        ResultSet rs = Main.getInstance().getMySQLManager().mysql.getMySQL().query("SELECT UUID FROM Projects WHERE UUID='" + this.uuid + "';");
        try {
            if(rs.next()){
                Main.getInstance().getMySQLManager().mysql.getMySQL().queryUpdate("UPDATE Projects SET " +
                        "Name='" + this.name + "', " +
                        "ProjectLead='" + this.projectManager + "', " +
                        "DisplayItem='" + Main.getInstance().getItemStackUtils().serializeItemStack(this.displayItem) + "' " +
                        "WHERE UUID='" + this.uuid + "';");
            }else{
                Main.getInstance().getMySQLManager().mysql.getMySQL().queryUpdate("INSERT INTO Projects (" +
                        "Name, ProjectLead, DisplayItem, UUID, TimeCreated) VALUES (" +
                        "'" + getName() + "', " +
                        "'" + getProjectManager() + "', " +
                        "'" + Main.getInstance().getItemStackUtils().serializeItemStack(getDisplayItem()) + "', " +
                        "'" + getUUID() + "', " +
                        System.currentTimeMillis() + ");");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static Project getProject(String uuid){
        return Main.getInstance().getProjectManager().getProject(uuid);
    }


    public UUID getUUID() {
        return uuid;
    }

    public ItemStack getDisplayItem() {
        return displayItem;
    }

    public void setDisplayItem(ItemStack displayItem) {
        this.displayItem = displayItem;
    }

    public List<UUID> getMemberList() {
        return memberList;
    }

    public boolean canAccess(UUID uuid){
        for(UUID u : memberList) {
            if (u.toString().equals(uuid.toString()))
                return true;
        }

        Player player = Bukkit.getPlayer(uuid);
        if(player != null){
            if(player.hasPermission("honeyfrost.trusted"))
                return true;
        }
        return false;
    }

    public void addMember(UUID uuid){
        memberList.removeIf(u -> uuid.toString().equals(u.toString()));
        memberList.add(uuid);
        Main.getInstance().getMySQLManager().mysql.update("REPLACE INTO AccessList (UUID, Project) VALUES ('" + uuid+"', '" + this.uuid + "');");
    }

    public void removeMember(UUID uuid){
        memberList.removeIf((uuid1 -> uuid1.toString().equals(uuid.toString())));
        Main.getInstance().getMySQLManager().mysql.update("DELETE FROM AccessList WHERE " +
                "UUID='" + uuid + "' AND Project='" +this.uuid + "';");
        Player p = Bukkit.getPlayer(uuid);
        if(p != null){
            HoneyfrostWorld world = HoneyfrostWorld.getWorld(p.getWorld().getName());
            if(world != null){
                if(world.getProject() != null) if(world.getProject() == this){
                    if(!this.canAccess(uuid)){
                        p.teleport(Main.getInstance().getSpawnLocation());
                        p.sendMessage("§cError: §4You do not have access to this world");
                        p.sendTitle("§cNo Access", "", 4, 40, 4);
                    }
                }
            }
        }
    }

    public List<Warp> getWarpsInProject(){
        List<Warp> warpList = new ArrayList<>();
        for(Warp warp : Main.getInstance().getWarpManager().getWarpList())
            if(warp.getHoneyfrostWorld() != null)
                if(warp.getHoneyfrostWorld().getProject() == this) {
                    warpList.add(warp);
                }
        return warpList;
    }

    public void loadAccess_Sync() throws SQLException {
        ResultSet rs = Main.getInstance().getMySQLManager().mysql.getMySQL().query("SELECT UUID FROM AccessList WHERE Project='" + this.uuid.toString() + "';");

        while(rs.next()){
            addMember(UUID.fromString(rs.getString("UUID")));
        }
    }

    public void setMemberList(List<UUID> memberList) {
        this.memberList = memberList;
    }

    public String getShortID(){
        return this.uuid.toString().substring(0, this.uuid.toString().indexOf("-"));
    }


}
