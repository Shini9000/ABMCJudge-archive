package net.abmc.shini9000.Commands;

import com.plotsquared.bukkit.util.BukkitUtil;
import com.plotsquared.core.player.PlotPlayer;
import net.abmc.shini9000.ABMCJudge;
import net.abmc.shini9000.Utils.PlotUtils;
import net.abmc.shini9000.Utils.SQLUtils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PlotLore implements CommandExecutor {

    private final SQLUtils sqlUtils = new SQLUtils();
    private ABMCJudge plugin;

    public PlotLore(ABMCJudge plugin){
        this.plugin = plugin;
        plugin.getCommand("plore").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if(!(sender instanceof Player)){ this.plugin.getConfig().getString("Console.error"); return true;}

        Player player = (Player) sender;
        if(!(player.hasPermission("abmcjudge.plore"))) {
            player.sendMessage(ChatColor.GRAY + "Lacking permission: " + ChatColor.GOLD + "abmcjudge.plore");
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

        if (args.length == 0) {
            TextComponent loreAdd = new TextComponent(ChatColor.BLUE + "[Add lore]");
            TextComponent loreEdit = new TextComponent(ChatColor.GREEN + "[Edit lore] ");

            loreAdd.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/plore add "));
            loreAdd.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                    new ComponentBuilder(ChatColor.GRAY + "Click to add lore").create()));

            loreEdit.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/plore edit "));
            loreEdit.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                    new ComponentBuilder(ChatColor.GRAY + "Click to edit lore").create()));

            player.spigot().sendMessage(loreAdd, loreEdit);
            return true;
        }

        switch (args[0].toLowerCase()){
            case "add" -> {
                List<String> lore = new ArrayList<>();
                for (String s : args) {
                    lore.add(s);
                }
                lore.remove(0);
                sqlUtils.addPlotLore(PlotUtils.getId(player).toString(), String.join(" ", lore));
                player.sendMessage(ChatColor.GOLD + "Lore added");
                break;
            }
            case "edit" -> {
                List<String> loreadd = new ArrayList<>();
                List<String> orlore = new ArrayList<>();
                orlore.add(sqlUtils.getPlotLore(PlotUtils.getId(player).toString()));
                for (String s : args) {
                    loreadd.add(s);
                }
                loreadd.remove(0);
                List<String> addedlore = Stream.of(orlore, loreadd)
                        .flatMap(x -> x.stream())
                        .collect(Collectors.toList());
                sqlUtils.addPlotLore(PlotUtils.getId(player).toString(), String.join(" ", addedlore));
                player.sendMessage(ChatColor.GOLD + "Lore updated");
                break;
            }
        }

        return true;
    }
}