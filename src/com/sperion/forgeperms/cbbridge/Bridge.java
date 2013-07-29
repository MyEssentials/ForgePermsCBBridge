package com.sperion.forgeperms.cbbridge;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import com.sperion.forgeperms.ForgePerms;

public class Bridge extends JavaPlugin {
    public static Logger log;
    public static Server server;
    public static Bridge instance;
    
    @Override
    public void onLoad() {
        instance = this;
        log = Logger.getLogger("Minecraft");
        server = getServer();
    }
    
    public static void log(Level level, String msg){
        log.log(level, "[Bridge]" + msg);
    }
    
    @Override
    public void onDisable() {
    }

    @Override
    public void onEnable() {
        ForgePerms.registerHandler(new VaultPermissions());
    }
}