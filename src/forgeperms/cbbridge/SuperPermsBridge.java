package forgeperms.cbbridge;

import org.bukkit.Bukkit;

import forgeperms.api.IPermissionManager;

/**
 * WIP SuperPerms Bridge
 * @author Joe Goett
 */
public class SuperPermsBridge implements IPermissionManager {
	@Override
	public String getName() {
		return "SuperPerms Bridge";
	}

	@Override
	public boolean load() {
		return true;
	}

	@Override
	public String getLoadError() {
		return "";
	}

	@Override
	public boolean canAccess(String player, String world, String node) {
		return Bukkit.getServer().getPlayerExact(player).hasPermission(node);
	}

	@Override
	public boolean addGroup(String player, String group) {
		return false;
	}

	@Override
	public boolean removeGroup(String player, String group) {
		return false;
	}

	@Override
	public String[] getGroupNames(String player) {
		return null;
	}

	@Override
	public String getPrimaryGroup(String world, String playerName) {
		return null;
	}
}