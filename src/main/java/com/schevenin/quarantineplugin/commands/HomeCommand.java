package com.schevenin.quarantineplugin.commands;

import com.schevenin.quarantineplugin.Home;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static com.schevenin.quarantineplugin.QuarantinePlugin.*;
import static org.bukkit.Bukkit.getLogger;

public class HomeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) throws NullPointerException {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            String unique_id = p.getUniqueId().toString();
            ArrayList<Home> playerHomes;

            if (p.hasPermission("quarantineplugin.home")) {
                // Add array of user homes to reference allHomes
                if (allHomes.isEmpty()) {
                    playerHomes = new ArrayList<Home>();
                } else {
                    if (allHomes.containsKey(unique_id)) {
                        if (!allHomes.get(unique_id).isEmpty()) {
                            playerHomes = allHomes.get(unique_id);
                        } else {
                            playerHomes = new ArrayList<Home>();
                        }
                    } else {
                        playerHomes = new ArrayList<Home>();
                    }
                }

                switch (args.length) {
                    case 1:
                        // home list
                        if (args[0].equalsIgnoreCase("list")) {
                            if (!playerHomes.isEmpty()) {
                                for (Home h : playerHomes) {
                                    h.toString();
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
                                        h.toString();
                                        break;
                                    }
                                }
                                p.sendMessage(ChatColor.DARK_RED + "Error" + ChatColor.RESET + ": you don't have a home named " + args[0]);
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
                                boolean homeAlreadyExists = false;
                                // Check if home exists
                                for (Home h : playerHomes) {
                                    if (h.getName().equalsIgnoreCase(args[1])) {
                                        playerHomes.remove(h);
                                        Home newHome = new Home(p, home_name);
                                        playerHomes.add(newHome);
                                        p.sendMessage("Updated home: " + ChatColor.GREEN + newHome.getName());
                                        getLogger().info(p.getName() + " updated home: " + newHome.getName());
                                        homeAlreadyExists = true;
                                        break;
                                    }
                                }
                                // If home doesn't already exist
                                if (!homeAlreadyExists) {
                                    Home newHome = new Home(p, home_name);
                                    playerHomes.add(newHome);
                                    p.sendMessage("Added home: " + ChatColor.GREEN + newHome.getName());
                                    getLogger().info(p.getName() + " added home: " + newHome.getName());
                                }
                            }
                            // If player doesn't have homes
                            else {
                                Home newHome = new Home(p, home_name);
                                playerHomes.add(newHome);
                                p.sendMessage("Added Home: " + ChatColor.GREEN + newHome.getName());
                                getLogger().info(p.getName() + " added home: " + newHome.getName());
                            }
                        }
                        // home remove <home>
                        else if (args[0].equalsIgnoreCase("remove")) {
                            if (!playerHomes.isEmpty()) {
                                for (Home h : playerHomes) {
                                    if (h.getName().equalsIgnoreCase(args[1])) {
                                        playerHomes.remove(h);
                                        p.sendMessage("Removed home: " + ChatColor.GREEN + args[1]);
                                        getLogger().info(p.getName() + " removed home: " + args[1]);
                                        break;
                                    }
                                }
                                p.sendMessage(ChatColor.DARK_RED + "Error" + ChatColor.RESET + ": you don't have a home named " + args[1]);
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
                // Add playerHomes array to HashMap
                if (allHomes.isEmpty()) {
                    allHomes.put(unique_id, playerHomes);
                } else {
                    if (allHomes.containsKey(unique_id)) {
                        allHomes.replace(unique_id, playerHomes);
                    } else {
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