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
        Bridge.log(Level.INFO, name + " at " + world + " requested " + node);
        return perms.has(world, name, node);
    }

    @Override
    public String getPrefix(String player, String world) {
        if (chat!=null){
            String prefix = chat.getPlayerPrefix(world, player);
            if (prefix==null){
                return "";
            } else{
                return prefix;
            }
        } else{
            return "";
        }
    }

    @Override
    public String getPostfix(String player, String world) {
        if (chat!=null){
            String suffix = chat.getPlayerSuffix(world, player);
            if (suffix==null){
                return "";
            } else{
                return suffix;
            }
        } else{
            return "";
        }
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
    
    private boolean setupChat() {
        RegisteredServiceProvider<Chat> chatProvider = Bridge.server.getServicesManager().getRegistration(Chat.class);
        if (chatProvider!=null){
            chat = chatProvider.getProvider();
            Bridge.log(Level.INFO, "Using " + chat.getName() + " for chat");
        }
        return chat != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> permsProvider = Bridge.server.getServicesManager().getRegistration(Permission.class);
        if (permsProvider!=null){
            perms = permsProvider.getProvider();
            Bridge.log(Level.INFO, "Using " + perms.getName() + " for permissions");
        }
        return perms != null;
    }
}
