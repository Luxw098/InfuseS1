/*
This file is part of InfuseS1.
InfuseS1 is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
InfuseS1 is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with InfuseS1. If not, see <https://www.gnu.org/licenses/>.
 */

package uk.sleepylux.infuseS1.events;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import uk.sleepylux.infuseS1.Main;
import uk.sleepylux.infuseS1.recipes.MysteryEffectRecipe;
import uk.sleepylux.infuseS1.registry.DataTable;
import uk.sleepylux.infuseS1.registry.Effects;
import uk.sleepylux.infuseS1.utility.EffectRandomizer;

import java.util.List;
import java.util.Map;

public class PlayerKill implements Listener {
    private final Main plugin;
    public PlayerKill(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        plugin.getLogger().info("PlayerKill Called");
        FileConfiguration config = plugin.getConfig();

        Player victim = event.getEntity();
        EntityDamageEvent cause = victim.getLastDamageCause();
        if ((config.getBoolean("ActivateUponDeathByPlayer") && victim.getKiller() == null)
            && (config.getBoolean("ActivateUponDeathByMob") && cause != null && cause.getCause() == DamageCause.ENTITY_ATTACK)) return;

        if (!(victim.getKiller() instanceof Player killer)) return;

        Map<String, List<PotionEffect>> datatable = DataTable.get(plugin);
        List<PotionEffect> effects = datatable.get(killer.getUniqueId().toString());

        PotionEffect randomNegativeEffect = EffectRandomizer.getRandomEffect(Effects.negativeEffects(config).stream().toList(),
                effects.stream().map(PotionEffect::getType).toList(), false);
        if (randomNegativeEffect != null) {
            effects.removeIf(effect -> effect.getType() == randomNegativeEffect.getType());
            killer.removePotionEffect(randomNegativeEffect.getType());
            killer.sendMessage(ChatColor.LIGHT_PURPLE + "[InfuseS1] " + ChatColor.GOLD + "Well done, You no longer have " +
                    ChatColor.RED + randomNegativeEffect.getType().getKey().toString().split(":")[1] + " from getting a kill!");
        } else {
            PotionEffect randomPositiveEffect = EffectRandomizer.getRandomEffect(Effects.positiveEffects(config).stream().toList(),
                    effects.stream().map(PotionEffect::getType).toList(), true);
            if (randomPositiveEffect != null) {
                effects.add(randomPositiveEffect);
                killer.addPotionEffect(randomPositiveEffect);
                killer.sendMessage(ChatColor.LIGHT_PURPLE + "[InfuseS1] " + ChatColor.GOLD + "Congrats, you have been awarded " +
                        ChatColor.RED + randomPositiveEffect.getType().getKey().toString().split(":")[1] + " for your kill!");
            } else killer.getWorld().dropItem(victim.getLocation(), MysteryEffectRecipe.getMysteryEffectItem(plugin.modelID));
        }

        datatable.put(killer.getUniqueId().toString(), effects);
        DataTable.set(plugin, datatable);
    }
}
