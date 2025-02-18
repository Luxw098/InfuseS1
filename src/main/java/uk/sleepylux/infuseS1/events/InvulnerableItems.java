/*
This file is part of InfuseS1.
InfuseS1 is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
InfuseS1 is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with InfuseS1. If not, see <https://www.gnu.org/licenses/>.
 */
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
