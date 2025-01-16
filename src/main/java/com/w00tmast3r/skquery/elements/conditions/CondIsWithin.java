package com.w00tmast3r.skquery.elements.conditions;

import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

import com.w00tmast3r.skquery.annotations.Description;
import com.w00tmast3r.skquery.annotations.Patterns;
import com.w00tmast3r.skquery.util.region.CuboidRegion;

import org.bukkit.Location;
import org.bukkit.event.Event;

@Name("Is Within Cuboid")
@Description("Checks whether or not a certain location is included in a 3d cube with 2 endpoints.")
@Patterns({"%locations% (is|are) within %location% (to|and) %location%", "%locations% (are|is)(n't| not) within %location% (to|and) %location%"})
public class CondIsWithin extends Condition {

	private Expression<Location> locations, pos1, pos2;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		locations = (Expression<Location>) expressions[0];
		pos1 = (Expression<Location>) expressions[1];
		pos2 = (Expression<Location>) expressions[2];
		setNegated(matchedPattern == 1);
		return true;
	}

	@Override
	public boolean check(Event event) {
		Location p1 = pos1.getSingle(event);
		Location p2 = pos2.getSingle(event);
		return locations.check(event, (location) -> {
				if (location == null || p1 == null || p2 == null)
					return false;
				return new CuboidRegion(p1, p2).checkHas(location.toVector());
		}, isNegated());
	}

	@Override
	public String toString(Event event, boolean debug) {
		return locations.toString(event, debug) + " are within cuboid from " + pos1.toString(event, debug) + " to " + pos2.toString(event, debug);
	}

}
