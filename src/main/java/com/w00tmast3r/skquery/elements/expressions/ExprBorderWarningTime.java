package com.w00tmast3r.skquery.elements.expressions;

import org.bukkit.WorldBorder;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import com.w00tmast3r.skquery.annotations.PropertyFrom;
import com.w00tmast3r.skquery.annotations.PropertyTo;
import com.w00tmast3r.skquery.annotations.UsePropertyPatterns;
import com.w00tmast3r.skquery.util.Collect;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.util.Timespan;

@UsePropertyPatterns
@PropertyFrom("worldborders")
@PropertyTo("[world[ ]border[s]] warning time")
public class ExprBorderWarningTime extends SimplePropertyExpression<WorldBorder, Number> {

	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}

	@Override
	protected String getPropertyName() {
		return "warning time";
	}

	@Override
	@Nullable
	public Number convert(WorldBorder border) {
		return border.getWarningTime();
	}

	@Override
	public Class<?>[] acceptChange(ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.REMOVE || mode == ChangeMode.ADD)
			return Collect.asArray(Timespan.class);
		return null;
	}

	@Override
	public void change(Event event, Object[] delta, ChangeMode mode) {
		if (delta[0] == null)
			return;
		Timespan timespan = (Timespan) delta[0];
		Number time = timespan.getAs(Timespan.TimePeriod.MILLISECOND) / 1000L;
		switch (mode) {
			case SET:
				for (WorldBorder border : getExpr().getArray(event))
					border.setWarningTime(time.intValue());
				break;
			case ADD:
				for (WorldBorder border : getExpr().getArray(event)) {
					int warning = border.getWarningTime();
					border.setWarningTime(warning + time.intValue());
				}
				break;
			case REMOVE:
				for (WorldBorder border : getExpr().getArray(event)) {
					int warning = border.getWarningTime();
					border.setWarningTime(warning - time.intValue());
				}
				break;
			case REMOVE_ALL:
			case DELETE:
			case RESET:
			default:
				break;
		}
	}

}
