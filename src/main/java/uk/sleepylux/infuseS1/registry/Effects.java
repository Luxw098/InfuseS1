/*
This file is part of InfuseS1.
InfuseS1 is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
InfuseS1 is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with InfuseS1. If not, see <https://www.gnu.org/licenses/>.
 */

package uk.sleepylux.infuseS1.registry;

import org.bukkit.NamespacedKey;
import org.bukkit.Registry.SimpleRegistry;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public final class Effects {
    public static Collection<PotionEffectType> positiveEffects (FileConfiguration config) {
        List<String> configEffects = config.getStringList("PositiveEffects");
        return configEffects.stream().map(configEffect -> {
            NamespacedKey key = NamespacedKey.fromString("minecraft:" + configEffect.toLowerCase());
            if (key == null) return null;
            return SimpleRegistry.EFFECT.get(key);
        }).filter(Objects::nonNull).toList();
    }
    public static Collection<PotionEffectType> negativeEffects(FileConfiguration config) {
        List<String> configEffects = config.getStringList("NegativeEffects");
        return configEffects.stream().map(configEffect -> {
            NamespacedKey key = NamespacedKey.fromString("minecraft:" + configEffect.toLowerCase());
            if (key == null) return null;
            return SimpleRegistry.EFFECT.get(key);

        }).filter(Objects::nonNull).toList();
    }

}
