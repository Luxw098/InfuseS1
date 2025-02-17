package uk.sleepylux.infuseS1.registry;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.checkerframework.checker.nullness.qual.NonNull;
import uk.sleepylux.infuseS1.Main;

import java.lang.reflect.Type;
import java.util.*;

public final class BanTable {
    private static final Gson gson = new Gson();
    private static final Type type = new TypeToken<List<UUID>>() {}.getType();

    @NonNull
    public static List<UUID> get(Main plugin) {
        String encodedBantable = plugin.getConfig().getString("BanTable");
        if (encodedBantable == null) encodedBantable = Base64.getEncoder().encodeToString("{}".getBytes());
        String byteTableString = new String(Base64.getDecoder().decode(encodedBantable));

        List<UUID> bantable = gson.fromJson(byteTableString, type);
        if (bantable == null) bantable = new ArrayList<>();
        return bantable;
    }

    public static void set(Main plugin, List<UUID> bantable) {
        String parsedList = gson.toJson(bantable, type);
        String encodedString = Base64.getEncoder().encodeToString(parsedList.getBytes());
        plugin.getConfig().set("BanTable", encodedString);
        plugin.saveConfig();
    }
}

