package com.w00tmast3r.skquery.elements.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.expressions.base.SimplePropertyExpression;

import com.w00tmast3r.skquery.annotations.PropertyFrom;
import com.w00tmast3r.skquery.annotations.PropertyTo;
import com.w00tmast3r.skquery.annotations.UsePropertyPatterns;
import com.w00tmast3r.skquery.util.Collect;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@UsePropertyPatterns
@PropertyFrom("players")
@PropertyTo("scaled health")
public class ExprHealthScale extends SimplePropertyExpression<Player, Number> {

	@Override
	protected String getPropertyName() {
		return "scaled health";
	}

	@Override
	public Number convert(Player player) {
		return player.getHealthScale();
	}

	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}

	@Override
	public Class<?>[] acceptChange(Changer.ChangeMode mode) {
		if (mode == ChangeMode.RESET || mode == ChangeMode.SET) return Collect.asArray(Number.class);
		return null;
	}

	@Override
	public void change(Event e, Object[] delta, ChangeMode mode) {
		Number n = delta[0] == null || ((Number) delta[0]).doubleValue() < 0 ? 1 : (Number) delta[0] ;
		switch (mode) {
			case SET:
				for (Player p : getExpr().getAll(e))  {
					p.setHealthScale(n.doubleValue());
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
