/*
This file is part of InfuseS1.
InfuseS1 is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
InfuseS1 is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with InfuseS1. If not, see <https://www.gnu.org/licenses/>.
 */
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
    private static final Type type = new TypeToken<Map<String, List<PotionEffectWrapper>>>() {}.getType();

    @NonNull
    public static Map<String, List<PotionEffect>> get(Main plugin) {
        String encodedDatatable = plugin.getConfig().getString("DataTable");
        if (encodedDatatable == null) encodedDatatable = Base64.getEncoder().encodeToString("{}".getBytes());
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
    }
}
