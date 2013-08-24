package com.sperion.forgeperms.cbbridge;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

import com.sperion.forgeperms.ForgePerms;

public class Bridge extends JavaPlugin {
	public static Logger log;
	public static Server server;
	public static Bridge instance;

	private Configuration config;
	private static boolean debugLogEnabled;

	@Override
	public void onLoad() {
		instance = this;
		log = Logger.getLogger("Minecraft");
		server = getServer();
		config = getConfig();
		debugLogEnabled = config.getBoolean("enableDebugLogging", false);
		config.set("enableDebugLogging", debugLogEnabled);
		saveConfig();
	}

	public static void log(Level level, String msg) {
		if (debugLogEnabled) {
			log.log(level, "[Bridge]" + msg);
		}

	}

	@Override
	public void onDisable() {
	}

	@Override
	public void onEnable() {
		ForgePerms.registerHandler(new VaultPermissions());
	}
}