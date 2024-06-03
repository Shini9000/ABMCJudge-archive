package net.abmc.shini9000.JudgeMenus;

import net.abmc.shini9000.Menu;
import net.abmc.shini9000.Utils.PlayerMenuUtils;
import net.abmc.shini9000.Utils.PlotUtils;
import net.abmc.shini9000.Utils.SQLUtils;
import net.abmc.shini9000.Utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class DenyMenu extends Menu {

    private final Utils utils = new Utils();
    private final SQLUtils sqlUtils = new SQLUtils();

    public DenyMenu(PlayerMenuUtils playerMenuUtils) {
        super(playerMenuUtils);
    }

    @Override
    public String getMenuName() {
        return ChatColor.DARK_RED + "Deny plot";
    }

    @Override
    public int getSlots() {
        return 9;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if (e.getCurrentItem() == null) return;

        switch (e.getCurrentItem().getType()) {
            case GREEN_CONCRETE -> {
                p.closeInventory();
                p.sendMessage(ChatColor.GOLD + "Plot Denied!");
                sqlUtils.setPlotStatus(PlotUtils.getId(p).toString(), "DENIED");
            }
            case RED_CONCRETE -> new JudgePlotInfoMenu(playerMenuUtils).open();
        }

        e.setCancelled(true);
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(3, utils.createGuiItem(Material.GREEN_CONCRETE,
                ChatColor.GREEN + "Confirm", 1));
        inventory.setItem(5, Utils.createGuiItem(Material.RED_CONCRETE,
                ChatColor.RED + "Cancel", 1));
    }
}