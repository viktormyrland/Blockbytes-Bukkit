package net.bbytes.bukkit.world;

import net.bbytes.bukkit.Main;
import net.bbytes.bukkit.message.Message;
import net.bbytes.bukkit.project.Project;
import org.apache.commons.io.FileUtils;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class WorldManager {

    private List<HoneyfrostWorld> worldList = new ArrayList<>();
    private WorldRecycleBin recycleBin = new WorldRecycleBin();

    public WorldManager(){
        File dir = new File(Main.getInstance().getDataFolder() + "/downloads");
        if(dir.isDirectory()){
            for(File file : dir.listFiles()) {
                try {
                    FileUtils.deleteDirectory(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        dir = new File(Main.getInstance().getDataFolder() + "/uploads");
        if(dir.isDirectory()){
            for(File file : dir.listFiles()) {
                try {
                    if(file.isDirectory()) FileUtils.deleteDirectory(file);
                    else file.delete();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        dir = new File(Main.getInstance().getDataFolder() + "/readyToUpload");
        if(dir.isDirectory()){
            for(File file : dir.listFiles()) {
                try {
                    if(file.isDirectory()) FileUtils.deleteDirectory(file);
                    else file.delete();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void saveWorlds(){
        if(worldList.size() == 0)return;

        Map<String, Object> data = new HashMap<String, Object>();
        for(HoneyfrostWorld hw : worldList)
            data.put(hw.getFileWorldName(), hw.serialize());

        DumperOptions options = new DumperOptions();
        options.setIndent(2);
        options.setPrettyFlow(true);
        options.setSplitLines(false);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        Yaml yaml = new Yaml(options);

        try {
            StringWriter stringWriter = new StringWriter();
            yaml.dump(data, stringWriter);
            OutputStreamWriter stream = new OutputStreamWriter(new FileOutputStream(Main.getInstance().getDataFolder() + "/worlds.yml"), StandardCharsets.UTF_8);
            stream.write(stringWriter.toString());
            stream.flush();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadWorlds(){
        Yaml yaml = new Yaml();
        File f = new File(Main.getInstance().getDataFolder() + "/worlds.yml");
        if(!f.exists())return;

        try {
            InputStream inputStream = new FileInputStream(f);

            Map<String, Object> map = yaml.load(inputStream);
            List<String>  worlds = new ArrayList<>(map.keySet());
            for(String key : worlds){
                newWorld((Map<String, Object>) map.get(key), key);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        new WorldCreator("plotworld").generator("PlotSquared").createWorld();


    }


    public void createNewWorld(Player sender, HoneyfrostWorldType type){
        createNewWorld(sender, type, null);
    }

    public void createNewWorld(Player sender, HoneyfrostWorldType type, Project project){
        HoneyfrostWorld honeyfrostWorld = Main.getInstance().getWorldManager().newWorld();
        honeyfrostWorld.getWorldProperties().setHoneyfrostWorldType(type);
        honeyfrostWorld.getWorldProperties().setSeed(new Random().nextLong());
        if(project != null) honeyfrostWorld.setProjectID(project.getUUID());
        generateName(honeyfrostWorld);

        if(type == HoneyfrostWorldType.VOID)
            honeyfrostWorld.setDisplayItem(new ItemStack(Material.BARRIER));
        else if(type == HoneyfrostWorldType.NORMAL)
            honeyfrostWorld.setDisplayItem(new ItemStack(Material.STONE));
        if(type == HoneyfrostWorldType.FLAT)
            honeyfrostWorld.setDisplayItem(new ItemStack(Material.GRASS));

        sender.sendMessage(Message.NEW_WORLD_CREATED.getWithPrefix(sender).replace("{type}", "§b" + type.name() + "§6"));

    }

    public void generateName(HoneyfrostWorld honeyfrostWorld){
        String type = honeyfrostWorld.getWorldProperties().getHoneyfrostWorldType().name();
        type = type.substring(0, 1) + type.substring(1).toLowerCase();

        honeyfrostWorld.setDisplayname(generateName(honeyfrostWorld.getProject(), "New " + type + " world"));
    }

    public String generateName(Project project, String expression){
        int i = 1;
        for(HoneyfrostWorld world : worldList) {
            if ((project != null ? world.getProjectIDStringNotNull().equals(project.getUUID().toString()) : world.getProjectID() == null))
                if (world.getDisplayname().startsWith(expression))
                    i++;
        }
        return expression + " " + i;
    }


    public HoneyfrostWorld newWorld(){
        return newWorld(null, null);
    }

    public HoneyfrostWorld newWorld(Map<String, Object> map){
        return newWorld(map, null);
    }

    public HoneyfrostWorld newWorld(Map<String, Object> map, String name){
        HoneyfrostWorld hw = (map != null ? new HoneyfrostWorld(map, name) : new HoneyfrostWorld());
        worldList.add(hw);
        return hw;
    }

    public List<HoneyfrostWorld> getWorldList() {
        return worldList;
    }

    public HoneyfrostWorld getWorld(String worldID){
        for(HoneyfrostWorld world : worldList)
            if(world.getFileWorldName().equals(worldID))
                return world;
            return null;
    }

    public HoneyfrostWorld getWorld(World w){
        for(HoneyfrostWorld world : worldList)
            if(world.getLoadedWorld() == w)
                return world;
        return null;
    }

    public List<HoneyfrostWorld> getUncategorizedWorlds(){
        List<HoneyfrostWorld> worldList = new ArrayList<>();
        for(HoneyfrostWorld world : this.worldList) {
            if (world.getProject() != null)
                    continue;
            worldList.add(world);
        }

        return worldList;
    };

    public WorldRecycleBin getRecycleBin() {
        return recycleBin;
    }
}
