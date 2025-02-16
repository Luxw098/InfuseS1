package uk.sleepylux.infuseS1.events;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import uk.sleepylux.infuseS1.Main;
import uk.sleepylux.infuseS1.registry.DataTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlayerJoin implements Listener {
    Main plugin;
    public PlayerJoin(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        plugin.getLogger().info("PlayerJoin Called");
        String UUID = event.getPlayer().getUniqueId().toString();
        Map<String, List<PotionEffect>> datatable = DataTable.get(plugin);
        if (!datatable.containsKey(UUID)) datatable.put(UUID, new ArrayList<>());
        DataTable.set(plugin, datatable);
    }
}
