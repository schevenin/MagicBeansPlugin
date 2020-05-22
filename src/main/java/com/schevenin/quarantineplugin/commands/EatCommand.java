package com.schevenin.quarantineplugin.commands;

import com.schevenin.quarantineplugin.QuarantinePlugin;
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
                p.setFoodLevel(20);
                p.sendMessage("Restored hunger.");
                return true;
            } else {
                p.sendMessage("You don't have permission.");
                return true;
            }
        } else {
            System.out.println("Must run this command as a player!");
            return true;
        }
    }
}
