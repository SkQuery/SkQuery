package com.skquery.skquery.elements.conditions;

import org.bukkit.Location;
import org.bukkit.WorldBorder;
import org.bukkit.event.Event;

import com.skquery.skquery.annotations.Description;
import com.skquery.skquery.annotations.Patterns;

import ch.njol.skript.conditions.base.PropertyCondition;
import ch.njol.skript.conditions.base.PropertyCondition.PropertyType;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Name("Within Border")
@Description("Checks whether or not a location is within the WorldBorder.")
@Patterns({"%locations% is [with]in [world[ ]border[s]] %worldborder%", "%locations% (isn't|is not) [with]in [world[ ]border[s]] %worldborder%"})
public class CondWithinBorder extends Condition {

	private Expression<WorldBorder> worldBorder;
	private Expression<Location> locations;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		locations = (Expression<Location>) expressions[0];
		worldBorder = (Expression<WorldBorder>) expressions[1];
		setNegated(matchedPattern == 1);
		return true;
	}

	@Override
	public boolean check(Event event) {
		WorldBorder border = worldBorder.getSingle(event);
		if (border == null)
			return false;
		return locations.check(event, location -> border.isInside(location), isNegated());
	}

	@Override
	public String toString(Event event, boolean debug) {
		return PropertyCondition.toString(this, PropertyType.CAN, event, debug, locations, " are within border " + worldBorder.toString(event, debug));
	}

}
