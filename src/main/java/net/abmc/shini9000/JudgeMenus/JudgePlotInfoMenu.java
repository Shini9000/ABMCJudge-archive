package net.abmc.shini9000.JudgeMenus;

import net.abmc.shini9000.Menu;
import net.abmc.shini9000.Utils.PlayerMenuUtils;
import net.abmc.shini9000.Utils.PlotUtils;
import net.abmc.shini9000.Utils.SQLUtils;
import net.abmc.shini9000.Utils.Utils;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;

public class JudgePlotInfoMenu extends Menu {

    private final SQLUtils sqlUtils = new SQLUtils();
    private final Utils utils = new Utils();

    LuckPerms lp = LuckPermsProvider.get();

    Player p = playerMenuUtils.getOwner();

    public JudgePlotInfoMenu(PlayerMenuUtils playerMenuUtils) {
        super(playerMenuUtils);
    }

    @Override
    public String getMenuName() {
        return ChatColor.DARK_BLUE + "Plot info";
    }

    @Override
    public int getSlots() {
        return 9;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        p = (Player) e.getWhoClicked();
        OfflinePlayer ptarget = sqlUtils.getPlayerByID(PlotUtils.getId(p).toString());
        List<String> status = new ArrayList<>();
        status.add(sqlUtils.getPlotStatus(PlotUtils.getId(p).toString()));

        if (e.getCurrentItem() == null) return;

        switch (e.getCurrentItem().getType()) {
            case PAPER -> {
                p.closeInventory();
                p.performCommand("pcomment");
            }
            case LIME_CONCRETE -> {
                if(status.contains("PENDING")) {
//                    // this was the old method but required the judge to know the rank first and if that player was
//                    // offline that would be a pain and require /lp user X info perms and some judges would be confused
//                    new RankMenu(playerMenuUtils).open();
//                    p.closeInventory();
//                    //p.performCommand("lp user "+name+" parent add novice");
//                    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
//                    String command = "lp user " + ptarget.getName() + " promote judge";
//                    Bukkit.dispatchCommand(console, command);
//                    p.sendMessage(ChatColor.GOLD + "Plot Accepted!");
//                    sqlUtils.setPlotStatus(PlotUtils.getId(p).toString(), ChatColor.GREEN + "ACCEPTED");
                    p.closeInventory();
                    //p.performCommand("lp user "+name+" parent add novice");
                    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                    String command = "lp user " + ptarget.getName() + " promote judge";
                    Bukkit.dispatchCommand(console, command);
                    p.sendMessage(ChatColor.GOLD + "Plot Accepted!");
                    sqlUtils.setPlotStatus(PlotUtils.getId(p).toString(), "ACCEPTED");
                }
                else if(status.contains("NOT SUBMITTED")) p.sendMessage(ChatColor.RED + "This plot has not been submitted");
                else if(status.contains("ACCEPTED")) p.sendMessage(ChatColor.RED + "This plot has already been accepted");
                else if(status.contains("DENIED")) p.sendMessage(ChatColor.RED + "This plot has already been denied");
            }
            case GREEN_CONCRETE -> {
                if(status.contains("PENDING")) {
                    new RankMenu(playerMenuUtils).open();
                }
                else if(status.contains("NOT SUBMITTED")) p.sendMessage(ChatColor.RED + "This plot has not been submitted");
                else if(status.contains("ACCEPTED")) p.sendMessage(ChatColor.RED + "This plot has already been accepted");
                else if(status.contains("DENIED")) p.sendMessage(ChatColor.RED + "This plot has already been denied");
            }
            case RED_CONCRETE -> {
                if(status.contains("PENDING")) {
                    new DenyMenu(playerMenuUtils).open();
                }
                else if(status.contains("NOT SUBMITTED")) p.sendMessage(ChatColor.RED + "This plot has not been submitted");
                else if(status.contains("ACCEPTED")) p.sendMessage(ChatColor.RED + "This plot has already been accepted");
                else if(status.contains("DENIED")) p.sendMessage(ChatColor.RED + "This plot has already been denied");
            }
        }

        e.setCancelled(true);
    }

    @Override
    public void setMenuItems() {
        OfflinePlayer player = sqlUtils.getPlayerByID(PlotUtils.getId(p).toString());
        inventory.setItem(0, utils.getHead(player, PlotUtils.getId(p).toString()));
        inventory.setItem(6, Utils.createGuiItem(Material.LIME_CONCRETE,
                ChatColor.GREEN + "Accept plot", 1));
        inventory.setItem(2, Utils.createGuiItem(Material.OAK_SIGN,
                ChatColor.GOLD + "Plot title:", 1,
                sqlUtils.getPlotTitle(PlotUtils.getId(p).toString())));
        inventory.setItem(3, Utils.createGuiItem(Material.BOOK,
                ChatColor.GOLD + "Plot lore:", 1,
                sqlUtils.getPlotLore(PlotUtils.getId(p).toString())));
        inventory.setItem(4, Utils.createGuiItem(Material.PAPER,
                ChatColor.LIGHT_PURPLE + "Judges comment:", 1,
                sqlUtils.getPlotComment(PlotUtils.getId(p).toString())));
        inventory.setItem(8, Utils.createGuiItem(Material.RED_CONCRETE,
                ChatColor.RED + "Deny plot", 1));
        inventory.setItem(7, Utils.createGuiItem(Material.GREEN_CONCRETE,
                ChatColor.RED + "Rank override", 1, ChatColor.RED + "USE CAREFULLY!"));
    }
}