package com.schevenin.quarantineplugin.events;

import com.schevenin.quarantineplugin.QuarantinePlugin;
import org.apache.commons.lang.ObjectUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;

import javax.lang.model.UnknownEntityException;

public class HoldingBeans implements Listener {


    private QuarantinePlugin plugin;

    public HoldingBeans(QuarantinePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void holdingBeans(PlayerItemHeldEvent holding) {
        try {
            Player p = holding.getPlayer();
            if (p.hasPermission("quarantineplugin.magicbeans")) {
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    try {
                        if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains(ChatColor.MAGIC + "" + ChatColor.BOLD + "MAGIC BEANS")) {
                            if (p.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
                                PotionEffectType effect = PotionEffectType.SPEED;
                                p.addPotionEffect(effect.createEffect(600, 1));
                                p.sendMessage(ChatColor.AQUA + "So much power!");
                            }
                        }
                    } catch (NullPointerException e) {}
                }, 3);
            } else {
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    try {
                        if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains(ChatColor.MAGIC + "" + ChatColor.BOLD + "MAGIC BEANS")) {
                            if (p.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
                                p.sendMessage(ChatColor.GRAY + "What do these magic beans do?");
                                p.getInventory().getItemInMainHand().removeEnchantment(Enchantment.SILK_TOUCH);
                            }
                        }
                    } catch (NullPointerException e) {}
                }, 3);
            }
        } catch (NullPointerException e) {}

    }
    @EventHandler
    public void movingWithBeans(PlayerMoveEvent move) {
        try {
            Player p = move.getPlayer();
            if (p.hasPermission("quarantineplugin.magicbeans")) {
                if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains(ChatColor.MAGIC + "" + ChatColor.BOLD + "MAGIC BEANS")) {
                    if (p.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
                        if ((move.getFrom().getY() < move.getTo().getY()) && (p.getLocation().subtract(0, 1, 0).getBlock().getType() != Material.AIR)) {
                            //SEND FLYING
                            p.setVelocity(p.getLocation().getDirection().multiply(2).setY(2));
                        }
                    }
                }
            }
        } catch (NullPointerException e) {}

    }
    @EventHandler
    public void takingFallDamageWithBeans(EntityDamageEvent damage) {
        try {
            if (damage.getEntity() instanceof Player) {
                Player p = (Player) damage.getEntity();
                if (p.hasPermission("quarantineplugin.magicbeans")) {
                    if (damage.getCause() == EntityDamageEvent.DamageCause.FALL) {
                        if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("MAGIC BEANS")) {
                            if (p.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
                                //CANCEL FALL DAMAGE
                                damage.setCancelled(true);
                            }
                        }
                    }
                }
            }
        } catch (NullPointerException e) {}
    }
}
