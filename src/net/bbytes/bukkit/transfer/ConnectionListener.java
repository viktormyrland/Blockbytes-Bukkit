package net.bbytes.bukkit.transfer;


import net.bbytes.bukkit.Main;
import net.bbytes.bukkit.project.Project;
import net.bbytes.bukkit.world.HoneyfrostWorld;
import net.bbytes.bukkit.world.HoneyfrostWorldType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class ConnectionListener implements Runnable {


    private long disconnected = -1;
    @Override
    public void run() {
        while (Main.getInstance().isEnabled()) {
            readInputStream();
        }
    }

    public void readInputStream(){
        int initialByte = -1;
        short length = -1;
        try {

            DataInputStream dis = new DataInputStream(Main.getInstance().getBbConnector().getSocket().getInputStream());
            initialByte = dis.readByte();
            if(disconnected != -1) disconnected = -1;

            if(initialByte == 0x05 || initialByte == -1 || initialByte == 0x00) return;


            length = dis.readShort();
//           System.out.println("Received 0x" + Integer.toHexString(initialByte) + ", length: " + length);


            byte[] b = new byte[length];
            while(dis.available() < length){

                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            dis.readFully(b);

            try(DataInputStream in = new DataInputStream(new ByteArrayInputStream(b))){
                switch(initialByte){
                    case 0x01:{
                        Main.getInstance().getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[HFTransfer] Authenticated");
                        Main.getInstance().getBbConnector().setDisconnected(false);
                        break;
                    }
                    case 0x02:{
                        String worldID = in.readUTF();
                        String displayname = in.readUTF();
                        ItemStack displayItem = Main.getInstance().getItemStackUtils().deserializeItemStack(in.readUTF(), true);
                        HoneyfrostWorldType type = HoneyfrostWorldType.valueOf(in.readUTF());
                        World.Environment environment = World.Environment.valueOf(in.readUTF());
                        Project project = Project.getProject(in.readUTF());
                        long seed = in.readLong();

                        new File(Bukkit.getWorldContainer() + "/" + worldID + "/uid.dat").delete();

                        HoneyfrostWorld world = Main.getInstance().getWorldManager().newWorld();
                        world.setFileWorldName(worldID);
                        world.setDisplayname(displayname);
                        world.setDisplayItem(displayItem);
                        world.getWorldProperties().setHoneyfrostWorldType(type);
                        world.getWorldProperties().setEnvironment(environment);
                        if(project != null)world.setProjectID(project.getUUID());
                        world.getWorldProperties().setSeed(seed);
                        break;
                    }
                    case 0x03:{
                        Response response = Response.valueOf(in.readUTF());
                        UUID playerID = UUID.fromString(in.readUTF());

                        Player player = Bukkit.getPlayer(playerID);
                        if(player != null){
                            switch(response){
                                case SERVER_DOWN:{
                                    player.sendMessage("§cError: §4World could not be transferred: Target server is down.");
                                    break;
                                }
                                case COPY_FAILED:{
                                    player.sendMessage("§cError: §4World could not be transferred: COPY_FAILED");
                                    break;
                                }
                                case SUCCESS:{
                                    String worldID = in.readUTF();
                                    HoneyfrostWorld world = HoneyfrostWorld.getWorld(worldID);
                                    player.sendMessage(Main.getInstance().PREFIX + "The world §b" + world.getDisplayname() + " §6was transferred successfully.");
                                    break;
                                }
                            }


                        }



                        break;
                    }
                    case 0x04:{
                        String identifier = in.readUTF();
                        String player = in.readUTF();
                        String message = in.readUTF();
                        Bukkit.broadcastMessage("§bDiscord §b§l" + identifier + " §3» §7" + player + " §8» §r" + message);

                        break;
                    }
                }
            }



        } catch (IOException ignored) {
            if(disconnected == -1) disconnected = System.currentTimeMillis();
            if(System.currentTimeMillis() - disconnected > 3000) Main.getInstance().getBbConnector().setDisconnected(true);
        }
    }


}