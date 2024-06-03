package net.abmc.shini9000.Commands;

import net.abmc.shini9000.ABMCJudge;
import net.abmc.shini9000.JudgeMenus.JudgeMenu;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Judge implements CommandExecutor {
    private ABMCJudge plugin;

    public Judge(ABMCJudge plugin){
        this.plugin = plugin;
        plugin.getCommand("judge").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            this.plugin.getConfig().getString("Console.error");
            return true;
        }

        Player player = (Player) sender;
        if(!(player.hasPermission("abmcjudge.judge"))) {
            player.sendMessage(ChatColor.GRAY + "Lacking permission: " + ChatColor.RED + "abmcjudge.judge");
            return true;
        }

        new JudgeMenu(plugin.getPlayerMenuUtils(player)).open();

        return true;
    }
}