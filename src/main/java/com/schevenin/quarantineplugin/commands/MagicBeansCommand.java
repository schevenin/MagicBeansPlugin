package com.schevenin.quarantineplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MagicBeansCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("quarantineplugin.magicbeans")) {
                // inventory is full
                if (p.getInventory().firstEmpty() == -1) {
                    p.sendMessage(ChatColor.ITALIC + "" + ChatColor.GRAY + "I seem to be carrying too much...");
                    Location loc = p.getLocation();
                    World world = p.getWorld();
                    world.dropItemNaturally(loc, getMagicBeans());
                } else {
                    p.getInventory().addItem(getMagicBeans());
                    p.sendMessage(ChatColor.ITALIC + "" + ChatColor.GRAY + "Something mysterious has fallen from above...");
                }
            } else {
                p.sendMessage("You don't have permission.");
            }
            return true;
        } else {
            System.out.println("Must run this command as a player!");
            return true;
        }
    }

    public static ItemStack getMagicBeans() {
        ItemStack beans = new ItemStack(Material.COCOA_BEANS);
        ItemMeta meta = beans.getItemMeta();

        meta.setDisplayName(ChatColor.MAGIC + "" + ChatColor.BOLD + "MAGIC BEANS");
        List<String> lore = new ArrayList<String>();
        lore.add("");
        lore.add(ChatColor.ITALIC + "Mysterious Beans They Are");

        meta.setLore(lore);
        meta.addEnchant(Enchantment.SILK_TOUCH, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setUnbreakable(true);

        beans.setItemMeta(meta);

        return beans;
    }
}
