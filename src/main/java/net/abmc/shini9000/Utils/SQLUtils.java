package net.abmc.shini9000.Utils;

import net.abmc.shini9000.ABMCJudge;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SQLUtils {

    private final ABMCJudge plugin = ABMCJudge.getInstance();

    public SQLUtils() {

    }

    public void setPlotTable(Player p){
        if(PlotUtils.getId(p) != null) {
            String id = PlotUtils.getId(p).toString();
            plugin.data.createPlot(id, p, "EMPTY", "EMPTY", "NOT SUBMITTED", "EMPTY");
        }
    }

    public String getPlayerName(String id){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT NAME FROM plotsubmit WHERE PLOTID=?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            String name;
            if(rs.next()){
                name = rs.getString("NAME");
                return name;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return "n/a";
    }

    public OfflinePlayer getPlayerByID(String id){
        OfflinePlayer p = Bukkit.getServer().getPlayer(getPlayerName(id));
        if(p == null) {
            p = plugin.getServer().getOfflinePlayer(getPlayerName(id));
            return p;
        }
        return p;
    }

    public void addPlotID(String plotID){
        UUID uuid = UUID.nameUUIDFromBytes(plotID.getBytes());
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE plotsubmit SET PLOTID=? WHERE UUID=?");
            ps.setString(1, plotID);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public String getPlotID(Player p){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT PLOTID FROM plotsubmit WHERE NAME=?");
            ps.setString(1, p.getName());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getString("PLOTID");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return "n/a";
    }

    public List<String> getSubmittedPlotID(){
        List<String> id = new ArrayList<>();
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT PLOTID FROM plotsubmit WHERE STATUS=?");
            ps.setString(1, "PENDING");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                id.add(rs.getString("PLOTID"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return id;
    }

    public List<String> getSubmittedPlotID(Player p){
        List<String> id = new ArrayList<>();
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT PLOTID FROM plotsubmit WHERE STATUS=? AND NAME=?");
            ps.setString(1, "PENDING");
            ps.setString(2, p.getName());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                id.add(rs.getString("PLOTID"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return id;
    }

    public void addPlotTitle(String plotID, String plotTitle){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE plotsubmit SET PLOTTITLE=? WHERE PLOTID=?");
            ps.setString(1, plotTitle);
            ps.setString(2, plotID);
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public String getPlotTitle(String plotID){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT PLOTTITLE FROM plotsubmit WHERE PLOTID=?");
            ps.setString(1, plotID);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getString("PLOTTITLE");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return "n/a";
    }

    public void addPlotLore(String plotID, String plotLore){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE plotsubmit SET PLOTLORE=? WHERE PLOTID=?");
            ps.setString(1, plotLore);
            ps.setString(2, plotID);
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public String getPlotLore(String plotID){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT PLOTLORE FROM plotsubmit WHERE PLOTID=?");
            ps.setString(1, plotID);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getString("PLOTLORE");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return "n/a";
    }

    public void setPlotStatus(String plotID, String status){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE plotsubmit SET STATUS=? WHERE PLOTID=?");
            ps.setString(1, status);
            ps.setString(2, plotID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getPlotStatus(String plotID){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT STATUS FROM plotsubmit WHERE PLOTID=?");
            ps.setString(1, plotID);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getString("STATUS");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return "NOT SUBMITTED";
    }

    public void addPlotComment(String plotID, String plotComment){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE plotsubmit SET COMMENT=? WHERE PLOTID=?");
            ps.setString(1, plotComment);
            ps.setString(2, plotID);
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public String getPlotComment(String plotID){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT COMMENT FROM plotsubmit WHERE PLOTID=?");
            ps.setString(1, plotID);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getString("COMMENT");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return "n/a";
    }
}