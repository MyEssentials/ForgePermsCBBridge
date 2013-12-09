package com.sperion.forgeperms.cbbridge;

import java.util.logging.Level;

import net.milkbowl.vault.chat.Chat;

import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.sperion.forgeperms.api.IChatManager;

public class VaultChat implements IChatManager {
    Chat chat = null;
    String loadError = "";
    boolean is_bPerms = false;
    
    private boolean setupChat(){
        RegisteredServiceProvider<Chat> chatProvider = Bridge.server.getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
        if (chatProvider != null) {
            chat = chatProvider.getProvider();
            is_bPerms = chat.getName().equals("bInfo");
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
        
        if (is_bPerms){
        	Player pl = Bridge.server.getPlayerExact(player);
    		if (pl == null) return "";
    		return chat.getPlayerPrefix(pl);
        }
        
        return chat.getPlayerPrefix(world, player);
    }

    @Override
    public String getPlayerSuffix(String world, String player) {
        checkChatProvider();
        
        if (player == null || player.isEmpty()){
            return "";
        }
        
        if (is_bPerms){
        	Player pl = Bridge.server.getPlayerExact(player);
    		if (pl == null) return "";
    		return chat.getPlayerSuffix(pl);
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
    public String getPlayerInfoString(String world, String playerName, String node, String defaultValue) {
        return chat.getPlayerInfoString(world, playerName, node, defaultValue);
    }

    @Override
    public int getPlayerInfoInteger(String world, String playerName, String node, int defaultValue) {
        return chat.getPlayerInfoInteger(world, playerName, node, defaultValue);
    }

    @Override
    public double getPlayerInfoDouble(String world, String playerName, String node, double defaultValue) {
        return chat.getPlayerInfoDouble(world, playerName, node, defaultValue);
    }

    @Override
    public boolean getPlayerInfoBoolean(String world, String playerName, String node, boolean defaultValue) {
        return chat.getPlayerInfoBoolean(world, playerName, node, defaultValue);
    }

    @Override
    public void setPlayerInfoString(String world, String playerName, String node, String value) {
        chat.setPlayerInfoString(world, playerName, node, value);
    }

    @Override
    public void setPlayerInfoInteger(String world, String playerName, String node, int value) {
        chat.setPlayerInfoInteger(world, playerName, node, value);
    }

    @Override
    public void setPlayerInfoDouble(String world, String playerName, String node, double value) {
        chat.setPlayerInfoDouble(world, playerName, node, value);
    }

    @Override
    public void setPlayerInfoBoolean(String world, String playerName, String node, boolean value) {
        chat.setPlayerInfoBoolean(world, playerName, node, value);
    }

    @Override
    public String getGroupInfoString(String world, String group, String node, String defaultValue) {
        return chat.getGroupInfoString(world, group, node, defaultValue);
    }

    @Override
    public int getGroupInfoInteger(String world, String group, String node, int defaultValue) {
        return chat.getGroupInfoInteger(world, group, node, defaultValue);
    }

    @Override
    public double getGroupInfoDouble(String world, String group, String node, double defaultValue) {
        return chat.getGroupInfoDouble(world, group, node, defaultValue);
    }

    @Override
    public boolean getGroupInfoBoolean(String world, String group, String node, boolean defaultValue) {
        return chat.getGroupInfoBoolean(world, group, node, defaultValue);
    }

    @Override
    public void setGroupInfoString(String world, String group, String node, String value) {
        chat.setGroupInfoString(world, group, node, value);
    }

    @Override
    public void setGroupInfoInteger(String world, String group, String node, int value) {
        chat.setGroupInfoInteger(world, group, node, value);
    }

    @Override
    public void setGroupInfoDouble(String world, String group, String node, double value) {
        chat.setGroupInfoDouble(world, group, node, value);
    }

    @Override
    public void setGroupInfoBoolean(String world, String group, String node, boolean value) {
        chat.setGroupInfoBoolean(world, group, node, value);
    }
}