package com.sperion.forgeperms.cbbridge;

import java.util.logging.Level;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.plugin.RegisteredServiceProvider;

import com.sperion.forgeperms.api.IEconomyManager;

public class VaultEconomy implements IEconomyManager {
    private Economy economy;
    private String loadError = "";

    private void checkEconomyProvider() {
        if (economy == null) {
            Bridge.log(Level.FINE, "Economy provider is null");
            setupEconomy();
        }
    }
    
    private boolean setupEconomy(){
        RegisteredServiceProvider<Economy> economyProvider = Bridge.server.getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }
        return (economy != null);
    }
    
    @Override
    public String getName() {
        return "VaultEcon";
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
        if (!setupEconomy()){
            loadError = "No economy plugin detected";
            return false;
        }
        return true;
    }

    @Override
    public String getLoadError() {
        return loadError;
    }

    @Override
    public double playerBalance(String playerName, String itemID, String world) {
        checkEconomyProvider();
        return economy.getBalance(playerName, world);
    }

    @Override
    public boolean playerHas(String playerName, String world, String itemID, double amount) {
        checkEconomyProvider();
        return economy.has(playerName, world, amount);
    }

    @Override
    public boolean playerWithdraw(String playerName, String world, String itemID, double amount) {
        checkEconomyProvider();
        return economy.withdrawPlayer(playerName, world, amount).transactionSuccess();
    }

    @Override
    public boolean playerDeposit(String playerName, String world, String itemID, double amount) {
        checkEconomyProvider();
        return economy.depositPlayer(playerName, world, amount).transactionSuccess();
    }

    @Override
    public boolean hasBankSupport() {
        checkEconomyProvider();
        return economy.hasBankSupport();
    }

    @Override
    public String format(String itemid, double amount) {
        checkEconomyProvider();
        return economy.format(amount);
    }

    @Override
    public boolean rightClickToPay() {
        return false;
    }

    @Override
    public double bankBalance(String name, String itemID) {
        checkEconomyProvider();
        return economy.bankBalance(name).balance;
    }

    @Override
    public boolean bankHas(String name, String itemID, double amount) {
        checkEconomyProvider();
        return economy.bankHas(name, amount).transactionSuccess();
    }

    @Override
    public boolean bankWithdraw(String name, String itemID, double amount) {
        checkEconomyProvider();
        return economy.bankWithdraw(name, amount).transactionSuccess();
    }

    @Override
    public boolean bankDeposit(String name, String itemID, double amount) {
        checkEconomyProvider();
        return economy.bankDeposit(name, amount).transactionSuccess();
    }
}