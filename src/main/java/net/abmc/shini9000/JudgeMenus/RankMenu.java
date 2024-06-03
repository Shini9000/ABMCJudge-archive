package net.abmc.shini9000.JudgeMenus;

import net.abmc.shini9000.Menu;
import net.abmc.shini9000.Utils.PlayerMenuUtils;
import net.abmc.shini9000.Utils.PlotUtils;
import net.abmc.shini9000.Utils.SQLUtils;
import net.abmc.shini9000.Utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class RankMenu extends Menu {

    private final Utils utils = new Utils();
    private final SQLUtils sqlUtils = new SQLUtils();

    public RankMenu(PlayerMenuUtils playerMenuUtils) {
        super(playerMenuUtils);
    }

    @Override
    public String getMenuName() {
        return ChatColor.RED + "Ranks";
    }

    @Override
    public int getSlots() {
        return 18;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        OfflinePlayer ptarget = sqlUtils.getPlayerByID(PlotUtils.getId(p).toString());
        String name = ptarget.getName();

        if (e.getCurrentItem() == null) return;

        switch (e.getCurrentItem().getType()) {

            case ORANGE_BANNER -> {
                p.closeInventory();
                //p.performCommand("lp user "+name+" parent add novice");
                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                String command = "lp user " + ptarget.getName() + " permission set group.novice";
                Bukkit.dispatchCommand(console, command);
                p.sendMessage(ChatColor.GOLD + "Plot Accepted! Rank up to Novice");
                sqlUtils.setPlotStatus(PlotUtils.getId(p).toString(), "ACCEPTED");
                String command2 = "bcast " + ptarget.getName() + " has reached Novice! ";
                Bukkit.dispatchCommand(console, command2);

            }
            case BLUE_BANNER -> {
                p.closeInventory();
                //p.performCommand("lp user "+name+" parent add disciple");
                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                String command = "lp user " + ptarget.getName() + " permission set group.disciple";
                Bukkit.dispatchCommand(console, command);
                p.sendMessage(ChatColor.GOLD + "Plot Accepted! Rank up to Disciple");
                sqlUtils.setPlotStatus(PlotUtils.getId(p).toString(), "ACCEPTED");
                String command2 = "bcast " + ptarget.getName() + " has reached Disciple! ";
                Bukkit.dispatchCommand(console, command2);
            }
            case GREEN_BANNER -> {
                p.closeInventory();
                //p.performCommand("lp user "+name+" parent add mentor");
                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                String command = "lp user " + ptarget.getName() + " permission set group.mentor";
                Bukkit.dispatchCommand(console, command);
                p.sendMessage(ChatColor.GOLD + "Plot Accepted! Rank up to Mentor");
                sqlUtils.setPlotStatus(PlotUtils.getId(p).toString(), "ACCEPTED");
                String command2 = "bcast " + ptarget.getName() + " has reached Mentor! ";
                Bukkit.dispatchCommand(console, command2);
            }
            case CYAN_BANNER -> {
                p.closeInventory();
                //p.performCommand("lp user "+name+" parent add guru");
                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                String command = "lp user " + ptarget.getName() + " permission set group.guru";
                Bukkit.dispatchCommand(console, command);
                p.sendMessage(ChatColor.GOLD + "Plot Accepted! Rank up to Guru");
                sqlUtils.setPlotStatus(PlotUtils.getId(p).toString(), "ACCEPTED");
                String command2 = "bcast " + ptarget.getName() + " has reached Guru! ";
                Bukkit.dispatchCommand(console, command2);
            }
            case PURPLE_BANNER -> {
                p.closeInventory();
                //p.performCommand("lp user "+name+" parent add expert");
                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                String command = "lp user " + ptarget.getName() + " permission set group.expert";
                Bukkit.dispatchCommand(console, command);
                p.sendMessage(ChatColor.GOLD + "Plot Accepted! Rank up to Expert");
                sqlUtils.setPlotStatus(PlotUtils.getId(p).toString(), "ACCEPTED");
                String command2 = "bcast " + ptarget.getName() + " has reached Expert! ";
                Bukkit.dispatchCommand(console, command2);
            }
            case RED_BANNER -> {
                p.closeInventory();
                //p.performCommand("lp user "+name+" parent add master");
                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                String command = "lp user " + ptarget.getName() + " permission set group.master";
                Bukkit.dispatchCommand(console, command);
                p.sendMessage(ChatColor.GOLD + "Plot Accepted! Congrats on Master!");
                sqlUtils.setPlotStatus(PlotUtils.getId(p).toString(), "ACCEPTED");
                String command2 = "bcast " + ptarget.getName() + " has reached Master! ";
                Bukkit.dispatchCommand(console, command2);
            }
        }

        e.setCancelled(true);
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(2, utils.createGuiItem(Material.ORANGE_BANNER,
                ChatColor.GOLD + "Novice", 1));
        inventory.setItem(4, Utils.createGuiItem(Material.BLUE_BANNER,
                ChatColor.BLUE + "Disciple", 1));
        inventory.setItem(6, Utils.createGuiItem(Material.GREEN_BANNER,
                ChatColor.GREEN + "Mentor", 1));
        inventory.setItem(11, Utils.createGuiItem(Material.CYAN_BANNER,
                ChatColor.AQUA + "Guru", 1));
        inventory.setItem(13, Utils.createGuiItem(Material.PURPLE_BANNER,
                ChatColor.DARK_PURPLE + "Expert", 1));
        inventory.setItem(15, Utils.createGuiItem(Material.RED_BANNER,
                ChatColor.RED + "Master", 1));
    }
}