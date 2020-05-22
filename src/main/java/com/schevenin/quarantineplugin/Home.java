package com.schevenin.quarantineplugin;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.Serializable;

public class Home implements Serializable {

    private String home_name;
    private double x_coordinate;
    private double y_coordinate;
    private double z_coordinate;

    public Home() {
        this.home_name = "home";
        this.x_coordinate = 0.0;
        this.y_coordinate = 0.0;
        this.z_coordinate = 0.0;
    }

    public Home(Player p) {
        this.home_name = "home";
        this.x_coordinate = p.getLocation().getX();
        this.y_coordinate = p.getLocation().getY();
        this.z_coordinate = p.getLocation().getZ();
    }

    public Home(Player p, String home_name) {
        this.home_name = home_name;
        this.x_coordinate = p.getLocation().getX();
        this.y_coordinate = p.getLocation().getY();
        this.z_coordinate = p.getLocation().getZ();
    }


    public String getName() {
        return home_name;
    }
    public void setName(String home_name) {
        this.home_name = home_name;
    }
    public double getX() {
        return x_coordinate;
    }
    public void setX(double x_coordinate) {
        this.x_coordinate = x_coordinate;
    }
    public double getY() {
        return y_coordinate;
    }
    public void setY(double y_coordinate) {
        this.y_coordinate = y_coordinate;
    }
    public double getZ() {
        return z_coordinate;
    }
    public void setZ(double z_coordinate) {
        this.z_coordinate = z_coordinate;
    }

    @Override
    public String toString() {
        return (ChatColor.GREEN + getName() + ChatColor.RESET + ": " + (int)getX() + ", " + (int)getY() + ", " + (int)getZ());
    }
}
