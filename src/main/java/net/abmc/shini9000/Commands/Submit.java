package net.abmc.shini9000.Commands;

import com.plotsquared.bukkit.util.BukkitUtil;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.PlotId;
import net.abmc.shini9000.ABMCJudge;
import net.abmc.shini9000.JudgeMenus.PlotOverviewMenu;
import net.abmc.shini9000.Utils.PlotUtils;
import net.abmc.shini9000.Utils.SQLUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Submit implements CommandExecutor {

    private final SQLUtils sqlUtils = new SQLUtils();
    private ABMCJudge plugin;

    public Submit(ABMCJudge plugin) {
        this.plugin = plugin;
        plugin.getCommand("submit").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){

        if (!(sender instanceof Player)) { this.plugin.getConfig().getString("Console.error"); return true;}

        Player player = (Player) sender;
        if(!player.hasPermission("abmcjudge.submit")) {
            player.sendMessage(ChatColor.GRAY + "Lacking permission: " + ChatColor.GOLD + "abmcjudge.submit");
            return true;
        }

        PlotId id = PlotUtils.getId(player);

        if(id == null) {
            player.sendMessage(ChatColor.RED + "You must be standing inside a plot");
            return true;
        }

        PlotPlayer p = BukkitUtil.adapt(player);

        boolean isOwner = Bukkit.getOfflinePlayer(p.getCurrentPlot().getOwner()).getName() == player.getName();

        if (!isOwner) {
            player.sendMessage(ChatColor.RED + "You must be standing inside your own plot");
            return true;
        }

        sqlUtils.setPlotTable(player);

        sqlUtils.addPlotID(id.toString());
        new PlotOverviewMenu(ABMCJudge.getPlayerMenuUtils(player)).open();
        return true;
    }

}