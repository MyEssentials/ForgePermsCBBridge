package com.sperion.mytown.cbbridge;

import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import ee.lutsu.alpha.mc.mytown.MyTown;

public class MyTownCBBridge extends JavaPlugin {
    public static Logger log;
    public static Server server;
    public static MyTownCBBridge instance;
    
    @Override
    public void onLoad() {
        instance = this;
        log = Logger.getLogger("Minecraft");
        server = getServer();
    }
    
    @Override
    public void onDisable() {
    }

    @Override
    public void onEnable() {
        MyTown.registerPermHandler(new VaultPermissions());
    }
}