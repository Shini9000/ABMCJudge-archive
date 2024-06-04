package net.abmc.shini9000.Utils;

import com.plotsquared.bukkit.util.BukkitUtil;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.PlotId;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlotUtils {

    public PlotUtils(){
    }

    public static PlotId getId(OfflinePlayer player){
        PlotPlayer p = BukkitUtil.adapt((Player) player);
        Plot plot = p.getCurrentPlot();

        if(plot == null) return null;

        if(plot.hasOwner()) return plot.getId();

        return null;
    }

    public static String getWorld(OfflinePlayer player){
        PlotPlayer p = BukkitUtil.adapt((Player) player);
        Plot plot = p.getCurrentPlot();
        if(plot.getWorldName().equals("plots")){ return "plots;";}
//        if(plot.getWorldName().equals("plotworld")){ return "plotworld;";}
//        if(plot.getWorldName().equals("smallplots")){ return "smallplots;";}
        if(plot.getWorldName().equals("plots")){ return "world;";}
        else return null;
    }

    public static String printId(Player player, PlotId id){
        PlotPlayer p = BukkitUtil.adapt(player);
        Plot plot = p.getCurrentPlot();

        if(id == null) return "You must be in a plot to use this!";
        if(plot.isOwner(p.getUUID())){
            //return "Plot ID: " + id.toString();
            return "Plot ID: " + plot.getWorldName().toString() + ";" + id.toString();
        } else {
            return "You must be in your own plot!";
        }
    }

}