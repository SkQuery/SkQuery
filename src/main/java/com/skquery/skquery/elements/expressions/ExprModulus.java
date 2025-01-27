package com.skquery.skquery.elements.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

import com.skquery.skquery.annotations.Patterns;
import com.skquery.skquery.util.Collect;

import org.bukkit.event.Event;


@Patterns("%number% mod %number%")
public class ExprModulus extends SimpleExpression<Number> {

    private Expression<Number> first, second;

    @Override
    protected Number[] get(Event event) {
        Number f = first.getSingle(event);
        Number s = second.getSingle(event);
        if (f == null || s == null) return null;
        return Collect.asArray(f.floatValue() % s.floatValue());
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "maths";
    }

    @SuppressWarnings("unchecked")
	@Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        first = (Expression<Number>) expressions[0];
        second = (Expression<Number>) expressions[1];
        return true;
    }
}
