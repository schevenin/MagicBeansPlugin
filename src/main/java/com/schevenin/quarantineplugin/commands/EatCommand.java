package com.schevenin.quarantineplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("quarantineplugin.eat")) {
                if (p.getFoodLevel() < 20.0) {
                    p.setFoodLevel(20);
                    p.sendMessage("Restored hunger.");
                } else {
                    p.sendMessage("You're not hungry!");
                }
                return true;
            } else {
                p.sendMessage(ChatColor.DARK_RED + "Error" + ChatColor.RESET + ": you don't have permission.");
                return true;
            }
        } else {
            System.out.println("Must run this command as a player!");
            return true;
        }
    }
}