package uk.sleepylux.infuseS1.wrappers;

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