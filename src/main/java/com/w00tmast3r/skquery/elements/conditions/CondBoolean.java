package com.w00tmast3r.skquery.elements.conditions;

import org.bukkit.event.Event;

import com.w00tmast3r.skquery.annotations.Patterns;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Patterns("%booleans%")
public class CondBoolean extends Condition {

	private Expression<Boolean> condition;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		condition = (Expression<Boolean>) expressions[0];
		return true;
	}

	@Override
	public boolean check(Event event) {
		for (Boolean b : condition.getAll(event))
			if (b)
				return true;
		return false;
	}

	@Override
	public String toString(Event event, boolean debug) {
		return "Boolean condition";
	}

}
