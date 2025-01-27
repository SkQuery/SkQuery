package com.skquery.skquery.elements.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

import com.skquery.skquery.annotations.Patterns;
import com.skquery.skquery.skript.LambdaCondition;
import com.skquery.skquery.util.Collect;

import org.bukkit.event.Event;

@Patterns("check[ed] %predicate%")
public class ExprCheck extends SimpleExpression<Boolean> {

    private Expression<LambdaCondition> lambda;

    @Override
    protected Boolean[] get(Event e) {
        LambdaCondition l = lambda.getSingle(e);
        if (l == null) return Collect.asArray(false);
        return Collect.asArray(l.check(e));
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
        return "check lambda";
    }

    @SuppressWarnings("unchecked")
	@Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        lambda = (Expression<LambdaCondition>) exprs[0];
        return true;
    }
}
