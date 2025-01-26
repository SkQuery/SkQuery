package com.skquery.skquery.elements.effects.base;

import java.io.File;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

public abstract class Pragma extends Effect {

    @Override
    protected void execute(Event event) {}

    @Override
    public String toString(Event event, boolean b) {
        return "pragma";
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        register(getParser().getCurrentScript().getConfig().getFile(), parseResult);
        return true;
    }

    protected abstract void register(File executingScript, SkriptParser.ParseResult parseResult);
}
