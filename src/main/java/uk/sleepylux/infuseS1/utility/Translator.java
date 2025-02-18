/*
This file is part of InfuseS1.
InfuseS1 is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
InfuseS1 is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with InfuseS1. If not, see <https://www.gnu.org/licenses/>.
 */
package uk.sleepylux.infuseS1.utility;


import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.HashMap;
import java.util.Map;

public class Translator {
    public static final Map<PotionEffectType, PotionType> EFFECT_TO_POTION = new HashMap<>();

    static {
        EFFECT_TO_POTION.put(PotionEffectType.WATER_BREATHING, PotionType.WATER_BREATHING);
        EFFECT_TO_POTION.put(PotionEffectType.STRENGTH, PotionType.STRENGTH);
        EFFECT_TO_POTION.put(PotionEffectType.INVISIBILITY, PotionType.INVISIBILITY);
        EFFECT_TO_POTION.put(PotionEffectType.REGENERATION, PotionType.REGENERATION);
        EFFECT_TO_POTION.put(PotionEffectType.SPEED, PotionType.SWIFTNESS);
        EFFECT_TO_POTION.put(PotionEffectType.FIRE_RESISTANCE, PotionType.FIRE_RESISTANCE);
        EFFECT_TO_POTION.put(PotionEffectType.WEAKNESS, PotionType.WEAKNESS);
        EFFECT_TO_POTION.put(PotionEffectType.SLOWNESS, PotionType.SLOWNESS);
        EFFECT_TO_POTION.put(PotionEffectType.SLOW_FALLING, PotionType.SLOW_FALLING);
    }

    public static PotionType getPotionTypeFromEffectType(PotionEffectType effect) {
        return EFFECT_TO_POTION.getOrDefault(effect, null);
    }

    public static String getDisplayNameFromTranslationKey(String translationKey) {
        String effectName = translationKey.replace("effect.minecraft.", "");
        effectName = effectName.substring(0, 1).toUpperCase() + effectName.substring(1);
        effectName = effectName.replace("_", " ");
        return effectName;
    }}
