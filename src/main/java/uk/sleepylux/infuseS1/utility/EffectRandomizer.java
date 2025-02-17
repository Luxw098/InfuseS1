package uk.sleepylux.infuseS1.utility;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public final class EffectRandomizer {
    @Nullable
    public static PotionEffect getRandomEffect(List<PotionEffectType> effectList, List<PotionEffectType> mask, boolean maskInclusive) {
        List<PotionEffect> maskedEffects = effectList.stream().filter(potionEffectType -> {
            if (maskInclusive && mask.contains(potionEffectType))
                return true;
            else return !maskInclusive && !mask.contains(potionEffectType);
        }).map(potionEffectType ->
                new PotionEffect(potionEffectType, PotionEffect.INFINITE_DURATION, 1, true, false)).toList();

        Random random = new Random();
        return maskedEffects.get(random.nextInt(maskedEffects.size()));
    }
}
