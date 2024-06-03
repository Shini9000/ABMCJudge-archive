package net.abmc.shini9000.JudgeMenus;

import net.abmc.shini9000.ABMCJudge;
import net.abmc.shini9000.Menu;
import net.abmc.shini9000.Utils.PlayerMenuUtils;
import net.abmc.shini9000.Utils.PlotUtils;
import net.abmc.shini9000.Utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.UUID;

public class JudgeMenu extends Menu {

    private final Utils utils = new Utils();

    Player p = playerMenuUtils.getOwner();

    public JudgeMenu(PlayerMenuUtils playerMenuUtils) {
        super(playerMenuUtils);
    }

    @Override
    public String getMenuName() {
        return ChatColor.DARK_PURPLE + "Judge Panel";
    }

    @Override
    public int getSlots() {
        return 9;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        boolean exists = false;

        if(PlotUtils.getId(p) != null){
            UUID uuid = UUID.nameUUIDFromBytes(PlotUtils.getId(p).toString().getBytes());
            exists =  ABMCJudge.getInstance().data.exists(uuid);
        }

        if (e.getCurrentItem() == null) return;

        switch (e.getCurrentItem().getType()) {
            case ENCHANTED_BOOK -> { new SubmittedMenu(playerMenuUtils).open(); break;}
            case MOJANG_BANNER_PATTERN -> {
//                if(PlotUtils.getId(p) != null && exists)
                    new JudgePlotInfoMenu(playerMenuUtils).open();
//                // remove v
//                else if (PlotUtils.getId(p) == null && !exists)
//                    new JudgePlotInfoMenu(playerMenuUtils).open();
//                // remove ^
//                else p.sendMessage(ChatColor.RED + "You must stand on a submitted plot");
            }
        }

        e.setCancelled(true);
    }

    @Override
    public void setMenuItems() {
        String idmsg;
        if(PlotUtils.getId(p) != null) idmsg = "Plot ID: " + PlotUtils.getId(p).toString();
        else idmsg = ChatColor.RED + "You must stand on a plot";

        inventory.setItem(3, utils.createGuiItem(Material.ENCHANTED_BOOK,
                ChatColor.GOLD + "Plot submissions", 1, ""));
        inventory.setItem(5, Utils.createGuiItem(Material.MOJANG_BANNER_PATTERN,
                ChatColor.YELLOW + "Current plot info", 1, idmsg));
    }
}