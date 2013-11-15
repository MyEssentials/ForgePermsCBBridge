package com.sperion.forgeperms.cbbridge;

import java.util.logging.Level;

import net.milkbowl.vault.chat.Chat;

import org.bukkit.plugin.RegisteredServiceProvider;

import com.sperion.forgeperms.api.IChatManager;

public class VaultChat implements IChatManager {
    Chat chat = null;
    String loadError = "";
    
    private boolean setupChat(){
        RegisteredServiceProvider<Chat> chatProvider = Bridge.server.getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
        if (chatProvider != null) {
            chat = chatProvider.getProvider();
        }
        return (chat != null);
    }
    
    private void checkChatProvider(){
        if (chat == null){
            Bridge.log(Level.FINE, "Chat provider is null");
            setupChat();
        }
    }

    @Override
    public String getName() {
        return "VaultChat";
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
        if (!setupChat()){
            loadError = "No chat plugin detected";
            return false;
        }
        return true;
    }

    @Override
    public String getLoadError() {
        return loadError;
    }

    @Override
    public String getPlayerPrefix(String world, String player) {
        checkChatProvider();
        
        if (player == null || player.isEmpty()){
            return "";
        }
        
        return chat.getPlayerPrefix(world, player);
    }

    @Override
    public String getPlayerSuffix(String world, String player) {
        checkChatProvider();
        
        if (player == null || player.isEmpty()){
            return "";
        }
        
        return chat.getPlayerSuffix(world, player);
    }

    @Override
    public void setPlayerPrefix(String world, String player, String prefix) {
        chat.setPlayerPrefix(world, player, prefix);
    }

    @Override
    public void setPlayerSuffix(String world, String player, String suffix) {
        chat.setPlayerSuffix(world, player, suffix);
    }

    @Override
    public String getGroupPrefix(String world, String group) {
        return chat.getGroupPrefix(world, group);
    }

    @Override
    public String getGroupSuffix(String world, String group) {
        return chat.getGroupSuffix(world, group);
    }

    @Override
    public void setGroupPrefix(String world, String group, String prefix) {
        chat.setGroupPrefix(world, group, prefix);
    }

    @Override
    public void setGroupSuffix(String world, String group, String suffix) {
        chat.setGroupSuffix(world, group, suffix);
    }

    @Override
    public boolean playerInGroup(String world, String player, String group) {
        return chat.playerInGroup(world, player, group);
    }

    @Override
    public String[] getPlayerGroups(String world, String player) {
        return chat.getPlayerGroups(world, player);
    }

    @Override
    public String getPrimaryGroup(String world, String player) {
        return chat.getPrimaryGroup(world, player);
    }

    @Override
    public String getOption(String player, String world, String node, String def) {
        return chat.getPlayerInfoString(world, player, node, def);
    }
}