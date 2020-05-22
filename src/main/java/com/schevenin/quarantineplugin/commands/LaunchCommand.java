package com.schevenin.quarantineplugin.commands;

import com.schevenin.quarantineplugin.QuarantinePlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LaunchCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            World world = p.getWorld();
            int playersOnline = world.getPlayers().size();
            Player otherPlayer;
            boolean canLaunchOthers = p.hasPermission("quarantineplugin.launch.others");
            boolean canLaunchSelf = p.hasPermission("quarantineplugin.launch.self");
            switch (args.length) {
                case 1:
                    if (playersOnline <= 1) {
                        if (canLaunchSelf) {
                            p.sendMessage("Error: You're the only one online.");
                            p.sendMessage("Usage: /launch <player>");
                        } else {
                            p.sendMessage("You don't have permission.");
                        }
                        break;
                    }
                    // launch specified player with default launch mode
                    if (canLaunchOthers) {
                        otherPlayer = (Player) Bukkit.getPlayer(args[0]);
                        otherPlayer.setVelocity(p.getLocation().getDirection().multiply(2).setY(2));
                        otherPlayer.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "You've been yeeted by " + p.getName() + "!");
                        p.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "Yeeting " + otherPlayer.getName());
                    } else {
                        p.sendMessage("You don't have permission.");
                    }
                    break;
                case 2:
                    // launch yourself with two specified launch modes
                    if (canLaunchSelf) {
                        p.setVelocity(p.getLocation().getDirection().multiply(Integer.parseInt(args[0])).setY(Integer.parseInt(args[1])));
                        p.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "Yeeting " + p.getName());
                    } else {
                        p.sendMessage("You don't have permission.");
                    }
                    break;
                case 3:
                    if (playersOnline <= 1) {
                        if (canLaunchSelf) {
                            p.sendMessage("Error: You're the only one online.");
                            p.sendMessage("Usage: /launch <player>");
                        } else {
                            p.sendMessage("You don't have permission.");
                        }
                        break;
                    }
                    // launch specified player with specified launch modes
                    if (canLaunchOthers) {
                        otherPlayer = (Player) Bukkit.getPlayer(args[0]);
                        otherPlayer.setVelocity(p.getLocation().getDirection().multiply(Integer.parseInt(args[1])).setY(Integer.parseInt(args[2])));
                        otherPlayer.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "You've been yeeted by " + p.getName() + "!");
                        p.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "Yeeting " + otherPlayer.getName());
                    } else {
                        p.sendMessage("You don't have permission.");
                    }
                    break;
                default:
                    // launch yourself with default launch mode
                    if (canLaunchSelf) {
                        p.setVelocity(p.getLocation().getDirection().multiply(2).setY(2));
                        p.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "Yeeting " + p.getName());
                    } else {
                        p.sendMessage("You don't have permission.");
                    }

                    break;
            }
            return true;
        } else {
            System.out.println("Must run this command as a player!");
            return true;
        }
    }
}
