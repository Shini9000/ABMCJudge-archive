package net.abmc.shini9000.JudgeMenus;

import net.abmc.shini9000.ABMCJudge;
import net.abmc.shini9000.Menu;
import net.abmc.shini9000.Utils.PlayerMenuUtils;
import net.abmc.shini9000.Utils.PlotUtils;
import net.abmc.shini9000.Utils.SQLUtils;
import net.abmc.shini9000.Utils.Utils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;


public class PlotInfo extends Menu {

    private final SQLUtils sqlUtils = new SQLUtils();
    private final Utils utils = new Utils();

    Player p = playerMenuUtils.getOwner();

    public PlotInfo(PlayerMenuUtils playerMenuUtils) {
        super(playerMenuUtils);
    }

    @Override
    public String getMenuName() {
        return ChatColor.BLUE + "Plot Info";
    }

    @Override
    public int getSlots() {
        return 9;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        p = (Player) e.getWhoClicked();

        if (e.getCurrentItem() == null) return;

        switch (e.getCurrentItem().getType()) {
            case OAK_SIGN -> {
                p.closeInventory();
                TextComponent titleEdit = new TextComponent(ChatColor.GREEN + "[Edit plot title]");

                titleEdit.setClickEvent(new ClickEvent
                        (ClickEvent.Action.SUGGEST_COMMAND, "/ptitle "));
                titleEdit.setHoverEvent(new HoverEvent
                        (HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GRAY + "Click to edit title").create()));

                p.spigot().sendMessage(titleEdit);
                break;

            }
            case BOOK -> {

                p.closeInventory();
                p.performCommand("plore");
                break;
            }
            case BARRIER -> {
                p.closeInventory();
                new PlotOverviewMenu(ABMCJudge.getPlayerMenuUtils(p)).open();

            }
        }
        e.setCancelled(true);
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(1, utils.getHead(p));
        inventory.setItem(3, Utils.createGuiItem(Material.OAK_SIGN,
                ChatColor.GOLD + "Edit Title:", 1,
                sqlUtils.getPlotTitle(PlotUtils.getId(p).toString())));
        inventory.setItem(4, Utils.createGuiItem(Material.BOOK,
                ChatColor.GOLD + "Edit Lore:", 1,
                sqlUtils.getPlotLore(PlotUtils.getId(p).toString())));
        inventory.setItem(6, Utils.createGuiItem(Material.PAPER,
                ChatColor.LIGHT_PURPLE + "Judges comments:", 1,
                sqlUtils.getPlotComment(PlotUtils.getId(p).toString())));
        inventory.setItem(7, Utils.createGuiItem(Material.BARRIER,
                ChatColor.GRAY + "Back", 1));
    }
}