/*
This file is part of InfuseS1.
InfuseS1 is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
InfuseS1 is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with InfuseS1. If not, see <https://www.gnu.org/licenses/>.
 */

package uk.sleepylux.infuseS1.registry;

import org.bukkit.potion.PotionEffectType;

import java.util.Collection;
import java.util.List;

public class Effects {
    public static final Collection<PotionEffectType> negativeEffects = List.of(PotionEffectType.UNLUCK, PotionEffectType.GLOWING,
            PotionEffectType.WEAKNESS, PotionEffectType.MINING_FATIGUE, PotionEffectType.JUMP_BOOST,
            PotionEffectType.SLOWNESS, PotionEffectType.SLOW_FALLING);

    public static final Collection<PotionEffectType> positiveEffects = List.of(PotionEffectType.WATER_BREATHING, PotionEffectType.RESISTANCE,
            PotionEffectType.STRENGTH, PotionEffectType.CONDUIT_POWER, PotionEffectType.HERO_OF_THE_VILLAGE,
            PotionEffectType.HASTE, PotionEffectType.INVISIBILITY, PotionEffectType.REGENERATION,
            PotionEffectType.SPEED, PotionEffectType.FIRE_RESISTANCE, PotionEffectType.DOLPHINS_GRACE);
}
