package provodd.simplePlayerStats;

import org.bukkit.plugin.java.JavaPlugin;
import provodd.simplePlayerStats.placeholders.PlaceholderHandler;

public class SimplePlayerStats extends JavaPlugin {

    @Override
    public void onEnable() {
        if (isPlaceholderApiAvailable()) {
            initializePlaceholder();
            getLogger().info("Enabled SimplePlayerStats");
        } else {
            getLogger().warning("PlaceholderAPI not found! SimplePlayerStats disabled...");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    private boolean isPlaceholderApiAvailable() {
        return getServer().getPluginManager().getPlugin("PlaceholderAPI") != null;
    }

    private void initializePlaceholder() {
        PlaceholderHandler placeholder = new PlaceholderHandler(this);
        placeholder.initializeCache();
        placeholder.register();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic, if needed
    }
}
