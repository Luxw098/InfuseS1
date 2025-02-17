package uk.sleepylux.infuseS1.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import uk.sleepylux.infuseS1.Main;
import uk.sleepylux.infuseS1.recipes.MysteryEffectRecipe;
import uk.sleepylux.infuseS1.recipes.ReviveToolRecipe;
import uk.sleepylux.infuseS1.recipes.UpgradeTokenRecipe;

public class UseItem implements Listener {
    Main plugin;
    public UseItem(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemUse(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (item == null) return;
        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.hasCustomModelData() || meta.getCustomModelData() != plugin.modelID) return;

        event.setCancelled(true);
        switch (item.getType()) {
            case Material.NETHER_STAR -> MysteryEffectRecipe.onUse(plugin ,event);
            case Material.BEACON -> ReviveToolRecipe.onUse(plugin, event);
            case Material.SUNFLOWER -> UpgradeTokenRecipe.onUse(plugin, event);
        }
    }
}
