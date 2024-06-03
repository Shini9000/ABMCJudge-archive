package net.abmc.shini9000.JudgeMenus;

import net.abmc.shini9000.Utils.PlayerMenuUtils;
import net.abmc.shini9000.Utils.SQLUtils;
import net.abmc.shini9000.Utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;

public class SubmittedMenu extends PaginatedMenu {

    private final SQLUtils sqlUtils = new SQLUtils();
    private final Utils utils = new Utils();

    public SubmittedMenu(PlayerMenuUtils playerMenuUtils) {
        super(playerMenuUtils);
    }

    @Override
    public String getMenuName() {
        return ChatColor.GOLD + "Submitted Plots";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        List<String> plots = sqlUtils.getSubmittedPlotID();

        if (e.getCurrentItem() == null) return;

        switch (e.getCurrentItem().getType()) {
            case PLAYER_HEAD -> { p.performCommand("p v " + String.join("", e.getCurrentItem().getItemMeta().getLore().get(0))); break;}
            case BARRIER -> { new JudgeMenu(playerMenuUtils).open(); break;}
            case ARROW, SPECTRAL_ARROW -> {
                if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Prev page")){
                    if (page == 0){
                        p.sendMessage(ChatColor.GRAY + "You are already on the first page.");
                    }else{
                        page = page - 1;
                        super.open();
                    }
                }else if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Next page")){
                    if (!((index + 1) >= plots.size())){
                        page = page + 1;
                        super.open();
                    }else{
                        p.sendMessage(ChatColor.GRAY + "You are on the last page.");
                    }
                }
                break;
            }
        }
    }

    @Override
    public void setMenuItems() {
        addMenuBorder();
        List<String> plots = sqlUtils.getSubmittedPlotID();
        if (plots == null && plots.isEmpty()) return;

        for (int i = 0; i < super.maxItemsPerPage; i++) {
            index = super.maxItemsPerPage * page + i;
            if(index >= plots.size()) break;
            if(plots.get(index) != null) {
                //Getting all submitted plots
                OfflinePlayer p = sqlUtils.getPlayerByID(plots.get(index));
                inventory.addItem(utils.getHead(p, plots.get(index)));
            }
        }
    }
}