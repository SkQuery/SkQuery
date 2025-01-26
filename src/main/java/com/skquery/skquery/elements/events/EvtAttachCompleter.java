package com.skquery.skquery.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import com.skquery.skquery.elements.events.bukkit.AttachedTabCompleteEvent;
import com.skquery.skquery.elements.events.bukkit.TabCompletion;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Event;

public class EvtAttachCompleter extends SkriptEvent {

    private String command;

    @SuppressWarnings({ "unchecked" })
	@Override
    public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
        String s = ((Literal<String>) args[0]).getSingle();
        if (s.startsWith("/") && s.length() > 1) s = s.substring(1);
        PluginCommand cmd = Bukkit.getPluginCommand(s);
        if (cmd == null) {
            Skript.error("A tab completer cannot be assigned before the command is defined.");
            return false;
        }
        command = s;
        new TabCompletion(cmd);
        return true;
    }

    @Override
    public boolean check(Event e) {
        return ((AttachedTabCompleteEvent) e).getCommand().getName().equals(command);
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "attached command";
    }
}
