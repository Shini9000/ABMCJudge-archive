package net.abmc.shini9000.Utils;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class LPUtils {
    private final LuckPerms lp;

    public LPUtils(LuckPerms lp){
        this.lp = lp;
    }

    public String getPlayerGroup(OfflinePlayer player){

        List<String> finalGroup = new ArrayList<>();

        getAllGroups(player).thenAcceptAsync(allGroups -> {
            for(int i = 0; i < allGroups.size(); i++){
                for(int j = 0; j < creativeGroups().size(); j++){
                    if(allGroups.get(i).contains(creativeGroups().get(j))){
                        finalGroup.add(allGroups.get(i));
                    }
                }
            }
        });
        return String.join("" + finalGroup);
    }

    public CompletableFuture<List<String>> getAllGroups(OfflinePlayer player){

        // Get a Bukkit player adapter.
        //PlayerAdapter<Player> playerAdapter = lp.getPlayerAdapter(Player.class);

        UserManager um = lp.getUserManager();

        CompletableFuture<User> userFuture = um.loadUser(player.getUniqueId());

        return userFuture.thenApplyAsync(user -> {
            Collection<Group> groups = user.getInheritedGroups(user.getQueryOptions());

            String groupsString = groups.stream().map(Group::getName).collect(Collectors.joining(", "));

            List<String> allGroups = Arrays.asList(groupsString.split(", "));

            return allGroups;
        });
        //User user = userFuture.join();

        // Get a LuckPerms user for the player.
        //User user = playerAdapter.getUser((Player) player);

        // Get all of the groups they inherit from on the current server.


        // Convert to a comma separated string (e.g. "admin, mod, default")
    }

    public List<String> creativeGroups(){
        List<String> creativeGroupsList = Arrays.asList("novice", "disciple", "mentor", "guru", "expert", "master");
        return creativeGroupsList;
    }
}