package com.schevenin.quarantineplugin;

import java.io.Serializable;
import java.util.List;

public class Home implements Serializable {

    private String player_id;
    private String home_name;
    private double x_coordinate;
    private double y_coordinate;
    private double z_coordinate;

    public Home() {

    }

    public Home(String home_name, double x_coordinate, double y_coordinate, double z_coordinate) {
        this.home_name = home_name;
        this.x_coordinate = x_coordinate;
        this.y_coordinate = y_coordinate;
        this.z_coordinate = z_coordinate;
    }


    public String getHome_name() {
        return home_name;
    }
    public void setHome_name(String home_name) {
        this.home_name = home_name;
    }
    public double getX_coordinate() {
        return x_coordinate;
    }
    public void setX_coordinate(double x_coordinate) {
        this.x_coordinate = x_coordinate;
    }
    public double getY_coordinate() {
        return y_coordinate;
    }
    public void setY_coordinate(double y_coordinate) {
        this.y_coordinate = y_coordinate;
    }
    public double getZ_coordinate() {
        return z_coordinate;
    }
    public void setZ_coordinate(double z_coordinate) {
        this.z_coordinate = z_coordinate;
    }

    @Override
    public String toString() {
        return "Name: " + home_name + "\nX: " + x_coordinate + "\nY: " + y_coordinate + "\nZ: " + z_coordinate;
    }
}
