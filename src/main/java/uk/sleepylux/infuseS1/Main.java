/*
This file is part of InfuseS1.
InfuseS1 is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
InfuseS1 is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with InfuseS1. If not, see <https://www.gnu.org/licenses/>.
 */

package uk.sleepylux.infuseS1;

import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.plugin.java.JavaPlugin;
import uk.sleepylux.infuseS1.events.MilkDrink;
import uk.sleepylux.infuseS1.events.PlayerDeath;
import uk.sleepylux.infuseS1.events.PlayerJoin;
import uk.sleepylux.infuseS1.events.PlayerKill;

import java.util.Objects;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();

        // I forgot to add these in the previous commits lol
        getServer().getPluginManager().registerEvents(new PlayerJoin(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDeath(this), this);
        getServer().getPluginManager().registerEvents(new PlayerKill(this), this);
        getServer().getPluginManager().registerEvents(new MilkDrink(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
