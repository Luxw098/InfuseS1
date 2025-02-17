package uk.sleepylux.infuseS1.events;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import uk.sleepylux.infuseS1.Main;
import uk.sleepylux.infuseS1.registry.DataTable;
import uk.sleepylux.infuseS1.registry.Effects;
import uk.sleepylux.infuseS1.utility.EffectRandomizer;

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
        String UUID = event.getPlayer().getUniqueId().toString();
        Map<String, List<PotionEffect>> datatable = DataTable.get(plugin);
        if (!datatable.containsKey(UUID)) {
            List<PotionEffect> effects = new ArrayList<>();
            PotionEffect randomPositiveEffect = EffectRandomizer.getRandomEffect(Effects.positiveEffects(plugin.getConfig()).stream().toList(),
                    List.of(), true);
            effects.add(randomPositiveEffect);
            datatable.put(UUID, effects);
        }
        DataTable.set(plugin, datatable);
    }
}
