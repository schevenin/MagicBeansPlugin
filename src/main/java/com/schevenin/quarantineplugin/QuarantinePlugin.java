package com.schevenin.quarantineplugin;

import com.schevenin.quarantineplugin.commands.*;
import com.schevenin.quarantineplugin.events.*;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public final class QuarantinePlugin extends JavaPlugin {
    public static File pluginDirectory = new File("./plugins/QuarantinePlugin");
    public static File userHomeDataFile = new File("./plugins/QuarantinePlugin/data.yml");
    public static Map<String, ArrayList<Home>> allHomes;
    public static YamlReader reader;
    public static YamlWriter writer;

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
     * READ DATA: YAML -> HASHMAP
     */
    public void readData() {
        try {
            // Ensure directory and yaml exist
            if (!pluginDirectory.exists()) {
                pluginDirectory.mkdir();
                getLogger().info("Plugin directory created!");
            }
            if (!userHomeDataFile.exists()) {
                writer = new YamlWriter(new FileWriter(userHomeDataFile));
                writer.close();
                getLogger().info("Data file created!");
            }

            // Read in data
            reader = new YamlReader(new FileReader(userHomeDataFile));
            Object object = reader.read();
            if (object == null) {
                allHomes = new HashMap<>();
            } else {
                allHomes = (HashMap<String, ArrayList<Home>>) object;
            }
            reader.close();

            // Success
            getLogger().info("YAML --> HashMap: Success (" + allHomes.size() + ")");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * WRITE DATA: HASHMAP -> YAML
     */
    public void writeData() {
        try {
            // Write HashMap to yaml
            writer = new YamlWriter(new FileWriter(userHomeDataFile));
            writer.write(allHomes);
            writer.close();

            // Success
            getLogger().info("HashMap --> YAML: Success (" + allHomes.size() + ")");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoClassDefFoundError e) {
            //YAMLBeans issue!
        }
    }
}
