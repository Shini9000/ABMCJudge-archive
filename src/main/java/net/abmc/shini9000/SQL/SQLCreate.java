package net.abmc.shini9000.SQL;

import net.abmc.shini9000.ABMCJudge;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SQLCreate {

    private final ABMCJudge plugin;
    public SQLCreate(ABMCJudge plugin) {
        this.plugin = plugin;
    }

    public void createTable(){
        PreparedStatement ps;
        try{
            ps = plugin.SQL.getConnection().prepareStatement(
                    "CREATE TABLE IF NOT EXISTS plotsubmit "
                            + "(NAME VARCHAR(100),UUID VARCHAR(100),PLOTID VARCHAR(100),PLOTTITLE VARCHAR(100),PLOTLORE VARCHAR(6000),"
                            + "STATUS VARCHAR(50),COMMENT VARCHAR(6000),PRIMARY KEY(PLOTID))");
            ps.executeUpdate();
            Bukkit.getConsoleSender().sendMessage("[kSJudge table created]");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createPlot(String id, Player player, String title, String lore, String status, String comment){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT * FROM plotsubmit WHERE PLOTID=?");
            ps.setString(1, id);
            ResultSet results = ps.executeQuery();
            results.next();
            UUID uuid = UUID.nameUUIDFromBytes(id.getBytes());
            if (!exists(uuid)){
                PreparedStatement ps2 = plugin.SQL.getConnection().prepareStatement(
                        "INSERT IGNORE INTO plotsubmit " + "(NAME,UUID,PLOTID,PLOTTITLE,PLOTLORE,STATUS,COMMENT) VALUES (?,?,?,?,?,?,?)");
                ps2.setString(1,player.getName());
                ps2.setString(2, uuid.toString());
                ps2.setString(3, id);
                ps2.setString(4, title);
                ps2.setString(5, lore);
                ps2.setString(6, status);
                ps2.setString(7, comment);
                ps2.executeUpdate();
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean exists(UUID uuid){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT * FROM plotsubmit WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet results = ps.executeQuery();
            return results.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}