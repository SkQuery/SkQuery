package com.skquery.skquery.elements.events.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class TabCompletion implements TabCompleter {

    public TabCompletion(PluginCommand toAttach) {
        toAttach.setTabCompleter(this);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        AttachedTabCompleteEvent event = new AttachedTabCompleteEvent(sender, command, args);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return null;
        return event.getResult();
}
}
