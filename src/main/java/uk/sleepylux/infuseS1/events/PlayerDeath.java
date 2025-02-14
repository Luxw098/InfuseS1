/*
This file is part of InfuseS1.
InfuseS1 is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
InfuseS1 is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with InfuseS1. If not, see <https://www.gnu.org/licenses/>.
 */

package uk.sleepylux.infuseS1.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import uk.sleepylux.infuseS1.registry.Effects;

import java.util.List;
import java.util.Random;

public class PlayerDeath implements Listener {
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        @SuppressWarnings("unchecked")
        List<PotionEffect> currentEffects = (List<PotionEffect>) player.getMetadata("potionEffects").getFirst().value();
        if (currentEffects == null || currentEffects.isEmpty()) return;
        Random random = new Random();


        List<PotionEffect> positiveEffectsMask = currentEffects.stream()
                .filter(potionEffect -> Effects.positiveEffects.contains(potionEffect.getType())).toList();
        if (!positiveEffectsMask.isEmpty()) {
            PotionEffect positiveEffect = positiveEffectsMask.get(random.nextInt(positiveEffectsMask.size()));
            currentEffects.remove(positiveEffect);
            player.sendMessage(ChatColor.LIGHT_PURPLE + "[InfuseS1] " + ChatColor.GOLD + "Due to your death, " + ChatColor.GREEN + positiveEffect.getType() + ChatColor.GOLD + " has been removed");
        } else {
            List<PotionEffectType> negativeEffectsMask = Effects.negativeEffects.stream()
                    .filter(negativeEffect -> !currentEffects.stream()
                            .map(PotionEffect::getType).toList().contains(negativeEffect)).toList();
            PotionEffectType negativeEffectType = negativeEffectsMask.get(random.nextInt(negativeEffectsMask.size()));
            PotionEffect negativeEffect = new PotionEffect(negativeEffectType, PotionEffect.INFINITE_DURATION, 1, true, false);
            currentEffects.add(negativeEffect);
            player.sendMessage(ChatColor.LIGHT_PURPLE + "[InfuseS1] " + ChatColor.GOLD + "Due to your death, you now have " + ChatColor.RED +  negativeEffectType);
        }
        player.addPotionEffects(currentEffects);
    }
}
