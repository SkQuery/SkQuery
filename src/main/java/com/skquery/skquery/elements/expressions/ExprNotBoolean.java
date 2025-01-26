package com.skquery.skquery.elements.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

import com.skquery.skquery.annotations.Patterns;
import com.skquery.skquery.util.Collect;

import org.bukkit.event.Event;

@Patterns("(!|not )%boolean%")
public class ExprNotBoolean extends SimpleExpression<Boolean> {

    private Expression<Boolean> toReverse;

    @Override
    protected Boolean[] get(Event e) {
        return Collect.asArray(toReverse.getSingle(e) == null || !toReverse.getSingle(e));
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "reverse boolean";
    }

    @SuppressWarnings("unchecked")
	@Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        toReverse = (Expression<Boolean>) exprs[0];
        return true;
    }
}
