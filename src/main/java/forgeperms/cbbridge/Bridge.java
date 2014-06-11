package forgeperms.cbbridge;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

import forgeperms.api.ForgePermsAPI;

public class Bridge extends JavaPlugin {
	public static Logger log;
	public static Server server;
	public static Bridge instance;

	private Configuration config;
	private static Level loggingLevel;
    private static boolean permissions = true;
    private static boolean economy = false;
    private static boolean chat = true;

	@Override
	public void onLoad() {
		instance = this;
		log = Logger.getLogger("Minecraft");
		server = getServer();
		config = getConfig();
		try {
		    loggingLevel = Level.parse(config.getString("loggingLevel", "OFF"));
            permissions = config.getBoolean("permissions", true);
            economy = config.getBoolean("economy", false);
            chat = config.getBoolean("chat", true);
            config.set("loggingLevel", loggingLevel.toString());
            config.set("permissions", permissions);
            config.set("economy", economy);
            config.set("chat", chat);
		} catch (Exception ex) {
		    loggingLevel = Level.OFF;
            config.set("loggingLevel", loggingLevel.toString());
            config.set("permissions", permissions);
            config.set("economy", economy);
            config.set("chat", chat);
		}
		saveConfig();
	}

	public static void log(Level level, String msg) {
	    if (level.intValue() >= loggingLevel.intValue()) {
	        log.log(Level.INFO, "[Bridge]" + msg);
	    }
	}

	@Override
	public void onDisable() {
	}

	@Override
	public void onEnable() {
	    if (permissions){
	    	ForgePermsAPI.permManager = new VaultPermissions();
	    }
	    if (chat){
	    	ForgePermsAPI.chatManager = new VaultChat();
	    }
	    if (economy){
	    	ForgePermsAPI.econManager = new VaultEconomy();
	    }
	}
}