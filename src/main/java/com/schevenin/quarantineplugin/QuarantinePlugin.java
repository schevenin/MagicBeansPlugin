package com.schevenin.quarantineplugin;

import com.schevenin.quarantineplugin.commands.*;
import com.schevenin.quarantineplugin.events.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.io.FileUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;



public final class QuarantinePlugin extends JavaPlugin {
    public static File pluginDirectory = new File("./plugins/QuarantinePlugin");
    public static File userHomeDataFile = new File("./plugins/QuarantinePlugin/data.json");
    public static File userHomeDataFileBackup = new File("./plugins/QuarantinePlugin/data.json.backup");
    public static Map<String, List<Home>> allHomes;

    /**
     * PLUGIN ENABLE
     */
    @Override
    public void onEnable() {
        // Plugin startup
        getServer().getPluginManager().registerEvents(new OnSleep(this), this);
        getServer().getPluginManager().registerEvents(new HoldingBeans(this), this);
        getCommand("beans").setExecutor(new MagicBeansCommand());
        getCommand("launch").setExecutor(new LaunchCommand());
        getCommand("eat").setExecutor(new EatCommand());
        getCommand("heal").setExecutor(new HealCommand());
        getCommand("god").setExecutor(new GodCommand());
        getCommand("home").setExecutor(new HomeCommand());
        readData();
        backupData();
        getLogger().info("QuarantinePlugin: Enabled!");
    }

    /**
     * PLUGIN DISABLE
     */
    @Override
    public void onDisable() {
        // Plugin shutdown
        writeData();
        getLogger().info("QuarantinePlugin: Disabled!");
    }

    /**
     * COMMANDS
     * @param sender
     * @param cmd
     * @param label
     * @param args
     * @return boolean
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("quarantine") || label.equalsIgnoreCase("qp")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                p.sendMessage(ChatColor.BOLD + "QuarantinePlugin: v1.0");
            } else {
                System.out.println("Must run this command as a player!");
            }
            return true;
        }
        return false;
    }

    /**
     * READ DATA: JSON -> HASHMAP
     */
    public void readData() {
        getLogger().info("Attempting to read from JSON");
        try {
            if (!(pluginDirectory.exists() && pluginDirectory.isDirectory())) {
                pluginDirectory.mkdir();
                getLogger().info("Creating directory: QuarantinePlugin");
            }
            // If data files don't exist
            if (!userHomeDataFile.exists()) {
                userHomeDataFile.createNewFile();
                getLogger().info("Creating file: data.json");
            }
            if (!userHomeDataFileBackup.exists()) {
                userHomeDataFileBackup.createNewFile();
                getLogger().info("Creating file: data.json.backup");
            }
            // If data files exists
            if (userHomeDataFile.exists() && userHomeDataFileBackup.exists()) {
                String jsonString;
                Type homeMapType;
                Gson gson = new Gson();
                allHomes = new HashMap<>();
                if (userHomeDataFile.length() == 0) {
                    getLogger().info("JSON file is empty, checking backup.");
                    if (userHomeDataFileBackup.length() == 0) {
                        getLogger().info("Backup JSON file is empty.");
                    } else {
                        getLogger().info("Attempting to read from backup JSON.");
                        jsonString = FileUtils.readFileToString(userHomeDataFileBackup, StandardCharsets.UTF_8);
                        homeMapType = new TypeToken<Map<String, List<Home>>>() {}.getType();
                        allHomes = gson.fromJson(jsonString, homeMapType);
                        
                        if (allHomes == null || allHomes.isEmpty() == true) {
                            getLogger().info("No data found in file: data.json.backup");
                            allHomes = new HashMap<>();
                        } else {
                            getLogger().info("Backup JSON --> HashMap: SUCCESS");
                        }
                    }
                } else {
                    getLogger().info("Attempting to read from JSON.");
                    jsonString = FileUtils.readFileToString(userHomeDataFileBackup, StandardCharsets.UTF_8);
                    homeMapType = new TypeToken<Map<String, List<Home>>>() {}.getType();
                    allHomes = gson.fromJson(jsonString, homeMapType);
                    
                    if (allHomes == null || allHomes.isEmpty() == true) {
                        getLogger().info("No data found in file: data.json");
                        allHomes = new HashMap<>();
                    } else {
                        getLogger().info("JSON --> HashMap: SUCCESS");
                    }
                }
            } else {
                getLogger().info("Data files could not be found.");
            }
        } catch (FileNotFoundException e) {
           e.printStackTrace();
           throw new RuntimeException("No data file found!");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Could not read data file!");
        }
    }

    /**
     * WRITE DATA: HASHMAP -> JSON
     */
    public void writeData() {
        getLogger().info("Attempting to write to JSON");
        try {
            // If data file doesn't exist
            if (!userHomeDataFile.exists()) {
                getLogger().info("HashMap --> JSON: FAIL (No data file found.)");
            } else {
                // Serialize HashMap
                Gson gson = new Gson();
                String jsonFormat = gson.toJson(allHomes);
                Files.write(Paths.get(String.valueOf(userHomeDataFile)), jsonFormat.getBytes());

                // Success
                getLogger().info("HashMap --> JSON: SUCCESS");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * BACKUP DATA: HASHMAP -> BACKUP
     */
    public void backupData() {
        getLogger().info("Attempting to backup HashMap");
        try {
            // If data file doesn't exist
            if (!userHomeDataFileBackup.exists()) {
                getLogger().info("HashMap --> Backup JSON: FAIL (No data file found.)");
            } else {
                // Serialize HashMap
                Gson gson = new Gson();
                String jsonFormat = gson.toJson(allHomes);
                Files.write(Paths.get(String.valueOf(userHomeDataFileBackup)), jsonFormat.getBytes());

                // Success
                getLogger().info("HashMap --> Backup JSON: SUCCESS");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
