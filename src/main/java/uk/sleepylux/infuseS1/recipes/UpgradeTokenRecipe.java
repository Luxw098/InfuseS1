package uk.sleepylux.infuseS1.recipes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;
import uk.sleepylux.infuseS1.Main;
import uk.sleepylux.infuseS1.registry.DataTable;

import java.util.List;
import java.util.Map;

public final class UpgradeTokenRecipe {
    public static void setupRecipe(Main plugin) {
        NamespacedKey key = new NamespacedKey(plugin, "upgrade_token");
        ItemStack item = getUpgradeToken(plugin.modelID);

        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape("TGW", "GSG", "BGE");
        recipe.setIngredient('T', Material.TOTEM_OF_UNDYING);
        recipe.setIngredient('G', Material.GOLD_BLOCK);
        recipe.setIngredient('W', Material.WITHER_SKELETON_SKULL);
        recipe.setIngredient('S', Material.SUNFLOWER);
        recipe.setIngredient('E', Material.ENCHANTED_GOLDEN_APPLE);
        recipe.setIngredient('B', Material.BLAZE_ROD);

        Bukkit.addRecipe(recipe);
    }

    public static ItemStack getUpgradeToken(int modelID) {
        ItemStack item = new ItemStack(Material.SUNFLOWER);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) meta = Bukkit.getItemFactory().getItemMeta(Material.SUNFLOWER);
        assert meta != null;

        meta.setDisplayName("Upgrade Token");
        meta.setRarity(ItemRarity.EPIC);
        meta.setCustomModelData(modelID);
        meta.setLore(List.of("Upgrade an effect to Level 2!"));

        item.setItemMeta(meta);
        return item;
    }

    public static void onUse(Main plugin, PlayerInteractEvent event) {
        Player player = event.getPlayer();

        Map<String, List<PotionEffect>> datatable = DataTable.get(plugin);
        List<PotionEffect> effects = datatable.get(player.getUniqueId().toString());

        Inventory gui = Bukkit.createInventory(null, 9, "Upgrade An Effect");

        for (PotionEffect effect : effects) {
            ItemStack item = new ItemStack(Material.POTION);
            PotionMeta meta = (PotionMeta) item.getItemMeta();
            if (meta == null) meta = (PotionMeta) Bukkit.getItemFactory().getItemMeta(Material.POTION);
            assert meta != null;

            meta.setDisplayName("Upgrade " + effect.getType().getKey().toString().split(":")[1]);
            meta.setBasePotionType(Registry.SimpleRegistry.POTION.get(effect.getType().getKey()));
            meta.setCustomModelData(plugin.modelID);

            item.setItemMeta(meta);
            gui.addItem(item);
        }

        player.openInventory(gui);
    }
}
