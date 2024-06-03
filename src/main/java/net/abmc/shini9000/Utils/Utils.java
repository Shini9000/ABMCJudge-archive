package net.abmc.shini9000.Utils;

import net.abmc.shini9000.ABMCJudge;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.event.LuckPermsEvent;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.track.Track;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.ChatPaginator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {

    private static ABMCJudge plugin = ABMCJudge.getInstance();
    private final LPUtils lpUtils = new LPUtils(plugin.luckPerms);
    private final SQLUtils sqlUtils = new SQLUtils();
    private final LuckPerms api = LuckPermsProvider.get();

    public Utils() {

    }

    // Nice little method to create a gui item with a custom name, and description
    public static ItemStack createGuiItem(Material material, String name, int amount, String... lore) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        String l = ChatColor.BLUE + String.join(" ", lore);

        List<String> metalore = Arrays.asList(ChatPaginator.wordWrap(l, 50));

        // Set the name of the item
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(metalore);

        item.setItemMeta(meta);

        return item;
    }

    public ItemStack getHead(Player p, String... lore){
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();

        meta.setOwningPlayer(p);
        meta.setDisplayName(p.getName());

        String plotStatus = sqlUtils.getPlotStatus(sqlUtils.getPlotID(p));
        String playerGroup = String.valueOf(lpUtils.getPlayerGroup(p));

        List<String> loreList = new ArrayList<>();
        loreList.add(PlotUtils.printId(p, PlotUtils.getId(p)));
        loreList.add(ChatColor.GRAY + "Status: " + ChatColor.GOLD + plotStatus);
        loreList.add(ChatColor.GRAY + "Rank:" + ChatColor.GOLD + playerGroup);

        meta.setLore(loreList);
        skull.setItemMeta(meta);

        return skull;
    }

    public ItemStack getHead(Player p, String id){
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();


        meta.setOwningPlayer(p);
        meta.setDisplayName(p.getName());

        List<String> l = new ArrayList<>();
        l.add(id);
        if(PlotUtils.getId(p) != null) {
            l.add(ChatColor.GRAY + "Status: " + ChatColor.GOLD + sqlUtils.getPlotStatus(id));
            l.add(ChatColor.GRAY + "Rank: " + ChatColor.GOLD + lpUtils.getPlayerGroup(p));
        }
        meta.setLore(l);
        skull.setItemMeta(meta);

        return skull;
    }

    public ItemStack getHead(OfflinePlayer player, String id){
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
//        User user = api.getUserManager().getUser(player.getUniqueId());
//        Track track = api.getTrackManager().getTrack("judge");
//        Group group = api.getGroupManager().getGroup("default");

        meta.setOwningPlayer(player);
        meta.setDisplayName(player.getName());

        List<String> l = new ArrayList<>();
        l.add(id);
        l.add(ChatColor.GRAY + "Status: " + ChatColor.GOLD + sqlUtils.getPlotStatus(id));
        //l.add(ChatColor.GRAY + "Rank: " + ChatColor.GOLD + lpUtils.getPlayerGroup(player));
        l.add(ChatColor.GRAY + "Rank: " + ChatColor.GOLD + lpUtils.getPlayerGroup(player));

        meta.setLore(l);
        skull.setItemMeta(meta);

        return skull;
    }

}