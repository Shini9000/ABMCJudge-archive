package net.abmc.shini9000.Commands;

import com.plotsquared.bukkit.util.BukkitUtil;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.PlotId;
import net.abmc.shini9000.ABMCJudge;
import net.abmc.shini9000.Utils.PlotUtils;
import net.abmc.shini9000.Utils.SQLUtils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PlotComment implements CommandExecutor {

    private final SQLUtils sqlUtils = new SQLUtils();
    private ABMCJudge plugin;

    public PlotComment(ABMCJudge plugin){
        this.plugin = plugin;
        plugin.getCommand("pcomment").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) { this.plugin.getConfig().getString("Console.error"); return true;}

        Player p = (Player) sender;
        if(!(p.hasPermission("abmcjudge.pcomment"))) {
            p.sendMessage(ChatColor.GRAY + "Lacking permission: " + ChatColor.GOLD + "abmcjudge.pcomment");
            return true;
        }

        if(PlotUtils.getId(p) == null) {
            p.sendMessage(ChatColor.RED + "You must stand on a plot");
            return true;
        }

        if(!isSubmit(p)) {
            p.sendMessage(ChatColor.RED + "This plot is not submitted");
            return true;
        }

        if (args.length == 0) {
            TextComponent comAdd = new TextComponent(ChatColor.GREEN + "[Add comment] ");
            TextComponent comEdit = new TextComponent(ChatColor.BLUE + "[Edit comment]");

            comAdd.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/pcomment add "));
            comAdd.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                    new ComponentBuilder(ChatColor.GRAY + "Click to add a comment").create()));

            comEdit.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/pcomment edit "));
            comEdit.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                    new ComponentBuilder(ChatColor.GRAY + "Click to edit a comment").create()));

            p.spigot().sendMessage(comEdit, comAdd);

            return true;
        }

        switch(args[0].toLowerCase()) {
            case "edit" -> {
                List<String> comment = new ArrayList<>();

                for (String s : args) {
                    comment.add(s);
                }

                comment.remove(0);
                sqlUtils.addPlotComment(PlotUtils.getId(p).toString(), String.join(" ", comment));
                p.sendMessage(ChatColor.GOLD + "Comment updated");
                break;
            }
            case "add" -> {
                List<String> comadd = new ArrayList<>();
                List<String> orcom = new ArrayList<>();

                orcom.add(sqlUtils.getPlotComment(PlotUtils.getId(p).toString()));

                for (String s : args) {
                    comadd.add(s);
                }

                comadd.remove(0);

                List<String> addedcom = Stream.of(orcom, comadd)
                        .flatMap(x -> x.stream())
                        .collect(Collectors.toList());

                sqlUtils.addPlotComment(PlotUtils.getId(p).toString(), String.join(" ", addedcom));
                p.sendMessage(ChatColor.GOLD + "Comment updated");
                break;
            }
        }

        return true;
    }

    public Boolean isSubmit(Player p){
        PlotPlayer pp = BukkitUtil.adapt(p);
        PlotId id = pp.getCurrentPlot().getId();

        if( sqlUtils.getSubmittedPlotID().contains(id.toString())){
            return true;
        } else return false;

    }
}