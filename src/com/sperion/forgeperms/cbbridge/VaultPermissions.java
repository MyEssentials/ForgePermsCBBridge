package com.sperion.forgeperms.cbbridge;

import java.util.logging.Level;

import org.apache.commons.lang.StringUtils;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import net.minecraft.command.ICommandSender;

import com.sperion.forgeperms.*;

public class VaultPermissions extends PermissionsBase {
    public Permission perms = null;
    public Chat chat = null;

    public VaultPermissions() {
        name = "Vault";
    }

    @Override
    public boolean load() {
        if (Bridge.server.getPluginManager().getPlugin("Vault") == null) {
            Bridge.log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", "MyTownCBBridge - Vault"));
            Bridge.server.getPluginManager().disablePlugin(Bridge.instance);
            return false;
        }
        this.setupPermissions();
        this.setupChat();
        return true;
    }

    @Override
    public boolean canAccess(String name, String world, String node) {
        Permission perms = getPermission();

        // Normal check
        boolean result = perms.has(world, name, node);

        String lastNode = "";
        // Check for mods that don't implement node.* entries
        if (!result) {
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
    public String getPrefix(String player, String world) {
        String prefix = "";
        Chat chat = getChat();
        if (chat != null) {
            String value = chat.getPlayerPrefix(world, player);
            if (prefix != null) {
                prefix = value;
            }
        }
        return prefix;
    }

    @Override
    public String getPostfix(String player, String world) {
        String suffix = "";
        Chat chat = getChat();
        if (chat != null) {
            String value = chat.getPlayerSuffix(world, player);
            if (value != null) {
                suffix = value;
            }
        }
        return suffix;
    }

    @Override
    public String getOption(String player, String world, String node, String def) {
        // TODO Auto-generated method stub
        return "";
    }

    @Override
    public String getOption(ICommandSender name, String node, String def) {
        // TODO Auto-generated method stub
        return "";
    }

    private Chat getChat() {
        if (chat == null) {
            Bridge.log(Level.FINE, "chatProvider is null");
            setupChat();
        }
        return chat;
    }

    private Permission getPermission() {
        if (perms == null) {
            Bridge.log(Level.FINE, "permissionProvider is null");
            setupChat();
        }
        return perms;
    }

    private boolean setupChat() {
        Bridge.log(Level.FINE, "Retrieving chatProvider..");
        RegisteredServiceProvider<Chat> chatProvider = Bridge.server.getServicesManager().getRegistration(Chat.class);
        if (chatProvider != null) {
            chat = chatProvider.getProvider();
            Bridge.log(Level.INFO, "Using " + chat.getName() + " for chat");
        } else {
            Bridge.log(Level.FINE, "Retrieving chatProvider failed.");
        }

        return chat != null;
    }

    private boolean setupPermissions() {
        Bridge.log(Level.FINE, "Retrieving permissionProvider..");
        RegisteredServiceProvider<Permission> permsProvider = Bridge.server.getServicesManager().getRegistration(Permission.class);
        if (permsProvider != null) {
            perms = permsProvider.getProvider();
            Bridge.log(Level.INFO, "Using " + perms.getName() + " for permissions");
        } else {
            Bridge.log(Level.FINE, "Retrieving chatProvider failed.");
        }
        return perms != null;
    }
}
