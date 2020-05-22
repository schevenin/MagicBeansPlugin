package com.schevenin.quarantineplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GodCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("quarantineplugin.god")) {
                if (p.isInvulnerable()) {
                    p.setInvulnerable(false);
                    p.sendMessage("You have are now vulnerable.");
                } else {
                    p.setInvulnerable(true);
                    p.sendMessage("You have are now invulnerable.");
                }
            } else {
                System.out.println("You don't have permission.");
            }
            return true;
        } else {
            System.out.println("Must run this command as a player!");
            return true;
        }
    }
}
