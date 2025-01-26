package com.skquery.skquery.elements.expressions;

import org.bukkit.Location;
import org.bukkit.event.Event;

import com.skquery.skquery.annotations.Patterns;
import com.skquery.skquery.util.Collect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.Direction;
import ch.njol.util.Kleenean;

@Patterns("direction from %location% to %location%")
public class ExprDirectionFromTo extends SimpleExpression<Direction> {

	private Expression<Location> from, to;

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends Direction> getReturnType() {
		return Direction.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean kleenean, ParseResult parseResult) {
		from = (Expression<Location>) expressions[0];
		to = (Expression<Location>) expressions[1];
		return true;
	}

	@Override
	protected Direction[] get(Event event) {
		Location from = this.from.getSingle(event);
		Location to = this.to.getSingle(event);
		if (from == null || to == null)
			return null;
		return Collect.asArray(new Direction(to.toVector().subtract(from.toVector())));
	}

	@Override
	public String toString(Event event, boolean debug) {
		if (event == null || debug)
			return "direction from";
		return "direction from " + from.toString(event, debug) + " to " + to.toString(event, debug);
	}

}
