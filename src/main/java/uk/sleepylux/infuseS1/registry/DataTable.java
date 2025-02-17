package uk.sleepylux.infuseS1.registry;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.bukkit.potion.PotionEffect;
import org.checkerframework.checker.nullness.qual.NonNull;
import uk.sleepylux.infuseS1.Main;
import uk.sleepylux.infuseS1.utility.PotionEffectWrapper;

import java.lang.reflect.Type;
import java.util.*;

public final class DataTable {
    private static final Gson gson = new Gson();

    @NonNull
    public static Map<String, List<PotionEffect>> get(Main plugin) {
        Type type = new TypeToken<Map<String, List<PotionEffectWrapper>>>() {}.getType();
        String encodedDatatable = plugin.getConfig().getString("DataTable");
        if (encodedDatatable == null) encodedDatatable = Base64.getEncoder().encodeToString(gson.toJson("{}").getBytes());
        String byteTableString = new String(Base64.getDecoder().decode(encodedDatatable));

        Map<String, List<PotionEffectWrapper>> datatableRaw = gson.fromJson(byteTableString, type);
        if (datatableRaw == null) datatableRaw = Map.of();
        Map<String, List<PotionEffect>> datatable = new HashMap<>();
        for (Map.Entry<String, List<PotionEffectWrapper>> entry : datatableRaw.entrySet()) {
            List<PotionEffect> effects = new ArrayList<>();
            for (PotionEffectWrapper effectWrapper : entry.getValue())
                effects.add(effectWrapper.toPotionEffect());
            datatable.put(entry.getKey(), effects);
        }
        return datatable;
    }

    public static void set(Main plugin, Map<String, List<PotionEffect>> datatable) {
        Type type = new TypeToken<Map<String, List<PotionEffectWrapper>>>() {}.getType();
        Map<String, List<PotionEffectWrapper>> wrapper = new HashMap<>();
        for (Map.Entry<String, List<PotionEffect>> entry : datatable.entrySet()) {
            List<PotionEffectWrapper> effectWrappers = new ArrayList<>();
            for (PotionEffect effect : entry.getValue())
                effectWrappers.add(new PotionEffectWrapper(effect));
            wrapper.put(entry.getKey(), effectWrappers);
        }
        String parsedMap = gson.toJson(wrapper, type);
        String encodedString = Base64.getEncoder().encodeToString(parsedMap.getBytes());
        plugin.getConfig().set("DataTable", encodedString);
        plugin.saveConfig();
        plugin.getLogger().info("Updated config");
    }
}
