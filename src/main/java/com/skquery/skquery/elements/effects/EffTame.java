package com.skquery.skquery.elements.effects;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.event.Event;

import com.skquery.skquery.annotations.Patterns;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Patterns("tame %entities% to %player%")
public class EffTame extends Effect {

	private Expression<Entity> entities;
	private Expression<Player> tamer;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expressions, int markedPattern, Kleenean kleenean, ParseResult parseResult) {
		entities = (Expression<Entity>) expressions[0];
		tamer = (Expression<Player>) expressions[1];
		return true;
	}

	@Override
	protected void execute(Event event) {
		Player player = tamer.getSingle(event);
		if (player == null)
			return;
		for (Entity entity : entities.getArray(event)) {
			if (!(entity instanceof Tameable))
				continue;
			((Tameable) entity).setOwner(player);
		}
	}

	@Override
	public String toString(Event event, boolean debug) {
		return "tame " + entities.toString(event, debug) + " to " + tamer.toString(event, debug);
	}

}
