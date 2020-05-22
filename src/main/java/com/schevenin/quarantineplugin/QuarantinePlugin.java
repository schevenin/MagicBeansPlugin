package com.schevenin.quarantineplugin;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;
import com.fasterxml.jackson.databind.annotation.NoClass;
import com.schevenin.quarantineplugin.commands.*;
import com.schevenin.quarantineplugin.events.HoldingBeans;
import com.schevenin.quarantineplugin.events.OnSleep;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public final class QuarantinePlugin extends JavaPlugin {

    public static File pluginDirectory = new File("./plugins/QuarantinePlugin");
    public static File userHomeDataFile = new File("./plugins/QuarantinePlugin/data.yml");

    public static YamlWriter writer;
    public static YamlReader reader;
    public static Map<String, ArrayList<Home>> allHomes;


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
    @Override
    public void onDisable() {
        // Plugin shutdown
        writeData();
        getLogger().info("QuarantinePlugin: Disabled!");

    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // /quarantine or /qp
        if (label.equalsIgnoreCase("quarantine") || label.equalsIgnoreCase("qp")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                p.sendMessage(ChatColor.BOLD + "QuarantinePlugin: v1.0");
                return true;
            } else {
                System.out.println("Must run this command as a player!");
                return true;
            }
        }
        return false;
    }

    /**
     * READ DATA
     */
    public void readData() {
        try {
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
            getLogger().info("YAML --> HashMap: Success (" + allHomes.size() + ")");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * WRITE DATA
     */
    public void writeData() {
        try {
            writer = new YamlWriter(new FileWriter(userHomeDataFile));
            writer.write(allHomes);
            writer.close();
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
