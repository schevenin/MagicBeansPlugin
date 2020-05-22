package com.schevenin.quarantineplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LaunchCommand implements CommandExecutor {
    /**
     * COMMANDS
     * @param sender
     * @param command
     * @param label
     * @param args
     * @return boolean
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            Player otherPlayer;
            World world = p.getWorld();

            int playersOnline = world.getPlayers().size();
            boolean canLaunchOthers = p.hasPermission("quarantineplugin.launch.others");
            boolean canLaunchSelf = p.hasPermission("quarantineplugin.launch.self");

            switch (args.length) {
                case 1:
                    // User is the only player online
                    if (onlyPlayerOnline(p, playersOnline, canLaunchSelf)) break;
                    // Launch specified player with default arguments
                    if (canLaunchOthers) {
                        otherPlayer = Bukkit.getPlayer(args[0]);
                        otherPlayer.setVelocity(p.getLocation().getDirection().multiply(2).setY(2));
                        otherPlayer.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "You've been yeeted by " + p.getName() + "!");
                        p.sendMessage(ChatColor.AQUA + "Yeeting " + p.getName());
                    } else {
                        p.sendMessage(ChatColor.DARK_RED + "Error" + ChatColor.RESET + ": you don't have permission.");
                    }
                    break;
                case 2:
                    // Launch yourself with two specified launch arguments
                    if (canLaunchSelf) {
                        p.setVelocity(p.getLocation().getDirection().multiply(Integer.parseInt(args[0])).setY(Integer.parseInt(args[1])));
                        p.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "Yeeting " + p.getName());
                    } else {
                        p.sendMessage(ChatColor.DARK_RED + "Error" + ChatColor.RESET + ": you don't have permission.");
                    }
                    break;
                case 3:
                    // User is the only player online
                    if (onlyPlayerOnline(p, playersOnline, canLaunchSelf)) break;
                    // Launch specified player with specified launch arguments
                    if (canLaunchOthers) {
                        otherPlayer = (Player) Bukkit.getPlayer(args[0]);
                        otherPlayer.setVelocity(p.getLocation().getDirection().multiply(Integer.parseInt(args[1])).setY(Integer.parseInt(args[2])));
                        otherPlayer.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "You've been yeeted by " + p.getName() + "!");
                        p.sendMessage(ChatColor.AQUA + "Yeeting " + p.getName());
                    } else {
                        p.sendMessage(ChatColor.DARK_RED + "Error" + ChatColor.RESET + ": you don't have permission.");
                    }
                    break;
                default:
                    // Launch yourself with default launch arguments
                    if (canLaunchSelf) {
                        p.setVelocity(p.getLocation().getDirection().multiply(2).setY(2));
                        p.sendMessage(ChatColor.AQUA + "Yeeting " + p.getName());
                    } else {
                        p.sendMessage(ChatColor.DARK_RED + "Error" + ChatColor.RESET + ": you don't have permission.");
                    }
                    break;
            }
        } else {
            System.out.println("Must run this command as a player!");
        }
        return true;
    }

    /**
     * ONLY PLAYER ONLINE
     * @param p
     * @param playersOnline
     * @param canLaunchSelf
     * @return boolean
     */
    private boolean onlyPlayerOnline(Player p, int playersOnline, boolean canLaunchSelf) {
        if (playersOnline <= 1) {
            if (canLaunchSelf) {
                p.sendMessage(ChatColor.DARK_RED + "Error" + ChatColor.RESET + ": you're the only one online.");
                p.sendMessage("Usage: /launch <player>");
            } else {
                p.sendMessage(ChatColor.DARK_RED + "Error" + ChatColor.RESET + ": you don't have permission.");
            }
            return true;
        }
        return false;
    }
}