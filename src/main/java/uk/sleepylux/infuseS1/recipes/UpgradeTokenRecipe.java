package uk.sleepylux.infuseS1.recipes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import uk.sleepylux.infuseS1.Main;

import java.util.List;

public final class UpgradeTokenRecipe {
    public static void setupRecipe(Main plugin) {
        NamespacedKey key = new NamespacedKey(plugin, "upgrade_token");
        ItemStack item = getUpgradeToken(plugin.modelID);

        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape("TGW", "GEG", "BGE");
        recipe.setIngredient('T', Material.TOTEM_OF_UNDYING);
        recipe.setIngredient('G', Material.GOLD_BLOCK);
        recipe.setIngredient('W', Material.WITHER_SKELETON_SKULL);
        recipe.setIngredient('E', Material.ENCHANTED_GOLDEN_APPLE);
        recipe.setIngredient('B', Material.BLAZE_ROD);

        Bukkit.addRecipe(recipe);
    }

    public static ItemStack getUpgradeToken(int modelID) {
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) meta = Bukkit.getItemFactory().getItemMeta(Material.ENCHANTED_GOLDEN_APPLE);
        assert meta != null;

        meta.setDisplayName("Upgrade Token");
        meta.setRarity(ItemRarity.EPIC);
        meta.setCustomModelData(modelID);
        meta.setLore(List.of("Upgrade an effect to Level 2!"));

        item.setItemMeta(meta);
        return item;
    }
}
