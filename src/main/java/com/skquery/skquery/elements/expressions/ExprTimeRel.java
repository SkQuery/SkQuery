package com.skquery.skquery.elements.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.Time;
import ch.njol.util.Kleenean;

import com.skquery.skquery.annotations.Patterns;
import com.skquery.skquery.util.Collect;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@Patterns({"(relative|player) time of %player% [with relative %-boolean%]", "%player%'s (relative|player) time [with relative %-boolean%]"})
public class ExprTimeRel extends SimpleExpression<Time> {

	private Expression<Player> player;
	private Expression<Boolean> relative;

	@Override
	protected Time[] get(Event event) {
		return new Time[]{new Time(((Number)player.getSingle(event).getPlayerTime()).intValue())};
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends Time> getReturnType() {
		return Time.class;
	}

	@Override
	public String toString(Event event, boolean b) {
		return "rel time";
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		player = (Expression<Player>) expressions[0];
		relative = (Expression<Boolean>) expressions[1];
		return true;
	}

	@Override
	public Class<?>[] acceptChange(Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.DELETE || mode == Changer.ChangeMode.SET) return Collect.asArray(Time[].class);
		return null;
	}

	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET) {
			Boolean rel = relative == null ? true : relative.getSingle(e);
			Time n = delta[0] == null ? new Time() : (Time) delta[0];
			if (n != null) {
				for (Player p : player.getAll(e))  {
					p.setPlayerTime(n.getTicks(), rel);
				}
			}
		} else if (mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.DELETE) {
			for (Player p : player.getAll(e))  {
				p.resetPlayerTime();
			}
		}
	}
}
