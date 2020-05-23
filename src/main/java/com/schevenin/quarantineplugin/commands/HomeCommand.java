package com.schevenin.quarantineplugin.commands;

import com.schevenin.quarantineplugin.Home;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static com.schevenin.quarantineplugin.QuarantinePlugin.allHomes;
import static org.bukkit.Bukkit.getLogger;

public class HomeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) throws NullPointerException {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            String unique_id = p.getUniqueId().toString();
            ArrayList<Home> playerHomes;

            if (p.hasPermission("quarantineplugin.home")) {

                // If HashMap is null or empty
                if (allHomes == null || allHomes.isEmpty() == true) {
                    playerHomes = new ArrayList<Home>();
                }
                // If HashMap contains data
                else {
                    // If HashMap contains data for player
                    if (allHomes.containsKey(unique_id)) {
                        playerHomes = (ArrayList<Home>) allHomes.get(unique_id);
                    }
                    // If HashMap doesn't contain data for the player
                    else {
                        playerHomes = new ArrayList<Home>();
                    }
                }

                // Console & Log
                getLogger().info("" + p.getName() + "'s homes: " + playerHomes.toString());

                boolean homeExists = false;
                switch (args.length) {
                    case 1:
                        // home list
                        if (args[0].equalsIgnoreCase("list")) {
                            if (!playerHomes.isEmpty()) {
                                for (Home h : playerHomes) {
                                    p.sendMessage(h.toString());
                                }
                            } else {
                                p.sendMessage(ChatColor.DARK_RED + "Error" + ChatColor.RESET + ": you don't have any homes.");
                            }
                        }
                        // home <home>
                        else {
                            if (!playerHomes.isEmpty()) {
                                for (Home h : playerHomes) {
                                    if (h.getName().equalsIgnoreCase(args[0])) {
                                        p.sendMessage(h.toString());
                                        homeExists = true;
                                        break;
                                    }
                                }
                                if (!homeExists) {
                                    p.sendMessage(ChatColor.DARK_RED + "Error" + ChatColor.RESET + ": you don't have a home named " + args[0]);
                                }
                            } else {
                                p.sendMessage(ChatColor.DARK_RED + "Error" + ChatColor.RESET + ": you don't have any homes.");
                            }
                        }
                        return true;
                    case 2:
                        // home add <home>
                        if (args[0].equalsIgnoreCase("add")) {
                            String home_name = args[1];
                            double x = p.getLocation().getX();
                            double y = p.getLocation().getY();
                            double z = p.getLocation().getZ();
                            // If user enters /home add list
                            if (args[1].equalsIgnoreCase("list")) {
                                p.sendMessage(ChatColor.DARK_RED + "Error" + ChatColor.RESET + ": can't use the name \"list\".");
                                return true;
                            }
                            // If player has homes
                            if (!playerHomes.isEmpty()) {
                                homeExists = false;
                                // Check if home exists
                                for (Home h : playerHomes) {
                                    if (h.getName().equalsIgnoreCase(args[1])) {
                                        h.updateHome(z, y, z);
                                        p.sendMessage("Updated home: " + ChatColor.GREEN + h.getName());
                                        getLogger().info(p.getName() + " updated home: " + h.getName());
                                        homeExists = true;
                                        break;
                                    }
                                }
                                // If home doesn't already exist
                                if (!homeExists) {
                                    Home newHome = new Home(home_name, x, y, z);
                                    playerHomes.add(newHome);
                                    p.sendMessage("Added home: " + ChatColor.GREEN + newHome.getName());
                                    getLogger().info(p.getName() + " added home: " + newHome.getName());
                                }
                            }
                            // If player doesn't have homes
                            else {
                                Home newHome = new Home(home_name, x, y, z);
                                playerHomes.add(newHome);
                                p.sendMessage("Added Home: " + ChatColor.GREEN + newHome.getName());
                                getLogger().info(p.getName() + " added home: " + newHome.getName());
                            }
                        }
                        // home remove <home>
                        else if (args[0].equalsIgnoreCase("remove")) {
                            homeExists = false;
                            if (!playerHomes.isEmpty()) {
                                for (Home h : playerHomes) {
                                    if (h.getName().equalsIgnoreCase(args[1])) {
                                        playerHomes.remove(h);
                                        p.sendMessage("Removed home: " + ChatColor.GREEN + args[1]);
                                        getLogger().info(p.getName() + " removed home: " + args[1]);
                                        homeExists = true;
                                        break;
                                    }
                                }
                                if (!homeExists) {
                                    p.sendMessage(ChatColor.DARK_RED + "Error" + ChatColor.RESET + ": you don't have a home named " + args[1]);
                                }
                            } else {
                                p.sendMessage(ChatColor.DARK_RED + "Error" + ChatColor.RESET + ": you don't have any homes.");
                                return true;
                            }
                        } else {
                            p.sendMessage(ChatColor.GOLD + "Commands:" + ChatColor.RESET + "\n/home add <name>\n/home remove <name>\n/home list");
                        }
                        break;
                    default:
                        // home
                        p.sendMessage(ChatColor.GOLD + "Commands:" + ChatColor.RESET + "\n/home add <name>\n/home remove <name>\n/home list");
                        return true;
                }

                // Console & Log
                getLogger().info("" + p.getName() + "'s new homes: " + playerHomes.toString());

                // If HashMap is null or empty
                if (allHomes == null || allHomes.isEmpty() == true) {
                    allHomes.put(unique_id, playerHomes);
                }
                // If HashMap contains data
                else {
                    // If HashMap contains data for player
                    if (allHomes.containsKey(unique_id)) {
                        allHomes.replace(unique_id, playerHomes);
                    }
                    // If HashMap doesn't contain data for player
                    else {
                        allHomes.put(unique_id, playerHomes);
                    }
                }
            } else {
                p.sendMessage(ChatColor.DARK_RED + "Error" + ChatColor.RESET + ": you don't have permission.");
            }
        } else {
            System.out.println("Must run this command as a player!");
        }
        return true;
    }
}