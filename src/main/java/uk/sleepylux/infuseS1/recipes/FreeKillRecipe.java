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

public final class FreeKillRecipe {
    public static void setupRecipe(Main plugin) {
        NamespacedKey key = new NamespacedKey(plugin, "free_kill");
        ItemStack item = getFreeKillItem(plugin.modelID);

        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape("TDT", "WNW", "TDT");
        recipe.setIngredient('T', Material.TOTEM_OF_UNDYING);
        recipe.setIngredient('D', Material.DIAMOND_BLOCK);
        recipe.setIngredient('W', Material.POTION);

        Bukkit.addRecipe(recipe);
    }

    public static ItemStack getFreeKillItem(int modelID) {
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) meta = Bukkit.getItemFactory().getItemMeta(Material.NETHER_STAR);
        assert meta != null;

        meta.setDisplayName("Free Kill");
        meta.setRarity(ItemRarity.EPIC);
        meta.setCustomModelData(modelID);
        meta.setLore(List.of("Gain a free positive effect!", "Or lose a negative one"));

        item.setItemMeta(meta);
        return item;
    }
}
