/*
This file is part of InfuseS1.
InfuseS1 is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
InfuseS1 is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with InfuseS1. If not, see <https://www.gnu.org/licenses/>.
 */

package uk.sleepylux.infuseS1;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import uk.sleepylux.infuseS1.Commands.CommandInfuse;
import uk.sleepylux.infuseS1.events.*;
import uk.sleepylux.infuseS1.recipes.MysteryEffectRecipe;
import uk.sleepylux.infuseS1.recipes.ReviveToolRecipe;
import uk.sleepylux.infuseS1.recipes.UpgradeTokenRecipe;

import java.util.Objects;

public final class Main extends JavaPlugin {
    public int modelID = 948193;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new PlayerJoin(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDeath(this), this);
        getServer().getPluginManager().registerEvents(new PlayerKill(this), this);
        getServer().getPluginManager().registerEvents(new MilkDrink(this), this);
        getServer().getPluginManager().registerEvents(new UseItem(this), this);
        getServer().getPluginManager().registerEvents(new InventoryClick(this), this);
        getServer().getPluginManager().registerEvents(new InvulnerableItems(this), this);

        MysteryEffectRecipe.setupRecipe(this);
        ReviveToolRecipe.setupRecipe(this);
        UpgradeTokenRecipe.setupRecipe(this);

        Objects.requireNonNull(this.getCommand("infuse")).setExecutor(new CommandInfuse());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
