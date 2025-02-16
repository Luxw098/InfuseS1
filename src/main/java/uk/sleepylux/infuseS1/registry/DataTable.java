package uk.sleepylux.infuseS1.registry;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.potion.PotionEffect;

import java.lang.reflect.Type;
import java.util.Base64;
import java.util.List;
import java.util.Map;

public class DataTable {
    private static final Gson gson = new Gson();
    public static Map<String, List<PotionEffect>> get(FileConfiguration config) {
        Type type = new TypeToken<Map<String, List<PotionEffect>>>() {}.getType();
        String byteTableString = new String(Base64.getDecoder().decode(config.getString("DataTable")));
        return gson.fromJson(byteTableString, type);
    }
    public static void set(FileConfiguration config, Map<String, List<PotionEffect>> datatable) {
        String parsedMap = gson.toJson(datatable);
        String encodedString = Base64.getEncoder().encodeToString(parsedMap.getBytes());
        config.set("DataTable", encodedString);
    }
}
