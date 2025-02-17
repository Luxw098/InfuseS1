package uk.sleepylux.infuseS1.events;

import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import uk.sleepylux.infuseS1.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InvulnerableItems implements Listener {
    Main plugin;
    public InvulnerableItems(Main plugin) {
        this.plugin = plugin;
    }

    List<UUID> droppedCustomItems = new ArrayList<>();
    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        Item item = event.getItemDrop();
        ItemStack itemStack = item.getItemStack();
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null || !meta.hasCustomModelData() || meta.getCustomModelData() != plugin.modelID) return;
        droppedCustomItems.add(item.getUniqueId());
    }

    @EventHandler
    public void onItemDamage(EntityDamageEvent event) {
        if (droppedCustomItems.contains(event.getEntity().getUniqueId())) event.setCancelled(true);
    }

    @EventHandler
    public void onItemDespawn(ItemDespawnEvent event) {
        if (droppedCustomItems.contains(event.getEntity().getUniqueId())) event.setCancelled(true);
    }
}
