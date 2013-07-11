package com.sperion.forgeperms.cbbridge;

import java.util.logging.Level;

import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import net.minecraft.command.ICommandSender;
import com.sperion.forgeperms.*;

public class VaultPermissions extends PermissionsBase {
    public Permission perms = null;
    public Chat chat = null;
    
    public VaultPermissions(){
        name = "Vault";
    }
    
    @Override
    public boolean load(){
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
        Bridge.log.log(Level.INFO, name + " at " + world + " requested " + node);
        return perms.has(world, name, node);
    }

    @Override
    public String getPrefix(String player, String world) {
        return chat.getPlayerPrefix(world, player);
    }

    @Override
    public String getPostfix(String player, String world) {
        return chat.getPlayerSuffix(world, player);
    }

    @Override
    public String getOption(String player, String world, String node, String def) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getOption(ICommandSender name, String node, String def) {
        // TODO Auto-generated method stub
        return null;
    }
    
    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = Bridge.server.getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = Bridge.server.getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }
}
