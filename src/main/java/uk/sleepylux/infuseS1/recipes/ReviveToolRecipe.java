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

public final class ReviveToolRecipe {
    public static void setupRecipe(Main plugin) {
        NamespacedKey key = new NamespacedKey(plugin, "revive_tool");
        ItemStack item = getReviveTool(plugin.modelID);

        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape("NDN", "DBD", "NDN");
        recipe.setIngredient('N', Material.NETHERITE_INGOT);
        recipe.setIngredient('D', Material.DIAMOND_BLOCK);
        recipe.setIngredient('B', Material.BEACON);

        Bukkit.addRecipe(recipe);
    }

    public static ItemStack getReviveTool(int modelID) {
        ItemStack item = new ItemStack(Material.BEACON);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) meta = Bukkit.getItemFactory().getItemMeta(Material.BEACON);
        assert meta != null;

        meta.setDisplayName("Revive Tool");
        meta.setRarity(ItemRarity.EPIC);
        meta.setCustomModelData(modelID);
        meta.setLore(List.of("Bring a user back to life!"));

        item.setItemMeta(meta);
        return item;
    }
}
