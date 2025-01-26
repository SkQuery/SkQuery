package com.skquery.skquery.elements.effects;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.skquery.skquery.annotations.Description;
import com.skquery.skquery.annotations.Patterns;

import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Name("Make Sleep")
@Description("Attempts to make the player sleep at the given location.")
@Patterns("(make|force) %player% [to] sleep at %location%")
public class EffMakeSleep extends Effect {

	private Expression<Location> location;
	private Expression<Player> player;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		location = (Expression<Location>) expressions[1];
		player = (Expression<Player>) expressions[0];
		return true;
	}

	@Override
	protected void execute(Event event) {
		if (player == null || location == null)
			return;
		player.getSingle(event).sleep(location.getSingle(event), true);
	}

	@Override
	public String toString(Event event, boolean debug) {
		if (debug)
			return "force sleep";
		return "force sleep for " + player.toString(event, debug) + " at " + location.toString(event, debug);
	}

}
