package com.skquery.skquery.elements.expressions;

import org.bukkit.WorldBorder;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import com.skquery.skquery.annotations.PropertyFrom;
import com.skquery.skquery.annotations.PropertyTo;
import com.skquery.skquery.annotations.UsePropertyPatterns;
import com.skquery.skquery.util.Collect;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.expressions.base.SimplePropertyExpression;

@UsePropertyPatterns
@PropertyFrom("worldborders")
@PropertyTo("[world[ ]border[s]] [damage] buffer")
public class ExprBorderDamageBuffer extends SimplePropertyExpression<WorldBorder, Number> {

	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}

	@Override
	protected String getPropertyName() {
		return "buffer";
	}

	@Override
	@Nullable
	public Number convert(WorldBorder border) {
		return border.getDamageBuffer();
	}

	@Override
	public Class<?>[] acceptChange(ChangeMode mode) {
		if (mode == ChangeMode.SET)
			return Collect.asArray(Number.class);
		return null;
	}

	@Override
	public void change(Event event, Object[] delta, ChangeMode mode) {
		if (delta[0] == null)
			return;
		Number amount = (Number) delta[0];
		for (WorldBorder border : getExpr().getArray(event))
			border.setDamageBuffer(amount.doubleValue());
	}

}
