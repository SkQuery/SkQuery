package com.skquery.skquery.elements.effects;

import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.util.Vector;

import com.skquery.skquery.annotations.Description;
import com.skquery.skquery.annotations.Examples;
import com.skquery.skquery.annotations.Patterns;

@Name("Entity Homing")
@Description("Cause an entity to home towards a locations. Specifying 'normally' reduces bugs caused by varying distances, but makes it less accurate.")
@Examples("make targeted entity home towards player normally")
@Patterns({"make %entity% home towards %location%", "make %entities% home towards %location% normally"})
public class EffHoming extends Effect {

	private Expression<Entity> entities;
	private Expression<Location> location;
	private boolean isNormal;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		entities = (Expression<Entity>) expressions[0];
		location = (Expression<Location>) expressions[1];
		isNormal = matchedPattern == 1;
		return true;
	}
	
	@Override
	protected void execute(Event event) {
		Location loc = location.getSingle(event);
		if (loc == null)
			return;
		for (Entity entity : entities.getArray(event)) {
			Vector vector = loc.toVector().subtract(entity.getLocation().toVector());
			entity.setVelocity(isNormal ? vector.normalize() : vector);
		}
	}

	@Override
	public String toString(Event event, boolean debug) {
		return "home from " + entities.toString(event, debug) + " to " + location.toString(event, debug);
	}

}
