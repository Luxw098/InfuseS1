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
import org.bukkit.potion.PotionEffectType;
import uk.sleepylux.infuseS1.Main;
import uk.sleepylux.infuseS1.registry.DataTable;
import uk.sleepylux.infuseS1.registry.Effects;

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

        Player player = event.getPlayer();

        Map<String, List<PotionEffect>> datatable = DataTable.get(plugin);
        Collection<PotionEffect> currentEffects = datatable.get(player.getUniqueId().toString());
        if (currentEffects.size() == Effects.negativeEffects(config).size()) {
            player.ban(ChatColor.RED + "You died with all possible negative effects, You are eliminated.\n"
                            + ChatColor.BLUE + "Maybe try a revive beacon?", Duration.ofDays(999), "");
            plugin.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + "[InfuseS1] " + ChatColor.RESET
                    + player.getDisplayName() + ChatColor.GOLD + " Has been eliminated!");

            datatable.remove(player.getUniqueId().toString());
            DataTable.set(plugin, datatable);
            return;
        }

        Random random = new Random();

        List<PotionEffect> positiveEffectsMask = currentEffects.stream()
                .filter(potionEffect -> Effects.positiveEffects(config).contains(potionEffect.getType())).toList();
        if (!positiveEffectsMask.isEmpty()) {
            PotionEffect positiveEffect = positiveEffectsMask.get(random.nextInt(positiveEffectsMask.size()));
            currentEffects.remove(positiveEffect);
            player.sendMessage(ChatColor.LIGHT_PURPLE + "[InfuseS1] " + ChatColor.GOLD + "Due to your death, " + ChatColor.GREEN +
                    positiveEffect.getType().getKey().toString().split(":")[1] + ChatColor.GOLD + " has been removed");
        } else {
            List<PotionEffectType> negativeEffectsMask = Effects.negativeEffects(config).stream()
                    .filter(negativeEffect -> !currentEffects.stream()
                            .map(PotionEffect::getType).toList().contains(negativeEffect)).toList();
            PotionEffectType negativeEffectType = negativeEffectsMask.get(random.nextInt(negativeEffectsMask.size()));
            PotionEffect negativeEffect = new PotionEffect(negativeEffectType, PotionEffect.INFINITE_DURATION, 1, true, false);
            currentEffects.add(negativeEffect);
            player.sendMessage(ChatColor.LIGHT_PURPLE + "[InfuseS1] " + ChatColor.GOLD + "Due to your death, you now have " +
                    ChatColor.RED + negativeEffect.getType().getKey().toString().split(":")[1]);
        }
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            plugin.getLogger().info(currentEffects.toString());
            player.addPotionEffects(currentEffects);
        }, 5L);
        datatable.put(player.getUniqueId().toString(), currentEffects.stream().toList());
        DataTable.set(plugin, datatable);
    }
}
