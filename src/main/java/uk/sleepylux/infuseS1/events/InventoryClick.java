package uk.sleepylux.infuseS1.events;

import org.bukkit.*;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import uk.sleepylux.infuseS1.Main;
import uk.sleepylux.infuseS1.registry.BanTable;
import uk.sleepylux.infuseS1.registry.DataTable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class InventoryClick implements Listener {
    public Main plugin;
    public InventoryClick(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();

        if (item == null || item.getType().isAir()) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.hasCustomModelData() || meta.getCustomModelData() != plugin.modelID) return;

        HumanEntity player = event.getWhoClicked();

        switch (event.getView().getTitle().toLowerCase().trim()) {
            case "revive a player":
                event.setCancelled(true);
                List<UUID> bantable = BanTable.get(plugin);
                OfflinePlayer revivePlayer = ((SkullMeta) meta).getOwningPlayer();

                Bukkit.getBanList(BanList.Type.NAME).pardon(revivePlayer.getName());
                bantable.remove(revivePlayer.getUniqueId());
                BanTable.set(plugin, bantable);

                Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "[InfuseS1] " + ChatColor.AQUA + revivePlayer.getName() + ChatColor.GOLD + " has been revived!");
                break;
            case "upgrade an effect":
                event.setCancelled(true);
                Map<String, List<PotionEffect>> datatable = DataTable.get(plugin);
                List<PotionEffect> effects = datatable.get(player.getUniqueId().toString());

                NamespacedKey namespacedKey = new NamespacedKey(plugin, "potion_effect_meta");
                PotionEffectType effect = Registry.SimpleRegistry.EFFECT.get(
                        NamespacedKey.fromString(meta.getPersistentDataContainer().get(namespacedKey, PersistentDataType.STRING))
                );
                PotionEffect newEffect = new PotionEffect(effect, -1, 1, true, false);

                effects.removeIf(potionEffect -> potionEffect.getType() == newEffect.getType());
                effects.add(newEffect);
                player.removePotionEffect(newEffect.getType());
                player.addPotionEffect(newEffect);

                datatable.put(player.getUniqueId().toString(), effects);
                DataTable.set(plugin, datatable);
                break;
            default:

        }

        player.closeInventory();

        ItemStack itemInuse = player.getInventory().getItemInMainHand();
        itemInuse.setAmount(itemInuse.getAmount()-1);

    }
}
