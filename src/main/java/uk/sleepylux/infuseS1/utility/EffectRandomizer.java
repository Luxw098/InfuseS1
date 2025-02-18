/*
This file is part of InfuseS1.
InfuseS1 is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
InfuseS1 is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with InfuseS1. If not, see <https://www.gnu.org/licenses/>.
 */
package uk.sleepylux.infuseS1.utility;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public final class EffectRandomizer {

    @Nullable
    public static PotionEffect getRandomEffect(List<PotionEffectType> effectList, List<PotionEffectType> mask, boolean maskInclusive) {
        List<PotionEffect> maskedEffects = effectList.stream().filter(potionEffectType ->
                maskInclusive ? mask.contains(potionEffectType) : !mask.contains(potionEffectType)
            ).map(potionEffectType ->
                new PotionEffect(potionEffectType, PotionEffect.INFINITE_DURATION, 0, true, false)).toList();

        Random random = new Random();
        if (maskedEffects.isEmpty()) return null;
        return maskedEffects.get(random.nextInt(maskedEffects.size()));
    }
}
