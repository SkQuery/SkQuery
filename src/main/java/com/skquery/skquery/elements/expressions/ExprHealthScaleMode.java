package com.skquery.skquery.elements.expressions;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.expressions.base.SimplePropertyExpression;

import com.skquery.skquery.annotations.PropertyFrom;
import com.skquery.skquery.annotations.PropertyTo;
import com.skquery.skquery.annotations.UsePropertyPatterns;
import com.skquery.skquery.util.Collect;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;


@UsePropertyPatterns
@PropertyFrom("players")
@PropertyTo("scaled health (state|ability|mode)")
public class ExprHealthScaleMode extends SimplePropertyExpression<Player, Boolean> {

	@Override
	protected String getPropertyName() {
		return "scaled health state";
	}

	@Override
	public Boolean convert(Player player) {
		return player.isHealthScaled();
	}

	@Override
	public Class<? extends Boolean> getReturnType() {
		return Boolean.class;
	}

	@Override
	public Class<?>[] acceptChange(ChangeMode mode) {
		if (mode == ChangeMode.RESET || mode == ChangeMode.SET) return Collect.asArray(Boolean.class);
		return null;
	}

	@Override
	public void change(Event e, Object[] delta, ChangeMode mode) {
		boolean b = delta != null && (Boolean) delta[0];
		switch (mode) {
			case SET:
				for (Player p : getExpr().getAll(e))  {
					p.setHealthScaled(b);
				}
				break;
			case RESET:
				for (Player p : getExpr().getAll(e))  {
					p.setHealthScaled(false);
				}
			case ADD:
			case REMOVE:
			case REMOVE_ALL:
			case DELETE:
				assert false;
		}
	}

}
