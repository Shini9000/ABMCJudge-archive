package net.abmc.shini9000;

import net.abmc.shini9000.Commands.*;
import net.abmc.shini9000.Listeners.MenuListener;
import net.abmc.shini9000.SQL.MySQL;
import net.abmc.shini9000.SQL.SQLCreate;
import net.abmc.shini9000.Utils.LPUtils;
import net.abmc.shini9000.Utils.PlayerMenuUtils;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.HashMap;

public final class ABMCJudge extends JavaPlugin implements Listener {

    public SQLCreate data;
    public MySQL SQL;
    public LuckPerms luckPerms;

    private static final HashMap<Player, PlayerMenuUtils> playerMenuUtilsMap = new HashMap<>();

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("Enabling ABMCJudge!");
        Bukkit.getConsoleSender().sendMessage("Version: " + this.getPluginMeta().getVersion());

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        new Submit(this);
        new Judge(this);
        new PlotTitle(this);
        new PlotLore(this);
        new PlotComment(this);
        new MenuListener(this);

        // Load an instance of 'LuckPerms' using the services' manager.
        this.luckPerms = getServer().getServicesManager().load(LuckPerms.class);

        new LPUtils(this.luckPerms);

        this.SQL = new MySQL();
        this.data = new SQLCreate(this);

        try {
            SQL.openConnection();
        } catch (ClassNotFoundException | SQLException e) {
            Bukkit.getLogger().info("Database not connected.");
        }

        try {
            if (SQL.checkConnection()){
                Bukkit.getLogger().info("Database is connected.");
                data.createTable();
                this.getServer().getPluginManager().registerEvents(this, this);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("Disabling kSJudge!");
        try {
            SQL.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ABMCJudge getInstance() {
        return ABMCJudge.getPlugin(ABMCJudge.class);
    }

    //Provide a player and return a menu system for that player
    //create one if they don't already have one
    public static PlayerMenuUtils getPlayerMenuUtils(Player p) {
        PlayerMenuUtils playerMenuUtils;
        if (!(playerMenuUtilsMap.containsKey(p))) { //See if the player has a playermenutils "saved" for them

            //This player doesn't. Make one for them and add it to the hashmap
            playerMenuUtils = new PlayerMenuUtils(p);
            playerMenuUtilsMap.put(p, playerMenuUtils);

            return playerMenuUtils;
        } else {
            return playerMenuUtilsMap.get(p); //Return the object by using the provided player
        }
    }

}