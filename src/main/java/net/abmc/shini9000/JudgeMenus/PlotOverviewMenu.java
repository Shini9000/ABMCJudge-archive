package net.abmc.shini9000.JudgeMenus;

import net.abmc.shini9000.ABMCJudge;
import net.abmc.shini9000.Menu;
import net.abmc.shini9000.Utils.PlayerMenuUtils;
import net.abmc.shini9000.Utils.PlotUtils;
import net.abmc.shini9000.Utils.SQLUtils;
import net.abmc.shini9000.Utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.io.File;

public class PlotOverviewMenu extends Menu {

    private final SQLUtils sqlUtils = new SQLUtils();
    private final Utils utils = new Utils();

    public PlotOverviewMenu(PlayerMenuUtils playerMenuUtils) {
        super(playerMenuUtils);
    }

    @Override
    public String getMenuName() {
        return ChatColor.AQUA + "Plot Overview";
    }

    @Override
    public int getSlots() {
        return 9;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if (e.getCurrentItem() == null) return;

        switch (e.getCurrentItem().getType()){
            case WRITABLE_BOOK -> {
                new PlayerPlotsMenu(playerMenuUtils).open();
                break;
            }
            case BOOK -> {
                if(PlotUtils.getId(p) != null){ new PlotInfo(playerMenuUtils).open();}
                else p.sendMessage( ChatColor.RED + "This is not your plot");
                break;
            }
            case ENCHANTED_BOOK -> {
                p.sendMessage( ChatColor.RED + "This feature is not available yet");
                break;
            }
            case GREEN_CONCRETE -> {
                if(PlotUtils.getId(p) != null){
                    new SubmissionMenu(playerMenuUtils).open();
                }
                else p.sendMessage( ChatColor.RED + "This is not your plot");
                break;
            }
        }

        e.setCancelled(true);
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(0, utils.getHead(playerMenuUtils.getOwner()));
        inventory.setItem(2, utils.createGuiItem(Material.WRITABLE_BOOK,
                ChatColor.YELLOW + "Currently submitted plots", 1));
        if(PlotUtils.getId(playerMenuUtils.getOwner()) != null){
            inventory.setItem(4, utils.createGuiItem(Material.BOOK,
                    ChatColor.YELLOW + "Plot details", 1, "Title: "
                            + sqlUtils.getPlotTitle(PlotUtils.getId(playerMenuUtils.getOwner()).toString())));
        } else {
            inventory.setItem(4, utils.createGuiItem(Material.BOOK,
                    ChatColor.YELLOW + "Plot details", 1, "You must be on your plot"));
        }
        inventory.setItem(6, utils.createGuiItem(Material.ENCHANTED_BOOK,
                ChatColor.DARK_GREEN + "Plot history", 1, ""));
        inventory.setItem(8, utils.createGuiItem(Material.GREEN_CONCRETE,
                ChatColor.DARK_GREEN + "Submit Plot", 1, ""));
    }
}