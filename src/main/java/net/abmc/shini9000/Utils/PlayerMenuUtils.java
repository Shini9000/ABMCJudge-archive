package net.abmc.shini9000.Utils;

import org.bukkit.entity.Player;

/*
Companion class to all menus. This is needed to pass information across the entire
 menu system no matter how many inventories are opened or closed.
 Each player has one of these objects, and only one.
*/

public class PlayerMenuUtils {

    private Player owner;

    public PlayerMenuUtils(Player p) {
        this.owner = p;
    }

    public Player getOwner() {
        return owner;
    }
}