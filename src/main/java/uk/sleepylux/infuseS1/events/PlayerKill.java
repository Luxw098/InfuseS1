/*
This file is part of InfuseS1.
InfuseS1 is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
InfuseS1 is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with InfuseS1. If not, see <https://www.gnu.org/licenses/>.
 */

package uk.sleepylux.infuseS1.events;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import uk.sleepylux.infuseS1.Main;
import uk.sleepylux.infuseS1.recipes.FreeKillRecipe;
import uk.sleepylux.infuseS1.registry.DataTable;
import uk.sleepylux.infuseS1.registry.Effects;

import java.util.List;
import java.util.Map;
import java.util.Random;

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
        Entity killer = victim.getKiller();
        EntityDamageEvent cause = victim.getLastDamageCause();
        if ((config.getBoolean("ActivateUponDeathByPlayer") && victim.getKiller() == null)
            && (config.getBoolean("ActivateUponDeathByMob") && cause != null && cause.getCause() == DamageCause.ENTITY_ATTACK)) return;

        if (!(killer instanceof Player attacker)) return;
        Random random = new Random();

        List<PotionEffect> currentEffects = new java.util.ArrayList<>(attacker.getActivePotionEffects().stream()
                .filter(potionEffect -> potionEffect.getDuration() != -1).toList());

        List<PotionEffect> effectNegativeMask = currentEffects.stream()
                .filter(potionEffect -> Effects.negativeEffects(config).contains(potionEffect.getType())).toList();
        if (!effectNegativeMask.isEmpty()) {
             PotionEffect negativeEffect = effectNegativeMask.get(random.nextInt(effectNegativeMask.size()));
             attacker.removePotionEffect(negativeEffect.getType());
             attacker.sendMessage(ChatColor.LIGHT_PURPLE + "[InfuseS1] " + ChatColor.GOLD + "Congrats, You no longer have" + ChatColor.RED + negativeEffect.getType());
        } else {
            List<PotionEffectType> positiveEffectMask = Effects.positiveEffects(config).stream()
                    .filter(positiveEffectType -> !currentEffects.stream()
                            .map(PotionEffect::getType).toList().contains(positiveEffectType)).toList();
            if (!positiveEffectMask.isEmpty()) {
                PotionEffectType positiveEffectType = positiveEffectMask.get(random.nextInt(positiveEffectMask.size()));

                PotionEffect positiveEffect = new PotionEffect(positiveEffectType, PotionEffect.INFINITE_DURATION, 1, true, false);
                attacker.addPotionEffect(positiveEffect);
                attacker.sendMessage(ChatColor.LIGHT_PURPLE + "[InfuseS1] " + ChatColor.GOLD + "You have been awarded " + ChatColor.GREEN + positiveEffect.getType() + ChatColor.GOLD + " for your kill!");
            } else {
                victim.getWorld().dropItem(victim.getLocation(), FreeKillRecipe.getFreeKillItem(plugin.modelID));
            }
        }

        List<PotionEffect> victimEffectMap = victim.getActivePotionEffects().stream()
                .filter(potionEffect -> potionEffect.getDuration() == -1).toList();
        List<PotionEffect> attackerEffectMap = attacker.getActivePotionEffects().stream()
                .filter(potionEffect -> potionEffect.getDuration() == -1).toList();

        Map<String, List<PotionEffect>> datatable = DataTable.get(plugin);
        datatable.put(victim.getUniqueId().toString(), victimEffectMap);
        datatable.put(attacker.getUniqueId().toString(), attackerEffectMap);
        DataTable.set(plugin, datatable);
    }
}
