package com.schevenin.quarantineplugin.events;

import com.schevenin.quarantineplugin.QuarantinePlugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;

import java.util.Date;
import java.util.HashMap;

public class OnSleep implements Listener {

    private QuarantinePlugin plugin;

    public OnSleep(QuarantinePlugin plugin) {
        this.plugin = plugin;
    }

    private HashMap<Player, Long> sleep = new HashMap<>();

    /**
     * PLAYER SLEEPS
     * @param event
     */
    @EventHandler
    public void onSleep(PlayerBedEnterEvent event) {
        Player p = event.getPlayer();
        World world = p.getWorld();
        Location loc = p.getLocation();

        long time = new Date().getTime();
        sleep.put(p, time);

        // If there are multiple players online
        if (world.getPlayers().size() > 1) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                if(p.isSleeping()) {
                    p.sleep(loc, true);
                    p.getLocation().getWorld().setTime(0);
                    world.setStorm(false);
                    world.setThundering(false);
                    p.setBedSpawnLocation(p.getLocation());
                    world.getPlayers().forEach(player -> player.sendMessage(ChatColor.GREEN + p.getName() + ChatColor.RESET + " woke up with morning wood."));
                }
            }, 3);
        }
    }

    /**
     * PLAYER WAKES
     * @param event
     */
    @EventHandler
    public void onWake(PlayerBedLeaveEvent event) {
        if (sleep.containsKey(event.getPlayer())) {
            sleep.remove(event.getPlayer());
        }
    }
}
