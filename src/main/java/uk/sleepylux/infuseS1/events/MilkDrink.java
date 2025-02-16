package uk.sleepylux.infuseS1.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import uk.sleepylux.infuseS1.Main;

import java.util.Collection;
import java.util.List;

public class MilkDrink implements Listener {
    Main plugin;
    public MilkDrink(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMilkDrink(PlayerItemConsumeEvent event) {
        plugin.getLogger().info("MilkDrink Called");
        ItemStack item = event.getItem();
        if (item.getType() != Material.MILK_BUCKET) return;

        Player player = event.getPlayer();
        List<PotionEffect> effects = player.getActivePotionEffects().stream()
                .filter(potionEffect -> potionEffect.getDuration() != -1).toList();
        for (PotionEffect effect : effects)
            player.removePotionEffect(effect.getType());

        event.setCancelled(true);
        event.setItem(new ItemStack(Material.BUCKET, 1));
    }
}
