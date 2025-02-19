/*
This file is part of InfuseS1.
InfuseS1 is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
InfuseS1 is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with InfuseS1. If not, see <https://www.gnu.org/licenses/>.
 */
package uk.sleepylux.infuseS1.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
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
        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.hasCustomModelData() || meta.getCustomModelData() != plugin.modelID
            || (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK)) return;

        event.setCancelled(true);

        switch (item.getType()) {
            case Material.NETHER_STAR -> MysteryEffectRecipe.onUse(plugin, event);
            case Material.BEACON -> ReviveToolRecipe.onUse(plugin, event);
            case Material.SUNFLOWER -> UpgradeTokenRecipe.onUse(plugin, event);

        }

    }
}
