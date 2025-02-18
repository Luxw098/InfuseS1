/*
This file is part of InfuseS1.
InfuseS1 is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
InfuseS1 is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with InfuseS1. If not, see <https://www.gnu.org/licenses/>.
 */
package uk.sleepylux.infuseS1.utility;

import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.potion.PotionEffect;

public class PotionEffectWrapper {
    private final String type;  // Store as String instead of PotionEffectType
    private final int amplifier;

    public PotionEffectWrapper(PotionEffect effect) {
        this.type = effect.getType().getKey().toString(); // Store key as string
        this.amplifier = effect.getAmplifier();
    }

    @SuppressWarnings("DataFlowIssue")
    public PotionEffect toPotionEffect() {
        return new PotionEffect(Registry.SimpleRegistry.EFFECT.get(NamespacedKey.fromString(type)), -1, amplifier, true, false);
    }
}