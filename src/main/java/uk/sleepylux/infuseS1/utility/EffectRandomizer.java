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
