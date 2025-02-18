package uk.sleepylux.infuseS1.recipes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import uk.sleepylux.infuseS1.Main;
import uk.sleepylux.infuseS1.registry.DataTable;
import uk.sleepylux.infuseS1.registry.Effects;
import uk.sleepylux.infuseS1.utility.EffectRandomizer;
import uk.sleepylux.infuseS1.utility.Translator;

import java.awt.*;
import java.util.List;
import java.util.Map;

public final class MysteryEffectRecipe {
    public static void setupRecipe(Main plugin) {
        NamespacedKey key = new NamespacedKey(plugin, "mystery_effect");
        ItemStack item = getMysteryEffectItem(plugin.modelID);

        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape("TDT", "WNW", "TDT");
        recipe.setIngredient('T', Material.TOTEM_OF_UNDYING);
        recipe.setIngredient('D', Material.DIAMOND_BLOCK);
        recipe.setIngredient('W', Material.WATER_BUCKET);
        recipe.setIngredient('N', Material.NETHERITE_INGOT);

        Bukkit.addRecipe(recipe);
    }

    public static ItemStack getMysteryEffectItem(int modelID) {
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) meta = Bukkit.getItemFactory().getItemMeta(Material.NETHER_STAR);
        assert meta != null;

        meta.setDisplayName("Mystery Effect");
        meta.setRarity(ItemRarity.EPIC);
        meta.setCustomModelData(modelID);
        meta.setLore(List.of("Gain a free positive effect!"));

        item.setItemMeta(meta);
        return item;
    }

    public static void onUse(Main plugin, PlayerInteractEvent event) {
        FileConfiguration config = plugin.getConfig();
        Player player = event.getPlayer();

        Map<String, List<PotionEffect>> datatable = DataTable.get(plugin);
        List<PotionEffect> effects = datatable.get(player.getUniqueId().toString());

        PotionEffect randomNegativeEffect = EffectRandomizer.getRandomEffect(Effects.negativeEffects(config).stream().toList(),
                effects.stream().map(PotionEffect::getType).toList(), true);
        if (randomNegativeEffect != null) {
            effects.removeIf(effect -> effect.getType() == randomNegativeEffect.getType());
            player.removePotionEffect(randomNegativeEffect.getType());
            player.sendMessage(ChatColor.LIGHT_PURPLE + "[InfuseS1] " + ChatColor.GOLD + "You no longer have " +
                    ChatColor.RED + randomNegativeEffect.getType().getKey().toString().split(":")[1] +
                    ChatColor.GOLD + " from redeeming a Mystery Effect!");
        } else {
            PotionEffect randomPositiveEffect = EffectRandomizer.getRandomEffect(Effects.positiveEffects(config).stream().toList(),
                    effects.stream().map(PotionEffect::getType).toList(), false);
            if (randomPositiveEffect != null) {
                effects.add(randomPositiveEffect);
                player.addPotionEffect(randomPositiveEffect);
                player.sendMessage(ChatColor.LIGHT_PURPLE + "[InfuseS1] " + ChatColor.GOLD + "You have been awarded " +
                        ChatColor.GREEN + Translator.getDisplayNameFromTranslationKey(randomPositiveEffect.getType().getTranslationKey()) +
                        ChatColor.GOLD + " from using a Mystery Effect");
            } else player.getWorld().dropItem(player.getLocation(), MysteryEffectRecipe.getMysteryEffectItem(plugin.modelID));
        }

        ItemStack itemInuse = player.getInventory().getItemInMainHand();
        itemInuse.setAmount(itemInuse.getAmount()-1);

        datatable.put(player.getUniqueId().toString(), effects);
        DataTable.set(plugin, datatable);
    }
}
