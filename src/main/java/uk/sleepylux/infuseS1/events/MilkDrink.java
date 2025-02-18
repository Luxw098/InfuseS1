/*
This file is part of InfuseS1.
InfuseS1 is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
InfuseS1 is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with InfuseS1. If not, see <https://www.gnu.org/licenses/>.
 */
package uk.sleepylux.infuseS1.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import uk.sleepylux.infuseS1.Main;

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
