package net.bbytes.bukkit.project;

import net.bbytes.bukkit.Main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProjectManager {

    private List<Project> projectList = new ArrayList<>();

    public void loadProjects(){
        ExecutorService ex = Executors.newSingleThreadExecutor();
        ex.execute(() -> {
            try {
                loadProjects_Sync();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        ex.shutdown();
    }

    public void loadProjects_Sync() throws SQLException {
        projectList.clear();
        ResultSet rs = Main.getInstance().getMySQLManager().mysql.getMySQL().query("SELECT * FROM Projects ORDER BY TimeCreated ASC;");

        while(rs.next()){
            Project project = newProject(UUID.fromString(rs.getString("UUID")));
            project.setName(rs.getString("Name"));
            project.setProjectManager(rs.getString("ProjectLead"));
            project.setDisplayItem(Main.getInstance().getItemStackUtils().deserializeItemStack(rs.getString("DisplayItem")));
        }

        rs.close();
        for(Project project : projectList)
            project.loadAccess_Sync();

    }

    public void saveProjects(){
        ExecutorService ex = Executors.newSingleThreadExecutor();
        ex.execute(this::saveProjects_Sync);
        ex.shutdown();
    }

    public void saveProjects_Sync(){
        for(Project project : projectList){
            project.saveProject_Sync();
        }

    }

    public Project getProject(String uuid){
        for(Project p : projectList)
            if(p.getUUID().toString().equals(uuid))
                return p;
        return null;

    }
    public Project getProject(UUID uuid){
        return getProject(uuid.toString());
    }

    public int getProjectIndex(UUID uuid){
        for(int i = 0; i < projectList.size(); i++)
            if(projectList.get(i).getUUID().toString().equals(uuid.toString()))
                return i;
            return -1;
    }

    public Project newProject(){
        Project project = newProject(UUID.randomUUID());
        project.saveProject();
        return project;
    }
    public Project newProject(UUID uuid){
        Project project = new Project(uuid);
        projectList.add(project);

        Main.getInstance().getRedisManager().publishMessage("UPDATE_PROJECTS", "");

        return project;
    }

    public void deleteProject(Project project) {
        Main.getInstance().getMySQLManager().mysql.update("DELETE FROM Projects WHERE UUID='" + project.getUUID() +"';");
        projectList.remove(project);
        Main.getInstance().getRedisManager().publishMessage("UPDATE_PROJECTS", "");
    }


    public List<Project> getProjectList() {
        return projectList;
    }


}
