package fr.army.stelynpcgui.util.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import fr.army.stelynpcgui.StelyNPCGUIPlugin;
import net.milkbowl.vault.economy.Economy;


public class EconomyManager {
    private Economy economy = null;

    public EconomyManager(StelyNPCGUIPlugin plugin){
        setupEconomy();
    }

    public boolean checkMoneyPlayer(Player player, Double money) {
        return economy.getBalance(player) >= ((double) money);
    }

    public void removeMoneyPlayer(Player player, double money) {
        economy.withdrawPlayer(player, money);
    }

    public void addMoneyPlayer(Player player, double money) {
        economy.depositPlayer(player, money);
    }

    public boolean setupEconomy(){
		RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}
		return (economy != null); 
	}
}
