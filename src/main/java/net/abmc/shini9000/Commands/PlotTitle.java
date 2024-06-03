package net.abmc.shini9000.Commands;

import com.plotsquared.bukkit.util.BukkitUtil;
import com.plotsquared.core.player.PlotPlayer;
import net.abmc.shini9000.ABMCJudge;
import net.abmc.shini9000.Utils.PlotUtils;
import net.abmc.shini9000.Utils.SQLUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlotTitle implements CommandExecutor {

    private final SQLUtils sqlUtils = new SQLUtils();
    private ABMCJudge plugin;

    public PlotTitle(ABMCJudge plugin){
        this.plugin = plugin;
        plugin.getCommand("ptitle").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){

        if(!(sender instanceof Player)){ this.plugin.getConfig().getString("Console.error"); return true;}

        Player player = (Player) sender;

        if(!(player.hasPermission("abmcjudge.title"))) {
            player.sendMessage(ChatColor.GRAY + "Lacking permission: " + ChatColor.RED + "abmcjudge.title");
            return true;
        }

        if(PlotUtils.getId(player) == null) {
            player.sendMessage(ChatColor.RED + "You must be on your plot");
            return true;
        }

        PlotPlayer p = BukkitUtil.adapt(player);

        boolean isOwner = Bukkit.getOfflinePlayer(p.getCurrentPlot().getOwner()).getName() == player.getName();

        if (!isOwner) {
            player.sendMessage(ChatColor.RED + "You must stand on your plot");
            return true;
        }

        sqlUtils.setPlotTable(player);
        if (args.length == 0) player.sendMessage(ChatColor.GREEN + "Input a title for you plot: /ptitle [title]");

        List<String> title = new ArrayList<>();
        for (String s : args) {
            title.add(s);
            sqlUtils.addPlotTitle(PlotUtils.getId(player).toString(), String.join(" ", title));
        }

        player.sendMessage(ChatColor.GOLD + "Title updated");

        return true;
    }
}