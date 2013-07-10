package com.sperion.mytown.cbbridge;

import java.util.logging.Level;

import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import ee.lutsu.alpha.mc.mytown.PermissionsBase;
//import ee.lutsu.alpha.mc.mytown.entities.Resident;

public class VaultPermissions extends PermissionsBase {
    public Permission perms = null;
    public Chat chat = null;
    
    public VaultPermissions(){
        name = "Vault";
    }
    
    @Override
    public boolean load(){
        if (MyTownCBBridge.server.getPluginManager().getPlugin("Vault") == null) {
            MyTownCBBridge.log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", "MyTownCBBridge - Vault"));
            MyTownCBBridge.server.getPluginManager().disablePlugin(MyTownCBBridge.instance);
            return false;
        }
        this.setupPermissions();
        this.setupChat();
        return true;
    }
    
    @Override
    public boolean canAccess(String name, String world, String node) {
        MyTownCBBridge.log.log(Level.INFO, name + " at " + world + " requested " + node);
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
        RegisteredServiceProvider<Chat> rsp = MyTownCBBridge.server.getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = MyTownCBBridge.server.getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }
}
