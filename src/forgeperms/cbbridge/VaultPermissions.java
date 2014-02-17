package forgeperms.cbbridge;

import java.util.logging.Level;

import net.milkbowl.vault.permission.Permission;

import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import forgeperms.api.IPermissionManager;

public class VaultPermissions implements IPermissionManager {
    public Permission perms = null;
    public String loadError = "";
    
    private boolean enhancedPermChecking;

    @Override
    public String getName() {
        return "VaultPerms";
    }

    @Override
    public String getLoadError() {
        return loadError;
    }

    @Override
    public boolean load() {
        Bridge.log.info("Loading " + getName());
        if (Bridge.server.getPluginManager().getPlugin("Vault") == null) {
            loadError = "Vault Missing";
            Bridge.log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", "MyTownCBBridge - Vault"));
            Bridge.server.getPluginManager().disablePlugin(Bridge.instance);
            return false;
        }
        
        if (!setupPermissions()){
            loadError = "No permission plugin detected";
            return false;
        }
        return true;
    }

    
    @Override
    public boolean canAccess(String name, String world, String node) {
        checkPermissionProvider();

        // Normal check
        boolean result = perms.has(world, name, node);

        String lastNode = "";
        // Check for mods that don't implement node.* entries
        if (!result && enhancedPermChecking) {
            String[] nodes = node.split("\\.");
            for (int i = 0; i < nodes.length - 1; i++) {
                lastNode = lastNode + nodes[i] + ".";
                if (perms.has(world, name, lastNode + "*")) {
                    result = true;
                }
            }
        }

        String message = name + " at " + world + " requested " + node;
        if (StringUtils.isNotBlank(lastNode)) {
            message += "(actual node requested: " + lastNode + ")";
        }
        message += " with result: " + result;
        Bridge.log(Level.FINER, message);

        return result;
    }

    @Override
    public boolean addGroup(String player, String group) {
        Player p = Bridge.server.getPlayer(player);
        return perms.playerAddGroup(p, group);
    }

    @Override
    public boolean removeGroup(String player, String group) {
        Player p = Bridge.server.getPlayer(player);
        return perms.playerRemoveGroup(p, group);
    }

    private void checkPermissionProvider() {
        if (perms == null) {
            Bridge.log(Level.FINE, "Permission provider is null");
            setupPermissions();
        }
    }

    private boolean setupPermissions() {
        Bridge.log(Level.FINE, "Retrieving permission provider..");
        RegisteredServiceProvider<Permission> permsProvider = Bridge.server.getServicesManager().getRegistration(Permission.class);
        if (permsProvider != null) {
            perms = permsProvider.getProvider();
            Bridge.log(Level.INFO, "Using " + perms.getName() + " for permissions");
            if (perms.getName().equals("PermissionsBukkit")) {
                enhancedPermChecking = true;
            } else {
                enhancedPermChecking = false;
            }
        }
        return (perms != null);
    }

    @Override
    public String[] getGroupNames(String player) {
        return perms.getGroups();
    }

    @Override
    public String getPrimaryGroup(String world, String playerName) {
        return perms.getPrimaryGroup(world, playerName);
    }
}
