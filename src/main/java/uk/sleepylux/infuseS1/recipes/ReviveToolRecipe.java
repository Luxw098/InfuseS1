package uk.sleepylux.infuseS1.recipes;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import uk.sleepylux.infuseS1.Main;
import uk.sleepylux.infuseS1.registry.BanTable;

import java.util.List;
import java.util.UUID;

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

    public static void onUse(Main plugin, PlayerInteractEvent event) {
        Player player = event.getPlayer();

        List<UUID> bantable = BanTable.get(plugin);

        Inventory gui = Bukkit.createInventory(null, 56, "Revive A Player");

        for (UUID uuid : bantable) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            ItemStack item = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) item.getItemMeta();
            if (meta == null) meta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.PLAYER_HEAD);
            assert meta != null;

            meta.setDisplayName("Revive " + offlinePlayer.getName());
            meta.setOwningPlayer(offlinePlayer);
            meta.setOwnerProfile(offlinePlayer.getPlayerProfile());
            meta.setCustomModelData(plugin.modelID);

            item.setItemMeta(meta);
            gui.addItem(item);
        }

        player.openInventory(gui);

    }
}
