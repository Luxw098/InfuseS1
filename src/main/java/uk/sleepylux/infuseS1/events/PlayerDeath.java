/*
This file is part of InfuseS1.
InfuseS1 is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
InfuseS1 is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with InfuseS1. If not, see <https://www.gnu.org/licenses/>.
 */

package uk.sleepylux.infuseS1.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import uk.sleepylux.infuseS1.Main;
import uk.sleepylux.infuseS1.registry.BanTable;
import uk.sleepylux.infuseS1.registry.DataTable;
import uk.sleepylux.infuseS1.registry.Effects;
import uk.sleepylux.infuseS1.utility.EffectRandomizer;

import java.time.Duration;
import java.util.*;

public class PlayerDeath implements Listener {
    private final Main plugin;
    public PlayerDeath(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        plugin.getLogger().info("PlayerDeath Called");
        FileConfiguration config = plugin.getConfig();

        Player deadPlayer = event.getPlayer();

        List<UUID> bantable = BanTable.get(plugin);
        Map<String, List<PotionEffect>> datatable = DataTable.get(plugin);
        List<PotionEffect> currentEffects = datatable.get(deadPlayer.getUniqueId().toString());

        PotionEffect randomPositiveEffect = EffectRandomizer.getRandomEffect(Effects.positiveEffects(config).stream().toList(),
                currentEffects.stream().map(PotionEffect::getType).toList(), true);
        if (randomPositiveEffect != null) {
            currentEffects.removeIf(potionEffect -> potionEffect.getType() == randomPositiveEffect.getType());
            deadPlayer.sendMessage(ChatColor.LIGHT_PURPLE + "[InfuseS1] " + ChatColor.GOLD + "You lost " +
                    ChatColor.GREEN + randomPositiveEffect.getType().getKey().toString().split(":")[1]);
        } else {
            PotionEffect randomNegativeEffect = EffectRandomizer.getRandomEffect(Effects.negativeEffects(config).stream().toList(),
                    currentEffects.stream().map(PotionEffect::getType).toList(), false);
            if (randomNegativeEffect != null) {
                currentEffects.add(randomNegativeEffect);
                deadPlayer.sendMessage(ChatColor.LIGHT_PURPLE + "[InfuseS1] " + ChatColor.GOLD + "You gained" +
                        ChatColor.RED + randomNegativeEffect.getType().getKey().toString().split(":")[1] + " from dying");
            } else {
                deadPlayer.ban(ChatColor.RED + "You died with all possible negative effects, You are eliminated.\n"
                        + ChatColor.BLUE + "Maybe try a revive beacon?", Duration.ofDays(999), "");

                plugin.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + "[InfuseS1] " + ChatColor.RESET
                        + deadPlayer.getDisplayName() + ChatColor.GOLD + " Has been eliminated!");

                bantable.add(deadPlayer.getUniqueId());
                BanTable.set(plugin, bantable);

                datatable.remove(deadPlayer.getUniqueId().toString());
                DataTable.set(plugin, datatable);
                return;
            }
        }

        Bukkit.getScheduler().runTaskLater(plugin, () -> deadPlayer.addPotionEffects(currentEffects), 5L);
        datatable.put(deadPlayer.getUniqueId().toString(), currentEffects.stream().toList());
        DataTable.set(plugin, datatable);
    }
}
