/*
This file is part of InfuseS1.
InfuseS1 is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
InfuseS1 is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with InfuseS1. If not, see <https://www.gnu.org/licenses/>.
 */

package uk.sleepylux.infuseS1.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import uk.sleepylux.infuseS1.Main;
import uk.sleepylux.infuseS1.registry.Effects;

import java.util.List;
import java.util.Random;

public class PlayerKill implements Listener {


    private final Main plugin;
    public PlayerKill(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        EntityDamageEvent damageEvent = player.getLastDamageCause();

        if (damageEvent == null
            || damageEvent.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) return;

        Entity attackerEntity = ((EntityDamageByEntityEvent) damageEvent).getDamager();
        if (!(attackerEntity instanceof Player attacker)) return;
        Random random = new Random();

        List<PotionEffect> currentEffects = new java.util.ArrayList<>(attacker.getActivePotionEffects().stream()
                .filter(potionEffect -> potionEffect.getDuration() != -1).toList());

        List<PotionEffect> effectNegativeMask = currentEffects.stream()
                .filter(potionEffect -> Effects.negativeEffects.contains(potionEffect.getType())).toList();
        if (!effectNegativeMask.isEmpty()) {
             PotionEffect negativeEffect = effectNegativeMask.get(random.nextInt(effectNegativeMask.size()));
             attacker.removePotionEffect(negativeEffect.getType());
             attacker.sendMessage(ChatColor.LIGHT_PURPLE + "[InfuseS1] " + ChatColor.GOLD + "Congrats, You no longer have" + ChatColor.RED + negativeEffect.getType());
        } else {
            List<PotionEffectType> positiveEffectMask = Effects.positiveEffects.stream()
                    .filter(positiveEffectType -> currentEffects.stream()
                            .map(PotionEffect::getType).toList().contains(positiveEffectType)).toList();

            PotionEffectType positiveEffectType = positiveEffectMask.get(random.nextInt(positiveEffectMask.size()));

            PotionEffect positiveEffect = new PotionEffect(positiveEffectType, PotionEffect.INFINITE_DURATION, 1, true, false);
            attacker.addPotionEffect(positiveEffect);
            attacker.sendMessage(ChatColor.LIGHT_PURPLE + "[InfuseS1] " + ChatColor.GOLD + "You have been awarded " + ChatColor.GREEN + positiveEffect.getType() + ChatColor.GOLD + " for your kill!");
        }

        List<PotionEffect> effects = (List<PotionEffect>) player.getActivePotionEffects();
        event.getEntity().setMetadata("potionEffects", new FixedMetadataValue(plugin, effects));
    }
}
