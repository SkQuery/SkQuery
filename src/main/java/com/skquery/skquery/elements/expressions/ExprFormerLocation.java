package com.skquery.skquery.elements.expressions;

import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;

import com.skquery.skquery.annotations.Patterns;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.parser.ParserInstance;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.util.Kleenean;

@Patterns("[the] (past|former) move[ment] [location]")
public class ExprFormerLocation extends SimpleExpression<Location>{
	
    @Override
    protected Location[] get(Event event) {
        return new Location[] {((PlayerMoveEvent)event).getFrom()};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Location> getReturnType() {
        return Location.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "former movement location";
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        if(!ParserInstance.get().isCurrentEvent(PlayerMoveEvent.class)) {
            Skript.error("Cannot use the former movement expression outside of a on any movement event", ErrorQuality.SEMANTIC_ERROR);
            return false;
        }
        return true;
    }
}