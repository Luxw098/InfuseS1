/*
This file is part of InfuseS1.
InfuseS1 is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
InfuseS1 is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with InfuseS1. If not, see <https://www.gnu.org/licenses/>.
 */
package uk.sleepylux.infuseS1.Commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandInfuse implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return true;

        String ghLink = "https://github.com/Luxw098/InfuseS1";
//        player.sendMessage( Component.text(ChatColor.LIGHT_PURPLE + "[InfuseS1 Information]\n" +
//                ChatColor.GOLD + "Source: " + Component.text("Github Page").clickEvent(ClickEvent.openUrl(ghLink)) + "\n" +
//                ChatColor.RED + "Report Issues: " + Component.text("Github Page").clickEvent(ClickEvent.openUrl(ghLink + "/issues/new")) + "\n" +
//                ChatColor.BLUE + "Contact @ Discord: TirednHigh");

        TextComponent message = new TextComponent("[InfuseS1 Information]\n");
        message.setColor(ChatColor.LIGHT_PURPLE);

        TextComponent messageSrcText = new TextComponent("\nSource Code @ ");
        messageSrcText.setColor(ChatColor.GOLD);
        TextComponent messageSrc = new TextComponent("Github page");
        messageSrc.setColor(ChatColor.GOLD);
        messageSrc.setUnderlined(true);
        messageSrc.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, ghLink));

        TextComponent messageReportText = new TextComponent("\nReport Issues @ ");
        messageReportText.setColor(ChatColor.RED);
        TextComponent messageReport = new TextComponent("Github issues");
        messageReport.setColor(ChatColor.RED);
        messageReport.setUnderlined(true);
        messageReport.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, ghLink + "/issues"));

        TextComponent messageContact = new TextComponent("\nContact my Discord @ TirednHigh");
        messageContact.setColor(ChatColor.BLUE);

        message.addExtra(messageSrcText);
        message.addExtra(messageSrc);
        message.addExtra(messageReportText);
        message.addExtra(messageReport);
        message.addExtra(messageContact);

        player.spigot().sendMessage(message);
        return true;
    }
}
